package com.xiongyingqi.model;

import com.xiongyingqi.util.EntityHelper;

import java.io.Serializable;

/**
 * Created by xiongyingqi on 14-3-13.
 */
public class Person extends EntityHelper implements Serializable{
    private String fullName;
    private String description;
    private String country;
    private String company;
    private String userPrincipalName;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserPrincipalName() {
        return userPrincipalName;
    }

    public void setUserPrincipalName(String userPrincipalName) {
        this.userPrincipalName = userPrincipalName;
    }
}
