package Banking;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Account
{
    private int accountId;
    private String iban;
    private String swiftCode;
    private float sold;
    private List<Card> cards = new ArrayList<>();
    private List<Transaction> transactions = new ArrayList<>();

//    constructor
    public Account(int accountId,String iban, String swiftCode, float sold)
    {
        this.accountId = accountId;
        this.iban = iban;
        this.swiftCode = swiftCode;
        this.sold = sold;
    }

    public Account(int accountId,String iban, String swiftCode)
    {
        this.accountId = accountId;
        this.iban = iban;
        this.swiftCode = swiftCode;
        this.sold = 0;
    }

//    getters

    public int getAccountId()
    {
        return this.accountId;
    }

    public String getIban()
    {
        return this.iban;
    }

    public String getSwiftCode()
    {
        return this.swiftCode;
    }

    public float getSold()
    {
        return this.sold;
    }

    public List<Card> getCards()
    {
        return this.cards;
    }

    public List<Transaction> getTransactions()
    {
        return this.transactions;
    }

    //    setters

    public void setAccountId(int accountId)
    {
        this.accountId = accountId;
    }

    public void setIban(String iban)
    {
        this.iban = iban;
    }

    public void setSwiftCode(String swiftCode)
    {
        this.swiftCode = swiftCode;
    }

    public void setSold(float sold)
    {
        this.sold = sold;
    }

    public void setCards(List<Card> cards)
    {
        this.cards = cards;
    }

    public void setTransactions(List<Transaction> transactions)
    {
        this.transactions = transactions;
    }

    public void addCard(Card card)
    {
        cards.add(card);
    }

    public void deleteCard(int cardId)
    {
        for (Card card : cards)
        {
            if (card.getCardId() == cardId)
            {
                cards.remove(card);
                break;
            }
        }
    }

    public void deleteCards()
    {
        cards.clear();
    }

    public void deleteTransactions()
    {
        transactions.clear();
    }

    public void receiveMoney(Transaction transaction)
    {
        sold += transaction.getAmount();
        transactions.add(transaction);
    }

    public void sendMoney(Transaction transaction)
    {
        sold -= transaction.getAmount();
        transactions.add(transaction);
    }

    public void addTransaction(Transaction transaction)
    {
        transactions.add(transaction);
    }

    public Card getCardbyId(int cardId)
    {
        for (Card card : cards)
        {
            if (card.getCardId() == cardId)
            {
                return card;
            }
        }
        return null;
    }
    @Override
    public String toString()
    {
        return "Account Id: " + accountId +
                "\nAccount IBAN: " + iban +
                "\nAccount Swift Code: " + swiftCode +
                "\nAccount Sold: " + sold;
    }
}
