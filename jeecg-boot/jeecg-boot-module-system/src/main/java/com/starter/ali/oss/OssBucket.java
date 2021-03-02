package com.starter.ali.oss;

/**
 * @author dingxl
 * @date 3/1/2021 5:35 PM
 */
public enum OssBucket {
    /**
     * OSS bucket
     */
    UPLOAD("mivp-rel-upload"),
    DOC("mivp-rel-doc");

    private final String name;

    OssBucket(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
