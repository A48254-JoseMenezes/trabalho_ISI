/**
 * ISEL-DEETC
 * Introdução a Sistemas de Informacao
 * user: MPato
 * version 1.0
 * date: january 2022
 */

package statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class CloseResultSet {
	final static String cmdSel = "select value from jdbcdemo";
	final static String cmdIns = "insert into jdbcdemo select max(id)+1,'Teste Insert' from jdbcdemo;";
		
	public static void main(String[] args) {
		try {
			String jdbcURL = "jdbc:postgresql://10.62.73.22:5432/";
			String username = "test_1";
			String password = "test_1";

			// JDBC and PostgreSQL: https://jdbc.postgresql.org/
			// PostgreSQL: https://www.postgresql.org/docs/7.4/jdbc-use.html

			// Step 1 - Load driver
			// Class.forName("org.postgresql.Driver"); // Class.forName() is not needed since JDBC 4.0

			// Step 2 -  Connecting to the Database
			Connection conn = DriverManager.getConnection(jdbcURL, username, password);

			// Step 3 - Execute statement 1
			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(cmdSel);			
			
			// execute the insert command
			stmt.executeUpdate(cmdIns);
			
			// access the ResultSet
			rs.first(); // generate exception

			// free the resources of the ResultSet
			if (rs != null) rs.close();
			// free the resources of the Statement
			if (stmt != null) stmt.close();
			// close connection
			if (conn != null) conn.close();
		}		
		catch(SQLException sqlex) {
			System.out.println(sqlex.getMessage());
		}
	}
}
