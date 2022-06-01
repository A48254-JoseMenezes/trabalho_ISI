/**
 * ISEL-DEETC
 * Introdução a Sistemas de Informacao
 * user: MPato
 * version 1.0
 * date: january 2022
 */

package transactions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TransactionsEx {
	final static String cmdIns = "insert into jdbcdemo select max(id)+1,? from jdbcdemo;";
    final static String cmdSel = "SELECT * from jdbcdemo;";
    private static String now(String dateFormat)
    {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(cal.getTime());
    }

	public static void main(String[] args) throws SQLException
	{
		String jdbcURL = "jdbc:postgresql://10.62.73.22:5432/";
        String username = "test_1";
        String password = "test_1";

        Connection conn = null;
        PreparedStatement pstmt = null;
        Statement stmt = null;
        ResultSet rs = null;
		try
		{
            // number of tuples to insert defined by user
            int num = (args.length >0 )? Integer.parseInt(args[0]):4;

            // close transaction
            boolean commit = (args.length >1)? Boolean.parseBoolean(args[1]) : false;

            // JDBC and PostgreSQL: https://jdbc.postgresql.org/
            // PostgreSQL: https://www.postgresql.org/docs/7.4/jdbc-use.html

            // Step 1 - Load driver
            // Class.forName("org.postgresql.Driver"); // Class.forName() is not needed since JDBC 4.0

            // Step 2 -  Connecting to the Database
            conn = DriverManager.getConnection(jdbcURL, username, password);

            // generate data
            String time = now("HH:mm:ss");

            // Get statement for the parameterized statement
            pstmt = conn.prepareStatement(cmdIns);
            pstmt.setString(1, time);

            // turn-off auto-commit at the end of each run! IMPORTANT
            conn.setAutoCommit(false);
            for (int i = 0; i < num ; i++)
                 	pstmt.executeUpdate();

            // finish according to the arguments passed to the program
            if(commit)
                conn.commit();
            else
                conn.rollback();

            // turn on the auto commit
            // Only needed if new commands were executed
            conn.setAutoCommit(true);

            // Execute statement to select command
            stmt = conn.createStatement();
			rs = stmt.executeQuery(cmdSel);
			// Get result
			while(rs.next())
                System.out.format("%d\t%s\n", 
                        rs.getInt(1),rs.getString(2));
			System.out.println();			
		}		
		catch(SQLException sqlex)
		{
			System.out.println(sqlex.getMessage());			
		}
		finally
        {
            // free the resources of the ResultSet
            if (rs != null) rs.close();
            // free the resources of the Statement
            if (stmt != null) stmt.close();
            if (pstmt != null) pstmt.close();
            // close connection
            if (conn != null) conn.close();
		}
	}
}
