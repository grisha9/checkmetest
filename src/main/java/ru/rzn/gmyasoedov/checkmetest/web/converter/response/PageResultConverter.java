package ru.rzn.gmyasoedov.checkmetest.web.converter.response;

import io.github.jklingsporn.vertx.jooq.shared.internal.VertxPojo;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import ru.rzn.gmyasoedov.checkmetest.model.PageDto;

public class PageResultConverter {

    public static void convert(PageDto<? extends VertxPojo> pageDto, RoutingContext context) {
        context.response().putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON.toString());
        JsonArray dataArray = new JsonArray();
        pageDto.getResult().forEach(element -> dataArray.add(element.toJson()));
        JsonObject result = new JsonObject()
                .put("data", dataArray)
                .put("count", pageDto.getCount())
                .put("limit", pageDto.getLimit())
                .put("offset", pageDto.getOffset());
        context.response().setStatusCode(HttpResponseStatus.OK.code());
        context.response().end(result.encode());
    }
}

