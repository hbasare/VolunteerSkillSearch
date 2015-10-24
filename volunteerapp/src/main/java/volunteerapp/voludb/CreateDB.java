package volunteerapp.voludb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.StringTokenizer;

public class CreateDB {

	public static final String DRIVER= "org.apache.derby.jdbc.EmbeddedDriver";
	public static final String JDBC_URL= "jdbc:derby:voludb;create=true";
	
	public void createAdminUsers() throws ClassNotFoundException, SQLException   {
		Class.forName(DRIVER);
		Connection connection=DriverManager.getConnection(JDBC_URL);
		connection.createStatement().execute("drop table adminusers");
		connection.createStatement().execute("create table adminusers(id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
				+ "firstname varchar(50), "
				+ "lastname varchar(50), "
				+ "companyname varchar(50), "
				+ "phonenumber varchar(50), "
				+ "email varchar(50), "
				+ "password varchar(50),"
				+ "CONSTRAINT primary_key_adminusers PRIMARY KEY (id))");
		connection.createStatement().execute("insert into adminusers(firstname,lastname,companyname,phonenumber,email,password) values "+
										     "('john','doe','company1','555-555-5555','admin1@voludb.com','1234'),"+
											 "('jane','doe','company2','555-666-6666','admin2@voludb.com','1234'),"+
											 "('temp','doe','company3','555-777-7777','admin3@voludb.com','1234')");
		System.out.println("adminusers table created and records sucessfully inserted.");

		if (connection !=null) connection.close();
		
	}
	
	public void createVolunteerUsers(String filename) throws ClassNotFoundException, SQLException, IOException   {
		String insertStatement="insert into volunteerusers(firstname,lastname,gender,dob,email,phone,address,city,province,language1,language2,skillset,password) values ";
		File f=new File(filename);
		FileReader fr=new FileReader(f);
		BufferedReader br=new BufferedReader(fr);
		String line=br.readLine(); //header
		line=br.readLine();
		
		while (line != null){
			StringTokenizer st=new StringTokenizer(line,",");
			insertStatement=insertStatement+"(";
			while (st.hasMoreTokens()){
				insertStatement=insertStatement+"'"+st.nextToken()+"',";
			}
			insertStatement=insertStatement.substring(0, insertStatement.lastIndexOf(","));
			insertStatement=insertStatement+"),\n";
			line=br.readLine();
			
		}
		insertStatement=insertStatement.substring(0, insertStatement.lastIndexOf(","));
		//System.out.println(insertStatement);
		
		
		
		Class.forName(DRIVER);
		Connection connection=DriverManager.getConnection(JDBC_URL);
		connection.createStatement().execute("drop table volunteerusers");
		connection.createStatement().execute("create table volunteerusers(id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
				+ "firstname varchar(50), "
				+ "lastname varchar(50), "
				+ "gender varchar(20), "
				+ "dob date, "
				+ "email varchar(50), "
				+ "phone varchar(50), "
				+ "address varchar(50), "
				+ "city varchar(50), "
				+ "province varchar(50), "
				+ "language1 varchar(50), "
				+ "language2 varchar(50), "
				+ "skillset varchar(50), "
				+ "password varchar(50),"
				+ "CONSTRAINT primary_key_volunteerusers PRIMARY KEY (id))");
		
		connection.createStatement().execute(insertStatement);
		System.out.println("volunteerusers table created and records sucessfully inserted.");

		if (connection !=null) connection.close();
		
	}
	
	public static void main(String[] args) throws IOException  {
		CreateDB cb=new CreateDB();
		try {
			cb.createAdminUsers();
			//File currentDirFile = new File(".");
			//String helper = currentDirFile.getAbsolutePath();
			//System.out.println(currentDir);
			cb.createVolunteerUsers("./lib/RAW_MOCK_DATA.csv");//"../../../../../lib/RAW_MOCK_DATA.csv");
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
