package ca.jrvs.apps.jdbc.util;

import java.sql.*;
import java.util.List;

// Why is DataTransferObject inside the angle bracket?
public abstract class DataAccessObject <T extends DataTransferObject> {

    protected final Connection connection;
    protected final static String LAST_VAL = "SELECT last_value FROM ";
    protected final static String CUSTOMER_SEQUENCE = "hp_customer_seq";

    public DataAccessObject(Connection connection){
        super();
        this.connection = connection;
    }

    // why T is not in an angle bracket?
    public abstract T findById(long id);
    public abstract List<T> findAll();
    public abstract T update(T dto);
    public abstract T create(T dto);
    public abstract void delete(long id);

    protected int getLastVal(String sequence){
        int key = 0;
        String sql = LAST_VAL + sequence;
        try(Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                key=rs.getInt(1);
            }
            return key;
        }catch (SQLException e ){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
