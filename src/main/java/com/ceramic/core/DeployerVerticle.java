package com.ceramic.core;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class DeployerVerticle extends AbstractVerticle {
  Logger logger = LoggerFactory.getLogger(this.getClass().getName());

  // Convenience method so you can run it in your IDE
/*  public static void main(String[] args) {
    Runner.runExample(DeployerVerticle.class);
  }*/

  @Override
  public void start() throws Exception {
    if (config().containsKey("verticles")) {
      JsonArray verticleList = config().getJsonArray("verticles");
      int verticleListLength = verticleList.size();
      logger.info("We found (" + verticleListLength + ") verticles to process.");
      if (verticleListLength > 0) {//We have some "verticle" to deploy
        AtomicInteger deployedVerticles = new AtomicInteger();
        for (int i = 0; i < verticleListLength; i++) {
          JsonObject vrt = verticleList.getJsonObject(i);
          String vName = vrt.getString("main");
          String vId = vrt.getString("id");
          if (vName != null && vName.length() > 0 && vrt.getBoolean("deploy")) {
            DeploymentOptions dO = vrt.getJsonObject("options") == null ? new DeploymentOptions() : setDeploymentOptions(vrt.getJsonObject("options"));
            JsonObject config = dO.getConfig();
            config.put("_id", vId);
            dO.setConfig(config);
            vertx.deployVerticle(vName, dO, res -> {
              if (res.succeeded()) {
                deployedVerticles.getAndIncrement();
                String deploymentID = res.result();

                System.out.println("BusVerticle \"" + vName + "\" deployed [OK], deploymentID = " + deploymentID);

              } else {
                res.cause().printStackTrace();
              }
            });
          }
        }
        //TODO compose logger.info(deployedVerticles + " verticles deployed successfully.");
      }
    }else{
      System.out.println("There is no verticles in config file for deploy!");
    }
  }

  private DeploymentOptions setDeploymentOptions(JsonObject conf) {
    DeploymentOptions dos = new DeploymentOptions();
    dos.setConfig(conf.getJsonObject("config") == null ? new JsonObject("{}") : conf.getJsonObject("config"));
    dos.setInstances(conf.getInteger("instances") == null ? 1 : conf.getInteger("instances"));
    dos.setWorker(conf.getBoolean("worker") != null && conf.getBoolean("worker"));
    //dos.setIsolationGroup(conf.getString("isolationGroup") == null ? "" : conf.getString("isolationGroup"));
    return dos;
  }
}
