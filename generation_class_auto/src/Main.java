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
            System.out.println("test:"+conf_classe.getPath()+conf_classe.getTable().getPkg());
            System.out.println("okok:"+conf_classe.getPath()+conf_classe.getTable().getPkg()+"/"+conf_classe.getTable().getTalbe_name()+".java");
            code.executeFramework(connection);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}