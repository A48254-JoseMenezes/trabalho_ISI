/** 
ISEL - DEETC
Introdução a Sistemas de Informação
MP,ND, 2014-2022
*/

import java.sql.*;
import java.util.HashMap;
import java.util.Scanner;



interface DbWorker 
{
	void doWork();
}	
class App
{

	String jdbcURL = "jdbc:postgresql://10.62.73.22:5432/l3n9";
	String username = "l3n9";
	String password = "chumbar";

	private enum Option
	{
		Unknown,
		Exit,
		CreateCasaAposta,
		NewPlayer,
		SuspendPlayer,
		GetNumberOfPlayersOfBettingHouse
	}
	private static App __instance = null;
	private String __connectionString;
	private HashMap<Option,DbWorker> __dbMethods;
	private static final String SELECT_CMD = "select name,dateBirth,sex from dbo.Student";
	
	private App()
	{
		__dbMethods = new HashMap<Option,DbWorker>();
		__dbMethods.put(Option.CreateCasaAposta, new DbWorker() {public void doWork() {App.this.CreateCasaApostas();}});
		__dbMethods.put(Option.NewPlayer, new DbWorker() {public void doWork() {App.this.NewPlayer();}});
		__dbMethods.put(Option.SuspendPlayer, new DbWorker() {public void doWork() {App.this.SuspendPlayer();}});
		__dbMethods.put(Option.GetNumberOfPlayersOfBettingHouse, new DbWorker() {public void doWork() {App.this.GetNumberOfPlayersOfBettingHouse();}});

	}
	public static App getInstance() 
	{
		if(__instance == null) 
		{
			__instance = new App();
		}
		return __instance;
	}

	private Option DisplayMenu()
	{ 
		Option option=Option.Unknown;
		try
		{
			System.out.println("Course management");
			System.out.println();
			System.out.println("1. Exit");
			System.out.println("2. New Casa de Apostas");
			System.out.println("3. New Player");
			System.out.println("4. Suspend Player");
			System.out.println("5. Number Of Players of Betting House");
			System.out.print(">");
			Scanner s = new Scanner(System.in);
			int result = s.nextInt();
			option = Option.values()[result];			
		}
		catch(RuntimeException ex)
		{
			//nothing to do. 
		}
		
		return option;
		
	}
	private final static void clearConsole() throws Exception
	{
	    for (int y = 0; y < 25; y++) //console is 80 columns and 25 lines
	    System.out.println("\n");

	}
	private void Login() throws java.sql.SQLException
	{

		Connection con = DriverManager.getConnection(getConnectionString());
		if(con != null)
			con.close();      
		
	}
	public void Run() throws Exception
	{
		Login ();
		Option userInput = Option.Unknown;
		do
		{
			clearConsole();
			userInput = DisplayMenu();
			clearConsole();		  	
			try
			{		
				__dbMethods.get(userInput).doWork();		
				System.in.read();		
				
			}
			catch(NullPointerException ex)
			{
				//Nothing to do. The option was not a valid one. Read another.
			}
			
		}while(userInput!=Option.Exit);
	}

	public String getConnectionString() 
	{
		return __connectionString;			
	}
	public void setConnectionString(String s) 
	{
		__connectionString = s;
	}

	/**
		To implement from this point forward. Do not need to change the code above.
	-------------------------------------------------------------------------------		
		IMPORTANT:
	--- DO NOT MOVE IN THE CODE ABOVE. JUST HAVE TO IMPLEMENT THE METHODS BELOW ---
	-------------------------------------------------------------------------------
	
	*/

	private void printResults(ResultSet dr)
	{
		//TODO
	}
	private void CreateCasaApostas()
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		Integer resultQuery = null;

		try {
		System.out.println("CreateCasaAposta()");
		conn = DriverManager.getConnection(jdbcURL, username, password);


		stmt = conn.prepareStatement("insert into casa_apostas(nome, nipc, aposta_minima) values (?, ?,?)");


		System.out.print("nome da casa de apostas > ");
		Scanner scannerName = new Scanner(System.in);
		String nomeCasa = scannerName.nextLine();

		System.out.print("NIPC > ");
		Scanner scannerNIPC = new Scanner(System.in);
		String  nipc = scannerNIPC.nextLine();

		System.out.print("aposta miníma > ");
		Scanner scannerMinBet = new Scanner(System.in);
		float minBet = scannerMinBet.nextFloat();

		 stmt.setString(1, nomeCasa);
		 stmt.setString(2, nipc);
		 stmt.setFloat(3, minBet);

		 resultQuery = stmt.executeUpdate();


			System.out.println(resultQuery);
			} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Step 5 Close connection
			try {
				// free the resources of the Statement
				if (stmt != null) stmt.close();
				// close connection
				if (conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	private void NewPlayer()
	{
		//TODO: Implement
	System.out.println("NewPlayer()");

	Connection conn = null;
	PreparedStatement stmt = null;
	Integer resultQuery = null;

	try {

		conn = DriverManager.getConnection(jdbcURL, username, password);


		stmt = conn.prepareStatement("insert into jogador( " +
				"id, " +
				"email, " +
				"nome, " +
				"nickname, " +
				"estado, " +
				"data_nascimento, " +
				"data_registo, " +
				"morada, " +
				"codigo_postal, " +
				"localidade,  " +
				"casa_apostas )" +
				" values (?,?,?,?,?,?,?,?,?,?,?)");



		System.out.print("id jogador > ");
		Scanner scannerId = new Scanner(System.in);
		Integer id = scannerId.nextInt();

		System.out.print("email > ");
		Scanner scannerEmail = new Scanner(System.in);
		String  email = scannerEmail.nextLine();

		System.out.print("nome jogador > ");
		Scanner scannerNome = new Scanner(System.in);
		String nome = scannerNome.nextLine();

		System.out.print("nickname > ");
		Scanner scannerNickname = new Scanner(System.in);
		String  nickname = scannerNickname.nextLine();

		System.out.print("estado > ");
		Scanner scannerEstado = new Scanner(System.in);
		String  estado = scannerEstado.nextLine();

		System.out.print("data nascimento > ");
		Scanner scannerDataNascimento = new Scanner(System.in);
		String  dataNascimento = scannerDataNascimento.nextLine(); /* Ver como Converter para DATE*/

		System.out.print("data de registo > ");
		Scanner scannerDataRegisto = new Scanner(System.in);
		String  dataRegisto = scannerDataRegisto.nextLine();/* Ver como Converter para DATE*/

		System.out.print("morada > ");
		Scanner scannerMorada = new Scanner(System.in);
		String  morada = scannerMorada.nextLine();

		System.out.print("codigo postal > ");
		Scanner scannerCodigoPostal = new Scanner(System.in);
		Integer  codigoPostal = scannerCodigoPostal.nextInt();

		System.out.print("localidade > ");
		Scanner scannerLocalidade = new Scanner(System.in);
		String  localidade = scannerLocalidade.nextLine();

		System.out.print("ID casa de apostas > ");
		Scanner scannerCasaApostas = new Scanner(System.in);
		Integer  casaApostas = scannerCasaApostas.nextInt();


		stmt.setInt(1, id);
		stmt.setString(2, email);
		stmt.setString(3, nome);
		stmt.setString(4, nickname);
		stmt.setString(5, estado);
		stmt.setDate(6, Date.valueOf(dataNascimento));
		stmt.setDate(7, Date.valueOf(dataRegisto));
		stmt.setString(8, morada);
		stmt.setInt(9, codigoPostal);
		stmt.setString(10, localidade);
		stmt.setInt(11, casaApostas);

		resultQuery = stmt.executeUpdate();


		System.out.println(resultQuery);
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		// Step 5 Close connection
		try {
			// free the resources of the Statement
			if (stmt != null) stmt.close();
			// close connection
			if (conn != null) conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}




	}

	private void makeBet(){
		// TODO implement
	}

	private void SuspendPlayer()
	{
		//TODO: Implement
		System.out.println("RegisterStudent()");

		Connection conn = null;
		PreparedStatement stmt = null;
		Integer resultQuery = null;

		try {

			conn = DriverManager.getConnection(jdbcURL, username, password);

			stmt = conn.prepareStatement("select * from jogador where id=?");

			System.out.print("id jogador > ");
			Scanner scannerId = new Scanner(System.in);
			Integer id = scannerId.nextInt();

			stmt.setInt(1,id);

			ResultSet resultQueryStudentExits = stmt.executeQuery();
			if(resultQueryStudentExits.next()){
				stmt = conn.prepareStatement("update  jogador set estado = 'suspenso' where id =?");
				stmt.setInt(1,id);

				Integer rs = stmt.executeUpdate();

				System.out.println(rs);
			}
			//System.out.println(rs.getString("nome"));
			else{ System.out.println("no player with that id exists");}

		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Step 5 Close connection
			try {
				// free the resources of the Statement
				if (stmt != null) stmt.close();
				// close connection
				if (conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	private void GetNumberOfPlayersOfBettingHouse()
	{
		//TODO: Implement
		System.out.println("NumbPlayersOfBettingHouse()");
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement prepStmt = null;
		Integer resultQuery = null;

		try {

			conn = DriverManager.getConnection(jdbcURL, username, password);
			stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery("select * from casa_apostas");

			while(rs.next()){
				System.out.println(rs.getString("nome") +" -> "+rs.getString("id"));
			}

			prepStmt = conn.prepareStatement("select cp.nome, count(cp.id) " +
					"from casa_apostas cp " +
					"join jogador j on cp.id = j.casa_apostas " +
					"group by cp.id, cp.nome " +
					"having cp.id = ? ");

			System.out.print("id casa de apostas > ");
			Scanner scannerCasaApostas = new Scanner(System.in);
			Integer  IdCasaApostas = scannerCasaApostas.nextInt();

			prepStmt.setInt(1, IdCasaApostas);

			rs = prepStmt.executeQuery();
			if(!rs.next()) {System.out.println("does not have any players registered");}

			while(rs.next()){
				System.out.println(rs.getString("nome") + " tem "+ rs.getString("count")+ " players");
			}




		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Step 5 Close connection
			try {
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

public class Ap3
{
	public static void main(String[] args) throws SQLException,Exception
	{
		String url =  "jdbc:postgresql://10.62.73.22:5432/?user=test_1&password=test_1&ssl=false";
		App.getInstance().setConnectionString(url);
		App.getInstance().Run();
	}
}

/* -------------------------------------------------------------------------------- 
private class Connect {
	private java.sql.Connection con = null;
    private final String url = "jdbc:sqlserver://";
    private final String serverName = "localhost";
    private final String portNumber = "1433";
    private final String databaseName = "aula03";
    private final String userName = "matildepato";
    private final String password = "xxxxxxx";

    // Constructor
    public Connect() {
    }

    private java.sql.Connection getConnection() {
        try {
            con = java.sql.DriverManager.getConnection(url, user, pwd);
            if (con != null) {
                System.out.println("Connection Successful!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error Trace in getConnection() : " + e.getMessage());
        }
        return con;
    }

    private void closeConnection() {
        try {
            if (con != null) {
                con.close();
            }
            con = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
 --------------------------------------------------------------------------------
 */