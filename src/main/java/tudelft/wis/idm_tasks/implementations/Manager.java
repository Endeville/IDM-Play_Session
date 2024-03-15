package tudelft.wis.idm_tasks.implementations;

import tudelft.wis.idm_tasks.basicJDBC.interfaces.JDBCManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Manager implements JDBCManager {

    public Manager() {

    }
    @Override
    public Connection getConnection() throws SQLException, ClassNotFoundException {
        String url = "jdbc:postgresql://localhost:5432/";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "123456");
        return DriverManager.getConnection(url, props);
    }
}
