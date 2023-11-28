package connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectToBdd {
    public Connection connectToPostgres() throws SQLException
    {
        Connection connection = null;
        String url = "jdbc:postgresql://localhost:5432/scallfolding";
        String user = "postgres";
        String password = "root";
        try
        {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("PostgreSQL JDBC driver not found!");
            e.printStackTrace();
            connection.close();
        }
        catch (SQLException e)
        {
            System.out.println("Connection failed!");
            e.printStackTrace();
            connection.close();
        }
        return connection;
    }

    public void closeConnection(Connection con) throws SQLException {
        con.close();
    }
}
