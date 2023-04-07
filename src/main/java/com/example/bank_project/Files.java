package com.example.bank_project;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Files {
    File fileDescriptor = new File("Test.txt");
    PrintWriter writer = new PrintWriter(fileDescriptor);


    public void testWriteFile() {

    }

    //reads the text file
    public void testReadFile(){
            Scanner reader = null;
            try {
                reader = new Scanner(fileDescriptor);
                while (reader.hasNext()) {
                    String record = reader.nextLine();
                    System.out.println(record);
                }
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            finally{
                if (reader != null){
                    reader.close();
                }
            }
        }


    public Files() throws FileNotFoundException {
    }
}
