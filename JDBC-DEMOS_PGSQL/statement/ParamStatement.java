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
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class ParamStatement {
	final static String cmdIns = "insert into jdbcdemo select max(id)+1,? from jdbcdemo;";
	final static String cmdSel = "SELECT * from jdbcdemo;";

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

			// Get statement for the parameterized statement
			PreparedStatement pstmt = conn.prepareStatement(cmdIns);

			// set the parameters
			pstmt.setString(1,"teste com parametros"); //

			// execute the insert command
			pstmt.executeUpdate();

			// get a statement for the select command
			Statement stmt = conn.createStatement();
			// get the results through a select
			ResultSet rs=stmt.executeQuery(cmdSel);

			// iterate
			while(rs.next())
				System.out.println(rs.getString("value"));
			System.out.println();

			// free the resources of the ResultSet, Statement, Prepared Statement and Connection
			rs.close();
			stmt.close();
			pstmt.close();
			conn.close();
		}
		catch(SQLException sqlex) {
			System.out.println(sqlex.getMessage());
		}
	}
}
