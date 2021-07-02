package com.example.it3a_grp5;

public class UserInformation {

    String amount;
    String name;

    public UserInformation()
    {

    }

    public UserInformation(String amount, String name) {
        this.amount = amount;
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
