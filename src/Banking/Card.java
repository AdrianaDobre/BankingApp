package Banking;

import java.util.Date;

public class Card
{
    protected int cardId;
    protected String iban;
    protected int CVV;
    protected Date expirationDate;
    protected String type;

//    constructor
    public Card(int cardId, String iban, int CVV, Date expirationDate, String type)
    {
        this.cardId = cardId;
        this.iban = iban;
        this.CVV = CVV;
        this.expirationDate = expirationDate;
        this.type = type;
    }

//    getters
    public int getCardId()
    {
        return this.cardId;
    }

    public String getIban()
    {
        return this.iban;
    }

    public int getCVV()
    {
        return this.CVV;
    }

    public Date getExpirationDate()
    {
        return this.expirationDate;
    }

    public String getType()
    {
        return this.type;
    }

//    setters
    public void setCardId(int cardId)
    {
        this.cardId = cardId;
    }

    public void setIban(String iban)
    {
        this.iban = iban;
    }

    public void setCVV(int CVV)
    {
        this.CVV = CVV;
    }

    public void setExpirationDate(Date expirationDate)
    {
        this.expirationDate = expirationDate;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Id Card: " + cardId +
                "\nIban: " + iban +
                "\nCVV: " + CVV +
                "\nExpiration Date: " + expirationDate +
                "\nType Card: " + type;
    }
}
