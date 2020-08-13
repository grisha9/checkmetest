package ru.rzn.gmyasoedov.checkmetest.web.converter.response;

import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.http.HttpHeaders;
import io.vertx.ext.web.RoutingContext;

public class DeleteUpdateConverter {

    public static void convert(Long updatedRows, RoutingContext context) {
        if (updatedRows > 0) {
            context.response().putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.TEXT_PLAIN.toString());
            context.response().setStatusCode(HttpResponseStatus.OK.code());
            context.response().end(updatedRows.toString());
        } else {
            context.response().putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.TEXT_PLAIN.toString());
            context.response().setStatusCode(HttpResponseStatus.NOT_FOUND.code());
            context.response().end(HttpResponseStatus.NOT_FOUND.reasonPhrase());
        }
    }

    public static void convert(Integer updatedRows, RoutingContext context) {
        convert((long) updatedRows, context);
    }
}
