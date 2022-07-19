package Services;

import java.io.*;
import java.sql.Timestamp;

public class AuditService
{
    private static AuditService instance = null;

    private AuditService() {}

    public static AuditService getInstance()
    {
        if (instance == null)
        {
            instance = new AuditService();
        }
        return instance;
    }

    public void writeMethod(String method)
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try (var out = new BufferedWriter(new FileWriter("CSV/audit.csv", true)))
        {
            String message = method + ", " + timestamp + "\n";
            out.write(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
