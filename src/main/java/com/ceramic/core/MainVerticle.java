package com.ceramic.core;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) {
        JsonArray verticleList = config().getJsonArray("verticles");
        if (config().containsKey("default")) {
            var def = config().getJsonObject("default");
            if (def.size() > 0)
                verticleList.forEach(jo -> {
                    JsonObject vert = (JsonObject) jo;
                    var opt = vert.getJsonObject("options");
                    if (opt.containsKey("config")) {
                        var cnf = opt.getJsonObject("config");
                        cnf.forEach(prop -> {
                            if (prop.getValue().toString().equalsIgnoreCase("default")) {
                                //cnf.remove(prop.getKey());
                                cnf.put(prop.getKey(), def.getValue(prop.getKey()));
                            }
                        });
                    }
                });
        }
        vertx.deployVerticle(DeployerVerticle.class.getName(),
                new DeploymentOptions().setConfig(new JsonObject().put("verticles", verticleList)), res -> {
                    if (res.succeeded())
                        startPromise.complete();
                    else
                        startPromise.fail(res.cause());
                });
    }
}
