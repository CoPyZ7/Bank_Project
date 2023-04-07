package com.example.bank_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.*;

public class BankingApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLogin = new FXMLLoader(BankingApplication.class.getResource("LoginMenu.fxml"));
        Scene loginMenu = new Scene(fxmlLogin.load(), 350, 400);


        BankingController bank = new BankingController();

        Accounts debit = new Accounts(99999, "Debit", 1291.22);
        debit.addToAccountsArrayList();
        Accounts savings = new Accounts(12345, "Momentum Minus Savings", 15293.36);
        savings.addToAccountsArrayList();
        Accounts credit = new Accounts(00323, "Scene-", 374.28);
        credit.addToAccountsArrayList();


        Accounts loan = new Accounts(221122, "Loan", 190291);
        loan.addToDebtArrayList();


        stage.setScene(loginMenu);
        stage.show();


        File fileDescriptor = new File("LoginInfo.txt");
        PrintWriter writer = new PrintWriter(fileDescriptor);
    }

    public static void main(String[] args) throws FileNotFoundException {
        launch();
    }
}