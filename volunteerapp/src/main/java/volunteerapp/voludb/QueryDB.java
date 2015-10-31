package volunteerapp.voludb;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;



import java.sql.ResultSetMetaData;

public class QueryDB {
	public static final String GET_ALL_ADMINUSERS = "select * from adminusers";
	public static final String GET_ALL_VOLUNTEERUSERS = "select * from volunteerusers";

	
	public void connectAndExecute(String SQL_Statement) throws SQLException{
		Connection connection = DriverManager.getConnection(CreateDB.JDBC_URL);
		Statement statement= connection.createStatement();
		ResultSet resultset=statement.executeQuery(SQL_Statement);
		/*ResultSetMetaData resultSetMetaData=resultset.getMetaData();
		int columnCount = resultSetMetaData.getColumnCount();
		for (int x =1; x<=columnCount; x++){
			System.out.format("%20s", resultSetMetaData.getColumnName(x)+ "|");
		}
		while (resultset.next()){
			System.out.println("");
			for (int x =1; x<=columnCount; x++){
				System.out.format("%20s", resultset.getString(x)+ "|");
			}
		}
	*/
	
		if (statement !=null) statement.close();
		if (connection !=null) connection.close();
		
	}
	public void connectAndReturnAll(String tableName, String orderBy) throws SQLException{
		Connection connection = DriverManager.getConnection(CreateDB.JDBC_URL);
		Statement statement= connection.createStatement();
		ResultSet resultset=statement.executeQuery("select * from "+tableName+" order by "+orderBy);
		ResultSetMetaData resultSetMetaData=resultset.getMetaData();
		int columnCount = resultSetMetaData.getColumnCount();
		for (int x =1; x<=columnCount; x++){
			System.out.format("%20s", resultSetMetaData.getColumnName(x)+ "|");
		}
		while (resultset.next()){
			System.out.println("");
			for (int x =1; x<=columnCount; x++){
				System.out.format("%20s", resultset.getString(x)+ "|");
			}
		}
	System.out.println("\n");
		if (statement !=null) statement.close();
		if (connection !=null) connection.close();
		
	}
	public void connectAndReturnASingleRecord(String tableName,String idToReturn) throws SQLException{
		Connection connection = DriverManager.getConnection(CreateDB.JDBC_URL);
		Statement statement= connection.createStatement();
		ResultSet resultset=statement.executeQuery("select * from "+tableName+" where id="+idToReturn);
		ResultSetMetaData resultSetMetaData=resultset.getMetaData();
		int columnCount = resultSetMetaData.getColumnCount();
		for (int x =1; x<=columnCount; x++){
			System.out.format("%20s", resultSetMetaData.getColumnName(x)+ "|");
		}
		while (resultset.next()){
			System.out.println("");
			for (int x =1; x<=columnCount; x++){
				System.out.format("%20s", resultset.getString(x)+ "|");
			}
		}
	
		if (statement !=null) statement.close();
		if (connection !=null) connection.close();
		
	}
	public boolean connectAndDeleteASingleRecord(String tableName, String idToDelete) {
		try{
		Connection connection = DriverManager.getConnection(CreateDB.JDBC_URL);
		connection.createStatement().executeUpdate("delete from "+tableName+" where id ="+idToDelete);
		
		if (connection !=null) connection.close();
		return true;
		}
		
		catch (SQLException e){
			e.printStackTrace();
			return false;
		}
	}
	
	private boolean connectAndUpdateARecord(String tableName, ArrayList <String> thingsToUpdate, String idToUpdate) {
		try{
		Connection connection = DriverManager.getConnection(CreateDB.JDBC_URL);
		String thingsToUpdateString = "";
		for (int i=0;i<thingsToUpdate.size();i++){
			thingsToUpdateString=thingsToUpdateString+thingsToUpdate.get(i)+",";	
		}
		thingsToUpdateString=thingsToUpdateString.substring(0, thingsToUpdateString.lastIndexOf(","));
		//System.out.println(thingsToUpdateString);
		System.out.println("update "+tableName+" set "+thingsToUpdateString+" where id ="+idToUpdate);
		connection.createStatement().executeUpdate("update "+tableName+" set "+thingsToUpdateString+" where id ="+idToUpdate);
		
		if (connection !=null) connection.close();
		return true;
		}
		
		catch (SQLException e){
			e.printStackTrace();
			return false;
		}
		
	}
	
	public static void main(String[] args)  {
		QueryDB qdb=new QueryDB();
		try {
			//qdb.connectAndExecute(GET_ALL_ADMINUSERS);
			//qdb.connectAndExecute(GET_ALL_VOLUNTEERUSERS);
			//qdb.connectAndReturnAll("volunteerusers");
			qdb.connectAndReturnAll("adminusers","id");
			qdb.connectAndDeleteASingleRecord("adminusers", "3");
			qdb.connectAndReturnAll("adminusers","firstname");
			qdb.connectAndReturnASingleRecord("adminusers", "3");
			ArrayList<String> updateArray=new ArrayList<String>();
			updateArray.add("firstname='kem'");
			updateArray.add("lastname='koe'");
			qdb.connectAndUpdateARecord("adminusers", updateArray, "3");
			qdb.connectAndReturnASingleRecord("adminusers", "3");
			
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
