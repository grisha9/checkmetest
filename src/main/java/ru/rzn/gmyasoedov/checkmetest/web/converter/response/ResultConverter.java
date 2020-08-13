package ru.rzn.gmyasoedov.checkmetest.web.converter.response;

import io.github.jklingsporn.vertx.jooq.shared.internal.VertxPojo;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.http.HttpHeaders;
import io.vertx.ext.web.RoutingContext;

public class ResultConverter {
    public static void convert(VertxPojo result, RoutingContext context) {
        if (result == null) {
            context.response().putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.TEXT_PLAIN.toString());
            context.response().setStatusCode(HttpResponseStatus.NOT_FOUND.code());
            context.response().end(HttpResponseStatus.NOT_FOUND.reasonPhrase());
        } else {
            context.response().putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON.toString());
            context.response().setStatusCode(HttpResponseStatus.OK.code());
            context.response().end(result.toJson().toString());
        }
    }
}
