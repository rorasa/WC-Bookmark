package wcBookmark;

import java.io.File;
import java.sql.*;

public class DbManager {

    private String dbname;
    private Connection connection;
    private Statement statement;

    public DbManager(String dbname){
        try {
            this.dbname = dbname;
            if (new File(dbname).isDirectory()) {
                connection = DriverManager.getConnection("jdbc:derby:"+dbname);
                statement = connection.createStatement();
                System.out.println("Connected to database");
            } else {
                connection = DriverManager.getConnection("jdbc:derby:"+dbname+";create=true");
                statement = connection.createStatement();
                System.out.println("New database created");
                createNewDatabase();
                System.out.println("Creaeted database structure");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        try {
            DriverManager.getConnection("jdbc:derby:"+dbname+";shutdown=true");
        } catch (SQLException e) {}
    }

    public Connection getConnection(){
        return this.connection;
    }

    public Statement getStatement(){
        return this.statement;
    }

    public String getName(){
        return this.dbname;
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

    private void createNewDatabase(){
        String sql;

        try {
            // create SHOP table
            sql = "CREATE TABLE SHOP (ID INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                "NAME VARCHAR(120) NOT NULL, " +
                "LOCATION VARCHAR(120), " +
                "COUNTRY VARCHAR(60), " +
                "TEL VARCHAR(20), " +
                "EMAIL VARCHAR(60), " +
                "WEBSITE VARCHAR(200), " +
                "NOTE VARCHAR(2000))";

            statement.execute(sql);

            // create BOOKMARK table
            sql =  "CREATE TABLE BOOKMARK (ID INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                "COLLECTION_ID VARCHAR(30), " +
                "NAME VARCHAR(120) NOT NULL, " +
                "DESCRIPTION VARCHAR(120), " +
                "TYPE VARCHAR(20), " +
                "PRICE NUMERIC, " +
                "BOUGHT_FROM INT REFERENCES SHOP(ID) ON DELETE SET NULL, " +
                "BOUGHT_DATE DATE, " +
                "DATE_ADDED DATE, " +
                "OWN BOOLEAN, " +
                "NOTE VARCHAR(2000))";

            statement.execute(sql);

            // create DETAIL_PAGE table
            sql = "CREATE TABLE DETAIL_PAGE (BOOKMARK_ID INT NOT NULL PRIMARY KEY REFERENCES BOOKMARK(ID) ON DELETE CASCADE, " +
                "DETAIL VARCHAR(2000), " +
                "DATE_ADDED DATE, " +
                "PHOTO BLOB(10M), " +
                "PHOTO_LABEL VARCHAR(120))";

            statement.execute(sql);

            // create PHOTO table
            sql = "CREATE TABLE PHOTO (BOOKMARK_ID INT NOT NULL PRIMARY KEY REFERENCES BOOKMARK(ID) ON DELETE CASCADE, " +
                "PHOTO BLOB(10M), " +
                "DATE_ADDED DATE)";

            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}