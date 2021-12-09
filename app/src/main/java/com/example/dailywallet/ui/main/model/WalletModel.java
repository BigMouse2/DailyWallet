package com.example.dailywallet.ui.main.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;
import java.util.Date;

public class WalletModel implements Parcelable {

    private String documentId;
    private String name;
    private float budgetAmount;
    private String currency;
    private String startDate;
    private String endDate;

    public WalletModel() {
        //non-args construc for firestore
    }

    public WalletModel(String name, float budgetAmount, String currency, String startDate, String endDate) {
        this.name = name;
        this.budgetAmount = budgetAmount;
        this.currency = currency;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public WalletModel(String documentId, String name, float budgetAmount, String currency, String startDate, String endDate) {
        this.documentId = documentId;
        this.name = name;
        this.budgetAmount = budgetAmount;
        this.currency = currency;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    protected WalletModel(Parcel in) {
        documentId = in.readString();
        name = in.readString();
        budgetAmount = in.readFloat();
        currency = in.readString();
        startDate = in.readString();
        endDate = in.readString();
    }

    public static final Creator<WalletModel> CREATOR = new Creator<WalletModel>() {
        @Override
        public WalletModel createFromParcel(Parcel in) {
            return new WalletModel(in);
        }
        @Override
        public WalletModel[] newArray(int size) {
            return new WalletModel[size];
        }
    };

    //pour ne pas envoyer l'ID dans la bdd -> éviter les redondances
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(documentId);
        parcel.writeString(name);
        parcel.writeFloat(budgetAmount);
        parcel.writeString(currency);
        parcel.writeString(startDate);
        parcel.writeString(endDate);
    }
}
