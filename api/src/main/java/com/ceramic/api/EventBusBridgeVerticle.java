package com.ceramic.api;

import com.ceramic.core.shared.ShareableRouter;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.healthchecks.HealthChecks;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.sockjs.SockJSBridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;

public class EventBusBridgeVerticle extends AbstractVerticle {
  private static final Logger log = LoggerFactory.getLogger(EventBusBridgeVerticle.class);
  private static HealthChecks healthChecks;
  @Override
  public void start(Promise<Void> startPromise){
    log.info("Starting verticle {" + this + "}");
    healthChecks = HealthChecks.create(vertx);
    vertx.eventBus().consumer("eventbus.bridge.verticle.health",
      message -> healthChecks.checkStatus()
        .onSuccess(res -> message.reply("OK"))
        .onFailure(err -> message.fail(0, err.getMessage())));
    String inboundAddress = config().getString("inboundAddress");
    String outboundAddress = config().getString("outboundAddress");
    HttpServer server = vertx.createHttpServer();
    Router router = Router.router(vertx);
    SockJSHandler sockJSHandler = SockJSHandler.create(vertx);
    PermittedOptions inboundPermitted = new PermittedOptions().setAddress(inboundAddress);
    PermittedOptions outboundPermitted = new PermittedOptions().setAddress(outboundAddress);
    SockJSBridgeOptions options = new SockJSBridgeOptions().
      addInboundPermitted(inboundPermitted).
      addOutboundPermitted(outboundPermitted);

    final Router main = ShareableRouter.router(vertx);
    main.mountSubRouter("/eb", router);
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
