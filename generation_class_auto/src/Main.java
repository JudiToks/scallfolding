import Code.Code;
import connect.ConnectToBdd;

import java.sql.Connection;

public class Main
{
    public static void main(String[] args)
    {
        ConnectToBdd connect = new ConnectToBdd();
        try
        {
            Connection connection = connect.connectToPostgres();
            Code code = new Code();
            code.getClassExist(connection, "emp");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}