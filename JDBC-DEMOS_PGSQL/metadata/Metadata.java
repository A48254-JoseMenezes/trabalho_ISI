/**
 * ISEL-DEETC
 * Introdução a Sistemas de Informacao
 * user: MPato
 * version 1.0
 * date: january 2022
 */

package metadata;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class Metadata {
    private static final int TAB_SIZE = 8;

	public static void main(String[] args) throws SQLException {

        String jdbcURL = "jdbc:postgresql://10.62.73.22:5432/";
        String username = "test_1";
        String password = "test_1";

	    Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
		try {
            // JDBC and PostgreSQL: https://jdbc.postgresql.org/
            // PostgreSQL: https://www.postgresql.org/docs/7.4/jdbc-use.html

            // Step 1 - Load driver
            // Class.forName("org.postgresql.Driver"); // Class.forName() is not needed since JDBC 4.0

            // Step 2 -  Connecting to the Database
            conn = DriverManager.getConnection(jdbcURL, username, password);

            // Step 3 - Execute statement 1
            stmt = conn.createStatement();
			rs=stmt.executeQuery("select * from jdbcdemo");

			ResultSetMetaData meta = rs.getMetaData();
            int columnsCount = meta.getColumnCount();
            StringBuffer sep = new StringBuffer("\n");

            // This code is just demonstrative and only works for the two
            // columns existing in the table jdbcdemo
            for (int i = 1; i <= columnsCount; i++) {
                System.out.print(meta.getColumnLabel(i));
                System.out.print("\t");
                
                for (int j = 0; j < meta.getColumnDisplaySize(i)+TAB_SIZE; j++) {
                    sep.append('-');
               }
            }
            System.out.println(sep);
            // Step 4 - Get result
            while (rs.next()) {
                // It's not the best way to do it. But as in this case the result
                // is to be exclusively displayed on the console, the practical
                // result serves
                for (int i = 1; i <= columnsCount; i++) {
                    System.out.print(rs.getObject(i));
                    System.out.print("\t");
                }
                System.out.println();
            }
			System.out.println();
		}		
		catch(SQLException sqlex) {
			System.out.println(sqlex.getMessage());
		}
        finally {
            // Step 5 Close connection
            try {
                // free the resources of the ResultSet
                if (rs != null) rs.close();
                // free the resources of the Statement
                if (stmt != null) stmt.close();
                // close connection
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}