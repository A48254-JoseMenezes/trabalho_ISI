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

public class DefaultStatement {
	final static String cmdIns = "insert into jdbcdemo select max(id)+1,'Test' from jdbcdemo;";
	final static String cmdSel = "SELECT * from jdbcdemo;";
	
	public static void main(String[] args) {

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
			
			// executar o comando de inserção
			stmt.executeUpdate(cmdIns);
			
			// obter os resultados através de um select
			rs = stmt.executeQuery(cmdSel);
			
			//iterar no resultado
			while(rs.next())
				  System.out.print(rs.getString("value"));

		} catch (SQLException e) {
			 e.printStackTrace();
		 } finally {
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