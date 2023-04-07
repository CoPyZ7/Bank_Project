package com.example.bank_project;


import java.io.*;
import java.util.Scanner;

public class Login {


    public static boolean verifyCredentials(String username, String password) throws IOException{

        File fileDescriptor = new File("LoginInfo.txt");
        PrintWriter writer = new PrintWriter(fileDescriptor);
        writer.print("Boiken,1234");
        writer.close();

        Scanner reader = new Scanner(fileDescriptor);
        while(reader.hasNext()){
            String record = reader.nextLine();
            String[] info = record.split(",");
            if (info[0].equals(username) && info[1].equals(password)){
                return true;
            }

        }
        reader.close();
        return false;
    }



}
