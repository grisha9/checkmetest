package ru.rzn.gmyasoedov.checkmetest.web.converter.request;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.RoutingContext;
import ru.rzn.gmyasoedov.checkmetest.Constants;
import ru.rzn.gmyasoedov.checkmetest.RequestUtils;
import ru.rzn.gmyasoedov.checkmetest.web.dto.FilteredRequest;

import java.util.Optional;

import static ru.rzn.gmyasoedov.checkmetest.Constants.MAX_PAGE_SIZE;
import static ru.rzn.gmyasoedov.checkmetest.Constants.QUERY_PARAM_LIMIT;
import static ru.rzn.gmyasoedov.checkmetest.Constants.QUERY_PARAM_NAME;
import static ru.rzn.gmyasoedov.checkmetest.Constants.QUERY_PARAM_OFFSET;

public class FilteredRequestConverter {

    public static FilteredRequest convert(HttpServerRequest request) {
        String nameFilter = RequestUtils.getArgument(() -> request.getParam(QUERY_PARAM_NAME), QUERY_PARAM_NAME);
        Integer offset = RequestUtils.getArgument(() -> Optional.ofNullable(request.getParam(QUERY_PARAM_OFFSET))
                .map(Integer::valueOf)
                .map(i -> Math.max(i, 0))
                .orElse(0), QUERY_PARAM_OFFSET);
        Integer limit = RequestUtils.getArgument(() -> Optional.ofNullable(request.getParam(QUERY_PARAM_LIMIT))
                .map(Integer::valueOf)
                .map(i -> Math.min(i, MAX_PAGE_SIZE))
                .orElse(Constants.MAX_PAGE_SIZE), QUERY_PARAM_LIMIT);

        return new FilteredRequest(nameFilter, offset, limit);
    }
}
