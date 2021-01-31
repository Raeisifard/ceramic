package com.ceramic.core;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Launcher extends io.vertx.core.Launcher {
    private Vertx vertx = null;

    // Main

    public static void main(String[] args) {
        new Launcher().dispatch(args);
    }

    // Constants

    private static final String configPath = "config.json";

    // Overrides
    @Override
    public void beforeStartingVertx(VertxOptions options) {
        //System.out.println("vertx options: " + options.toJson().encodePrettily());
    }

    @Override
    public void afterStartingVertx(Vertx vertx) {
        this.vertx = vertx;
    }

    @Override
    public void beforeDeployingVerticle(DeploymentOptions deploymentOptions) {
        System.out.println("deploy options: " + deploymentOptions.toString());
        super.beforeDeployingVerticle(deploymentOptions);

        defaultOptions(deploymentOptions);
        if (deploymentOptions.getConfig().isEmpty())
            try {
                readConfig(deploymentOptions);
            } catch (FileNotFoundException e) {
                System.out.println("Unable to read config: " + e.getMessage());
            }
    }

    // Private

    private void defaultOptions(DeploymentOptions options) {
        if (options.getConfig() != null) return;

        options.setConfig(new JsonObject());
    }

    private void readConfig(DeploymentOptions options) throws FileNotFoundException {
        File configFile = new File(configPath);
        JsonObject config = getConfig(configFile);
        options.getConfig().mergeIn(config);
        retrieveConfig();
    }

    private void retrieveConfig() {
        ConfigRetriever newRetriever = ConfigRetriever.create(this.vertx, new ConfigRetrieverOptions()
                .addStore(new ConfigStoreOptions().setType("file").setConfig(new JsonObject().put("path", configPath))));

        newRetriever.listen(change -> {
            JsonObject prevJson = change.getPreviousConfiguration();
            JsonObject newJson = change.getNewConfiguration();
            if (prevJson.isEmpty() || newJson.isEmpty())
                return;
            this.vertx.eventBus().publish("new-configuration", newJson);
            System.out.println("Config changed:" + newJson.toString());
        });
    }

    private JsonObject getConfig(File config) throws FileNotFoundException {
        if (!config.isFile()) return new JsonObject();

        Scanner scanner = new Scanner(config).useDelimiter("\\A");
        String confStr = scanner.next();

        return new JsonObject(confStr);
    }
}
