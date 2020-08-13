package ru.rzn.gmyasoedov.checkmetest.web.converter.response;

import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.http.HttpHeaders;
import io.vertx.ext.web.RoutingContext;

public class InsertConverter {

    public static void convert(Long id, RoutingContext context) {
        context.response().putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.TEXT_PLAIN.toString());
        context.response().setStatusCode(HttpResponseStatus.CREATED.code());
        context.response().end(id.toString());
    }

    public static void convert(Integer updatedRows, RoutingContext context) {
        convert((long) updatedRows, context);
    }
}
