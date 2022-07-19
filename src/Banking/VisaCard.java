package Banking;

import java.util.Date;

public class VisaCard extends Card
{
    private boolean lostLuggageReimbursement;

//        constructor
    public VisaCard(int cardId, String iban, int CVV, Date expirationDate,String type, boolean lostLuggageReimbursement)
    {
        super(cardId,iban,CVV,expirationDate,type);
        this.lostLuggageReimbursement = lostLuggageReimbursement;
    }

//        getter
    public boolean isLostLuggageReimbursement()
    {
        return this.lostLuggageReimbursement;
    }

//        setter
    public void setLostLuggageReimbursement(boolean lostLuggageReimbursement)
    {
        this.lostLuggageReimbursement = lostLuggageReimbursement;
    }

    @Override
    public String toString() {
        return super.toString() + "\nLost Luggage Reimbursement: " + lostLuggageReimbursement;
    }
}
