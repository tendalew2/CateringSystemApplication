package com.techelevator.model.ordering;


import com.techelevator.AuditSystem.Audit;

import java.text.SimpleDateFormat;

public class CustomerAccount {

    private double balance;

    private final int MAX_BALANCE_LIMIT = 5000;
    protected String addMoneyTimeStamp;

    private final Audit audit;

    public CustomerAccount(Audit audit) {
        this.balance = 0.00;
        this.audit = audit;
    }

    public double getBalance() {
        return balance;
    }

    /**
     * adds
     *
     * @return true is money can be added false otherwise
     */

    public boolean updateBalance(double amount) {
        boolean moneyAdded = false;


        if ((amount + balance <= MAX_BALANCE_LIMIT)) {

            double prevBalance = balance;

            moneyAdded = true;
            balance += amount;


            if (amount > 0) {
                audit.addToLog("ADD Money: " + prevBalance + " " + balance);
            }


        }
        return moneyAdded;

    }
}
