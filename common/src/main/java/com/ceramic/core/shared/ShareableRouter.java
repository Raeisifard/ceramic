package com.ceramic.core.shared;

import io.vertx.core.Vertx;
import io.vertx.core.shareddata.Shareable;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.impl.RouterImpl;

public class ShareableRouter extends RouterImpl implements Shareable {

    public static Router router(Vertx vertx) {
        return (Router) vertx.sharedData()
                .getLocalMap("router")
                .computeIfAbsent("main", n -> new ShareableRouter(vertx));
    }

    ShareableRouter(final Vertx vertx) {
        super(vertx);
    }
}
