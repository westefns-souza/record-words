package com.westefns.recordswords.model;

import java.sql.Timestamp;

public class PhraseExample {
    private Long Id;
    private Long RecordWordId;
    private String Exemple;
    private Timestamp createAt;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Long getRecordWordId() {
        return RecordWordId;
    }

    public void setRecordWordId(Long recordWordId) {
        RecordWordId = recordWordId;
    }

    public String getExemple() {
        return Exemple;
    }

    public void setExemple(String exemple) {
        Exemple = exemple;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }
}
