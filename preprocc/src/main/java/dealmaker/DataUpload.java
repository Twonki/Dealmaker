package dealmaker;

import dealmaker.entity.Account;
import dealmaker.entity.Transaction;

import java.sql.*;
import java.util.List;

public class DataUpload {
    public static void upload(List<Account> accounts){
        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:deakMaker.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate(MyUtil.readsql());

            for(Account a: accounts){
                statement.executeUpdate(a.insertStatement());
                for (Transaction t: a.getTransactions()){
                    statement.executeUpdate(t.insertStatement());
                }
            }
            ResultSet rs = statement.executeQuery("select * from accounts");
            ResultSet rs2 = statement.executeQuery("select * from transactions");
            int rsCount = 1;
            while(rs.next())
            {
                // read the result set
                System.out.println("accountId = " + rs.getString("accountId"));
                System.out.println("firstname = " + rs.getInt("firstname"));
                rsCount++;
            }
            int rs2Count = 1;
            while(rs2.next())
            {
                // read the result set
                System.out.println("tid = " + rs.getString("tid"));
                System.out.println("accountId = " + rs.getInt("accountId"));
                rs2Count++;
            }
            System.out.println("==================================================");
            System.out.println("number of accounts:" +rsCount);
            System.out.println("number of transactions:" +rs2Count);
        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
        finally
        {
            try
            {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e)
            {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    }
}
