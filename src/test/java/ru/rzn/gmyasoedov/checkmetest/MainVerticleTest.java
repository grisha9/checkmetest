package ru.rzn.gmyasoedov.checkmetest;

import io.netty.handler.codec.http.HttpHeaderValues;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.ext.web.client.WebClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
//@Ignore("integration tests. need db")
public class MainVerticleTest {
    private static final int TEST_PORT = 8080;
    private static final String LOCALHOST = "localhost";
    private Vertx vertx;
    private WebClient client;

    @Before
    public void setUp(TestContext tc) {
        vertx = Vertx.vertx();
        vertx.deployVerticle(MainVerticle.class.getName(), tc.asyncAssertSuccess());
        client = WebClient.create(vertx);
    }

    @After
    public void tearDown(TestContext tc) {
        vertx.close(tc.asyncAssertSuccess());
    }

    @Test
    public void getClinics(TestContext tc) {
        Async async = tc.async();
        client.get(TEST_PORT, LOCALHOST, "/clinic")
                .addQueryParam("limit", "3")
                .addQueryParam("offset", "1")
                //.addQueryParam("name", "1")
                .send(ar -> {
                    tc.assertTrue(ar.succeeded());
                    tc.assertTrue(ar.result().body().length() > 0);
                    async.complete();

                });
    }

    @Test
    public void getClinicById(TestContext tc) {
        Async async = tc.async();
        client.get(TEST_PORT, LOCALHOST, "/clinic/1")
                .send(ar -> {
                    tc.assertTrue(ar.succeeded());
                    tc.assertTrue(ar.result().body().length() > 0);
                    async.complete();
                });
    }

    @Test
    public void createClinic(TestContext tc) {
        Async async = tc.async();
        client.post(TEST_PORT, LOCALHOST, "/clinic")
                .putHeader(HttpHeaders.CONTENT_TYPE.toString(), HttpHeaderValues.APPLICATION_JSON.toString())
                .sendJsonObject(new JsonObject()
                        .put("name", "test3"), ar -> {
                    tc.assertTrue(ar.succeeded());
                    tc.assertTrue(ar.result().body().length() > 0);
                    async.complete();
                });
    }

    @Test
    public void deleteClinic(TestContext tc) {
        Async async = tc.async();
        client.delete(TEST_PORT, LOCALHOST, "/clinic/12")
                .send(ar -> {
                    tc.assertTrue(ar.succeeded());
                    tc.assertTrue(ar.result().body().length() > 0);
                    async.complete();
                });
    }

    @Test
    public void updateClinic(TestContext tc) {
        Async async = tc.async();
        client.put(TEST_PORT, LOCALHOST, "/clinic/1")
                .putHeader(HttpHeaders.CONTENT_TYPE.toString(), HttpHeaderValues.APPLICATION_JSON.toString())
                .sendJsonObject(new JsonObject()
                        .put("id", 1L)
                        .put("version", 0L)
                        .put("name", "upd"), ar -> {
                    tc.assertTrue(ar.succeeded());
                    tc.assertTrue(ar.result().body().length() > 0);
                    async.complete();
                });
    }

    @Test
    public void getExaminations(TestContext tc) {
        Async async = tc.async();
        client.get(TEST_PORT, LOCALHOST, "/examination")
                .addQueryParam("limit", "3")
                .addQueryParam("offset", "0")
                .send(ar -> {
                    tc.assertTrue(ar.succeeded());
                    tc.assertTrue(ar.result().body().length() > 0);
                    async.complete();

                });

    }

    @Test
    public void getExaminationById(TestContext tc) {
        Async async = tc.async();
        client.get(TEST_PORT, LOCALHOST, "/examination/1")
                .send(ar -> {
                    tc.assertTrue(ar.succeeded());
                    tc.assertTrue(ar.result().body().length() > 0);
                    async.complete();
                });
    }

    @Test
    public void createExamination(TestContext tc) {
        Async async = tc.async();
        client.post(TEST_PORT, LOCALHOST, "/examination")
                .putHeader(HttpHeaders.CONTENT_TYPE.toString(), HttpHeaderValues.APPLICATION_JSON.toString())
                .sendJsonObject(new JsonObject()
                        .put("name", "ex2"), ar -> {
                    tc.assertTrue(ar.succeeded());
                    tc.assertTrue(ar.result().body().length() > 0);
                    async.complete();
                });
    }

    @Test
    public void deleteExamination(TestContext tc) {
        Async async = tc.async();
        client.delete(TEST_PORT, LOCALHOST, "/examination/2")
                .send(ar -> {
                    tc.assertTrue(ar.succeeded());
                    tc.assertTrue(ar.result().body().length() > 0);
                    async.complete();
                });
    }

    @Test
    public void updateExamination(TestContext tc) {
        Async async = tc.async();
        client.put(TEST_PORT, LOCALHOST, "/examination/1")
                .putHeader(HttpHeaders.CONTENT_TYPE.toString(), HttpHeaderValues.APPLICATION_JSON.toString())
                .sendJsonObject(new JsonObject()
                        .put("id", 1L)
                        .put("version", 0L)
                        .put("name", "ex-upd"), ar -> {
                    tc.assertTrue(ar.succeeded());
                    tc.assertTrue(ar.result().body().length() > 0);
                    async.complete();
                });
    }


}