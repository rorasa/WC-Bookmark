package wcBookmark;

import java.io.File;
import java.sql.*;

public class DbManager {

    public String dbname;
    public Connection connection;
    public Statement statement;

    public DbManager(String dbname){
        try {
            this.dbname = dbname;
            if (new File(dbname).isDirectory()) {
                connection = DriverManager.getConnection("jdbc:derby:"+dbname);
                System.out.println("Connected to database");
            } else {
                connection = DriverManager.getConnection("jdbc:derby:"+dbname+";create=true");
                System.out.println("New database created");
            }

            statement = connection.createStatement();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public boolean execute(String sql){
        boolean flag = false;
        try {
            flag = statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public ResultSet executeQuery(String sql){
        ResultSet rs;
        try{
            rs = statement.executeQuery(sql);
            return rs;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}