package Services;

import Banking.*;
import Person.Client;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;
import java.util.Date;

public class InterrogationService
{
    Map<Integer, Client> clients;
    ReadService readService;
    AuditService auditService;
    WriteService writeService;
    JdbcConnection jdbcConnection;

    public InterrogationService()
    {
        clients = new HashMap<>();
        readService = ReadService.getInstance();
        auditService = AuditService.getInstance();
        writeService = WriteService.getInstance();
        jdbcConnection = JdbcConnection.getInstance();
    }

    public void loadDataFromDb()
    {
        try (Statement statement = jdbcConnection.getConnection().createStatement())
        {
            String query = "select * from client";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next())
            {
                int idClient = rs.getInt("idClient");
                Client client = new Client(rs.getString("firstName"),rs.getString("LastName"),rs.getString("phoneNumber"),rs.getString("address"),new Date(rs.getString("dateOfBirth")),rs.getString("mailAddress"),idClient);
                clients.put(idClient,client);
            }
            if (!clients.isEmpty()) {
                query = "select * from account";
                rs = statement.executeQuery(query);
                while (rs.next()) {
                    int idClient = rs.getInt("idClient");
                    Account account = new Account(rs.getInt("idAccount"), rs.getString("iban"), rs.getString("swiftCode"), rs.getFloat("sold"));
                    Client client = clients.get(idClient);
                    client.addAccount(account);
                }

                query = "select * from card";
                rs = statement.executeQuery(query);
                while (rs.next()) {
                    if (Objects.equals(rs.getString("type"), "Master")) {
                        Card card = new MasterCard(rs.getInt("idCard"), rs.getString("iban"), rs.getInt("cvv"), new Date(rs.getString("expirationDate")), rs.getString("type"), rs.getBoolean("priceProtection"));
                        Client client = clients.get(rs.getInt("idClient"));
                        for (Account account : client.getAccounts()) {
                            if (account.getAccountId() == rs.getInt("idAccount")) {
                                account.addCard(card);
                            }
                        }
                    } else {
                        Card card = new VisaCard(rs.getInt("idCard"), rs.getString("iban"), rs.getInt("cvv"), new Date(rs.getString("expirationDate")), rs.getString("type"), rs.getBoolean("lostLug"));
                        Client client = clients.get(rs.getInt("idClient"));
                        for (Account account : client.getAccounts()) {
                            if (account.getAccountId() == rs.getInt("idAccount")) {
                                account.addCard(card);
                            }
                        }
                    }
                    }
                query = "select * from savingsaccount";
                rs = statement.executeQuery(query);
                while (rs.next()) {
                    int idClient = rs.getInt("idClient");
                    SavingsAccount account = new SavingsAccount(rs.getInt("idAccount"), rs.getString("iban"), rs.getString("swiftCode"), rs.getFloat("sold"), new Date(rs.getString("startDate")), new Date(rs.getString("endDate")), rs.getFloat("amount"));
                    Client client = clients.get(idClient);
                    client.addSavingsAccount(account);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addClientsFromCsv()
    {
        List<String> list = readService.readCSV("CSV/clients.csv");
        for (String line : list)
        {
            String [] fields = line.replaceAll(" ", "").split(",");
            try
            {
                int idClient = Integer.parseInt(fields[0]);
                Client client = new Client(fields[1],fields[2],fields[3],fields[4],new SimpleDateFormat("dd-MM-yyyy").parse(fields[5]),fields[6],idClient);
                clients.put(idClient,client);
                writeService.showClientCreated("Client",client);

            } catch (ParseException e){
                e.printStackTrace();
            }
        }
//        auditService.writeMethod("addClientsFromCsv");
    }

    public void addClient() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter Client Data: ");
        System.out.print("Enter Client Id: ");
        int idClient = in.nextInt();in.nextLine();
        System.out.print("Enter First Name: ");
        String firstName = in.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = in.nextLine();
        System.out.print("Enter Phone Number: ");
        String phoneNumber = in.nextLine();
        System.out.print("Enter Address: ");
        String address = in.nextLine();
        System.out.print("Enter Date of Birth: ");
        String dateOfBirth = in.nextLine();
        System.out.print("Enter Mail Address: ");
        String mailAddress = in.nextLine();

        Client client = new Client(firstName,lastName,phoneNumber,address,new Date(dateOfBirth),mailAddress,idClient);
        clients.put(idClient,client);
//        folosit la etapa a II-a:
//        auditService.writeMethod("addClient");
//        writeService.showClientCreated("Client",client);
        try
        {
            String query = "insert into client values(?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = jdbcConnection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1,idClient);
            preparedStatement.setString(2,firstName);
            preparedStatement.setString(3,lastName);
            preparedStatement.setString(4,phoneNumber);
            preparedStatement.setString(5,address);
            preparedStatement.setString(6,dateOfBirth);
            preparedStatement.setString(7,mailAddress);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showClient()
    {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter your id to see your personal data:");
        int clientId = in.nextInt();
        Client client = clients.get(clientId);
        if (client == null)
            System.out.println("The client does not exits");
        else System.out.println(client.toString());
//        auditService.writeMethod("showClient");
    }

    public void showClients()
    {
        System.out.println("The clients are: ");
        for(int id : clients.keySet())
            System.out.println(clients.get(id));
//        auditService.writeMethod("showClients");
    }

    public void deleteClient()
    {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter Client Id: ");
        int idClient = in.nextInt();in.nextLine();
        Client client = clients.get(idClient);
        if (client == null) {
            System.out.println("The client does not exists!");
        }
        for (Account account : client.getAccounts()) {
            if (account != null) {
//                System.out.println(account.toString());
                for (Card card : account.getCards()) {
//                    System.out.println(card.toString());
                    try (Statement statement = jdbcConnection.getConnection().createStatement()) {
                        String query = "delete from card where idCard = " + card.getCardId();
                        statement.executeUpdate(query);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
//                for (Transaction transaction : account.getTransactions()) {
//                    System.out.println(transaction.toString());
//                }
                account.deleteCards();
                account.deleteTransactions();
//                System.out.println();
//                System.out.println("Cardurile ramase:");
//                for (Card card : account.getCards())
//                    System.out.println(card.toString());
//                System.out.println();
//                System.out.println("Tranzactiile ramase");
//                for (Transaction transaction : account.getTransactions()) {
//                    System.out.println(transaction.toString());
//                }
//                System.out.println();
                try (Statement statement = jdbcConnection.getConnection().createStatement()) {
                    String query = "delete from account where idAccount = " + account.getAccountId();
                    statement.executeUpdate(query);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("The client does not have accounts");
            }
        }
        for (SavingsAccount account : client.getSavingsAccounts()) {
//            System.out.println(account.toString());
            try (Statement statement = jdbcConnection.getConnection().createStatement()) {
                String query = "delete from savingsaccount where idAccount = " + account.getAccountId();
                statement.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        client.deleteAccounts();
        client.deleteSavingsAccount();
//        System.out.println("Conturile ramase");
//        for (Account account : client.getAccounts()) {
//            System.out.println(account.toString());
//        }
//        System.out.println("Conturile de economie ramase");
//        for (SavingsAccount account : client.getSavingsAccounts()) {
//            System.out.println(account.toString());
//        }
        try (Statement statement = jdbcConnection.getConnection().createStatement()) {
            String query = "delete from client where idClient = " + idClient;
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        clients.remove(idClient);
    }

    public void updateClient()
    {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter Client Id: ");
        int idClient = in.nextInt();in.nextLine();
        System.out.print("Update your phone number: ");
        String phoneNumber = in.nextLine();
        System.out.print("Update your address: ");
        String address = in.nextLine();
        System.out.print("Update your mail address: ");
        String mailAddress = in.nextLine();

        try (Statement statement = jdbcConnection.getConnection().createStatement()) {
                String query = "update client set phoneNumber = " + "'" + phoneNumber + "'" + " where idClient = " + idClient;
                statement.executeUpdate(query);
                query = "update client set address = " + "'" + address + "'" + " where idClient = " + idClient;
                statement.executeUpdate(query);
                query = "update client set mailAddress = " + "'" + mailAddress + "'" + " where idClient = " + idClient;
                statement.executeUpdate(query);
        } catch (SQLException e) {
                e.printStackTrace();
            }
        for(int id : clients.keySet()) {
            if (id == idClient)
            {
                Client client = clients.get(idClient);
                client.setPhoneNumber(phoneNumber);
                client.setAddress(address);
                client.setMailAddress(mailAddress);
            }
        }
    }

    public void createCard() {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter your id client: ");
        int clientId = in.nextInt();in.nextLine();
        System.out.print("Enter your account id: ");
        int accountId = in.nextInt();in.nextLine();
        System.out.print("Enter your card id: ");
        int cardId = in.nextInt();in.nextLine();
        System.out.print("Enter IBAN: ");
        String iban = in.nextLine();
        System.out.print("Enter CVV: ");
        int cvv = in.nextInt();in.nextLine();
        System.out.print("Enter Expiration Date: ");
        String expirationDate = in.nextLine();
        System.out.print("Enter type card(Master/Visa): ");
        String type = in.nextLine();

        Client client = clients.get(clientId);
        if (client == null) {
            System.out.println("The client does not exists!");
        }

        boolean foundAccount = false;
        for(Account account : client.getAccounts())
        {
            if (account.getAccountId() == accountId)
            {
                foundAccount = true;
                if (Objects.equals(type, "Master"))
                {
                    System.out.print("Enter if you have price protection (true/false): ");
                    boolean priceProtection = in.nextBoolean();
                    Card card = new MasterCard(cardId,iban,cvv,new Date(expirationDate),type,priceProtection);
                    account.addCard(card);
//                    writeService.showCardCreated("MasterCard",card);
                    try
                    {
                        String query = "insert into card values(?,?,?,?,?,?,?,?,null)";
                        PreparedStatement preparedStatement = jdbcConnection.getConnection().prepareStatement(query);
                        preparedStatement.setInt(1,cardId);
                        preparedStatement.setInt(2,clientId);
                        preparedStatement.setInt(3,accountId);
                        preparedStatement.setString(4,iban);
                        preparedStatement.setInt(5,cvv);
                        preparedStatement.setString(6,expirationDate);
                        preparedStatement.setString(7,type);
                        preparedStatement.setBoolean(8,priceProtection);

                        preparedStatement.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                else if (Objects.equals(type, "Visa"))
                {
                    System.out.print("Enter if you have lost luggage reimbursement (true/false): ");
                    boolean lostLuggageReimbursement = in.nextBoolean();
                    Card card = new VisaCard(cardId,iban,cvv,new Date(expirationDate),type,lostLuggageReimbursement);
                    account.addCard(card);
//                    writeService.showCardCreated("VisaCard",card);
                    try
                    {
                        String query = "insert into card values(?,?,?,?,?,?,?,null,?)";
                        PreparedStatement preparedStatement = jdbcConnection.getConnection().prepareStatement(query);
                        preparedStatement.setInt(1,cardId);
                        preparedStatement.setInt(2,clientId);
                        preparedStatement.setInt(3,accountId);
                        preparedStatement.setString(4,iban);
                        preparedStatement.setInt(5,cvv);
                        preparedStatement.setString(6,expirationDate);
                        preparedStatement.setString(7,type);
                        preparedStatement.setBoolean(8,lostLuggageReimbursement);

                        preparedStatement.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        if (!foundAccount)
        {
            System.out.println("The account does not exits");
        }
//        auditService.writeMethod("createCard");
    }

    public void addCardsFromCsv()
    {
        List<String> list = readService.readCSV("CSV/cards.csv");
        for (String line : list)
        {
            String [] fields = line.replaceAll(" ", "").split(",");
            int idClient = Integer.parseInt(fields[0]);
            Client client = clients.get(idClient);
            int accountId = Integer.parseInt(fields[1]);
            String type = fields[6];
            if (client == null) {
                System.out.println("The client does not exists!");
            }
            boolean foundAccount = false;
            for(Account account : client.getAccounts())
            {
                if (account.getAccountId() == accountId)
                {
                    foundAccount = true;
                    if (Objects.equals(type, "Master"))
                    {
                        try {
                            Card card = new MasterCard(Integer.parseInt(fields[2]),fields[3],Integer.parseInt(fields[4]),new SimpleDateFormat("dd-MM-yyyy").parse(fields[5]), type,Boolean.parseBoolean(fields[7]));
                            account.addCard(card);
                            writeService.showCardCreated("MasterCard",card);
                        } catch (ParseException e){
                            e.printStackTrace();
                        }
                    }
                    else if (Objects.equals(type, "Visa"))
                    {
                        try {
                            Card card = new VisaCard(Integer.parseInt(fields[2]),fields[3],Integer.parseInt(fields[4]),new SimpleDateFormat("dd-MM-yyyy").parse(fields[5]), type,Boolean.parseBoolean(fields[7]));
                            account.addCard(card);
                            writeService.showCardCreated("VisaCard",card);
                        } catch (ParseException e){
                            e.printStackTrace();
                        }
                    }

                }
            }
            if (!foundAccount)
            {
                System.out.println("The account does not exits");
            }
        }
//        auditService.writeMethod("addCardsFromCsv");
    }

    public void deleteCard()
    {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter Client Id: ");
        int idClient = in.nextInt();in.nextLine();
        System.out.print("Enter Account Id: ");
        int idAccount = in.nextInt();in.nextLine();
        System.out.print("Enter Card Id: ");
        int cardId = in.nextInt();
        Client client = clients.get(idClient);
        if (client == null) {
            System.out.println("The client does not exists!");
        }

        boolean foundAccount = false;
        for(Account account : client.getAccounts())
        {
            if (account.getAccountId() == idAccount)
            {
                foundAccount = true;
                account.deleteCard(cardId);
                try (Statement statement = jdbcConnection.getConnection().createStatement()) {
                    String query = "delete from card where idCard = " + cardId;
                    statement.executeUpdate(query);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        if (!foundAccount)
        {
            System.out.println("The account does not exits");
        }
//        auditService.writeMethod("deleteCard");
    }

    public void updateCard()
    {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter Client Id: ");
        int idClient = in.nextInt();in.nextLine();
        System.out.print("Enter Account Id: ");
        int idAccount = in.nextInt();in.nextLine();
        System.out.print("Enter Card Id: ");
        int cardId = in.nextInt(); in.nextLine();
        Card card = clients.get(idClient).getAccountById(idAccount).getCardbyId(cardId);
        System.out.println("We are checking if your card expires this year...");
        Calendar calendar = Calendar.getInstance();
        if (card.getExpirationDate().getYear() + 1900 == calendar.get(Calendar.YEAR)) {
            System.out.print("Yes, your card expires this year. Do you want to renew it? (Yes/No): ");
            String answear = in.nextLine();
            if (Objects.equals(answear, "Yes")) {
                Month month = Month.of(calendar.get(Calendar.MONTH) + 1);
                Locale locale = Locale.getDefault();
                calendar.add(Calendar.YEAR, 8);
                String newDate = calendar.get(Calendar.DATE) + "-" + month.getDisplayName(TextStyle.SHORT, locale) + "-" + calendar.get(Calendar.YEAR);
                card.setExpirationDate(new Date(newDate));
                try (Statement statement = jdbcConnection.getConnection().createStatement()) {
                    String query = "update card set expirationDate = " + "'" + newDate + "'" + " where idCard = " + cardId;
                    statement.executeUpdate(query);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void showCards()
    {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter Client Id: ");
        int idClient = in.nextInt();in.nextLine();
        System.out.print("Enter Account Id: ");
        int idAccount = in.nextInt();
        Client client = clients.get(idClient);
        if (client == null) {
            System.out.println("The client does not exists!");
        }

        boolean foundAccount = false;
        for(Account account : client.getAccounts())
        {
            if (account.getAccountId() == idAccount)
            {
                foundAccount = true;
                for (Card card : account.getCards())
                    System.out.println(card.toString());
            }
        }
        if (!foundAccount)
        {
            System.out.println("The account does not exits");
        }
//        auditService.writeMethod("showCards");
    }

    public void addAccountsFromCsv()
    {
        List<String> list = readService.readCSV("CSV/accounts.csv");
        for (String line : list)
        {
            String [] fields = line.replaceAll(" ", "").split(",");
            int idClient = Integer.parseInt(fields[0]);
            Client client = clients.get(idClient);
            if (client == null) {
                System.out.println("The client does not exists!");
            }
            Account account = new Account(Integer.parseInt(fields[1]),fields[2],fields[3],Float.parseFloat(fields[4]));
            client.addAccount(account);
            writeService.showAccountCreated("Account",account);
        }
//        auditService.writeMethod("addAccountsFromCsv");
    }
    public void createAccount()
    {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter Client Id: ");
        int idClient = in.nextInt();in.nextLine();
        System.out.print("Enter Account Id: ");
        int idAccount = in.nextInt();in.nextLine();
        System.out.print("Enter IBAN: ");
        String iban = in.nextLine();
        System.out.print("Enter Swift Code: ");
        String swiftCode = in.nextLine();
        System.out.print("Enter Sold: ");
        float sold = in.nextFloat();

        Client client = clients.get(idClient);
        if (client == null) {
            System.out.println("The client does not exists!");
        }
        Account account = new Account(idAccount,iban,swiftCode,sold);
        client.addAccount(account);
//        writeService.showAccountCreated("Account",account);
//        auditService.writeMethod("createAccount");
        try
        {
            String query = "insert into account values(?,?,?,?,?)";
            PreparedStatement preparedStatement = jdbcConnection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1,idAccount);
            preparedStatement.setInt(2,idClient);
            preparedStatement.setString(3,iban);
            preparedStatement.setString(4,swiftCode);
            preparedStatement.setFloat(5,sold);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAccount()
    {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter Client Id: ");
        int idClient = in.nextInt();in.nextLine();
        System.out.print("Enter Account Id: ");
        int idAccount = in.nextInt();
        Client client = clients.get(idClient);
        if (client == null) {
            System.out.println("The client does not exists!");
        }
        Account account = client.getAccountById(idAccount);
        if (account != null)
        {
            for (Card card : account.getCards()) {
//                System.out.println(card.toString());
                try (Statement statement = jdbcConnection.getConnection().createStatement()) {
                    String query = "delete from card where idCard = " + card.getCardId();
                    statement.executeUpdate(query);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
//            for (Transaction transaction : account.getTransactions()) {
//                System.out.println(transaction.toString());
//            }
            account.deleteCards();
            account.deleteTransactions();
//            System.out.println("Cardurile ramase");
//            for (Card card : account.getCards())
//                System.out.println(card.toString());
//            System.out.println(client.getAccountById(idAccount));
//            System.out.println("Tranzactiile ramase");
//            for (Transaction transaction : account.getTransactions()) {
//                System.out.println(transaction.toString());
//            }
            client.deleteAccount(idAccount);
            try (Statement statement = jdbcConnection.getConnection().createStatement()) {
                String query = "delete from account where idAccount = " + idAccount;
                statement.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("The account does not exits");
        }
//        auditService.writeMethod("deleteAccount");
    }

    public void showAccounts()
    {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter Client Id: ");
        int idClient = in.nextInt();
        Client client = clients.get(idClient);
        if (client == null) {
            System.out.println("The client does not exists!");
        }
        for (Account account : client.getAccounts())
            System.out.println(account.toString());
//        auditService.writeMethod("showAccounts");
    }

//    public void createTransaction()
//    {
//        Scanner in = new Scanner(System.in);
//        System.out.print("Enter Client Id: ");
//        int idClient = in.nextInt();in.nextLine();
//        System.out.print("Enter Account Id: ");
//        int idAccount = in.nextInt();in.nextLine();
//        System.out.print("Enter Transaction Id: ");
//        int idTransaction = in.nextInt();in.nextLine();
//        System.out.print("Enter your IBAN: ");
//        String fromIban = in.nextLine();
//        System.out.print("Enter the IBAN of the person you want to send money to: ");
//        String toIban = in.nextLine();
//        System.out.print("Enter the amount you want to send: ");
//        int amount = in.nextInt();in.nextLine();
//        System.out.print("Enter Description: ");
//        String description = in.nextLine();
//        Date dateOfTransaction = new Date();
//
//        Client client = clients.get(idClient);
//        if (client == null) {
//            System.out.println("The client does not exists!");
//        }
//
//        boolean foundAccount = false;
//        for(Account account : client.getAccounts())
//        {
//            if (account.getAccountId() == idAccount)
//            {
//                foundAccount = true;
//                Transaction transaction = new Transaction(idTransaction,fromIban,toIban,amount,description,dateOfTransaction);
//                account.addTransaction(transaction);
//            }
//        }
//        if (!foundAccount)
//        {
//            System.out.println("The account does not exits");
//        }
//    }

    public void addTransactionsFromCsv()
    {
        List<String> list = readService.readCSV("CSV/transactions.csv");
        for (String line : list)
        {
            String [] fields = line.replaceAll(" ", "").split(",");
            int idClient1 = Integer.parseInt(fields[0]);
            int idAccount1 = Integer.parseInt(fields[1]);
            int idClient2 = Integer.parseInt(fields[2]);
            int idAccount2 = Integer.parseInt(fields[3]);
            Date dateOfTransaction = new Date();

            Client client1 = clients.get(idClient1);
            if (client1 == null) {
                System.out.println("The first client does not exists!");
            }

            Client client2 = clients.get(idClient2);
            if (client2 == null) {
                System.out.println("The second client does not exists!");
            }

            boolean foundAccount = false;
            for(Account account : client1.getAccounts())
            {
                if (account.getAccountId() == idAccount1)
                {
                    foundAccount = true;
                    Transaction transaction = new Transaction(Integer.parseInt(fields[4]),fields[5],fields[6],Float.parseFloat(fields[7]),fields[8],dateOfTransaction);
                    account.sendMoney(transaction);
//                    writeService.showTransactionCreated("Transaction",transaction);
                }
            }
            if (!foundAccount)
            {
                System.out.println("The first account does not exits");
            }

            foundAccount = false;
            for(Account account : client2.getAccounts())
            {
                if (account.getAccountId() == idAccount2)
                {
                    foundAccount = true;
                    Transaction transaction = new Transaction(Integer.parseInt(fields[4]),fields[5],fields[6],Float.parseFloat(fields[7]),fields[8],dateOfTransaction);
                    account.receiveMoney(transaction);
//                    writeService.showTransactionCreated("Transaction",transaction);
                }
            }
            if (!foundAccount)
            {
                System.out.println("The second account does not exits");
            }
        }
//        auditService.writeMethod("addTransactionsFromCsv");
    }

    public void createTransaction()
        {
            Scanner in = new Scanner(System.in);
            System.out.print("Enter Client1 Id: ");
            int idClient1 = in.nextInt();in.nextLine();
            System.out.print("Enter Account1 Id: ");
            int idAccount1 = in.nextInt();in.nextLine();
            System.out.print("Enter Client2 Id: ");
            int idClient2 = in.nextInt();in.nextLine();
            System.out.print("Enter Account2 Id: ");
            int idAccount2 = in.nextInt();in.nextLine();
            System.out.print("Enter Transaction Id: ");
            int idTransaction = in.nextInt();in.nextLine();
            System.out.print("Enter your IBAN: ");
            String fromIban = in.nextLine();
            System.out.print("Enter the IBAN of the person you want to send money to: ");
            String toIban = in.nextLine();
            System.out.print("Enter the amount you want to send: ");
            int amount = in.nextInt();in.nextLine();
            System.out.print("Enter Description: ");
            String description = in.nextLine();
            Date dateOfTransaction = new Date();

            Client client1 = clients.get(idClient1);
            if (client1 == null) {
                System.out.println("The first client does not exists!");
            }

            Client client2 = clients.get(idClient2);
            if (client2 == null) {
                System.out.println("The second client does not exists!");
            }

            boolean foundAccount = false;
            for(Account account : client1.getAccounts())
            {
                if (account.getAccountId() == idAccount1)
                {
                    foundAccount = true;
                    if (account.getSold() > amount) {
                        Transaction transaction = new Transaction(idTransaction, fromIban, toIban, amount, description, dateOfTransaction);
                        account.sendMoney(transaction);
                        //                    writeService.showTransactionCreated("Transaction",transaction);
                        try (Statement statement = jdbcConnection.getConnection().createStatement()) {
                            String query = "update account set sold = " + account.getSold() + " where idAccount = " + idAccount1;
                            statement.executeUpdate(query);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            if (!foundAccount)
            {
                System.out.println("The first account does not exits");
            }

            foundAccount = false;
            for(Account account : client2.getAccounts())
            {
                if (account.getAccountId() == idAccount2)
                {
                    foundAccount = true;
                    Transaction transaction = new Transaction(idTransaction,fromIban,toIban,amount,description,dateOfTransaction);
                    account.receiveMoney(transaction);
//                    writeService.showTransactionCreated("Transaction",transaction);
                    try (Statement statement = jdbcConnection.getConnection().createStatement()) {
                        String query = "update account set sold = " + account.getSold() + " where idAccount = " + idAccount2;
                        statement.executeUpdate(query);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (!foundAccount)
            {
                System.out.println("The second account does not exits");
            }
//            auditService.writeMethod("createTransaction");
        }

    public void showTransactions()
    {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter Client Id: ");
        int idClient = in.nextInt();in.nextLine();
        System.out.print("Enter Account Id: ");
        int idAccount = in.nextInt();
        Client client = clients.get(idClient);
        if (client == null) {
            System.out.println("The client does not exists!");
        }

        boolean foundAccount = false;
        for(Account account : client.getAccounts())
        {
            if (account.getAccountId() == idAccount)
            {
                foundAccount = true;
                for (Transaction transaction : account.getTransactions())
                    System.out.println(transaction.toString());
            }
        }
        if (!foundAccount)
        {
            System.out.println("The account does not exits");
        }
//        auditService.writeMethod("showTransactions");
    }

    public void createSavingsAccount()
    {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter Client Id: ");
        int idClient = in.nextInt();in.nextLine();
        System.out.print("Enter Savings Account Id: ");
        int idAccount = in.nextInt();in.nextLine();
        System.out.print("Enter IBAN: ");
        String iban = in.nextLine();
        System.out.print("Enter Swift Code: ");
        String swiftCode = in.nextLine();
        System.out.print("Enter Start Date: ");
        String startDate = in.nextLine();
        System.out.print("Enter End Date: ");
        String endDate = in.nextLine();
        System.out.print("Enter amount: ");
        float amount = in.nextFloat();


        Client client = clients.get(idClient);
        if (client == null) {
            System.out.println("The client does not exists!");
        }
        SavingsAccount account = new SavingsAccount(idAccount,iban,swiftCode,0,new Date(startDate),new Date(endDate),amount);
        client.addSavingsAccount(account);
        try
        {
            String query = "insert into savingsaccount values(?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = jdbcConnection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1,idAccount);
            preparedStatement.setInt(2,idClient);
            preparedStatement.setString(3,iban);
            preparedStatement.setString(4,swiftCode);
            preparedStatement.setFloat(5,account.getSold());
            preparedStatement.setString(6,startDate);
            preparedStatement.setString(7,endDate);
            preparedStatement.setFloat(8,amount);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        auditService.writeMethod("createSavingsAccount");
//        writeService.showSavingsAccountCreated("SavingsAccount",account);
    }

    public void showSavingsAccounts()
    {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter Client Id: ");
        int idClient = in.nextInt();
        Client client = clients.get(idClient);
        if (client == null) {
            System.out.println("The client does not exists!");
        }
        for (SavingsAccount account : client.getSavingsAccounts())
            System.out.println(account.toString());
//        auditService.writeMethod("showSavingsAccounts");
    }

    public void updateSavingsAccount()
    {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter Client Id: ");
        int idClient = in.nextInt();in.nextLine();
        System.out.print("Enter Savings Account Id: ");
        int idAccount = in.nextInt();in.nextLine();
        System.out.println("Update your savings account: ");
        System.out.print("Enter Start Date: ");
        String startDate = in.nextLine();
        System.out.print("Enter End Date: ");
        String endDate = in.nextLine();
        System.out.print("Enter amount: ");
        float amount = in.nextFloat();
        Client client = clients.get(idClient);
        if (client == null) {
            System.out.println("The client does not exists!");
        }
        else
        {
            SavingsAccount account = client.getSavingsAccountById(idAccount);
            if (account != null)
            {
                account.setStartDate(new Date(startDate));
                account.setEndDate(new Date(endDate));
                account.setAmount(amount);
                try (Statement statement = jdbcConnection.getConnection().createStatement()) {
                    String query = "update savingsaccount set startDate = " + "'" + startDate + "'" + " where idAccount = " + idAccount;
                    statement.executeUpdate(query);
                    query = "update savingsaccount set endDate = " + "'" + endDate + "'" + " where idAccount = " + idAccount;
                    statement.executeUpdate(query);
                    query = "update savingsaccount set amount = " + amount + " where idAccount = " + idAccount;
                    statement.executeUpdate(query);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else
                System.out.println("The client does not have savings accounts.");
        }
    }

    public void deleteSavingsAccount()
    {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter Client Id: ");
        int idClient = in.nextInt();in.nextLine();
        System.out.print("Enter Savings Account Id: ");
        int idAccount = in.nextInt();in.nextLine();
        Client client = clients.get(idClient);
        if (client == null) {
            System.out.println("The client does not exists!");
        }
        else
        {
            if (client.getSavingsAccountById(idAccount) != null)
            {
                client.deleteSavingsAccount(idAccount);
                try (Statement statement = jdbcConnection.getConnection().createStatement()) {
                    String query = "delete from savingsaccount where idAccount = " + idAccount;
                    statement.executeUpdate(query);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else
                System.out.println("The client does not have savings accounts.");
        }
    }
}
