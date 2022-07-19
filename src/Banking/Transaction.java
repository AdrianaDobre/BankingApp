package Banking;

import java.util.Date;

public class Transaction
{
    private int idTransaction;
    private String fromAccount;
    private String toAccount;
    private float amount;
    private String description;
    private Date dateOfTransaction;

//    constructor
    public Transaction(int idTransaction,String fromAccount, String toAccount, float amount, String description, Date dateOfTransaction)
    {
        this.idTransaction = idTransaction;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.description = description;
        this.dateOfTransaction = dateOfTransaction;
    }

//    getters
    public int getIdTransaction()
    {
        return idTransaction;
    }

    public String getFromIban()
    {
        return this.fromAccount;
    }

    public String getToIban()
    {
        return this.toAccount;
    }

    public float getAmount()
    {
        return this.amount;
    }

    public String getDescription()
    {
        return this.description;
    }

    public Date getDateOfTransaction()
    {
        return this.dateOfTransaction;
    }

//    setters
    public void setIdTransaction()
    {
        this.idTransaction = idTransaction;
    }

    public void setFromIban(String fromAccount)
    {
        this.fromAccount = fromAccount;
    }

    public void setToIban(String toAccount)
    {
        this.toAccount = toAccount;
    }

    public void setAmount(float amount)
    {
        this.amount = amount;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setDateOfTransaction(Date dateOfTransaction)
    {
        this.dateOfTransaction = dateOfTransaction;
    }

    public String toString()
    {
        return "Id Transaction: " + idTransaction +
                "\nFrom Account: " + fromAccount +
                "\ntoAccount: " + toAccount +
                "\nAmount: " + amount +
                "\nDescription: " + description +
                "\nDate Of Transaction: " + dateOfTransaction;
    }

}
