package com.ceramic.api;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sse.EventBusSSEBridge;

import java.util.Date;
import java.util.UUID;

public class SseSunyVerticle extends AbstractVerticle {
  private final static int PORT = 9001;
  private final static String retry = "5000";
  private final static Logger LOG = LoggerFactory.getLogger(SseSunyVerticle.class.getName());
  private final static String EB_ADDRESS = "sse.test";

  private HttpServer server;
  private final StaticHandler staticFiles = StaticHandler.create();
  //private final SSEHandler sse = SSEHandler.create();
  private final EventBusSSEBridge eventBusSSEBridge = EventBusSSEBridge.create();
  private Long timerId;
  private final String magicToken = "theOneThatRulesThemAll";
private final Router router = Router.router(vertx);
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    //var router = Router.router(vertx);
    router.get("/").handler(rc -> rc.reroute("/static/index.html"));
    router.get("/static/*").handler(staticFiles);
    eventBusSSEBridge.mapping(request ->
    {
      var bnk_name = request.getParam("bnk_name");
      var use = request.getParam("user");
      var pass = request.getParam("pass");
      return "sse.bsi.behnam";
    });
    router.get("/sse/:bnk_name/:user/:pass/:last_event_id").handler(eventBusSSEBridge);
    //router.get("/sse/test").handler(sse);
    eventBusSSEBridge.connectHandler(sseConnection -> {
      HttpServerRequest request = sseConnection.request();
      String token = request.getParam("authenticationToken");
      if (token == null) {
        sseConnection.reject(401);
      } else if (!magicToken.equals(token)) {
        sseConnection.reject(403);
      }

      System.out.println("Last Id: " + sseConnection.lastId());
    });
    server = vertx.createHttpServer();
    vertx.setPeriodic(5000, this::fetchISSPosition);
    server.requestHandler(router);
    server.listen(PORT, http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port " + PORT);
      } else {
        startPromise.fail(http.cause());
      }
    });
  }

  private void fetchISSPosition(Long timerId) {
    this.timerId = timerId;
    DeliveryOptions dO = new DeliveryOptions();
    var uuid = UUID.randomUUID().toString();
    dO.addHeader("event", "statement").addHeader("retry", retry).addHeader("id", uuid);
    var date = new Date().toString();
    System.out.println(date + ":" + uuid);
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
