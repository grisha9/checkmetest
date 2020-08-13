package ru.rzn.gmyasoedov.checkmetest.web.converter.response;

import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.http.HttpHeaders;
import io.vertx.ext.web.RoutingContext;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

public class ErrorConverter {

    public static void convert(Throwable error, RoutingContext context) {
        String message = ExceptionUtils.getRootCauseMessage(error);
        if (error instanceof IllegalStateException
                || error instanceof IllegalArgumentException) {
            context.response().putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.TEXT_PLAIN.toString());
            HttpResponseStatus responseStatus = HttpResponseStatus.BAD_REQUEST;
            context.response().setStatusCode(responseStatus.code());
            context.response().end(ObjectUtils.defaultIfNull(message, responseStatus.reasonPhrase()));
        } else {
            HttpResponseStatus responseStatus = HttpResponseStatus.INTERNAL_SERVER_ERROR;
            context.response().setStatusCode(responseStatus.code());
            context.response().end(ObjectUtils.defaultIfNull(message, responseStatus.reasonPhrase()));
        }
    }
}
