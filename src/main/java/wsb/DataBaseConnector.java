package wsb;

import java.sql.*;
import java.util.Properties;

public class DataBaseConnector implements DatabaseQuery{

    public static final  String DATABASE_LOCAL_URL = "jdbc:postgresql://localhost:5432/";
    public static final  String DATABASE_NAME = "CurrencyApi";

    public Connection connect() throws SQLException {
        String url = DATABASE_LOCAL_URL + DATABASE_NAME;
        Properties props = new Properties();
        props.setProperty("user",DatabaseConfig.USERNAME);
        props.setProperty("password",DatabaseConfig.PASS);
        //props.setProperty("ssl","true");
        Connection conn = DriverManager.getConnection(url, props);
        return conn;

    }



//    public ResultSet execute(String query) throws SQLException {
//
//    }

    @Override
    public void insertQuery(String query) {
        try {
            Connection conn = connect();
            Statement stm = conn.createStatement();
            stm.execute(query);


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public ResultSet selectQuery(String query) {
        try {
            Connection conn = connect();
            Statement stm = conn.createStatement();
            return stm.executeQuery(query);

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }
}
