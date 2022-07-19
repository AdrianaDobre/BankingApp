package Services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadService
{
    private static ReadService instance = null;

    private ReadService() {}

    public static ReadService getInstance()
    {
        if (instance == null)
        {
            instance = new ReadService();
        }
        return instance;
    }

    public List<String> readCSV(String input)
    {
        List<String> list = new ArrayList<>();
        try (var in = new BufferedReader(new FileReader(input)))
        {
            String line = "";
            while( (line = in.readLine()) != null ) {
                list.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

}
