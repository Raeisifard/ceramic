package com.ceramic.api;

import com.ceramic.core.shared.ShareableRouter;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.healthchecks.HealthChecks;
import io.vertx.ext.web.Router;

public class FooVerticle extends AbstractVerticle {

  private static final Logger log = LoggerFactory.getLogger(FooVerticle.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    log.info("Starting verticle {" + this + "}");
    HealthChecks healthChecks = HealthChecks.create(vertx);
    HttpServer server = vertx.createHttpServer();
    vertx.eventBus().consumer("foo.verticle.health",
      message -> healthChecks.checkStatus()
        .onSuccess(res -> message.reply("OK"))
        .onFailure(err -> message.fail(0, err.getMessage())));
    final Router router = Router.router(vertx);
    router.route("/foo").handler(rc -> {
      rc.response().putHeader("ContentType", "text/html")
        .end("<html><body><strong>foo</strong></body></html>");
    });
    final Router main = ShareableRouter.router(vertx);
    main.mountSubRouter("/1", router);
    // start server
    server.requestHandler(main).listen(config().getInteger("http-port"), lh -> {
      if (lh.failed()) {
        System.out.println("foo failed");
        startPromise.fail(lh.cause());
      } else {
        startPromise.complete();
      }
    });
    //startPromise.complete();
  }

}
