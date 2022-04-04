package wsb;

import java.sql.ResultSet;

interface  DatabaseQuery {

    void insertUpdateQuery(String query);
    ResultSet selectQuery(String query);


}
