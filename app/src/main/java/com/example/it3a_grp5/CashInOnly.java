package com.example.it3a_grp5;

public class CashInOnly {

    String amount;
    String name;
    String CashIn;

    public CashInOnly()
    {

    }

    public CashInOnly(String amount, String name, String cashIn) {
        this.amount = amount;
        this.name = name;
        CashIn = cashIn;
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

    public String getCashIn() {
        return CashIn;
    }

    public void setCashIn(String cashIn) {
        CashIn = cashIn;
    }
}
