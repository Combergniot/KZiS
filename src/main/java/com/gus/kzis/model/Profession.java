package com.gus.kzis.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor

@Entity
public class Profession {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String code;
    @Lob
    private String synthesis;
    @Lob
    private String tasks;
    @Lob
    private String additionalTasks;

    /**
     * Czy zawód został ujęty w klasyfikacji zawodów szkolnictwa zawodowego"
     */
    private Boolean vocationalEducation;

    @Lob
    private String url;

    public Profession() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSynthesis() {
        return synthesis;
    }

    public void setSynthesis(String synthesis) {
        this.synthesis = synthesis;
    }

    public String getTasks() {
        return tasks;
    }

    public void setTasks(String tasks) {
        this.tasks = tasks;
    }

    public String getAdditionalTasks() {
        return additionalTasks;
    }

    public void setAdditionalTasks(String additionalTasks) {
        this.additionalTasks = additionalTasks;
    }

    public Boolean getVocationalEducation() {
        return vocationalEducation;
    }

    public void setVocationalEducation(Boolean vocationalEducation) {
        this.vocationalEducation = vocationalEducation;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
