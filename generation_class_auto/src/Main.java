import Code.*;
import connect.ConnectToBdd;

import java.io.File;
import java.sql.Connection;
import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        ConnectToBdd connect = new ConnectToBdd();
        try
        {
            Code code = new Code();
            ConfigBdd config = new ConfigBdd();
            config = code.readXMLDataBaseConfig();
            ConfigClasse conf_classe = code.readXMLConfig();
            Connection connection = connect.connectToPostgres(config);
            code.executeFramework(connection);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}