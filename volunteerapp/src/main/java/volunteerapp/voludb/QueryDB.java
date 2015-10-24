package volunteerapp.voludb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.ResultSetMetaData;

public class QueryDB {
	public static final String GET_ALL_ADMINUSERS = "select * from adminusers";
	public static final String GET_ALL_VOLUNTEERUSERS = "select * from volunteerusers";
	
	public void connectAndExecute(String SQL_Statement) throws SQLException{
		Connection connection = DriverManager.getConnection(CreateDB.JDBC_URL);
		Statement statement= connection.createStatement();
		ResultSet resultset=statement.executeQuery(SQL_Statement);
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
	
	public static void main(String[] args)  {
		QueryDB qdb=new QueryDB();
		try {
			qdb.connectAndExecute(GET_ALL_ADMINUSERS);
			qdb.connectAndExecute(GET_ALL_VOLUNTEERUSERS);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
