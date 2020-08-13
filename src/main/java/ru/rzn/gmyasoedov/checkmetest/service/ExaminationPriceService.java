package ru.rzn.gmyasoedov.checkmetest.service;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SelectLimitPercentAfterOffsetStep;
import org.jooq.UpdateConditionStep;
import ru.rzn.gmyasoedov.checkmetest.tables.daos.ExaminationPriceDao;
import ru.rzn.gmyasoedov.checkmetest.tables.pojos.ExaminationPrice;
import ru.rzn.gmyasoedov.checkmetest.tables.records.ExaminationPriceRecord;

import java.util.List;

import static ru.rzn.gmyasoedov.checkmetest.Constants.TABLE_CLINIC;
import static ru.rzn.gmyasoedov.checkmetest.Constants.TABLE_EXAMINATION_PRICE;

public class ExaminationPriceService {

    private final ExaminationPriceDao examinationPriceDao;

    public ExaminationPriceService(ExaminationPriceDao examinationPriceDao) {
        this.examinationPriceDao = examinationPriceDao;
    }

    public Future<Long> insert(ExaminationPrice examinationPrice) {
        return examinationPriceDao.insertReturningPrimary(examinationPrice);
    }

    public Future<Integer> deleteById(Long id) {
        return examinationPriceDao.deleteById(id);
    }

    public Future<ExaminationPrice> getById(Long id) {
        return examinationPriceDao.findOneById(id);
    }

    public Future<Integer> update(ExaminationPrice price) {
        return examinationPriceDao.queryExecutor().execute(dslContext -> getUpdateQuery(dslContext, price));
    }

    public Future<List<JsonObject>> getList(Long examinationId, int offset, int limit, boolean descPrice) {
        return examinationPriceDao.queryExecutor()
                .findManyJson(dslContext -> getFindQuery(dslContext, examinationId, offset, limit, descPrice));
    }

    private SelectLimitPercentAfterOffsetStep<Record> getFindQuery(DSLContext context,
                                                                   Long examinationId,
                                                                   int offset, int limit,
                                                                   boolean descPrice) {
        return context.select()
                .from(TABLE_EXAMINATION_PRICE)
                .join(TABLE_CLINIC)
                .on(TABLE_EXAMINATION_PRICE.CLINIC_ID.eq(TABLE_CLINIC.ID))
                .where(TABLE_EXAMINATION_PRICE.EXAMINATION_ID.eq(examinationId))
                .orderBy(descPrice ? TABLE_EXAMINATION_PRICE.PRICE.desc() : TABLE_EXAMINATION_PRICE.PRICE.asc())
                .offset(offset)
                .limit(limit);
    }

    private UpdateConditionStep<ExaminationPriceRecord> getUpdateQuery(DSLContext context, ExaminationPrice updated) {
        return context.update(TABLE_EXAMINATION_PRICE)
                .set(TABLE_EXAMINATION_PRICE.CLINIC_ID, updated.getClinicId())
                .set(TABLE_EXAMINATION_PRICE.EXAMINATION_ID, updated.getExaminationId())
                .set(TABLE_EXAMINATION_PRICE.PRICE, updated.getPrice())
                .set(TABLE_EXAMINATION_PRICE.VERSION, updated.getVersion() + 1)
                .where(TABLE_EXAMINATION_PRICE.ID.eq(updated.getId())
                        .and(TABLE_EXAMINATION_PRICE.VERSION.eq(updated.getVersion())));
    }
}
