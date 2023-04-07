package com.example.bank_project;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Accounts {
    protected long accountNumber;
    protected String accountName;
    protected double accountBalance;

    private static ArrayList<Accounts> debtArrayList = new ArrayList<>();
    private static ArrayList<Accounts> accountsArrayList = new ArrayList<Accounts>();
    public static ArrayList<Accounts> getAccountsArrayList() {
        return accountsArrayList;
    }
    public static ArrayList<Accounts> getDebtArrayList() {
        return debtArrayList;
    }



    //getters
    public double getAccountBalance(){
        return accountBalance;
    }
    public String getAccountName() { return accountName;}





    public static double getTotalBalance(ArrayList<Accounts> acc) {
        double tot = 0;
        for(Accounts a : acc) {
            tot = tot + a.getAccountBalance();
        }
        return tot;
    }


    public void addToAccountsArrayList(){
        accountsArrayList.add(this);
    }
    public void addToDebtArrayList(){
        debtArrayList.add(this);
    }
    public void addAccountBalance(double amount) {
        this.accountBalance += amount;
    }
    public void subtractAccountBalance(double amount) {
        this.accountBalance -= amount;
    }

    public Accounts(){}
    public Accounts(long accountNumber, String accountName, double accountBalance) {
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.accountBalance = accountBalance;
    }



    public static void printAllAccountsToString(ArrayList<Accounts> acc){
        for(Accounts a : acc) {
            System.out.println(a.toString());
        }
    }


    // returns the balance of an account as a string
    public String balanceToString() {
        return String.valueOf(accountBalance);
    }

    @Override
    public String toString() {
        return "Account Name: " + accountName
                + "\n------------------------------------"
                + "\nAccount Number: " + accountNumber
                + "\nAccount Balance: " + accountBalance + "\n\n\n";
    }
}
