package ru.rzn.gmyasoedov.checkmetest.web.converter.response;

import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.List;

public class ListResultConverter {

    public static void convert(List<JsonObject> list, RoutingContext context) {
        context.response().putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON.toString());
        JsonArray result = new JsonArray();
        list.forEach(result::add);
        context.response().setStatusCode(HttpResponseStatus.OK.code());
        context.response().end(result.encode());
    }
}
