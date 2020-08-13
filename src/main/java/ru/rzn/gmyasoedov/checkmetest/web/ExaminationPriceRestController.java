package ru.rzn.gmyasoedov.checkmetest.web;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.ext.web.RoutingContext;
import ru.rzn.gmyasoedov.checkmetest.service.ExaminationPriceService;
import ru.rzn.gmyasoedov.checkmetest.tables.pojos.ExaminationPrice;
import ru.rzn.gmyasoedov.checkmetest.web.converter.request.ExaminationPriceRequestConverter;
import ru.rzn.gmyasoedov.checkmetest.web.converter.response.DeleteUpdateConverter;
import ru.rzn.gmyasoedov.checkmetest.web.converter.response.ErrorConverter;
import ru.rzn.gmyasoedov.checkmetest.web.converter.response.InsertConverter;
import ru.rzn.gmyasoedov.checkmetest.web.converter.response.ListResultConverter;
import ru.rzn.gmyasoedov.checkmetest.web.converter.response.ResultConverter;
import ru.rzn.gmyasoedov.checkmetest.web.dto.ExaminationPriceRequest;

import static ru.rzn.gmyasoedov.checkmetest.RequestUtils.getIdArgument;

public class ExaminationPriceRestController {
    private final ExaminationPriceService examinationPriceService;

    public ExaminationPriceRestController(ExaminationPriceService examinationPriceService) {
        this.examinationPriceService = examinationPriceService;
    }

    public void getList(RoutingContext context) {
        Future.<ExaminationPriceRequest>future(event -> getRequest(context, event))
                .compose(r -> examinationPriceService
                        .getList(r.getExaminationId(), r.getOffset(), r.getLimit(), r.isDescPrice()))
                .onSuccess(result -> ListResultConverter.convert(result, context))
                .onFailure(error -> ErrorConverter.convert(error, context));
    }

    private void getRequest(RoutingContext context, Promise<ExaminationPriceRequest> event) {
        event.complete(ExaminationPriceRequestConverter.convert(context.request()));
    }

    public void getById(RoutingContext context) {
        Future.<Long>future(event -> event.complete(getIdArgument(context)))
                .compose(examinationPriceService::getById)
                .onSuccess(result -> ResultConverter.convert(result, context))
                .onFailure(error -> ErrorConverter.convert(error, context));
    }

    public void deleteById(RoutingContext context) {
        Future.<Long>future(event -> event.complete(getIdArgument(context)))
                .compose(examinationPriceService::deleteById)
                .onSuccess(result -> DeleteUpdateConverter.convert(result, context))
                .onFailure(error -> ErrorConverter.convert(error, context));
    }

    public void insert(RoutingContext context) {
        Future.<ExaminationPrice>future(event -> event.complete(new ExaminationPrice(context.getBodyAsJson())))
                .compose(examinationPriceService::insert)
                .onSuccess(result -> InsertConverter.convert(result, context))
                .onFailure(error -> ErrorConverter.convert(error, context));
    }

    public void update(RoutingContext context) {
        Future.<ExaminationPrice>future(event -> event.complete(new ExaminationPrice(context.getBodyAsJson())))
                .compose(examinationPriceService::update)
                .onSuccess(result -> DeleteUpdateConverter.convert(result, context))
                .onFailure(error -> ErrorConverter.convert(error, context));
    }
}
