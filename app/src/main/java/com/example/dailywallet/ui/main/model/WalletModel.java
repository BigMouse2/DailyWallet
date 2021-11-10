package com.example.dailywallet.ui.main.model;

import com.google.firebase.firestore.Exclude;

import java.util.Date;

public class WalletModel {

    private String documentId;
    private String name;
    private float budgetAmount;
    private String currency;
    private String startDate;
    private String endDate;
    private ReceiptModel receipt;


    public WalletModel() {
        //non-args construc for firestore
    }

    public WalletModel(String name, float budgetAmount) {
        this.name = name;
        this.budgetAmount = budgetAmount;
    }

    public WalletModel(String name, float budgetAmount, String currency, String startDate, String endDate) {
        this.name = name;
        this.budgetAmount = budgetAmount;
        this.currency = currency;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public WalletModel(String name, float budgetAmount, String startDate, String endDate) {
        this.name = name;
        this.budgetAmount = budgetAmount;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public ReceiptModel getReceipt() {
        return receipt;
    }

    public void setReceipt(ReceiptModel receipt) {
        this.receipt = receipt;
    }
}
