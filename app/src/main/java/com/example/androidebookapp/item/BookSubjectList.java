package com.example.androidebookapp.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BookSubjectList implements Serializable {

    @SerializedName("subjects_id")
    @Expose
    private String subjectsId;
    @SerializedName("subj_lang_type")
    @Expose
    private String subjLangType;
    @SerializedName("subj_title")
    @Expose
    private String subjTitle;
    @SerializedName("subj_subTitle")
    @Expose
    private String subjSubTitle;
    @SerializedName("subj_image")
    @Expose
    private String subjImage;

    public String getSubjectsId() {
        return subjectsId;
    }

    public void setSubjectsId(String subjectsId) {
        this.subjectsId = subjectsId;
    }

    public String getSubjLangType() {
        return subjLangType;
    }

    public void setSubjLangType(String subjLangType) {
        this.subjLangType = subjLangType;
    }

    public String getSubjTitle() {
        return subjTitle;
    }

    public void setSubjTitle(String subjTitle) {
        this.subjTitle = subjTitle;
    }

    public String getSubjSubTitle() {
        return subjSubTitle;
    }

    public void setSubjSubTitle(String subjSubTitle) {
        this.subjSubTitle = subjSubTitle;
    }

    public String getSubjImage() {
        return subjImage;
    }

    public void setSubjImage(String subjImage) {
        this.subjImage = subjImage;
    }
}
