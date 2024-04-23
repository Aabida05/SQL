import java.sql.*;
import java.util.*;

public class BankAccount{
	
	private static Connection conn;
	
	
	public static void main(String[] args) throws Exception{
		
		dbConnect();
			System.out.println("1 - Add Customer");
			System.out.println("2 - Update Balance");
			System.out.println("3 - Delete Account");
			System.out.println("4 - View All Customer");
			System.out.println("0 - exit");
			
			Scanner scanner = new Scanner(System.in);
			System.out.print("Enter the option: ");
			int option = scanner.nextInt();
			
			while(option!=0) {
			if(option==1) {
				insertUsingPs();
			}else if(option==2) {
				update();
			}else if(option==3) {
				delete();
			}else if(option==4) {
				readRecord();
			}
			
			System.out.println("1 - Add Customer");
			System.out.println("2 - Update Balance");
			System.out.println("3 - Delete Account");
			System.out.println("4 - View All Customer");
			System.out.println("0 - exit");
			
			System.out.print("Enter the option: ");
			option = scanner.nextInt();
		}
	}
	

		//-------Connecting Databases---------
		public static void dbConnect(){
			try {
				
				Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/?","root","");
				Statement statement = connection.createStatement();
				String createDatabaseQuery = "CREATE DATABASE IF NOT EXISTS newdatabase";
				
				statement.executeUpdate(createDatabaseQuery);
				
				String useDatabaseQuery = "USE newdatabase";
				
				statement.executeUpdate(useDatabaseQuery);
				
				String createTableQuery = "CREATE TABLE IF NOT EXISTS account(name VARCHAR(255),id INT,balance INT)";
				
				statement.executeUpdate(createTableQuery);
				
				statement.close();
				connection.close();
				
				System.out.println("Database and table create successully");
				
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/newdatabase","root","");
				
			}catch(Exception e) {
				System.out.println("Error: " + e.getMessage());
			}

		}
		

		

		//----------Insert Using PreparedStatement-------------
		public static void insertUsingPs() throws Exception{
			Scanner scan = new Scanner(System.in);
			System.out.print("Enter the Name: ");
			String name = scan.next();
			System.out.print("Enter the ID: ");
			int id = scan.nextInt();
			System.out.print("Enter the Amount: ");
			int balance = scan.nextInt();
			
			String query = "insert into account values (?,?,?);";
			
			Statement st = conn.createStatement();
			int rows = st.executeUpdate("insert into account values ('"+ name + "', " +id+ ", "+ balance +")");
			
			System.out.println("Number of rows Affected: " + rows);
		}
	
		//---------To Update Account----------
		public static void update() throws Exception{
			Scanner scan= new Scanner(System.in);
			
			System.out.print("Enter the ID: ");
			int id = scan.nextInt();
			System.out.print("Enter the update Amount: ");
			int amount = scan.nextInt();
					
			String query = "update account set balance = " + amount +" where ID=" + id;
			
			Statement st = conn.createStatement();
			int rows = st.executeUpdate(query);

			System.out.println("Number of rows Affected: " + rows);

		}
	
		//--------To Delete Account---------
		public static void delete() throws Exception{
			Scanner scan = new Scanner(System.in);
			
			System.out.print("Enter the ID to delete: ");
			int id = scan.nextInt();
			
			String query = "delete from account where ID=" + id;
			

			Statement st = conn.createStatement();
			int rows = st.executeUpdate(query);
			
			System.out.println("Number of rows Affected: " + rows);

		}
	
		//------To Read Records----------
		public static void readRecord() throws Exception{
			String query = "SELECT * from account";
		
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
	
			System.out.println("Name \t\t ID \t\t Balance");
			System.out.println("-------------------------------------------");
			while(rs.next()) {
				System.out.println(rs.getString(1) + "\t\t" + rs.getInt(2) + "\t\t" + rs.getInt(3));
			}
			System.out.println("-------------------------------------------");

		}
}
