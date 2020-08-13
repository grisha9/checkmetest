package ru.rzn.gmyasoedov.checkmetest.web.dto;

public class ExaminationPriceRequest {
    private final long examinationId;
    private final int offset;
    private final int limit;
    private final boolean descPrice;

    public ExaminationPriceRequest(long examinationId, int offset, int limit, boolean descPrice) {
        this.examinationId = examinationId;
        this.offset = offset;
        this.limit = limit;
        this.descPrice = descPrice;
    }

    public long getExaminationId() {
        return examinationId;
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

    public boolean isDescPrice() {
        return descPrice;
    }
}
