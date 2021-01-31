package com.vx6.master;

import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;

import java.util.LinkedList;
import java.util.Queue;

public class Buffer {
    private int lt = 0, ht = 0, size = 0;
    private Queue<Message> buf = new LinkedList<>();
    private String address;
    private EventBus eb;

    public Buffer(EventBus eb, String address, int size) throws Exception {
        if (size < 3) {
            throw new Exception("Buffer size must be greater than " + size);
        }
        this.eb = eb;
        this.address = address;
        this.size = size;
        lt = Math.round(size / 3);
        ht = Math.round(size * 2 / 3);
    }

    public Message<?> getMessage() {
        if (buf.size() == lt)
            eb.publish(address, "Buffer is hungry!", new DeliveryOptions().addHeader("cmd", "buffer_drained"));
        return buf.poll();
    }

    public boolean putMessage(Message<?> msg) {
        if (buf.size() == ht)
            eb.publish(address, "Buffer is filling!", new DeliveryOptions().addHeader("cmd", "buffer_filled"));
        return buf.add(msg);
    }

    public int getBuffSize() {
        return buf.size();
    }

    public int getLt() {
        return lt;
    }

    public int getHt() {
        return ht;
    }

    public int getSize() {
        return size;
    }
}
