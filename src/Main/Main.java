package Main;

import Services.InterrogationService;

import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        InterrogationService service = new InterrogationService();
        service.loadDataFromDb();
        boolean exit = false;
        Scanner in = new Scanner(System.in);
        while(!exit)
        {
            System.out.println("Option 1 : ADD CLIENT \n" +
                    "Option 2 : ADD CLIENTS FROM CSV \n" +
                    "Option 3 : SHOW CLIENT PERSONAL DATA \n" +
                    "Option 4 : SHOW ALL CLIENTS \n" +
                    "Option 5 : DELETE CLIENT \n" +
                    "Option 6 : UPDATE CLIENT \n" +
                    "Option 7 : CREATE ACCOUNT \n" +
                    "Option 8 : ADD ACCOUNTS FROM CSV \n" +
                    "Option 9 : DELETE ACCOUNT \n" +
                    "Option 10 : SHOW ALL ACCOUNTS \n" +
                    "Option 11 : CREATE CARD \n" +
                    "Option 12 : ADD CARDS FROM CSV \n" +
                    "Option 13 : DELETE CARD \n" +
                    "Option 14 : UPDATE CARD \n" +
                    "Option 15 : SHOW ALL CARDS \n" +
                    "Option 16 : CREATE TRANSACTION \n" +
                    "Option 17 : ADD TRANSACTIONS FROM CSV \n" +
                    "Option 18 : SHOW TRANSACTION HISTORY \n" +
                    "Option 19 : CREATE SAVINGS ACCOUNT \n" +
                    "Option 20 : SHOW SAVINGS ACCOUNTS \n" +
                    "Option 21 : UPDATE SAVINGS ACCOUNT \n" +
                    "Option 22 : DELETE SAVINGS ACCOUNT \n" +
                    "Option 23 : EXIT");
            System.out.print("Enter your option: ");
            int option = in.nextInt();
            switch (option)
            {
                case 1 -> service.addClient();
                case 2 -> service.addClientsFromCsv();
                case 3 -> service.showClient();
                case 4 -> service.showClients();
                case 5 -> service.deleteClient();
                case 6 -> service.updateClient();
                case 7 -> service.createAccount();
                case 8 -> service.addAccountsFromCsv();
                case 9 -> service.deleteAccount();
                case 10 -> service.showAccounts();
                case 11 -> service.createCard();
                case 12 -> service.addCardsFromCsv();
                case 13 -> service.deleteCard();
                case 14 -> service.updateCard();
                case 15 -> service.showCards();
                case 16 -> service.createTransaction();
                case 17 -> service.addTransactionsFromCsv();
                case 18 -> service.showTransactions();
                case 19 -> service.createSavingsAccount();
                case 20 -> service.showSavingsAccounts();
                case 21 -> service.updateSavingsAccount();
                case 22 -> service.deleteSavingsAccount();
                case 23 -> exit = true;
                default -> System.out.println("Invalid command");
            }
        }
    }
}
