package Banking;

import java.util.Date;

public class SavingsAccount extends Account
{
    private Date startDate;
    private Date endDate;
    private float amount;

    public SavingsAccount(int accountId,String iban, String swiftCode, float sold, Date startDate, Date endDate,float amount)
    {
        super(accountId,iban,swiftCode,sold);
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
    }

    public Date getStartDate()
    {
        return this.startDate;
    }

    public Date getEndDate()
    {
        return this.endDate;
    }

    public float getAmount()
    {
        return this.amount;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public void setAmount(float amount)
    {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nStart Date: " + startDate +
                "\nEnd Date: " + endDate +
                "\nAmount: " + amount;
    }
}
