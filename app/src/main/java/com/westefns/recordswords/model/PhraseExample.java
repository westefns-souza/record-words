package com.westefns.recordswords.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class PhraseExample implements Serializable {
    private Long Id;
    private String Word;
    private String Exemple;
    private Timestamp createAt;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getRecordWord() {
        return Word;
    }

    public void setRecordWord(String word) {
        Word = word;
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
