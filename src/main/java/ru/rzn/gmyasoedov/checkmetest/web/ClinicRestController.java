package ru.rzn.gmyasoedov.checkmetest.web;

import io.vertx.core.Future;
import io.vertx.ext.web.RoutingContext;
import ru.rzn.gmyasoedov.checkmetest.service.ClinicService;
import ru.rzn.gmyasoedov.checkmetest.tables.pojos.Clinic;
import ru.rzn.gmyasoedov.checkmetest.web.converter.request.FilteredRequestConverter;
import ru.rzn.gmyasoedov.checkmetest.web.converter.response.DeleteUpdateConverter;
import ru.rzn.gmyasoedov.checkmetest.web.converter.response.ErrorConverter;
import ru.rzn.gmyasoedov.checkmetest.web.converter.response.PageResultConverter;
import ru.rzn.gmyasoedov.checkmetest.web.converter.response.ResultConverter;
import ru.rzn.gmyasoedov.checkmetest.web.dto.FilteredRequest;

import static ru.rzn.gmyasoedov.checkmetest.RequestUtils.getIdArgument;

public class ClinicRestController {
    private final ClinicService clinicService;

    public ClinicRestController(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    public void getList(RoutingContext context) {
        Future.<FilteredRequest>future(event -> event.complete(FilteredRequestConverter.convert(context.request())))
                .compose(r -> clinicService.getList(r.getNameFilter(), r.getOffset(), r.getLimit()))
                .onSuccess(result -> PageResultConverter.convert(result, context))
                .onFailure(error -> ErrorConverter.convert(error, context));
    }

    public void getById(RoutingContext context) {
        Future.<Long>future(event -> event.complete(getIdArgument(context)))
                .compose(clinicService::getById)
                .onSuccess(result -> ResultConverter.convert(result, context))
                .onFailure(error -> ErrorConverter.convert(error, context));
    }

    public void deleteById(RoutingContext context) {
        Future.<Long>future(event -> event.complete(getIdArgument(context)))
                .compose(clinicService::deleteById)
                .onSuccess(result -> DeleteUpdateConverter.convert(result, context))
                .onFailure(error -> ErrorConverter.convert(error, context));
    }

    public void insert(RoutingContext context) {
        Future.<Clinic>future(event -> event.complete(new Clinic(context.getBodyAsJson())))
                .compose(clinicService::insert)
                .onSuccess(result -> DeleteUpdateConverter.convert(result, context))
                .onFailure(error -> ErrorConverter.convert(error, context));
    }

    public void update(RoutingContext context) {
        Future.<Clinic>future(event -> event.complete(new Clinic(context.getBodyAsJson())))
                .compose(clinicService::update)
                .onSuccess(result -> DeleteUpdateConverter.convert(result, context))
                .onFailure(error -> ErrorConverter.convert(error, context));
    }
}
