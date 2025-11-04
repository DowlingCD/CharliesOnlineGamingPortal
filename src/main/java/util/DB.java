package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;

public class DB {
    private static String url, user, pass, driver;

    static {
        try {
            Properties p = new Properties();
            InputStream in = DB.class.getClassLoader().getResourceAsStream("db.properties");
            if (in == null) throw new RuntimeException("db.properties not found on classpath");
            p.load(in);
            url = p.getProperty("jdbc.url");
            user = p.getProperty("jdbc.user");
            pass = p.getProperty("jdbc.password");
            driver = p.getProperty("jdbc.driver", "com.mysql.cj.jdbc.Driver");
            Class.forName(driver);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load DB config: " + e.getMessage(), e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }
}
