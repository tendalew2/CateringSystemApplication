package com.techelevator;


import com.techelevator.AuditSystem.Audit;
import com.techelevator.model.ordering.CustomerAccount;

//import com.techelevator.model.Ordering.CustomerAccount;
import com.techelevator.service.CateringService;
import com.techelevator.service.OrderingService;
import com.techelevator.view.Menu;

/*
 * This class should control the workflow of the application, but not do any other work
 *
 * The menu class should communicate with the user, but do no other work
 *
 * The work of the Catering System should be in other classes that we build and
 * call from here.
 */
public class CateringSystemCLI {

    /*
     * The menu class is instantiated in the main() method at the bottom of this file.
     * It is the only class instantiated in the starter code.
     *  We need to instantiate all other classes using the new keyword before we can use them.
     *
     * Every class and data structure is a data types and can be passed as arguments to methods or constructors.
     */
    private Menu menu;
    private CateringService cateringService;
    private OrderingService orderingService;
    private Audit audit;


    public CateringSystemCLI(Menu menu) {
        this.menu = menu;
    }

    /*
     * Your application starts here
     */
    public void run() {


        //show welcome message
        menu.showWelcomeMessage();


        //initialize all the service and accounts needed
        cateringService = new CateringService();

        //initialize Audit for logging service
        audit= new Audit();

        //ordering service needs inventory to work with so pass cateringService into ordering service
        orderingService = new OrderingService(cateringService,audit);


        while (true) {

            CustomerAccount customerAccount = orderingService.getCustomerAccount();

            int userChoice = menu.userChoice();

            if (userChoice == 1) {
                cateringService.inventoryReport();


            } else if (userChoice == 2) {
                //order  menu
                double customerBalance = customerAccount.getBalance();
                int purchaseMenuChoice = menu.purchasingMenu(customerBalance);

                if (purchaseMenuChoice == 1) {
                    int amount = menu.addMoneyOptions();

                    while (!customerAccount.updateBalance(amount)) {
                        amount = menu.addMoneyToAccountPrompt(customerBalance);
                    }
                } else if (purchaseMenuChoice == 2) {

                    String productToPurchase = menu.selectProductToPurchase();


                    int productAmount = menu.selectProductToPurchaseAmount();

                    String orderResponse = orderingService.order(productToPurchase, productAmount);

                    menu.printStringMessage(orderResponse);
                } else if (purchaseMenuChoice == 3) {
                    System.out.println("\n");
                    orderingService.completeTransaction();

                    //write log file
                    audit.writeLogToFile();

                }


            } else if (userChoice == 3) {
                break;
            }

        }

    }

    /*
     * This starts the application, but we shouldn't need to change it.
     */
    public static void main(String[] args) {
        Menu menu = new Menu();
        CateringSystemCLI cli = new CateringSystemCLI(menu);
        cli.run();
    }
}
