package com.paypal.forex.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TRANSACTION_RECORD")
public class TransactionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long applicationId;

    @Column
    private String assetType;

    @Column
    private double assetPrice;

    @Column
    private double blockedAsset;

    @Column
    private double initial_LTV;

    @Column
    private double loanAmount;

    @Column String email;


    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public double getAssetPrice() {
        return assetPrice;
    }

    public void setAssetPrice(double assetPrice) {
        this.assetPrice = assetPrice;
    }

    public double getBlockedAsset() {
        return blockedAsset;
    }

    public void setBlockedAsset(double blockedAsset) {
        this.blockedAsset = blockedAsset;
    }

    public double getInitial_LTV() {
        return initial_LTV;
    }

    public void setInitial_LTV(double initial_LTV) {
        this.initial_LTV = initial_LTV;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
