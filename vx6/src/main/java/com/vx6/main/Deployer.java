package com.vx6.main;

import com.mxgraph.io.mxCodec;
import com.mxgraph.io.mxCodecRegistry;
import com.mxgraph.io.mxObjectCodec;
//import com.vx6.tool.VueComponent;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

//import com.isc.verticle.Website;

/*
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class Deployer extends AbstractVerticle {
  Logger logger = LoggerFactory.getLogger(this.getClass().getName());

  // Convenience method so you can run it in your IDE
  public static void main(String[] args) {
    /*Runner.runExample(Deployer.class);*/
  }

  @Override
  public void start() throws Exception {
    //mxCodecRegistry.addPackage("com.isc.verticle");
    mxCodecRegistry.addPackage("io.vertx.core.json");
    mxCodecRegistry.addPackage("com.isc.Object");
    mxCodecRegistry.addPackage("com.vx6.tool");
      /*mxCodecRegistry.register(new mxObjectCodec(new Website()) {
        public Object decode(mxCodec dec, Node node, Object into) {
          return new Website(node.getTextContent());
        }

        public Node encode(mxCodec enc, Object obj) {
          Document doc = enc.getDocument();
          Element elm = doc.createElement("Website");
          elm.setAttribute("as", "data");
          elm.setTextContent(obj.toString());
          return elm;
        }
      });*/
    mxCodecRegistry.register(new mxObjectCodec(new JsonObject()) {
      public Object decode(mxCodec dec, Node node, Object into) {
        return new JsonObject(node.getTextContent());
      }
      public Node encode(mxCodec enc, Object obj) {
        Document doc = enc.getDocument();
        Element elm = doc.createElement("JsonObject");
        elm.setAttribute("as", "data");
        elm.setTextContent(((JsonObject) obj).encode());
        return elm;
      }
    });
   /* mxCodecRegistry.register(new mxObjectCodec(new VueComponent()) {
      @Override
      public Object decode(mxCodec dec, Node node, Object into) {
        return new VueComponent(node.getAttributes().getNamedItem("name").getNodeValue(), node.getAttributes().getNamedItem("as").getNodeValue());
      }

      @Override
      public Node encode(mxCodec enc, Object obj) {
        VueComponent vueComponent = (VueComponent) obj;
        Document doc = enc.getDocument();
        Element elm = doc.createElement("VueComponent");
        elm.setAttribute("name", vueComponent.getName());
        elm.setAttribute("as", vueComponent.getAs());
        //elm.setTextContent(((JsonObject)obj).encode());
        return elm;
      }
    });*/
    //TODO set a set of log statements trough codes.
    System.out.println(config().toString());
    JsonArray verticleList = config().getJsonArray("verticles");
    //JsonArray skipDeloyList = config().getJsonArray("skipDeploy");
    Integer verticleListLength = verticleList.size();
    if (verticleListLength > 0) {//We have some "verticle" to deploy
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

              String deploymentID = res.result();

              System.out.println("BusVerticle \"" + vName + "\" deployed [OK], deploymentID = " + deploymentID);

            } else {
              res.cause().printStackTrace();
            }
          });
        }
      }
    }
  }

  // Different ways of deploying verticle

  // Deploy a verticle and don't wait for it to start
  //vertx.deployVerticle("com.sol.vertx.main.OtherVerticle");

  // Deploy another instance and  want for it to start
        /*vertx.deployVerticle("com.sol.vertx.main.OtherVerticle", res -> {
            if (res.succeeded()) {

                String deploymentID = res.result();

                System.out.println("Other verticle deployed ok, deploymentID = " + deploymentID);

                // You can also explicitly undeploy a verticle deployment.
                // Note that this is usually unnecessary as any verticle deployed by a verticle will be automatically
                // undeployed when the parent verticle is undeployed

                vertx.undeploy(deploymentID, res2 -> {
                    if (res2.succeeded()) {
                        System.out.println("Undeployed ok!");
                    } else {
                        res2.cause().printStackTrace();
                    }
                });

            } else {
                res.cause().printStackTrace();
            }
        });
*/
  // Deploy specifying some config
     /*   JsonObject config = new JsonObject().put("foo", "bar");
        vertx.deployVerticle("com.sol.vertx.main.OtherVerticle", new DeploymentOptions().setConfig(config));

        // Deploy 10 instances
        vertx.deployVerticle("com.sol.vertx.main.OtherVerticle", new DeploymentOptions().setInstances(10));

        // Deploy it as a worker verticle
        vertx.deployVerticle("com.sol.vertx.main.OtherVerticle", new DeploymentOptions().setWorker(true));

    }

    private JsonObject setConfig(JsonObject conf) {

    }*/

  private DeploymentOptions setDeploymentOptions(JsonObject conf) {
    DeploymentOptions dos = new DeploymentOptions();
    dos.setConfig(conf.getJsonObject("config") == null ? new JsonObject("{}") : conf.getJsonObject("config"));
    dos.setInstances(conf.getInteger("instances") == null ? 1 : conf.getInteger("instances"));
    dos.setWorker(conf.getBoolean("worker") == null ? false : conf.getBoolean("worker"));
    //dos.setIsolationGroup(conf.getString("isolationGroup") == null ? "" : conf.getString("isolationGroup"));
    return dos;
  }
}
