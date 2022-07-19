package Banking;

import java.util.Date;

public class MasterCard extends Card
{
    private boolean priceProtection;

//    constructor
    public MasterCard(int cardId, String iban, int CVV, Date expirationDate, String type,boolean priceProtection)
    {
        super(cardId,iban,CVV,expirationDate,type);
        this.priceProtection = priceProtection;
    }

//    getter
    public boolean isPriceProtection()
    {
        return this.priceProtection;
    }


//    setter
    public void setPriceProtection(boolean priceProtection)
    {
        this.priceProtection = priceProtection;
    }

    @Override
    public String toString() {
        return super.toString() + "\nPrice Protection: " + priceProtection;
    }
}
