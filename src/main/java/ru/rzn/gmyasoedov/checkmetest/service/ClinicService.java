package ru.rzn.gmyasoedov.checkmetest.service;

import io.github.jklingsporn.vertx.jooq.shared.async.AsyncQueryResult;
import io.github.jklingsporn.vertx.jooq.shared.internal.QueryResult;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import org.apache.commons.lang3.StringUtils;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.SelectJoinStep;
import org.jooq.SelectWhereStep;
import org.jooq.UpdateConditionStep;
import ru.rzn.gmyasoedov.checkmetest.model.PageResult;
import ru.rzn.gmyasoedov.checkmetest.tables.daos.ClinicDao;
import ru.rzn.gmyasoedov.checkmetest.tables.pojos.Clinic;
import ru.rzn.gmyasoedov.checkmetest.tables.records.ClinicRecord;

import java.util.List;

import static org.jooq.impl.DSL.count;
import static ru.rzn.gmyasoedov.checkmetest.Constants.LIKE_REGEXP;
import static ru.rzn.gmyasoedov.checkmetest.Constants.TABLE_CLINIC;

public class ClinicService {

    private final ClinicDao clinicDao;

    public ClinicService(ClinicDao clinicDao) {
        this.clinicDao = clinicDao;
    }

    public Future<Clinic> getById(Long id) {
        return clinicDao.findOneById(id);
    }

    public Future<Integer> deleteById(Long id) {
        return clinicDao.deleteById(id);
    }

    public Future<Long> insert(Clinic clinic) {
        return clinicDao.insertReturningPrimary(clinic);
    }

    public Future<PageResult<Clinic>> getList(String nameFilter, int offset, int limit) {
        Future<List<Clinic>> listFuture = clinicDao.queryExecutor()
                .findMany(dslContext -> getFindQuery(dslContext, nameFilter, offset, limit));
        Future<QueryResult> countFuture = clinicDao.queryExecutor()
                .query(dslContext -> getCountQuery(dslContext, nameFilter));

        return CompositeFuture.all(listFuture, countFuture)
                .map(combine -> {
                    List<Clinic> list = (List<Clinic>) combine.list().get(0);
                    AsyncQueryResult countResult = (AsyncQueryResult) combine.list().get(1);
                    Integer count = countResult.get(0, Integer.class);
                    return new PageResult<>(list, count, offset, limit);
                });
    }

    public Future<Integer> update(Clinic clinic) {
        return clinicDao.queryExecutor().execute(dslContext -> getUpdateQuery(dslContext, clinic));
    }

    private SelectWhereStep<ClinicRecord> getFindQuery(DSLContext context, String nameFilter, int offset, int limit) {
        SelectWhereStep<ClinicRecord> queryBase = context.selectFrom(TABLE_CLINIC);
        if (StringUtils.isNoneEmpty(nameFilter)) {
            queryBase.where(TABLE_CLINIC.NAME.like(nameFilter + LIKE_REGEXP));
        }
        queryBase
                .orderBy(TABLE_CLINIC.NAME)
                .offset(offset)
                .limit(limit);
        return queryBase;
    }

    private SelectJoinStep<Record1<Integer>> getCountQuery(DSLContext context, String nameFilter) {
        SelectJoinStep<Record1<Integer>> queryBase = context.selectCount().from(TABLE_CLINIC);
        if (StringUtils.isNoneEmpty(nameFilter)) {
            queryBase.where(TABLE_CLINIC.NAME.like(nameFilter + LIKE_REGEXP));
        }
        return queryBase;
    }

    private UpdateConditionStep<ClinicRecord> getUpdateQuery(DSLContext context, Clinic updated) {
        return context.update(TABLE_CLINIC)
                .set(TABLE_CLINIC.NAME, updated.getName())
                .set(TABLE_CLINIC.VERSION, updated.getVersion() + 1)
                .where(TABLE_CLINIC.ID.eq(updated.getId()).and(TABLE_CLINIC.VERSION.eq(updated.getVersion())));
    }
}
