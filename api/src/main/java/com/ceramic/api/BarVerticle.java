package com.ceramic.api;

import com.ceramic.core.shared.ShareableRouter;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.healthchecks.HealthChecks;
import io.vertx.ext.web.Router;

public class BarVerticle extends AbstractVerticle {

  private static final Logger log = LoggerFactory.getLogger(BarVerticle.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    log.info("Starting verticle {" + this + "}");
    HealthChecks healthChecks = HealthChecks.create(vertx);
    HttpServer server = vertx.createHttpServer();
    vertx.eventBus().consumer("bar.verticle.health",
      message -> healthChecks.checkStatus()
        .onSuccess(res -> message.reply("OK"))
        .onFailure(err -> message.fail(0, err.getMessage())));
    final Router router = Router.router(vertx);
    router.route("/bar").handler(rc -> {
      rc.response().putHeader("ContentType", "text/html")
        .end("<html><body><strong>bar</strong></body></html>");
    });
    final Router main = ShareableRouter.router(vertx);
    main.mountSubRouter("/1", router);

    // start server
    server.requestHandler(main).listen(config().getInteger("http-port"), lh -> {
      if (lh.failed()) {
        System.out.println("bar failed");
        startPromise.fail(lh.cause());
      } else {
        startPromise.complete();
      }
    });
    //startPromise.complete();
    Future<String> future = DoSomthingLazy("Encripted Message", 5);
    System.out.println("Future initiated");
    future.onComplete(res -> {
      System.out.println("Future completed with res: " + res.result());
    });
  }

  private Future<String> DoSomthingLazy(String encripted_message, int i) {
    Promise<String> promise = Promise.promise();
    vertx.setTimer(i * 1000, id -> {
      System.out.println("And " + i + " second later " + encripted_message + " is printed");
      promise.complete(encripted_message + " returned back!!!!");
    });
    return promise.future();
  }
}
