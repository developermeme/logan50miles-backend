package com.Logan50miles.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.poiji.annotation.ExcelCellName;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int imId;
    private String imageUrl;
    @ManyToOne
    @JoinColumn(name="mc_Id")
    @ExcelCellName("McId")
    private Products mcId;

    public ProductImage() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getImId() {
        return imId;
    }

    public void setImId(int imId) {
        this.imId = imId;
    }

    public Products getMcId() {
        return mcId;
    }

    public void setMcId(Products mcId) {
        this.mcId = mcId;
    }
}

