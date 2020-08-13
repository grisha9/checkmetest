package ru.rzn.gmyasoedov.checkmetest.web.dto;

public class FilteredRequest {
    private final String nameFilter;
    private final int offset;
    private final int limit;

    public FilteredRequest(String nameFilter, int offset, int limit) {
        this.nameFilter = nameFilter;
        this.offset = offset;
        this.limit = limit;
    }

    public String getNameFilter() {
        return nameFilter;
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }
}
