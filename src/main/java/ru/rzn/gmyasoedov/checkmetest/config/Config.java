package ru.rzn.gmyasoedov.checkmetest.config;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.asyncsql.AsyncSQLClient;
import io.vertx.ext.asyncsql.PostgreSQLClient;
import org.jooq.Configuration;
import org.jooq.SQLDialect;
import org.jooq.conf.RenderNameCase;
import org.jooq.conf.RenderQuotedNames;
import org.jooq.impl.DefaultConfiguration;
import ru.rzn.gmyasoedov.checkmetest.Constants;
import ru.rzn.gmyasoedov.checkmetest.service.ClinicService;
import ru.rzn.gmyasoedov.checkmetest.service.ExaminationService;
import ru.rzn.gmyasoedov.checkmetest.tables.daos.ClinicDao;
import ru.rzn.gmyasoedov.checkmetest.tables.daos.ExaminationDao;
import ru.rzn.gmyasoedov.checkmetest.web.ClinicRestController;
import ru.rzn.gmyasoedov.checkmetest.web.ExaminationRestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private static final String HOST = "host";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String DATABASE = "database";
    private static final String PORT = "web.port";

    private ClinicRestController clinicRestController = null;
    private Properties properties;
    private ExaminationRestController examinationRestController;

    public Config(Vertx vertx) {
        init(vertx);
    }

    public ClinicRestController getClinicRestController() {
        return clinicRestController;
    }

    public ExaminationRestController getExaminationRestController() {
        return examinationRestController;
    }

    public int getWebPort() {
        return Integer.valueOf(properties.getProperty(PORT, "8080"));
    }

    private void init(Vertx vertx) {
        Configuration configuration = new DefaultConfiguration();
        configuration.set(SQLDialect.POSTGRES);
        configuration.settings().withRenderQuotedNames(RenderQuotedNames.NEVER);
        configuration.settings().withRenderNameCase(RenderNameCase.LOWER);

        properties = loadProperties();

        JsonObject config = new JsonObject()
                .put(HOST, properties.getProperty(HOST))
                .put(USERNAME, properties.getProperty(USERNAME))
                .put(PASSWORD, properties.getProperty(PASSWORD))
                .put(DATABASE, properties.getProperty(DATABASE));
        AsyncSQLClient client = PostgreSQLClient.createNonShared(vertx, config);


        ClinicDao clinicDao = new ClinicDao(configuration, client);
        ExaminationDao examinationDao = new ExaminationDao(configuration, client);

        clinicRestController = new ClinicRestController(new ClinicService(clinicDao));
        examinationRestController = new ExaminationRestController(new ExaminationService(examinationDao));
    }

    private Properties loadProperties() {
        try (InputStream stream = this.getClass().getClassLoader()
                .getResourceAsStream(Constants.DB_CONFIG_FILE_NAME)) {
            Properties properties = new Properties();
            properties.load(stream);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
