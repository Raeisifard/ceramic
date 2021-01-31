package com.ceramic.api;

import com.ceramic.core.shared.ShareableRouter;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.sse.EventBusSSEBridge;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.UUID;

public class FreeboardApiVerticle extends AbstractVerticle {
  private int http_port;
  private Long timerId;
  private HttpServer server;
  private int retry = 5000;
  private String EB_ADDRESS = "sse.freeboard";
  private final EventBusSSEBridge eventBusSSEBridge = EventBusSSEBridge.create();
  private final static Logger LOG = LoggerFactory.getLogger(FreeboardApiVerticle.class.getName());
  private final Router router = Router.router(vertx);
  private EventBus eb;

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    LOG.info("Starting verticle {" + this + "}");
    this.eb = vertx.eventBus();
    this.EB_ADDRESS = config().getString("eb_address");
    this.retry = config().getInteger("retry");
    this.http_port = config().getInteger("http-port");
    eventBusSSEBridge.mapping(request -> EB_ADDRESS);
    //final Router router = Router.router(vertx);
    //router.route("/sse").handler(eventBusSSEBridge);
    router.get("/").handler(rc -> rc.response().setStatusCode(302).putHeader("location", config().getString("location")).end());
    router.get("/sse/*").handler(EventBusSSEBridge.create());
    router.get("/:req").handler(ctx -> {
      var body = new JsonObject();
      var address = "";
      try {
        body = new JsonObject(ctx.request().getParam("body"));
      } catch (Exception ignored) {
      }
      try {
        address = ctx.request().getParam("address");
      } catch (Exception ignored) {
      }
      if (body.isEmpty() || StringUtils.isEmpty(address)) {
        ctx.response().setStatusCode(400).end();
      } else {
        String req = ctx.pathParam("req");
        switch (req){
          case "msg":
            eb.publish(address,body);
            ctx.response().setStatusCode(200).end("OK");
            break;
          case "cmd":
            eb.request(address, body, res -> {
              if (res.succeeded())
                ctx.response().setStatusCode(200).end(res.result().body().toString());
              else
                ctx.response().setStatusCode(500).end(res.cause().getMessage());
            });
            break;
          default:
            ctx.response().setStatusCode(400).end();
        }
      }

    });
    final Router main = ShareableRouter.router(vertx);
    main.mountSubRouter("/freeboard", router);
    if (config().getBoolean("server")) {
      // start server
      this.server = vertx.createHttpServer().requestHandler(main).listen(this.http_port, http -> {
        if (http.succeeded()) {
          vertx.setPeriodic(5000, this::fetchISSPosition);
          startPromise.complete();
          System.out.println("HTTP server started on port " + this.http_port);
        } else {
          startPromise.fail(http.cause());
        }
      });
    } else {
      startPromise.complete();
    }
  }

  private void fetchISSPosition(Long timerId) {
    this.timerId = timerId;
    DeliveryOptions dO = new DeliveryOptions();
    var uuid = UUID.randomUUID().toString();
    dO.addHeader("event", "statement").addHeader("retry", this.retry + "").addHeader("id", uuid);
    //var date = new Date().toString();
    //System.out.println(date + ":" + uuid);
    var msgTest = new JsonObject().put("data", new Date().toString()).put("id", uuid);
    vertx.eventBus().publish(EB_ADDRESS, msgTest, dO);
  }

  @Override
  public void stop(Promise<Void> stopPromise) {
    if (timerId != null) {
      vertx.cancelTimer(timerId);
    }
    if (this.server != null) {
      this.server.close();
    }
    router.clear();
    stopPromise.complete();
  }
}
