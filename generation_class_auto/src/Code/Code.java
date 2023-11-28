package Code;

import connect.ConnectToBdd;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.List;

public class Code
{
    public List<Table> getClassExist(Connection conn, String table_name)
    {
        try
        {
            if (conn == null)
            {
                ConnectToBdd connection = new ConnectToBdd();
                conn = connection.connectToPostgres();
            }
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet resultSet = metaData.getColumns(null, null, table_name, null);
            while (resultSet.next())
            {
                String colonne_name = resultSet.getString("COLUMN_NAME");
                String colonne_type = resultSet.getString("TYPE_NAME");

                System.out.println("Nom de la colonne : " + colonne_name);
                System.out.println("Type de données : " + colonne_type);
                System.out.println("-------------------------------------------");
            }
        }
        catch (Exception e)
        {
            System.out.println("dans la fonction getClassExist!");
            e.printStackTrace();
        }
    }
}
