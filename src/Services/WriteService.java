package Services;

import Banking.*;
import Person.Client;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;

public class WriteService
{
    private static WriteService instance = null;

    private WriteService() {}

    public static WriteService getInstance()
    {
        if (instance == null)
        {
            instance = new WriteService();
        }
        return instance;
    }

    public void showClientCreated(String type, Client object)
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try (var out = new BufferedWriter(new FileWriter("CSV/createdObjects.csv", true)))
        {
            String message = "Tip obiect: " + type + " \nA fost creat: " + timestamp + "\nObiectul: " + object.toString() + "\n\n";
            out.write(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAccountCreated(String type, Account object)
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try (var out = new BufferedWriter(new FileWriter("CSV/createdObjects.csv", true)))
        {
            String message = "Tip obiect: " + type + " \nA fost creat: " + timestamp + "\nObiectul: " + object.toString() + "\n\n";
            out.write(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showCardCreated(String type,Card object)
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try (var out = new BufferedWriter(new FileWriter("CSV/createdObjects.csv", true)))
        {
            String message = "Tip obiect: " + type + " \nA fost creat: " + timestamp + "\nObiectul: " + object.toString() + "\n\n";
            out.write(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showTransactionCreated(String type, Transaction object)
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try (var out = new BufferedWriter(new FileWriter("CSV/createdObjects.csv", true)))
        {
            String message = "Tip obiect: " + type + " \nA fost creat: " + timestamp + "\nObiectul: " + object.toString() + "\n\n";
            out.write(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showSavingsAccountCreated(String type, SavingsAccount object)
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try (var out = new BufferedWriter(new FileWriter("CSV/createdObjects.csv", true)))
        {
            String message = "Tip obiect: " + type + " \nA fost creat: " + timestamp + "\nObiectul: " + object.toString() + "\n\n";
            out.write(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
