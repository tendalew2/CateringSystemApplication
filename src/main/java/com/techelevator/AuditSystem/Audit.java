package com.techelevator.AuditSystem;

import com.techelevator.model.ordering.Cart;
import com.techelevator.model.ordering.CustomerAccount;
import com.techelevator.service.CateringService;
import com.techelevator.service.OrderingService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.List;

public class Audit {

   List <String> logs;

   /*
   Create a log system that adds time
   -might need a list that displays the required parts of the audit entries
    */

    //Audit should give a list

    public Audit() {
       this.logs= new ArrayList<>();
    }
    /*

    create addLog that displays requirements
   -need to grab from OrderingService (CompleteTransaction-gives change and items bought), CustomerAccount
   has updated balance
    */
    public void addToLog(String message) {

        String addToCartTimestamp = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(System.currentTimeMillis());


        logs.add(addToCartTimestamp+ " "+ message);

    }

    /*
    create print writer that will display transaction. How to display it in log.file??? should we find a way that
    shows log.txt when it runs.
    */

    public void writeLogToFile(){
        String pathToWrite= "Log.txt"; //Try if this could run

        File fileToWrite= new File(pathToWrite);
        try (PrintWriter printWriter = new PrintWriter(fileToWrite)){
            for(String log: logs){
                printWriter.println(log);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



    }



}
