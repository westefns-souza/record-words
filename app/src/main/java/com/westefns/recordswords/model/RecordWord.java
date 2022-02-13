package com.westefns.recordswords.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class RecordWord implements Serializable {
    private Long Id;
    private String word;
    private String classification;
    private String translation;
    private Timestamp createAt;
    private List<PhraseExample> frases = new ArrayList<>();

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public List<PhraseExample> getPhrases() {
        return frases;
    }

    public void setFrases(List<PhraseExample> frases) {
        this.frases = frases;
    }

    @Override
    public String toString() {
        return word;
    }
}
