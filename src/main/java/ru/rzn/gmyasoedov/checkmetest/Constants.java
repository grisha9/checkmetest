package ru.rzn.gmyasoedov.checkmetest;

import ru.rzn.gmyasoedov.checkmetest.tables.Clinic;
import ru.rzn.gmyasoedov.checkmetest.tables.Examination;

public final class Constants {
    public static final int MAX_PAGE_SIZE = 100;
    public static final String LIKE_REGEXP = "%";

    public static final Clinic TABLE_CLINIC = Clinic.CLINIC;
    public static final Examination TABLE_EXAMINATION = Examination.EXAMINATION;

    public static final String QUERY_PARAM_ID = "id";
    public static final String QUERY_PARAM_NAME = "name";
    public static final String QUERY_PARAM_LIMIT = "limit";
    public static final String QUERY_PARAM_OFFSET = "offset";

    public static final String DB_CONFIG_FILE_NAME = "application.properties";

    private Constants() {}

}
