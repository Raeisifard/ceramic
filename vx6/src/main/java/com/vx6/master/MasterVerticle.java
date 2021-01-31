package com.vx6.master;

//import com.sun.org.apache.bcel.internal.classfile.Unknown;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

import java.util.LinkedList;
import java.util.Queue;

enum Cmd {
  SET,
  UPDATE,
  NEXT,
  FLUSH,
  PAUSE,
  RESUME,
  STOP,
  START,
  READY,
  BUFFER_DRAINED,
  BUFFER_FILLED
}

public class MasterVerticle extends AbstractVerticle {
  public EventBus eb;
  public JsonObject setting = new JsonObject();
  public boolean inputConnected = false, outputConnected = false, errorConnected = false, triggerConnected = false;
  public int bufferSize = 0;
  public Queue<Message> q = new LinkedList<>();//Input queue
  public int lt, ht;//low threshold & high threshold
  public boolean holdOn = true, pushedBack = false, autoNext = true, ready = false;
  public AddressBook addressBook;
  public Buffer buffer = null;

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    start();
    initialize(startPromise);
    //startFuture.complete();
  }

  public void initialize(Promise<Void> initPromise) throws Exception {
    initPromise.complete();
  }

  @Override
  public void start() throws Exception {
    this.eb = vertx.eventBus();
    setting = config().getJsonObject("data").getJsonObject("setting");
    addressBook = new AddressBook(config());
    if (config().containsKey("Result")) {
      outputConnected = !config().getJsonArray("Result").isEmpty();
    }
    if (config().containsKey("Input")) {
      inputConnected = !config().getJsonArray("Input").isEmpty();
    }
    if (config().containsKey("Error")) {
      errorConnected = !config().getJsonArray("Error").isEmpty();
    }
    if (config().containsKey("Trigger")) {
      triggerConnected = !config().getJsonArray("Trigger").isEmpty();
    }
    if (inputConnected) {
      bufferSize = config().getJsonObject("data").getJsonObject("config").getInteger("BUFFER_SIZE", 0);
      if (bufferSize > 0)
        buffer = new Buffer(eb, addressBook.getTrigger(), bufferSize);
    }
    initConsumers();
  }

  public void initConsumers() {
    this.eb.consumer(addressBook.getGraph_id(), this::trigger);
    this.eb.consumer(addressBook.getTrigger(), this::trigger);
    addressBook.getTriggerIns().forEach(adrs -> eb.consumer(adrs, this::trigger));
    addressBook.getInputIns().forEach(adrs -> eb.consumer(adrs, this::input));
  }

  public <T> void input(Message<T> tMessage) {
    if (buffer != null) {
      buffer.putMessage(tMessage);
      if (autoNext && !holdOn)
        eb.publish(addressBook.getTrigger(), "Next message", new DeliveryOptions().addHeader("cmd", "next"));
    } else
      process(tMessage);
  }

  public <T> void process(Message<T> tMessage) {
    //TODO Overload this method to set up your process.
    eb.publish(addressBook.getResult(), process(tMessage.body().toString()), addressBook.getDeliveryOptions(tMessage));//.addHeader("buffer_size", buffer.getBuffSize() + ""));
  }

  public String process(String msg) {
    //TODO Overload this method to set up your process.
    return msg;
  }

  public <T> void trigger(Message<T> tMessage) {
    String cmd = "";
    if (tMessage.headers().contains("cmd")) {
      cmd = tMessage.headers().get("cmd");
      Cmd command;
      try {
        command = Cmd.valueOf(cmd.toUpperCase());
        switch (command) {
          case NEXT:
            if (!holdOn)
              next(tMessage);
            break;
          case SET:
            set(tMessage);
            break;
          case UPDATE:
            update(tMessage);
            break;
          case PAUSE:
            pause(tMessage);
            break;
          case RESUME:
            resume(tMessage);
            break;
          case BUFFER_FILLED:
            addressBook.getPushBackPorts().forEach(adrs -> eb.publish(adrs, "Pause", new DeliveryOptions().addHeader("cmd", "pause")));
            break;
          case BUFFER_DRAINED:
            addressBook.getPushBackPorts().forEach(adrs -> eb.publish(adrs, "Resume", new DeliveryOptions().addHeader("cmd", "resume")));
            break;
          case FLUSH:
            flush(tMessage);
            break;
          case START:
          case READY:
            this.ready = true;
            ready(tMessage);
            break;
          case STOP:
            stop(tMessage);
            break;
          default:
            defaultCmd(tMessage, cmd);
            break;
        }
      } catch (Exception e) {
        unknownCmd(tMessage, cmd);
      }
    } else {
      noCmd(tMessage, cmd);
    }
  }

  public <T> void unknownCmd(Message<T> tMessage, String cmd) {
    eb.publish(addressBook.getError(), tMessage.body(), addressBook.getDeliveryOptions(tMessage).addHeader("error", "UNKNOWN_CMD"));
  }

  public <T> void defaultCmd(Message<T> tMessage, String cmd) {
    eb.publish(addressBook.getError(), tMessage.body(), addressBook.getDeliveryOptions(tMessage).addHeader("error", "DEFAULT_CMD"));
  }

  public <T> void noCmd(Message<T> tMessage, String cmd) {
    eb.publish(addressBook.getError(), tMessage.body(), addressBook.getDeliveryOptions(tMessage).addHeader("error", "NO_CMD"));
  }

  public <T> void ready(Message<T> tMessage) {
    holdOn = false;
    if (autoNext) {
      eb.publish(addressBook.getTrigger(), "Next message", new DeliveryOptions().addHeader("cmd", "next"));
    }
  }

  public <T> void stop(Message<T> tMessage) {
    eb.publish(addressBook.getTrigger(), "Next message", new DeliveryOptions().addHeader("cmd", "flush"));
    this.ready = false;
  }

  public <T> void set(Message<T> tMessage) {
    setting = new JsonObject(tMessage.body().toString());
  }

  public <T> void update(Message<T> tMessage) {
    setting.mergeIn(new JsonObject(tMessage.body().toString()), true);
  }

  public <T> void pause(Message<T> tMessage) {
    holdOn = true;
    if (inputConnected && buffer == null)
      addressBook.getPushBackPorts().forEach(adrs -> eb.publish(adrs, "Pause", new DeliveryOptions().addHeader("cmd", "pause")));
  }

  public <T> void resume(Message<T> tMessage) {
    holdOn = false;
    if (inputConnected && buffer == null)
      addressBook.getPushBackPorts().forEach(adrs -> eb.publish(adrs, "Resume", new DeliveryOptions().addHeader("cmd", "resume")));
    else if (autoNext)
      eb.publish(addressBook.getTrigger(), "Next message", new DeliveryOptions().addHeader("cmd", "next"));
  }

  public <T> void flush(Message<T> tMessage) {
  }

  public <T> void next(Message<T> tMessage) {
    if (inputConnected && buffer != null) {
      Message msg = buffer.getMessage();
      if (msg != null) {
        process(tMessage);
        if (autoNext) {
          eb.publish(addressBook.getTrigger(), "Next message", new DeliveryOptions().addHeader("cmd", "next"));
        }
      }
    }
  }

  @Override
  public void stop() throws Exception {
  }

  @Override
  public void stop(Promise<Void> stopPromise) throws Exception {
    stop();
    stopPromise.complete();
  }
}
