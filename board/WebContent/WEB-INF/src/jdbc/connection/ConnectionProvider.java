package jdbc.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Connection 객체 구할 수 있는 클래스
public class ConnectionProvider {

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:apache:commons:dbcp:board");
    }
}
