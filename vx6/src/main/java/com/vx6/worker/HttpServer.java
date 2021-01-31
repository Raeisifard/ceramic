package com.vx6.worker;

import com.ceramic.core.shared.ShareableRouter;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.healthchecks.HealthChecks;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.sockjs.SockJSBridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;

public class HttpServer extends AbstractVerticle {
  private static final Logger log = LoggerFactory.getLogger(HttpServer.class);
  private static HealthChecks healthChecks;
  @Override
  public void start(Promise<Void> startPromise){
    healthChecks = HealthChecks.create(vertx);
    vertx.eventBus().consumer("vx6.main.verticle.health",
            message -> healthChecks.checkStatus()
                    .onSuccess(res -> message.reply("OK"))
                    .onFailure(err -> message.fail(0, err.getMessage())));

    io.vertx.core.http.HttpServer server = vertx.createHttpServer();
    SockJSHandler sockJSHandler = SockJSHandler.create(vertx);

    SockJSBridgeOptions options = new SockJSBridgeOptions()
            .addOutboundPermitted(new PermittedOptions().setAddressRegex("^vx\\.mx(\\.\\w+)+"))
            .addInboundPermitted(new PermittedOptions().setAddress("mx.vx"))
            .addInboundPermitted(new PermittedOptions().setAddress("gateway"))
            .addOutboundPermitted(new PermittedOptions().setAddress("vx.mx"))
            .addInboundPermitted(new PermittedOptions().setAddressRegex("mx.vx\\..+"))
            .addOutboundPermitted(new PermittedOptions().setAddressRegex("vx.mx\\..+"));

    final Router main = ShareableRouter.router(vertx);
    main.mountSubRouter("/eventbus", sockJSHandler.bridge(options));
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
