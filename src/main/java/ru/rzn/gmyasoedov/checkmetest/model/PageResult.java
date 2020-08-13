package ru.rzn.gmyasoedov.checkmetest.model;

import io.github.jklingsporn.vertx.jooq.shared.internal.VertxPojo;

import java.util.List;

public class PageResult<T extends VertxPojo> {
    private final List<T> result;
    private final int count;
    private final int offset;
    private final int limit;

    public PageResult(List<T> result, int count, int offset, int limit) {
        this.result = result;
        this.count = count;
        this.offset = offset;
        this.limit = limit;
    }

    public List<T> getResult() {
        return result;
    }

    public int getCount() {
        return count;
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }
}
