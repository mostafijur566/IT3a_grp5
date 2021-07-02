package com.example.it3a_grp5;

public class SendMoneyClass {
    String amount;
    String name;
    String SendMoney;

    public SendMoneyClass()
    {

    }

    public SendMoneyClass(String amount, String name, String sendMoney) {
        this.amount = amount;
        this.name = name;
        SendMoney = sendMoney;
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

    public String getSendMoney() {
        return SendMoney;
    }

    public void setSendMoney(String sendMoney) {
        SendMoney = sendMoney;
    }
}
