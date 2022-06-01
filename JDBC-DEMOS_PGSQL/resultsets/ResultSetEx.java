/**
 * ISEL-DEETC
 * Introdução a Sistemas de Informacao
 * user: MPato
 * version 1.0
 * date: january 2022
 */

package resultsets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class ResultSetEx {
	public static void main(String[] args) throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
		try {
			String jdbcURL = "jdbc:postgresql://10.62.73.22:5432/";
			String username = "test_1";
			String password = "test_1";
			// JDBC and PostgreSQL: https://jdbc.postgresql.org/
			// PostgreSQL: https://www.postgresql.org/docs/7.4/jdbc-use.html

			// Step 1 - Load driver
			// Class.forName("org.postgresql.Driver"); // Class.forName() is not needed since JDBC 4.0

			// Step 2 -  Connecting to the Database
			conn = DriverManager.getConnection(jdbcURL, username, password);
			
			// execute the select command
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select id, value from jdbcdemo");
			
			// iterate
			while(rs.next())
               System.out.format("%d\t%s\n", rs.getInt(1), rs.getString(2));
               //System.out.format("%s\t%s\n", rs.getString(1), rs.getString("value"));//Exemplo de coerção de tipo
		}		
		catch(SQLException sqlex) {
			System.out.println(sqlex.getMessage());
		}
        finally {
			// free the resources of the ResultSet
			if (rs != null) rs.close();
			// free the resources of the Statement
			if (stmt != null) stmt.close();
			// close connection
			if (conn != null) conn.close();
        }
	}
}
