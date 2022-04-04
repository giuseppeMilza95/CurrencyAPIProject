package wsb;

import java.sql.ResultSet;

interface  DatabaseQuery {

    public void insertQuery(String query);
    public ResultSet selectQuery(String query);


}
