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
import ru.rzn.gmyasoedov.checkmetest.model.PageDto;
import ru.rzn.gmyasoedov.checkmetest.tables.daos.ExaminationDao;
import ru.rzn.gmyasoedov.checkmetest.tables.pojos.Examination;
import ru.rzn.gmyasoedov.checkmetest.tables.records.ExaminationRecord;

import java.util.List;

import static ru.rzn.gmyasoedov.checkmetest.Constants.LIKE_REGEXP;
import static ru.rzn.gmyasoedov.checkmetest.Constants.TABLE_EXAMINATION;

public class ExaminationService {
    private final ExaminationDao examinationDao;

    public ExaminationService(ExaminationDao examinationDao) {
        this.examinationDao = examinationDao;
    }


    public Future<Examination> getById(Long id) {
        return examinationDao.findOneById(id);
    }

    public Future<Integer> deleteById(Long id) {
        return examinationDao.deleteById(id);
    }

    public Future<PageDto<Examination>> getList(String nameFilter, int offset, int limit) {
        Future<List<Examination>> listFuture = examinationDao.queryExecutor()
                .findMany(dslContext -> getFindQuery(dslContext, nameFilter, offset, limit));
        Future<QueryResult> countFuture = examinationDao.queryExecutor()
                .query(dslContext -> getCountQuery(dslContext, nameFilter));

        return CompositeFuture.all(listFuture, countFuture)
                .map(combine -> {
                    List<Examination> list = (List<Examination>) combine.list().get(0);
                    AsyncQueryResult countResult = (AsyncQueryResult) combine.list().get(1);
                    Integer count = countResult.get(0, Integer.class);
                    return new PageDto<>(list, count, offset, limit);
                });
    }

    public Future<Long> insert(Examination examination) {
        return examinationDao.insertReturningPrimary(examination);
    }

    public Future<Integer> update(Examination examination) {
        return examinationDao.queryExecutor().execute(d -> getUpdateQuery(d, examination));
    }

    private SelectWhereStep<ExaminationRecord> getFindQuery(DSLContext context,
                                                            String nameFilter, int offset, int limit) {
        SelectWhereStep<ExaminationRecord> queryBase = context.selectFrom(TABLE_EXAMINATION);
        if (StringUtils.isNoneEmpty(nameFilter)) {
            queryBase.where(TABLE_EXAMINATION.NAME.like(nameFilter + LIKE_REGEXP));
        }
        queryBase
                .orderBy(TABLE_EXAMINATION.NAME)
                .offset(offset)
                .limit(limit);
        return queryBase;
    }

    private SelectJoinStep<Record1<Integer>> getCountQuery(DSLContext context, String nameFilter) {
        SelectJoinStep<Record1<Integer>> queryBase = context.selectCount().from(TABLE_EXAMINATION);
        if (StringUtils.isNoneEmpty(nameFilter)) {
            queryBase.where(TABLE_EXAMINATION.NAME.like(nameFilter + LIKE_REGEXP));
        }
        return queryBase;
    }

    private UpdateConditionStep<ExaminationRecord> getUpdateQuery(DSLContext context, Examination examination) {
        return context.update(TABLE_EXAMINATION)
                .set(TABLE_EXAMINATION.NAME, examination.getName())
                .set(TABLE_EXAMINATION.VERSION, examination.getVersion() + 1)
                .where(TABLE_EXAMINATION.ID.eq(examination.getId())
                        .and(TABLE_EXAMINATION.VERSION.eq(examination.getVersion())));
    }
}
