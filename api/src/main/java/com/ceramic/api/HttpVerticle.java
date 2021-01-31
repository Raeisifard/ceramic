package com.ceramic.api;

import com.ceramic.core.shared.ShareableRouter;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

public class HttpVerticle extends AbstractVerticle {
  private static final Logger log = LoggerFactory.getLogger(HttpVerticle.class);
  private final StaticHandler staticFiles = StaticHandler.create();
  private final Router router = Router.router(vertx);
  private HttpServer server;
  @Override
  public void start(Promise<Void> startPromise) {
    log.info("Starting verticle {" + this + "}");
    //create a router defining the endpoints of the service

    //router.get("/test").handler(ctx -> ctx.response().end("OK test"));
    //staticFiles.setIndexPage("/freeboard/index.html");
    //router.route("/lib/*").handler(StaticHandler.create("META-INF/resources/webjars"));
    /*<link rel='stylesheet' href='stt/lib/vertx__eventbus-bridge-client.js/1.0.0-1/vertx-eventbus.js'>*/
    router.get("/*").handler(staticFiles);
    //mount the router as subrouter to the shared router
    final Router main = ShareableRouter.router(vertx);
    main.mountSubRouter("/stt", router);
    //Create landing page.
    main.get("/").handler(rc -> rc.response().setStatusCode(302).putHeader("location", config().getString("location")).end());
    server = vertx.createHttpServer().requestHandler(main).listen(config().getInteger("http-port"), res -> {
      if (res.succeeded()) {
        startPromise.complete();
      } else {
        startPromise.fail(res.cause());
      }
    });
  }
  @Override
  public void stop(Promise<Void> stopPromise) {
    if (this.server != null) {
      this.server.close();
    }
    router.clear();
    stopPromise.complete();
  }
}
