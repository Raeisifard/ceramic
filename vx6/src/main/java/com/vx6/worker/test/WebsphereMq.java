package com.vx6.worker.test;

import com.ibm.mq.constants.CMQC;
import com.ibm.mq.constants.MQConstants;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import com.ibm.mq.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Hashtable;

public class WebsphereMq extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    EventBus eb = vertx.eventBus();
    eb.consumer("mx.vx.test.webspheremq", this::processMessage);
    startPromise.complete();
  }

  private <T> void processMessage(Message<T> tMessage) {
    final Hashtable<String, Object> mqProperties = new Hashtable<String, Object>();
    JsonObject config = (JsonObject) tMessage.body();
    String hostName = config.getString("ip");
    int port = config.getInteger("port");
    String userName = config.getString("user");
    String password = config.getString("pass");
    String qManagerName = config.getString("qm");
    String queueName = config.getString("qName");
    String channelName = config.getString("channelName");
    String mode = config.getString("mode");
    boolean readAhead = config.getBoolean("readAhead");
    String codePage = config.getString("codePage");
    mqProperties.put(MQConstants.HOST_NAME_PROPERTY, hostName);
    mqProperties.put(MQConstants.PORT_PROPERTY, port);
    mqProperties.put(MQConstants.USER_ID_PROPERTY, userName);
    mqProperties.put(MQConstants.PASSWORD_PROPERTY, password);
    mqProperties.put(MQConstants.CHANNEL_PROPERTY, channelName);
    mqProperties.put(MQConstants.CCSID_PROPERTY, codePage);
    DeliveryOptions opt = new DeliveryOptions();
    MQQueueManager qMgr = null;
    MQQueue queue = null;
    try {
      qMgr = new MQQueueManager(qManagerName, mqProperties);
      int openOptions = CMQC.MQOO_OUTPUT | CMQC.MQOO_INQUIRE;
      if (mode.equalsIgnoreCase("reading"))
        if (readAhead)
          openOptions = CMQC.MQOO_INPUT_SHARED | CMQC.MQOO_INQUIRE | CMQC.MQOO_READ_AHEAD;
        else
          openOptions = CMQC.MQOO_INPUT_SHARED | CMQC.MQOO_INQUIRE | CMQC.MQOO_NO_READ_AHEAD;
      queue = qMgr.accessQueue(queueName, openOptions);
      MQException.logExclude(MQConstants.MQRC_NO_MSG_AVAILABLE);
      opt.addHeader("QueueName", queueName)
        .addHeader("Depth", queue.getCurrentDepth() + "")
        .addHeader("MaximumDepth", queue.getMaximumDepth() + "")
        .addHeader("MaximumMessageLength", queue.getMaximumMessageLength() + "")
        .addHeader("OpenInputCount", queue.getOpenInputCount() + "")
        .addHeader("OpenOutputCount", queue.getOpenOutputCount() + "")
        .addHeader("QueueType", queue.getQueueType() + "");
      tMessage.reply("{\"testStatus\": \"ok\"}", opt);
    } catch (Exception e) {
      opt.addHeader("cause", e.getMessage()).addHeader("stack_trace", exceptionAsString(e));
      tMessage.fail(0,"{\"testStatus\": \"failed\"}");
    } finally {
      try {
        if (queue != null) {
          queue.close();
        }
        if (qMgr != null)
          qMgr.disconnect();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private String exceptionAsString(Throwable e) {
    StringWriter sw = new StringWriter();
    e.printStackTrace(new PrintWriter(sw));
    return sw.toString();
  }
}

