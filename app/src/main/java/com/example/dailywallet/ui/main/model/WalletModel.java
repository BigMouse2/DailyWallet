package com.example.dailywallet.ui.main.model;

import com.google.firebase.firestore.Exclude;

import java.util.Date;

public class WalletModel {

    private String documentId;
    private String name;
    private float budgetAmount;
    private String currency;
    private Date startDate;
    private Date endDate;

    public WalletModel() {
        //non-args construc for firestore
    }

    public WalletModel(String name, float budgetAmount) {
        this.name = name;
        this.budgetAmount = budgetAmount;
    }

    @Exclude //pour ne pas envoyer l'ID dans la bdd -> éviter les redondances
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(float budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
