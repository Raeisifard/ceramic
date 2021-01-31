package com.ceramic.core;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;

import java.util.concurrent.atomic.AtomicReference;

public class StartConfig {
    public static void main(String[] args) {
        AtomicReference<Vertx> vertx = com.ceramic.core.Vertx.vertx;
        // Create the config retriever
        ConfigRetriever retriever = ConfigRetriever.create(vertx.get(), new ConfigRetrieverOptions()
                .addStore(new ConfigStoreOptions().setType("file").setConfig(new JsonObject().put("path", "config.json"))));

        // Retrieve the configuration
        retriever.getConfig(cnf -> {
            JsonObject result = cnf.result();
            System.out.println("First Config:" + result.toString());
            // Close the vert.x instance, we don't need it anymore.
            retriever.close();
            Future<Void> future = vertx.get().close();
            future.onComplete(ar -> {
                // Create a new Vert.x instance using the retrieve configuration
                VertxOptions options = new VertxOptions(result);
                vertx.set(Vertx.vertx(options));
                ConfigRetriever newRetriever = ConfigRetriever.create(vertx.get(), new ConfigRetrieverOptions()
                        .addStore(new ConfigStoreOptions().setType("file").setConfig(new JsonObject().put("path", "config.json"))));

                Future<JsonObject> future1 = newRetriever.getConfig();
                future1.onComplete(ar1 -> {
                    if (ar1.failed()) {
                        System.out.println("Failed to retrieve the configuration!");
                    } else {
                        JsonObject config = ar1.result();
                        vertx.get().deployVerticle(MainVerticle.class.getName(), new DeploymentOptions().setConfig(config));
                    }
                });
                newRetriever.listen(change -> {
                    JsonObject json = change.getNewConfiguration();
                    vertx.get().eventBus().publish("new-configuration", json);
                    System.out.println("Config:" + json.toString());
                });
            });
        });
    }
}
