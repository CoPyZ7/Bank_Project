package com.example.bank_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.Optional;

public class BankingController implements Initializable {

    @FXML
    TextField tfUsername;
    @FXML
    PasswordField pfPassword;
    @FXML
    Button btnLogin;
    @FXML
    Button btnTransfer;
    @FXML
    Button btnTransferBetweenAccounts;
    @FXML
    Button btnGoBackToAccounts;
    @FXML
    Button btnGoBackToTransfers;
    @FXML
    Button btnPayBills;
    @FXML
    MenuButton menuSelectFromAccount;
    @FXML
    MenuButton menuSelectToAccount;
    @FXML
    TextField tfTransferAmount;
    @FXML
    Label lbAccountDebit;
    @FXML
    Label lbAccountSavings;
    @FXML
    Label lbAccountCredit;
    @FXML
    Label lbAccountDebitAmount = new Label("0");
    @FXML
    Text lbAccountSavingsAmount = new Text("0");
    @FXML
    Text lbAccountCreditBalance = new Text("0");
    @FXML
    Text lbAccountCreditAvailableCredit = new Text("0");
    @FXML
    Text lbAccountCreditCreditLimit = new Text("0");
    @FXML
    Text lbMoneyOwed = new Text("0");
    @FXML
    MenuButton menuSelectAccountBills = new MenuButton("test");
    @FXML
    MenuButton menuSelectToPayee = new MenuButton("test");
    @FXML
    TextField tfAmountBills;

    @FXML
    Label lbTransferErrorMessage;
    @FXML
    Label lbBillErrorMessage;

    private MenuItem selectedFromAccount;
    private MenuItem selectedToAccount;
    private Double creditLimit = 1000.0;
    MenuItem selectedBillFromAccount;
    MenuItem selectedToPayee;

    public BankingController() throws IOException {}
    Alert a = new Alert(Alert.AlertType.ERROR);

    @Override
    public void initialize(URL url, ResourceBundle rb){

        if (menuSelectFromAccount != null) {
            for (Accounts account : Accounts.getAccountsArrayList()){
                MenuItem menuItem = new MenuItem(account.getAccountName());
                menuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        selectedFromAccount = menuItem;
                        menuSelectFromAccount.setText(account.getAccountName());

                    }
                });
                menuSelectFromAccount.getItems().add(menuItem);
            }

            for (Accounts account : Accounts.getAccountsArrayList()){
                MenuItem menuItem = new MenuItem(account.getAccountName());
                menuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        selectedToAccount = menuItem;
                        menuSelectToAccount.setText(account.getAccountName());
                    }
                });
                menuSelectToAccount.getItems().add(menuItem);
            }
        }

        if(menuSelectAccountBills != null){
            for(Accounts account : Accounts.getAccountsArrayList()){
                MenuItem menuItem3 = new MenuItem(account.getAccountName());
                menuItem3.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        selectedBillFromAccount = menuItem3;
                        menuSelectAccountBills.setText(account.getAccountName());
                    }
                });
                menuSelectAccountBills.getItems().add(menuItem3);
            }
            for(Accounts account : Accounts.getDebtArrayList()){
                MenuItem menuItem4 = new MenuItem(account.getAccountName());
                menuItem4.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        selectedToPayee = menuItem4;
                        menuSelectToPayee.setText(account.getAccountName());
                        lbMoneyOwed.setText(Double.toString(account.getAccountBalance()));
                    }
                });
                menuSelectToPayee.getItems().add(menuItem4);
            }

        }

        refreshAccountBalances();
    }

    public void payBills(){
        String billFromAccountName = selectedBillFromAccount.getText();
        String toPayeeName = selectedToPayee.getText();
        Accounts fromAccount = null;
        Accounts toPayee = null;

        for(Accounts a : Accounts.getAccountsArrayList()){
            if (a.getAccountName().equals(billFromAccountName)){
                fromAccount = a;
            }
        }
        for(Accounts a : Accounts.getDebtArrayList()){
            if (a.getAccountName().equals(toPayeeName)){
                toPayee = a;
            }
        }
        if (fromAccount.accountBalance < Double.parseDouble(tfAmountBills.getText())) {
            lbBillErrorMessage.setText("Not Enough Money!");
        }
        else {
            fromAccount.subtractAccountBalance(Double.parseDouble(tfAmountBills.getText()));
            toPayee.subtractAccountBalance(Double.parseDouble(tfAmountBills.getText()));
            refreshAccountBalances();
            lbBillErrorMessage.setText("");
        }

    }
    public void transferMoneyBetweenAccounts(){
        String fromAccountName = selectedFromAccount.getText();
        String toAccountName = selectedToAccount.getText();
        Accounts fromAccount = null;
        Accounts toAccount = null;

        for(Accounts a : Accounts.getAccountsArrayList()){
            if(a.getAccountName().equals(fromAccountName)) {
                fromAccount = a;
            }
        }

        for(Accounts a : Accounts.getAccountsArrayList()){
            if(a.getAccountName().equals(toAccountName)){
                toAccount = a;
            }
        }

        if (fromAccount.accountBalance < Double.parseDouble(tfTransferAmount.getText())) {
            lbTransferErrorMessage.setText("Not Enough Money!");
        }
        else{
            fromAccount.subtractAccountBalance(Double.parseDouble(tfTransferAmount.getText()));
            toAccount.addAccountBalance(Double.parseDouble(tfTransferAmount.getText()));
            lbTransferErrorMessage.setText("");
        }

    }




    //*******   Refreshes each Account's Balance Label when calleds  ***********
    public void refreshAccountBalances(){
        String targetName = "Debit";
        for (Accounts a : Accounts.getAccountsArrayList()){
            if (targetName.equals(a.getAccountName())) {
                lbAccountDebitAmount.setText(Double.toString(a.getAccountBalance()));
            }
        }

        String targetName2 = "Momentum Minus Savings";
        for (Accounts a : Accounts.getAccountsArrayList()){
            if (targetName2.equals(a.getAccountName())) {
                lbAccountSavingsAmount.setText(Double.toString(a.getAccountBalance()));
            }
        }

        String targetName3 = "Scene-";
        for (Accounts a : Accounts.getAccountsArrayList()){
            if (targetName3.equals(a.getAccountName())) {
                lbAccountCreditBalance.setText(Double.toString(a.getAccountBalance()));
                lbAccountCreditAvailableCredit.setText(Double.toString(creditLimit - a.getAccountBalance()));
                lbAccountCreditCreditLimit.setText(Double.toString(creditLimit));
            }
        }
        String targetName4 = "Loan";
        for (Accounts a : Accounts.getDebtArrayList()){
            if (targetName4.equals(a.getAccountName())) {
                lbMoneyOwed.setText(Double.toString(a.getAccountBalance()));
            }
        }
    }

    //---------------------------------------- switching the scene ----------------------------------------
    //Method ensures that the user login info is valid, it then loads and switches
    //the scene to the main Accounts scene.
    public void loginOnButtonClick(ActionEvent event) throws Exception{
        Parent accountsMenu = FXMLLoader.load(getClass().getResource("Accounts.fxml"));
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        if(Login.verifyCredentials(tfUsername.getText(), pfPassword.getText())){
            stage.setScene(new Scene(accountsMenu));
        }
//        if(Login.loginValidator(tfUsername.getText(), pfPassword.getText())){
//            stage.setScene(new Scene(accountsMenu));
//        }

        else{
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Incorrect Login Credentials");
            Optional<ButtonType> result = a.showAndWait();
            if(result.get() == ButtonType.OK){
                a.close();
            }
        }
    }


    //When called, switches the scene back to the main Accounts scene.
    public void goBackToAccountsMenu() throws Exception{
        Parent accountsMenu = FXMLLoader.load(getClass().getResource("Accounts.fxml"));
        Stage stage = (Stage) btnGoBackToAccounts.getScene().getWindow();
        stage.setScene(new Scene(accountsMenu));
    }

    //self-explanatory, same as above but different scene.
    public void goBackToTransfersMenu() throws Exception{
        Parent transfersMenu = FXMLLoader.load(getClass().getResource("Transfers.fxml"));
        Stage stage = (Stage) btnGoBackToTransfers.getScene().getWindow();
        stage.setScene(new Scene(transfersMenu));
    }
    public void goToTransfers() throws Exception{
        Parent transfersMenu = FXMLLoader.load(getClass().getResource("Transfers.fxml"));
        Stage stage = (Stage) btnTransfer.getScene().getWindow();
        stage.setScene(new Scene(transfersMenu));
    }
    public void goToTransferBetweenAccounts() throws Exception{
        Parent transferBetweenAccountsMenu = FXMLLoader.load(getClass().getResource("TransferBetweenAccounts.fxml"));
        Stage stage = (Stage) btnTransferBetweenAccounts.getScene().getWindow();
        stage.setScene(new Scene(transferBetweenAccountsMenu));
    }
    public void goToPayBills() throws Exception{
        Parent transferBillsLoansMenu = FXMLLoader.load(getClass().getResource("PayBills.fxml"));
        Stage stage = (Stage) btnPayBills.getScene().getWindow();
        stage.setScene(new Scene(transferBillsLoansMenu));
    }


    public void exit(ActionEvent event) {
        a.setAlertType(Alert.AlertType.CONFIRMATION);
        a.setContentText("Are you sure you want to quit?");
        Optional<ButtonType> result = a.showAndWait();
        if(result.get() == ButtonType.OK){
            Platform.exit();
        }
        else if (result.get() == ButtonType.CANCEL){
            a.close();
        }
    }

    public void writeToFile() throws FileNotFoundException {
         File fileDescriptor = new File("Users.txt");
         PrintWriter writer = new PrintWriter(fileDescriptor);

    }

    //------------------------------------------------------------------------------------------------------------------------
}
