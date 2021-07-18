package com.techelevator.view;

import com.techelevator.PromptFileReader;


import java.util.Scanner;

/*
 * This is the only class that should have any usage of System.out or System.in
 */
public class Menu {

    private  final Scanner in = new Scanner(System.in);

    public void showWelcomeMessage() {
        System.out.println("*************************");
        System.out.println("**     Weyland Corp.   **");
        System.out.println("**      Catering       **");
        System.out.println("*************************");
        System.out.println();
    }



    public int userChoice() {
        Scanner in = new Scanner(System.in);
        System.out.println();
        System.out.println("(1) Display Catering Items");
        System.out.println("(2) Order");
        System.out.println("(3) Quit");
        return  waitForInt();

    }

    public int purchasingMenu(double balance) {
        Scanner in = new Scanner(System.in);
        System.out.println();
        System.out.println("(1) Add Money");
        System.out.println("(2) Select Products");
        System.out.println("(3) Complete Transaction");
        System.out.println("Current Account Balance: $" + balance);
        return  waitForInt();
    }

    public int addMoneyOptions() {
        Scanner in = new Scanner(System.in);
        System.out.println("Add money amount in whole dollar: $ ");

        return  waitForInt();

    }

    public int addMoneyToAccountPrompt(double balance) {

        System.out.println("The amount entered will make balance EXCEED $5000. Try another amount.");
        System.out.println("Current Balance Amount: " + balance);
        return addMoneyOptions();
    }


    public String selectProductToPurchase() {
        System.out.println("Provide Product Code to add to Cart: ");
        String userChoice = in.nextLine();

        return userChoice;

    }

    public int selectProductToPurchaseAmount() {
        System.out.println("How much of the product you would like to add to cart: ");
        return waitForInt();
    }

    public void printStringMessage(String message) {
        System.out.println(message);
    }

    public int waitForInt(){
        int userInt=0;
        Scanner in= new Scanner(System.in);
        while (!in.hasNextInt()){
           in.next();
        }
        userInt=in.nextInt();

        return  userInt;


    }


}
