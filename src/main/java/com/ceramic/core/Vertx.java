package com.ceramic.core;

import java.util.concurrent.atomic.AtomicReference;

public class Vertx {
    //public static final io.vertx.core.Vertx vertx = io.vertx.core.Vertx.vertx();
    public static final AtomicReference<io.vertx.core.Vertx> vertx = new AtomicReference<>(io.vertx.core.Vertx.vertx());
    public Vertx(){
        super();
    }
}
