package ru.rzn.gmyasoedov.checkmetest.web.converter.response;

import io.github.jklingsporn.vertx.jooq.shared.internal.VertxPojo;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import ru.rzn.gmyasoedov.checkmetest.model.PageResult;

import java.util.List;

public class PageResultConverter {

    public static void convert(PageResult<? extends VertxPojo> pageResult, RoutingContext context) {
        context.response().putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON.toString());
        JsonArray dataArray = new JsonArray();
        pageResult.getResult().forEach(element -> dataArray.add(element.toJson()));
        JsonObject result = new JsonObject()
                .put("data", dataArray)
                .put("count", pageResult.getCount())
                .put("limit", pageResult.getLimit())
                .put("offset", pageResult.getOffset());
        context.response().setStatusCode(HttpResponseStatus.OK.code());
        context.response().end(result.encode());
    }
}

