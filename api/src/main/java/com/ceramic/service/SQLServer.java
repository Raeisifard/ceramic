package com.ceramic.service;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.sql.UpdateResult;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class SQLServer extends AbstractVerticle {
  private EventBus eb;
  private SQLClient client;
  private String query, queryType;
  private boolean prepared = false;
  private JsonArray params;
  private String sendAddress, receiveAddress;
  private List<JsonObject> rows = null;
  private int No = 0, count = 0;
  private boolean autoNext = true;

  @Override
  public void start(Promise<Void> startPromise) {
    this.eb = vertx.eventBus();
    String ip = config().getString("ip");
    Integer port = config().getInteger("port");
    String userName = config().getString("user");
    String password = config().getString("password");
    String dbName = config().getString("dbName");
    String url = "jdbc:sqlserver://" + ip + ":" + port + ";SelectMethod=cursor;DatabaseName=" + dbName;
    JsonObject config = new JsonObject()
      .put("url", url)
      .put("driver_class", "com.microsoft.sqlserver.jdbc.SQLServerDriver")
      .put("user", userName)
      .put("password", password)
      .put("max_pool_size", 30);
    this.sendAddress = config().getString("sendAddress");
    this.receiveAddress = config().getString("receiveAddress");
    this.query = config().getString("query");
    this.queryType = config().getString("queryType", "select");
    this.prepared = config().getBoolean("prepared", false);
    this.params = config().getJsonArray("params", new JsonArray());
    this.autoNext = config().getBoolean("autoNext", true);
    this.client = JDBCClient.createShared(vertx, config);
    eb.consumer(receiveAddress, this::input);
    if (config().getBoolean("autoStart", true)) {
      eb.publish(this.receiveAddress, "", new DeliveryOptions().addHeader("cmd", "next"));
    }
    startPromise.complete();
  }

  private <T> void input(Message<T> tMessage) {
    var cmd = tMessage.headers().get("cmd");
    var body = new JsonObject();
    if (!tMessage.body().toString().isEmpty()) {
      body = new JsonObject(tMessage.body().toString());
    }
    if (StringUtils.isEmpty(cmd))
      cmd = body.getString("cmd");
    switch (cmd) {
      case "set":
        if (body.containsKey("query"))
          this.query = body.getString("query");
        if (body.containsKey("params"))
          this.params = body.getJsonArray("params");
        this.rows = null;
        if (this.queryType.equalsIgnoreCase("insert") ||
          this.queryType.equalsIgnoreCase("update") ||
          this.queryType.equalsIgnoreCase("delete"))
          executeQuery();
        break;
      case "next":
        this.query = body.containsKey("query") ? body.getString("query") : this.query;
        this.params = body.containsKey("params") ? body.getJsonArray("params") : this.params;
        executeQuery();
        break;
      case "start":
        this.start(Promise.promise());
        break;
      case "stop":
        this.stop(Promise.promise());
        break;
      default:
        break;
    }
  }

  private void executeQuery() {
    switch (this.queryType) {
      case "select":
        if (prepared) {
          queryWithParams();
        } else {
          query();
        }
        break;
      case "update":
      case "insert":
      case "delete":
        if (prepared) {
          updateWithParams();
        } else {
          update();
        }
        break;
    }
  }

  private void update() {
    client.getConnection(res -> {
      if (res.succeeded()) {
        SQLConnection connection = res.result();
        connection.update(query, res1 -> {
          if (res1.succeeded()) {
            UpdateResult result = res1.result();
            this.No = result.getUpdated() - 1;
            this.count = result.getKeys().size();
            publish(new JsonObject().put("keys", result.getKeys()));
          } else {
            res1.cause().printStackTrace();
          }
        });
      } else {
        res.cause().printStackTrace();
      }
    });
  }

  private void updateWithParams() {
    client.getConnection(res -> {
      if (res.succeeded()) {
        SQLConnection connection = res.result();
        connection.updateWithParams(query, params, res1 -> {
          if (res1.succeeded()) {
            UpdateResult result = res1.result();
            this.No = result.getUpdated() - 1;
            this.count = result.getKeys().size();
            publish(new JsonObject().put("keys", result.getKeys()));
          } else {
            res1.cause().printStackTrace();
          }
        });
      } else {
        res.cause().printStackTrace();
      }
    });
  }

  private void query() {
    if (this.rows == null || this.rows.isEmpty()) {
      client.getConnection(res -> {
        if (res.succeeded()) {
          SQLConnection connection = res.result();
          connection.query(query, res1 -> {
            if (res1.succeeded()) {
              ResultSet resultSet = res1.result();
              this.rows = resultSet.getRows();
              this.count = rows.size();
              if (rows.isEmpty()) {
                publish();
                return;
              }
              publish(rows.remove(0));
            } else {
              res1.cause().printStackTrace();
            }
          });
        } else {
          res.cause().printStackTrace();
        }
      });
    } else {
      publish(rows.remove(0));
    }
  }

  private void queryWithParams() {
    if (this.rows == null || this.rows.isEmpty()) {
      client.getConnection(res -> {
        if (res.succeeded()) {
          SQLConnection connection = res.result();
          connection.queryWithParams(query, params, res1 -> {
            if (res1.succeeded()) {
              ResultSet resultSet = res1.result();
              this.rows = resultSet.getRows();
              this.count = this.rows.size();
              if (rows.isEmpty()) {
                publish();
                return;
              }
              publish(rows.remove(0));
            } else {
              res1.cause().printStackTrace();
            }
          });
        } else {
          res.cause().printStackTrace();
        }
      });
    } else {
      publish(rows.remove(0));
    }
  }

  private void publish(JsonObject jo) {
    this.eb.publish(this.sendAddress, jo, new DeliveryOptions().addHeader("No", ++this.No + "").addHeader("count", count + ""));
    System.out.println("Publish: " + jo.toString());
    if (this.queryType.equalsIgnoreCase("select") && !this.rows.isEmpty() && this.autoNext) {
      eb.publish(this.receiveAddress, "", new DeliveryOptions().addHeader("cmd", "next"));
    } else {
      this.rows = null;
    }
  }

  private void publish() {
    this.eb.publish(this.sendAddress, new JsonObject(), new DeliveryOptions().addHeader("No", 0 + "").addHeader("count", count + ""));
  }

  @Override
  public void stop(Promise<Void> stopPromise) {
    stopPromise.complete();
  }
}
