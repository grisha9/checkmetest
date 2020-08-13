package ru.rzn.gmyasoedov.checkmetest;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rzn.gmyasoedov.checkmetest.config.Config;
import ru.rzn.gmyasoedov.checkmetest.web.ClinicRestController;
import ru.rzn.gmyasoedov.checkmetest.web.ExaminationRestController;

import static io.netty.handler.codec.http.HttpHeaderValues.APPLICATION_JSON;

public class MainVerticle extends AbstractVerticle {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void start(Promise<Void> promise) throws Exception {
        startHttpServer().onComplete(ar -> {
            if (ar.succeeded()) {
                promise.complete();
            } else {
                promise.fail(ar.cause());
            }
        });
    }

    private Future<Void> startHttpServer() {
        Config config = new Config(vertx);
        ClinicRestController clinicRestController = config.getClinicRestController();
        ExaminationRestController examinationRestController = config.getExaminationRestController();

        Promise<Void> promise = Promise.promise();
        HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx);
        router.post().handler(BodyHandler.create());
        router.put().handler(BodyHandler.create());

        router.get("/clinic").handler(clinicRestController::getList);
        router.get("/clinic/:id").handler(clinicRestController::getById);
        router.post("/clinic").consumes(APPLICATION_JSON.toString()).handler(clinicRestController::insert);
        router.put("/clinic/:id").consumes(APPLICATION_JSON.toString()).handler(clinicRestController::update);
        router.delete("/clinic/:id").handler(clinicRestController::deleteById);

        router.get("/examination").handler(examinationRestController::getList);
        router.get("/examination/:id").handler(examinationRestController::getById);
        router.post("/examination").consumes(APPLICATION_JSON.toString()).handler(examinationRestController::insert);
        router.put("/examination/:id").consumes(APPLICATION_JSON.toString()).handler(examinationRestController::update);
        router.delete("/examination/:id").handler(examinationRestController::deleteById);

        server.requestHandler(router)
                .listen(config.getWebPort(), ar -> {
                    if (ar.succeeded()) {
                        logger.info("HTTP server running on port 8080");
                        promise.complete();
                    } else {
                        logger.error("Could not start a HTTP server", ar.cause());
                        promise.fail(ar.cause());
                    }
                });

        return promise.future();
    }


}
