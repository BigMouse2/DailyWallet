package com.example.dailywallet.ui.main.model;

//import com.google.firebase.firestore.Exclude;

import com.google.firebase.firestore.Exclude;

public class CategoryModel {

    private String documentId;
    private String name;
    private ReceiptModel receipt;

    public CategoryModel() {
        //non-args constructur for firestore
    }

    public CategoryModel(String name) {
        this.name = name;
    }

    public CategoryModel(String name, ReceiptModel receipt) {
        this.name = name;
        this.receipt = receipt;
    }

    @Exclude //pour ne pas envoyer l'ID en bdd -> redondance
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

    public ReceiptModel getReceipt() {
        return receipt;
    }

    public void setReceipt(ReceiptModel receipt) {
        this.receipt = receipt;
    }
}
