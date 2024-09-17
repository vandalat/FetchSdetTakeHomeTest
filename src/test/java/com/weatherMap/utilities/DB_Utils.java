package com.weatherMap.utilities;

import java.sql.*;

public class DB_Utils {
    private static Connection con ;
    private static Statement stm ;
    private static ResultSet rs ;
    private static ResultSetMetaData rsmd ;


    /**
     * Create Connection by jdbc url and username , password provided
     * @param url  jdbc url for any database
     * @param username username for database
     * @param password password for database
     */
    public static void createConnection(String url , String username, String password){


        try {
            con = DriverManager.getConnection(url, username, password) ;
            System.out.println("CONNECTION SUCCESSFUL");
        } catch (Exception e) {
            System.out.println("CONNECTION HAS FAILED " + e.getMessage() );
        }

    }


    /**
     * Create connection method , just checking one connection successful or not
     */
    public static void createConnection(){

        String url      = Environment.DB_URL;
        String username = Environment.DB_USERNAME;
        String password = Environment.DB_PASSWORD;

        createConnection(url, username, password);

    }


    /**
     * Run the sql query provided and return ResultSet object
     * @param sql the query to run
     * @return ResultSet object  that contains data
     */
    public static ResultSet runQuery(String sql){

        try {
            stm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stm.executeQuery(sql); // setting the value of ResultSet object
            rsmd = rs.getMetaData() ;  // setting the value of ResultSetMetaData for reuse
        }catch(Exception e){
            System.out.println("ERROR OCCURRED WHILE RUNNING QUERY "+ e.getMessage() );
        }

        return rs ;

    }

    /**
     * destroy method to clean up all the resources after being used
     */
    public static void destroy(){
        try {
            if( rs!=null)  rs.close();
            if( stm!=null)  stm.close();
            if( con!=null)  con.close();
        } catch (Exception e) {
            System.out.println("ERROR OCCURRED WHILE CLOSING RESOURCES " + e.getMessage() );
        }

    }

    /**
     * This method will reset the cursor to before first location
     */
    private static void resetCursor(){

        try {
            rs.beforeFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * find out the row count
     * @return row count of this ResultSet
     */
    public static int getRowCount(){

        int rowCount = 0 ;
        try {
            rs.last() ;
            rowCount = rs.getRow() ;
        } catch (Exception e) {
            System.out.println("ERROR OCCURRED WHILE GETTING ROW COUNT " + e.getMessage() );
        }finally {
            resetCursor();
        }

        return rowCount ;

    }

}
