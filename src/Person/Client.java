package Person;

import Banking.Account;
import Banking.Card;
import Banking.SavingsAccount;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Client extends Person
{
    private int clientId;
    private List<Account> accounts = new ArrayList<>();
    private List<SavingsAccount> savingsAccounts = new ArrayList<>();

    public Client(String firstName, String lastName, String phoneNumber, String address,Date dateOfBirth, String mailAddress, int clientId)
    {
       super(firstName,lastName,phoneNumber, address,dateOfBirth,mailAddress);
       this.clientId = clientId;
    }

    public int getClientId()
    {
        return this.clientId;
    }

    public List<Account> getAccounts()
    {
        return this.accounts;
    }

    public List<SavingsAccount> getSavingsAccounts()
    {
        return this.savingsAccounts;
    }

    public void setClientId(int newClientId)
    {
        this.clientId = newClientId;
    }

    public void setAccounts(List<Account> accounts)
    {
        this.accounts = accounts;
    }

    public void setSavingsAccounts(List<SavingsAccount> savingsAccounts)
    {
        this.savingsAccounts = savingsAccounts;
    }

    public void addAccount(Account account)
    {
        accounts.add(account);
    }

    public void addSavingsAccount(SavingsAccount savingsAccount)
    {
        savingsAccounts.add(savingsAccount);
    }

    public void deleteAccount(int accountId)
    {
        for (Account account : accounts)
        {
            if (account.getAccountId() == accountId)
            {
                accounts.remove(account);
                break;
            }
        }
    }
    public Account getAccountById(int accountId)
    {
        for (Account account : accounts)
        {
            if (account.getAccountId() == accountId)
            {
                return account;
            }
        }
        return null;
    }

    public SavingsAccount getSavingsAccountById(int accountId)
    {
        for (SavingsAccount account : savingsAccounts)
        {
            if (account.getAccountId() == accountId)
            {
                return account;
            }
        }
        return null;
    }

    public void deleteSavingsAccount(int accountId)
    {
        for (SavingsAccount account : savingsAccounts)
        {
            if (account.getAccountId() == accountId)
            {
                savingsAccounts.remove(account);
                break;
            }
        }
    }
    public void deleteAccounts()
    {
        accounts.clear();
    }

    public void deleteSavingsAccount()
    {
        savingsAccounts.clear();
    }

    @Override
    public String toString()
    {
        return "Client Id: " + clientId + super.toString();
    }
}