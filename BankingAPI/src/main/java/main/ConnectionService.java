package main;

//import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
//import java.util.Properties;

public class ConnectionService {
	private static Connection connection;

	public static Connection getConnection() {
		if (connection == null) {
			try {
				// FileInputStream fis = new FileInputStream("properties");
				// Properties prop = new Properties();
				// prop.load(fis);
				// connection = DriverManager.getConnection(prop.getProperty("url"),
				// prop.getProperty("username"),
				// prop.getProperty("password"));
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection("jdbc:postgresql://lallah.db.elephantsql.com:5432/cfxdpnkf",
						"cfxdpnkf", "lwQjxrmgA47OWt0n8zr1y0st4wyYlC9R");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return connection;
	}

	public static void closeConnection() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
