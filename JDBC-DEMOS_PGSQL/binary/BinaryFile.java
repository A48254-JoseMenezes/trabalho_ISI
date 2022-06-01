/**
 * ISEL-DEETC
 * Introdução a Sistemas de Informacao
 * user: MPato
 * version 1.0
 * date: january 2022
 */

package binary;

import java.sql.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class BinaryFile
{
    final static String cmdIns = "insert into jdbcblob values(?,?);";
    final static String cmdSel = "select databin from jdbcblob where filename = ?";

    private static byte[] ReadFile(File file) throws IOException
    {
        long length = file.length();
        FileInputStream in = new FileInputStream(file);
        ByteArrayOutputStream out = new ByteArrayOutputStream((int) length);
        byte[] tmp = new byte[1024];
        int len;
        do {
            len = in.read(tmp);
            out.write(tmp, 0, len);
        }
        while (len == tmp.length);
        return out.toByteArray();
    }

    private static void WriteFile(String name, byte[] data) throws IOException
    {
        File file = new File(name);
        file.createNewFile();

        FileOutputStream out = new FileOutputStream(file);
        out.write(data);
        out.close();
    }
    private static String getFile(String[] args)
    {
        if (args.length < 1)
        {
            Usage();
        }
        return args[0];
    }

    private static void Usage()
    {
        System.out.println("Usage: java program <binary-filepath>");
        System.exit(1);
    }

	public static void main(String[] args) throws IOException
	{
        String jdbcURL = "jdbc:postgresql://10.62.73.22:5432/";
        String username = "test_1";
        String password = "test_1";

        try
		{
            // JDBC and PostgreSQL: https://jdbc.postgresql.org/
            // PostgreSQL: https://www.postgresql.org/docs/7.4/jdbc-use.html

            // Step 1 - Load driver
            // Class.forName("org.postgresql.Driver"); // Class.forName() is not needed since JDBC 4.0

            // Step 2 -  Connecting to the Database
            Connection conn = DriverManager.getConnection(jdbcURL, username, password);

            // Handling File
            // IMPORTANT: do not forget to put the filename with complete path as args
            // Example: consider the .txt file inside this folder
            File file = new File(getFile(args));
            if(!file.exists())
                Usage();
            byte[] tmp = ReadFile(file);

            conn.setAutoCommit(false); // to allow you to run the program multiple times
            // Get statement for the parameterized statement
			PreparedStatement pstmt = conn.prepareStatement(cmdIns);
            pstmt.setString(1, file.getName());
            pstmt.setBytes(2, tmp);
            pstmt.executeUpdate();
            
            // free the resources of the PreparedStatement
            pstmt.close();
            // Get statement for the parameterized statement
            pstmt = conn.prepareStatement(cmdSel);
            pstmt.setString(1, file.getName());

            ResultSet rs = pstmt.executeQuery();
            rs.next();
            byte[] data = rs.getBytes(1);

            // Allows running the program multiple times
            // con.rollback();
            // To check what remains in the BD, replace the rollback with a commit.
            conn.commit();

            // free the resources of the ResultSet
            if (rs != null) rs.close();
            // free the resources of the Statement
            if (pstmt != null) pstmt.close();
            // close connection
            if (conn != null) conn.close();
            // write file to a filesystem
            WriteFile("new_"+file.getName(),data);
		}		
		catch(SQLException sqlex)
		{
			System.out.println(sqlex.getMessage());
		}
	}
}
