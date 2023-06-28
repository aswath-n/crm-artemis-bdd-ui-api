package com.maersk.crm.utilities.etl_util;


import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.nio.ByteBuffer;
import java.util.Base64;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.secretsmanager.*;
import com.amazonaws.services.secretsmanager.model.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.maersk.crm.utilities.ConfigurationReader;


public class JDBC_Utils {


    private static Connection con;
    private static Statement stm;
    private static ResultSet rs;
    private static ResultSetMetaData rsmd;

    private static String secret, decodedBinarySecret;

    public JDBC_Utils() {
        createConnection();
    }

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            JDBC_Utils jdbc_utils = new JDBC_Utils();
            jdbc_utils.createConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `mars-ecms-order-blcrm`.EXTERNAL_LINKS where EXTERNAL_LINK_ID='1'");
            List<String> results = new ArrayList<>();
            while (rs.next()) {
                results.add(rs.getString("INTERNAL_REF_TYPE"));
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Create connection method , just checking one connection successful or not
     */
    public void createConnection() {
        String url = ConfigurationReader.getProperty("dbUrl");
        getSecret();
        JsonObject convertedObject = new Gson().fromJson(secret, JsonObject.class);
        String username = convertedObject.get("username").getAsString();
        String password = convertedObject.get("password").getAsString();
        createConnection(url, username, password);
    }

    /**
     * Create Connection by jdbc url and username , password provided
     *
     * @param url
     * @param username
     * @param password
     */
    public void createConnection(String url, String username, String password) {
//        DriverManager.setLoginTimeout(60);
//        System.out.println("DriverManager.getLoginTimeout() = " + DriverManager.getLoginTimeout());
//https://stackoverflow.com/questions/13979622/java-use-ssl-for-database-connection

//        url = url + "?verifyServerCertificate=false" +
//                "&useSSL=true" +
//                "&requireSSL=true";
//        url = url + "?integratedSecurity=true&encrypt=true&trustServerCertificate=true";

        try {
            con = DriverManager.getConnection(url, username, password);
            System.out.println("CONNECTION SUCCESSFUL");
        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("CONNECTION HAS FAILED " + e.getMessage());
        }

    }

    public static void getSecret() {

        String secretName = ConfigurationReader.getProperty("dbSecretName");
        String region = "us-west-2";
        AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard()
                .withRegion(region)
                .build();

        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest()
                .withSecretId(secretName);
        GetSecretValueResult getSecretValueResult = null;
        try {
            getSecretValueResult = client.getSecretValue(getSecretValueRequest);
        } catch (DecryptionFailureException | InternalServiceErrorException | InvalidParameterException | InvalidRequestException | ResourceNotFoundException e) {
            e.printStackTrace();
        }
        if (getSecretValueResult.getSecretString() != null) {
            secret = getSecretValueResult.getSecretString();
        } else {
            decodedBinarySecret = new String(Base64.getDecoder().decode(getSecretValueResult.getSecretBinary()).array());
        }

    }


    /**
     * Run the sql query provided and return ResultSet object
     *
     * @param sql
     * @return ResultSet object  that contains data
     */
    public ResultSet runQuery(String sql) {

        try {
            stm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stm.executeQuery(sql); // setting the value of ResultSet object
            rsmd = rs.getMetaData();  // setting the value of ResultSetMetaData for reuse
        } catch (SQLException e) {
            System.out.println("ERROR OCCURRED WHILE RUNNING QUERY " + e.getMessage());
            e.printStackTrace();
        }

        return rs;

    }

    /**
     * destroy method to clean up all the resources after being used
     */
    public void destroy() {
        // WE HAVE TO CHECK IF WE HAVE THE VALID OBJECT FIRST BEFORE CLOSING THE RESOURCE
        // BECAUSE WE CAN NOT TAKE ACTION ON AN OBJECT THAT DOES NOT EXIST
        try {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            System.out.println("ERROR OCCURRED WHILE CLOSING RESOURCES " + e.getMessage());
        }

    }

    /**
     * This method will reset the cursor to before first location
     */
    private void resetCursor() {

        try {
            rs.beforeFirst();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    // find out the row count

    /**
     * find out the row count
     *
     * @return row count of this ResultSet
     */
    public int getRowCount() {

        int rowCount = 0;
        try {
            rs.last();
            rowCount = rs.getRow();
        } catch (SQLException e) {
            System.out.println("ERROR OCCURRED WHILE GETTING ROW COUNT " + e.getMessage());
        } finally {
            resetCursor();
        }

        return rowCount;

    }


    /**
     * find out the column count
     *
     * @return column count of this ResultSet
     */
    public int getColumnCount() {

        int columnCount = 0;

        try {
            columnCount = rsmd.getColumnCount();

        } catch (SQLException e) {
            System.out.println("ERROR OCCURRED WHILE GETTING COLUMN COUNT " + e.getMessage());
        }

        return columnCount;

    }

    // Get all the Column names into a list object

    public List<String> getAllColumnNamesAsList() {

        List<String> columnNameLst = new ArrayList<>();

        try {
            for (int colIndex = 1; colIndex <= getColumnCount(); colIndex++) {
                String columnName = rsmd.getColumnName(colIndex);
                columnNameLst.add(columnName);
            }
        } catch (SQLException e) {
            System.out.println("ERROR OCCURRED WHILE getAllColumnNamesAsList " + e.getMessage());
        }

        return columnNameLst;

    }

    // get entire row of data according to row number

    /**
     * Getting entire row of data in a List of String
     *
     * @param rowNum
     * @return row data as List of String
     */
    public List<String> getRowDataAsList(int rowNum) {

        List<String> rowDataAsLst = new ArrayList<>();
        int colCount = getColumnCount();

        try {
            rs.absolute(rowNum);

            for (int colIndex = 1; colIndex <= colCount; colIndex++) {

                String cellValue = rs.getString(colIndex);
                rowDataAsLst.add(cellValue);

            }


        } catch (SQLException e) {
            System.out.println("ERROR OCCURRED WHILE getRowDataAsList " + e.getMessage());
        } finally {
            resetCursor();
        }


        return rowDataAsLst;
    }


    /**
     * getting the cell value according to row num and column index
     *
     * @param rowNum
     * @param columnIndex
     * @return the value in String at that location
     */
    public String getCellValue(int rowNum, int columnIndex) {

        String cellValue = "";

        try {
            rs.absolute(rowNum);
            cellValue = rs.getString(columnIndex);

        } catch (SQLException e) {
            System.out.println("ERROR OCCURRED WHILE getCellValue " + e.getMessage());
        } finally {
            resetCursor();
        }
        return cellValue;

    }

    /**
     * getting the cell value according to row num and column name
     *
     * @param rowNum
     * @param columnName
     * @return the value in String at that location
     */
    public String getCellValue(int rowNum, String columnName) {

        String cellValue = "";

        try {
            rs.absolute(rowNum);
            cellValue = rs.getString(columnName);

        } catch (SQLException e) {
            System.out.println("ERROR OCCURRED WHILE getCellValue " + e.getMessage());
        } finally {
            resetCursor();
        }
        return cellValue;

    }

    public int getNumericCellValue(int rowNum, String columnName) {

        int cellValue = 0;

        try {
            rs.absolute(rowNum);
            cellValue = rs.getInt(columnName);

        } catch (SQLException e) {
            System.out.println("ERROR OCCURRED WHILE getCellValue " + e.getMessage());
        } finally {
            resetCursor();
        }
        return cellValue;

    }


    /**
     * Get First Cell Value at First row First Column
     */
    public String getFirstRowFirstColumn() {

        return getCellValue(1, 1);

    }

    //

    /**
     * getting entire column data as list according to column number
     *
     * @param columnNum
     * @return List object that contains all rows of that column
     */
    public List<String> getColumnDataAsList(int columnNum) {

        List<String> columnDataLst = new ArrayList<>();

        try {
            rs.beforeFirst(); // make sure the cursor is at before first location
            while (rs.next()) {

                String cellValue = rs.getString(columnNum);
                columnDataLst.add(cellValue);
            }

        } catch (SQLException e) {
            System.out.println("ERROR OCCURRED WHILE getColumnDataAsList " + e.getMessage());
        } finally {
            resetCursor();
        }


        return columnDataLst;

    }

    /**
     * getting entire column data as list according to column Name
     *
     * @param columnName
     * @return List object that contains all rows of that column
     */
    public List<String> getColumnDataAsList(String columnName) {

        List<String> columnDataLst = new ArrayList<>();

        try {
            rs.beforeFirst(); // make sure the cursor is at before first location
            while (rs.next()) {

                String cellValue = rs.getString(columnName);
                columnDataLst.add(cellValue);
            }

        } catch (SQLException e) {
            System.out.println("ERROR OCCURRED WHILE getColumnDataAsList " + e.getMessage());
        } finally {
            resetCursor();
        }


        return columnDataLst;

    }


    /**
     * display all data from the ResultSet Object
     */
    public void displayAllData() {

        int columnCount = getColumnCount();
        resetCursor();
        try {

            while (rs.next()) {

                for (int colIndex = 1; colIndex <= columnCount; colIndex++) {

                    //System.out.print( rs.getString(colIndex) + "\t" );
                    System.out.printf("%-25s", rs.getString(colIndex));
                }
                System.out.println();

            }

        } catch (SQLException e) {
            System.out.println("ERROR OCCURRED WHILE displayAllData " + e.getMessage());
        } finally {
            resetCursor();
        }

    }

    /**
     * Save entire row data as Map<String,String>
     *
     * @param rowNum
     * @return Map object that contains key value pair
     * key     : column name
     * value   : cell value
     */
    public Map<String, String> getRowMap(int rowNum) {

        Map<String, String> rowMap = new LinkedHashMap<>();
        int columnCount = getColumnCount();

        try {

            rs.absolute(rowNum);

            for (int colIndex = 1; colIndex <= columnCount; colIndex++) {
                String columnName = rsmd.getColumnName(colIndex);
                String cellValue = rs.getString(colIndex);
                rowMap.put(columnName, cellValue);
            }

        } catch (SQLException e) {
            System.out.println("ERROR OCCURRED WHILE getRowMap " + e.getMessage());
        } finally {
            resetCursor();
        }


        return rowMap;
    }

    /**
     * We know how to store one row as map object
     * Now Store All rows as List of Map object
     *
     * @return List of List of Map object that contain each row data as Map<String,String>
     */
    public List<Map<String, String>> getAllRowAsListOfMap() {

        List<Map<String, String>> allRowLstOfMap = new ArrayList<>();
        int rowCount = getRowCount();
        // move from first row till last row
        // get each row as map object and add it to the list

        for (int rowIndex = 1; rowIndex <= rowCount; rowIndex++) {

            Map<String, String> rowMap = getRowMap(rowIndex);
            allRowLstOfMap.add(rowMap);

        }
        resetCursor();

        return allRowLstOfMap;

    }

    public Connection getConnection() {
        return con;
    }


}
