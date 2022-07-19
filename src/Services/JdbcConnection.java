package Services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnection
{
    private static JdbcConnection instance = null;
    Connection connection = null;

    public JdbcConnection()
    {
        try {
            String url = "jdbc:mysql://localhost:3306/bankingapp";
            String username = "root";
            String password = "";
            connection = DriverManager.getConnection(url, username, password);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static JdbcConnection getInstance()
    {
        if (instance == null)
        {
            instance = new JdbcConnection();
        }
        return instance;
    }

    public Connection getConnection()
    {
        return connection;
    }

    public void setConnection(Connection connection)
    {
        this.connection = connection;
    }
}
