package ru.rzn.gmyasoedov.checkmetest.web.converter.request;

import io.vertx.core.http.HttpServerRequest;
import ru.rzn.gmyasoedov.checkmetest.Constants;
import ru.rzn.gmyasoedov.checkmetest.RequestUtils;
import ru.rzn.gmyasoedov.checkmetest.web.dto.ExaminationPriceRequest;

import java.util.Optional;

import static ru.rzn.gmyasoedov.checkmetest.Constants.MAX_PAGE_SIZE;
import static ru.rzn.gmyasoedov.checkmetest.Constants.QUERY_PARAM_DESC_PRICE;
import static ru.rzn.gmyasoedov.checkmetest.Constants.QUERY_PARAM_EXAMINATION_ID;
import static ru.rzn.gmyasoedov.checkmetest.Constants.QUERY_PARAM_LIMIT;
import static ru.rzn.gmyasoedov.checkmetest.Constants.QUERY_PARAM_NAME;
import static ru.rzn.gmyasoedov.checkmetest.Constants.QUERY_PARAM_OFFSET;

public class ExaminationPriceRequestConverter {

    public static ExaminationPriceRequest convert(HttpServerRequest request) {
        Long examinationId = RequestUtils.getArgument(() -> Long.valueOf(request.getParam(QUERY_PARAM_EXAMINATION_ID)),
                QUERY_PARAM_NAME);
        int offset = RequestUtils.getArgument(() -> Optional.ofNullable(request.getParam(QUERY_PARAM_OFFSET))
                .map(Integer::valueOf)
                .map(i -> Math.max(i, 0))
                .orElse(0), QUERY_PARAM_OFFSET);
        int limit = RequestUtils.getArgument(() -> Optional.ofNullable(request.getParam(QUERY_PARAM_LIMIT))
                .map(Integer::valueOf)
                .map(i -> Math.min(i, MAX_PAGE_SIZE))
                .orElse(Constants.MAX_PAGE_SIZE), QUERY_PARAM_LIMIT);
        boolean descPrice = RequestUtils.getArgument(() -> Optional.ofNullable(request.getParam(QUERY_PARAM_DESC_PRICE))
                .map(Boolean::valueOf)
                .orElse(false), QUERY_PARAM_DESC_PRICE);

        return new ExaminationPriceRequest(examinationId, offset, limit, descPrice);
    }
}
