package ru.rzn.gmyasoedov.checkmetest;

import io.vertx.ext.web.RoutingContext;
import ru.rzn.gmyasoedov.checkmetest.tables.Clinic;
import ru.rzn.gmyasoedov.checkmetest.tables.Examination;

import java.util.function.Supplier;

import static ru.rzn.gmyasoedov.checkmetest.Constants.QUERY_PARAM_ID;
import static ru.rzn.gmyasoedov.checkmetest.Constants.QUERY_PARAM_NAME;

public final class RequestUtils {
    private RequestUtils() {}

    public static  <T> T getArgument(Supplier<T> supplier, String name) {
        T t;
        try {
            t = supplier.get();
        } catch (Exception e) {
            throw new IllegalArgumentException("bad argument: " + name);
        }
        return t;
    }

    public static Long getIdArgument(RoutingContext context) {
        return getArgument(() -> Long.valueOf(context.request().getParam(QUERY_PARAM_ID)), QUERY_PARAM_ID);
    }
}
