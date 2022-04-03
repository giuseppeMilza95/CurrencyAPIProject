package wsb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DataBaseConnector {

    public static final  String DATABASE_LOCAL_URL = "jdbc:postgresql://localhost:5432/";
    public static final  String DATABASE_NAME = "CurrencyAPI";

    public Connection connect() throws SQLException {
        String url = DATABASE_LOCAL_URL + DATABASE_NAME;
        Properties props = new Properties();
        props.setProperty("user",DatabaseConfig.USERNAME);
        props.setProperty("password",DatabaseConfig.PASS);
        //props.setProperty("ssl","true");
        Connection conn = DriverManager.getConnection(url, props);
        return conn;

    }



    public void execute(String query) throws SQLException {
        try {
            Connection conn = connect();
            Statement stm = conn.createStatement();
            stm.execute(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
