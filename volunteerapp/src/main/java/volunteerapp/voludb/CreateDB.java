package volunteerapp.voludb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CreateDB {

	public static final String DRIVER= "org.apache.derby.jdbc.EmbeddedDriver";
	public static final String JDBC_URL= "jdbc:derby:voludb;create=true";
	
	public void createAdminUsers() throws ClassNotFoundException, SQLException   {
		Class.forName(DRIVER);
		Connection connection=DriverManager.getConnection(JDBC_URL);
		connection.createStatement().execute("drop table adminusers");
		connection.createStatement().execute("create table adminusers(firstname varchar(50), "
				+ "lastname varchar(50), "
				+ "companyname varchar(50), "
				+ "phonenumber varchar(50), "
				+ "email varchar(50), "
				+ "username varchar(50), "
				+ "password varchar(50))");
		connection.createStatement().execute("insert into adminusers values "+
										     "('john','doe','company1','555-555-5555','admin1@company1.com','admin1','1234'),"+
											 "('jane','doe','company2','555-666-6666','admin2@company2.com','admin2','1234')");
		System.out.println("adminusers table created and records sucessfully inserted.");

		
		
	}
	public static void main(String[] args)  {
		CreateDB cb=new CreateDB();
		try {
			cb.createAdminUsers();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
