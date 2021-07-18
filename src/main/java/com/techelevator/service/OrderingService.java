package com.techelevator.service;

import com.techelevator.AuditSystem.Audit;
import com.techelevator.model.catering.Product;
import com.techelevator.model.catering.ProductShelf;
import com.techelevator.model.ordering.Cart;
import com.techelevator.model.ordering.CustomerAccount;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class OrderingService {
    /*
    Function of this class:
    Access CustomerAccount
     -decide if user can purchase

     Access CateringService
     -Manage if the product is available in catering inventory

      Access Cart
      -to update when order is made
      Methods needed: order, complete transaction
      */

    private final CustomerAccount customerAccount;

    private final CateringService cateringService;

    private final Cart cart;

    private final Audit audit;


    public OrderingService(CateringService cateringService, Audit audit) {
        this.cateringService = cateringService;
        customerAccount = new CustomerAccount(audit);
        cart = new Cart();
        this.audit = audit;


    }

    //to order users must pass productCode and amount they want
    public String order(String productCode, int amount) {
        Map<String, ProductShelf> inventory = cateringService.getProductInventory();

        if (!inventory.containsKey(productCode)) {
            return "Product NOT FOUND in inventory!";
        }

        Product product = inventory.get(productCode).getProduct();
        int amountInStock = inventory.get(productCode).getNumOfProducts();
        if (amountInStock == 0) {
            return "SOLD OUT!";
        }
        if (amountInStock < amount) {
            return "Insufficient Stock!";
        }

        double requiredBalanceForPurchase = product.getProductPrice() * amount;

        if (customerAccount.getBalance() < requiredBalanceForPurchase) {
            return "NOT ENOUGH FUND available for purchase! Please add more money to balance.";
        }

        //remove the cost of product from CustomerAccount
        customerAccount.updateBalance(-1 * requiredBalanceForPurchase);


        //there must be enough
        cateringService.removeFromShelf(productCode, amount);

        //update Cart
        cart.addToCart(productCode, amount);

//        String addToCartTimestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(System.currentTimeMillis());

        return "Successfully added to cart! :)";


    }

    public void completeTransaction() {
        Map<String, Integer> cartItems = cart.getCartItems();
        double totalAmount = 0;

        for (String productCode : cartItems.keySet()) {

            Product product = cateringService.getProductInventory().get(productCode).getProduct();

            int numOfProduct = cartItems.get(productCode);
            String productType = product.toString();
            String productName = product.getProductName();
            Double productPrice = product.getProductPrice();
            Double tolCostPerProduct = numOfProduct * productPrice;


            System.out.printf("%-10s %-15s %-23s $%-15.2f $%-15.2f\n",
                    numOfProduct,
                    productType,
                    productName,
                    productPrice,
                    tolCostPerProduct);

            System.out.println("");


            totalAmount += tolCostPerProduct;

            audit.addToLog(numOfProduct + " " + productName + " " + productCode
                    + " " + totalAmount + " " + customerAccount.getBalance());


        }
        System.out.println("Total: " + totalAmount);


        //give back the user his change
        System.out.println("\n");

        final Double[] BILLS = {50.00, 20.00, 10.00, 100.00, 5.00, 1.00};
        final Double[] COINS = {0.5, 0.25, 0.1, 0.05, 0.01};

        double balanceBeforeChange = customerAccount.getBalance();

        double customerChangeDollar = (int) customerAccount.getBalance();
        double customerChangeCent = (customerAccount.getBalance() - customerChangeDollar);

        Map<Double, Integer> dollarChange = getChange(BILLS, customerChangeDollar);
        Map<Double, Integer> centChange = getChange(COINS, customerChangeCent);


        System.out.println("Your change is " + dollarChange + " dollars and " + centChange + " cents");


        //make user balance go to zero
        customerAccount.updateBalance(-1 * customerAccount.getBalance());

        audit.addToLog("GIVE CHANGE: " + balanceBeforeChange + " " + customerAccount.getBalance());

    }

    public CustomerAccount getCustomerAccount() {
        return customerAccount;
    }


    //giving the smallest amount of change, draw this out!

    private Map<Double, Integer> getChange(Double[] changeDrawer, Double moneyToChange) {


        //need to sort the change drawer in dec. order
        Arrays.sort(changeDrawer, Collections.reverseOrder());

        //initialize our Map that will contain the changes and their count
        Map<Double, Integer> changeToGive = new HashMap<>();

        for (int i = 0; i < changeDrawer.length; i++) {
            if (changeDrawer[i] <= moneyToChange) {

                int billCount = 0;

                if (changeToGive.containsKey(changeDrawer[i])) {
                    billCount = changeToGive.get(changeDrawer[i]);
                }
                changeToGive.put(changeDrawer[i], billCount + 1);
                moneyToChange = moneyToChange - changeDrawer[i];
                if (moneyToChange >= changeDrawer[i]) {
                    i--;
                }

            }

        }
        return changeToGive;

    }


}
