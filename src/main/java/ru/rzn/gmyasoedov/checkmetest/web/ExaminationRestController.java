package ru.rzn.gmyasoedov.checkmetest.web;

import io.vertx.core.Future;
import io.vertx.ext.web.RoutingContext;
import ru.rzn.gmyasoedov.checkmetest.service.ExaminationService;
import ru.rzn.gmyasoedov.checkmetest.tables.pojos.Examination;
import ru.rzn.gmyasoedov.checkmetest.web.converter.request.FilteredRequestConverter;
import ru.rzn.gmyasoedov.checkmetest.web.converter.response.DeleteUpdateConverter;
import ru.rzn.gmyasoedov.checkmetest.web.converter.response.ErrorConverter;
import ru.rzn.gmyasoedov.checkmetest.web.converter.response.InsertConverter;
import ru.rzn.gmyasoedov.checkmetest.web.converter.response.PageResultConverter;
import ru.rzn.gmyasoedov.checkmetest.web.converter.response.ResultConverter;
import ru.rzn.gmyasoedov.checkmetest.web.dto.FilteredRequest;

import static ru.rzn.gmyasoedov.checkmetest.RequestUtils.getIdArgument;

public class ExaminationRestController {
    private final ExaminationService examinationService;

    public ExaminationRestController(ExaminationService examinationService) {
        this.examinationService = examinationService;
    }

    public void getList(RoutingContext context) {
        Future.<FilteredRequest>future(event -> event.complete(FilteredRequestConverter.convert(context.request())))
                .compose(r -> examinationService.getList(r.getNameFilter(), r.getOffset(), r.getLimit()))
                .onSuccess(result -> PageResultConverter.convert(result, context))
                .onFailure(error -> ErrorConverter.convert(error, context));
    }

    public void getById(RoutingContext context) {
        Future.<Long>future(event -> event.complete(getIdArgument(context)))
                .compose(examinationService::getById)
                .onSuccess(result -> ResultConverter.convert(result, context))
                .onFailure(error -> ErrorConverter.convert(error, context));
    }

    public void deleteById(RoutingContext context) {
        Future.<Long>future(event -> event.complete(getIdArgument(context)))
                .compose(examinationService::deleteById)
                .onSuccess(result -> DeleteUpdateConverter.convert(result, context))
                .onFailure(error -> ErrorConverter.convert(error, context));
    }

    public void insert(RoutingContext context) {
        Future.<Examination>future(event -> event.complete(new Examination(context.getBodyAsJson())))
                .compose(examinationService::insert)
                .onSuccess(result -> InsertConverter.convert(result, context))
                .onFailure(error -> ErrorConverter.convert(error, context));
    }

    public void update(RoutingContext context) {
        Future.<Examination>future(event -> event.complete(new Examination(context.getBodyAsJson())))
                .compose(examinationService::update)
                .onSuccess(result -> DeleteUpdateConverter.convert(result, context))
                .onFailure(error -> ErrorConverter.convert(error, context));
    }
}
