package com.example.dailywallet.ui.main.model;

import com.google.firebase.firestore.Exclude;
import com.google.type.DateTime;

import java.util.Date;

public class ReceiptModel {

    private String documentId;
    private String name;
    private DateTime hour;
    private String date;
    private String note;
    private float price;

    public ReceiptModel() {
        //non-args construc for firestore
    }

    public ReceiptModel(String name) {
        this.name = name;
    }

    public ReceiptModel(String name, String date, String note, float price) {
        this.name = name;
        this.date = date;
        this.note = note;
        this.price = price;
    }

    public ReceiptModel(DateTime hour, float price) {
        this.hour = hour;
        this.price = price;
    }

    public ReceiptModel(String name, DateTime hour, String date, String note, float price) {
        this.name = name;
        this.hour = hour;
        this.date = date;
        this.note = note;
        this.price = price;
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

    public DateTime getHour() {
        return hour;
    }

    public void setHour(DateTime hour) {
        this.hour = hour;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

}
