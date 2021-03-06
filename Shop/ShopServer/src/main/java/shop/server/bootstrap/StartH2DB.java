package shop.server.bootstrap;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.h2.tools.Console;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class StartH2DB extends Thread{

	private static final String propertyFile = System.getProperty("user.home") + "/Development";
	public DataSource createDataSource(){
		
		System.out.println("initializing H2 DB in memory...");
		
		EmbeddedDatabase database = null;
		try{
			database = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).setName("~/shop").build();
			openH2Console();
		}catch(Exception e){
			System.out.println("failed to initialize H2 DB " + e.getMessage());
		}
		
		return database;
	}
	
	@Override
	public void run(){
		createDataSource();
		
	}
	
	private static void openH2Console(){
		String params[] = new String[4];
		params[0] = "-web";
		params[1] = "-webAllowOthers";
		params[2] = "-properties";
		params[3] = propertyFile;
		
		try {
			Console.main(params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
