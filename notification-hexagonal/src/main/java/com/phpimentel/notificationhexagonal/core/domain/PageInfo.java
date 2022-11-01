package com.phpimentel.notificationhexagonal.core.domain;

import java.io.Serializable;

public class PageInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private int pageNumber;
    private int pageSize;

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
