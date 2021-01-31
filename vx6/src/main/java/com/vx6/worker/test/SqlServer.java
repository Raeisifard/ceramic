package com.vx6.worker.test;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLClient;

import java.io.PrintWriter;
import java.io.StringWriter;

public class SqlServer extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    EventBus eb = vertx.eventBus();
    eb.consumer("mx.vx.test.sqlserver", this::processMessage);
    startPromise.complete();
  }

  private <T> void processMessage(Message<T> tMessage) {
    JsonObject config = (JsonObject) tMessage.body();
    String ip = config.getString("ip");
    Integer port = config.getInteger("port");
    String userName = config.getString("user");
    String password = config.getString("pass");
    String dbName = config.getString("dbName");
    String url = "jdbc:sqlserver://" + ip + ":" + port + ";SelectMethod=cursor;DatabaseName=" + dbName;
    JsonObject clientConfig = new JsonObject()
      .put("url", url)
      .put("driver_class", "com.microsoft.sqlserver.jdbc.SQLServerDriver")
      .put("user", userName)
      .put("password", password)
      .put("max_pool_size", 30);
    DeliveryOptions opt = new DeliveryOptions();
    SQLClient client = JDBCClient.createShared(vertx, clientConfig);
    client.getConnection(res -> {
      if (res.succeeded()) {
        opt.addHeader("ip", ip)
          .addHeader("port", port + "")
          .addHeader("dbName", dbName)
          .addHeader("dbType", "sqlServer");
        res.result().close();
        tMessage.reply("{\"testStatus\": \"ok\"}", opt);
      } else {
        opt.addHeader("cause", res.cause().getMessage()).addHeader("stack_trace", exceptionAsString(res.cause()));
        tMessage.fail(0, "{\"testStatus\": \"failed\"}");
      }
      client.close();
    });
  }

  private String exceptionAsString(Throwable e) {
    StringWriter sw = new StringWriter();
    e.printStackTrace(new PrintWriter(sw));
    return sw.toString();
  }
}

