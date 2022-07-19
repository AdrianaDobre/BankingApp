package Person;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Person
{
    protected String firstName;
    protected String lastName;
    protected String phoneNumber;
    protected String address;
    protected Date dateOfBirth;
    protected String mailAddress;

//    constructor
    public Person(String firstName, String lastName, String phoneNumber, String address, Date dateOfBirth, String mailAddress)
    {
        this.firstName = firstName;
        this.lastName =  lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.mailAddress = mailAddress;
    }

//    getters and setters
    public String getFirstName()
    {
        return this.firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return this.lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getPhoneNumber()
    {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress()
    {
        return this.address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public Date getDateOfBirth()
    {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth)
    {
        this.dateOfBirth = dateOfBirth;
    }

    public String getMailAddress()
    {
        return this.mailAddress;
    }

    public void setMailAddress(String mailAddress)
    {
        this.mailAddress = mailAddress;
    }

    @Override
    public String toString()
    {
        return "\nClient First Name: " + firstName +
                "\nClient Last Name: " + lastName +
                "\nClient Phone Number: " + phoneNumber +
                "\nClient Address: " + address +
                "\nClient Date Of Birth: " + (new SimpleDateFormat("dd-MMM-YYYY")).format(dateOfBirth) +
                "\nClient Mail Address: " + mailAddress;
    }

}


