package com.ceramic.api;

import com.ceramic.core.shared.ShareableRouter;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.healthchecks.HealthCheckHandler;
import io.vertx.ext.healthchecks.Status;
import io.vertx.ext.web.Router;

public class HealthCheckVerticle extends AbstractVerticle {
  private static final Logger log = LoggerFactory.getLogger(HealthCheckVerticle.class);
  private final Router router = Router.router(vertx);
  private HttpServer server;
  @Override
  public void start(Promise<Void> startPromise){
    log.info("Starting verticle {" + this + "}");
    HttpServer server = vertx.createHttpServer();
    HealthCheckHandler healthCheckHandler = HealthCheckHandler.create(vertx);
    healthCheckHandler.register(
      "status/eventbus-bridge-verticle",
      promise -> {
        vertx.eventBus().request("eventbus.bridge.verticle.health", "ping")
          .onSuccess(msg -> {
            promise.complete(Status.OK());
          })
          .onFailure(err -> {
            promise.complete(Status.KO());
          });
      });
    healthCheckHandler.register(
      "status/bar-verticle",
      promise -> {
        vertx.eventBus().request("bar.verticle.health", "ping")
          .onSuccess(msg -> {
            promise.complete(Status.OK());
          })
          .onFailure(err -> {
            promise.complete(Status.KO());
          });
      });
    healthCheckHandler.register(
      "status/foo-verticle",
      promise -> {
        vertx.eventBus().request("foo.verticle.health", "ping")
          .onSuccess(msg -> {
            promise.complete(Status.OK());
          })
          .onFailure(err -> {
            promise.complete(Status.KO());
          });
      });

    router.get("/*").handler(healthCheckHandler);
    final Router main = ShareableRouter.router(vertx);
    main.mountSubRouter("/health", router);
    // start server
    server.requestHandler(main).listen(config().getInteger("http-port"), lh -> {
      if (lh.failed()) {
        System.out.println("FreeboardEventBus failed");
        startPromise.fail(lh.cause());
      } else {
        startPromise.complete();
      }
    });
  }

  @Override
  public void stop(Promise<Void> stopPromise) {
  }
}
