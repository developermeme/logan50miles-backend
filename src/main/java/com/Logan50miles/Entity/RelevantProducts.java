package com.Logan50miles.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class,property = "id")
public class RelevantProducts {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int rId;
    @ManyToOne
    @JoinColumn(name="mc_Id")
    private Products mcId;

    public RelevantProducts() {
    }

    public int getrId() {
        return rId;
    }

    public void setrId(int rId) {
        this.rId = rId;
    }

    public Products getMcId() {
        return mcId;
    }

    public void setMcId(Products mcId) {
        this.mcId = mcId;
    }
}
