package Database;

import RF_ORF_FileReader.OrfProperty;

import java.sql.*;
import java.util.ArrayList;

public class Database {

    // Highest known integer of the id's from the database
    int idSequentie;
    int idORF;

    // Driver name, used to connect with the database
    private String myDriver = "com.mysql.jdbc.Driver";

    // Database URL
    private String myUrl = "myserver";

    // Database User
    private String user = "user";

    // Database Password
    private String password = "password";

    // Boolean used, for checking if the sequence you want to insert, already exists in the database. When looper = true; it doesn't exist yet
    // When looper = false; only the ORF's will be written away.
    private Boolean looper = true;

    private String dna;
    private String head;
    //all headers currently in the database, to be displayed in a dbSeqComboBox in the GUI
    private ArrayList<String> fastaHeaders;



    public void insertIntoDatabase(String fastaHeader, String dnaSequentie, ArrayList<OrfProperty> orfs){

        // Checking driver for SQL connection.
        try {
            Class.forName("com.mysql.jdbc.Driver");

        // If driver not found, throws exception in commandline.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Pre-defining.
        Connection conn = null;
        PreparedStatement preparedInsertIntoSequntieStatement = null;
        PreparedStatement preparedInsertIntoORFStatement = null;



        try {
            // Making connection with the database.
            conn = DriverManager.getConnection(myUrl, user, password);

            // Pre-defining.
            Statement statement_fastaHeader;
            statement_fastaHeader = conn.createStatement();
            String fastaHeaderDB;

            // SQL Query, used to get the fastaHeader and idSequentie from database.
            String getHeader = "SELECT fastaHeader, idSequentie from Sequentie";

            // Executing the query, saving results into resultSet_Header.
            ResultSet resultSet_Header= statement_fastaHeader.executeQuery(getHeader);

            // Looping thru the resultSet_Header.
            while (resultSet_Header.next()) {
                fastaHeaderDB = resultSet_Header.getString("fastaHeader");

                // If a header is equal to the header you want to place in, make looper False.
                if (fastaHeaderDB.equals(fastaHeader)) {
                    idSequentie = resultSet_Header.getInt("idSequentie");
                    looper = false;
                }
            }


            // If the header is not yet in the database:
            if (looper == true){

                // Pre-defining
                Statement statement_maxIds;
                statement_maxIds = conn.createStatement();

                // SQL Query, used to get the max id known in the Sequentie table.
                String getMaxID = "SELECT MAX(idSequentie) as idSequentie from Sequentie";

                // Executing the query, saving results into resultSet_Max_idSequentie.
                ResultSet resultSet_MaxID_idSequentie = statement_maxIds.executeQuery(getMaxID);

                // Looping thru the resultSet_MaxID_idSequentie.
                while (resultSet_MaxID_idSequentie.next()) {
                    idSequentie = resultSet_MaxID_idSequentie.getInt("idSequentie");
                }

                // Adding plus one to the highest idSequentie in the database.
                idSequentie +=1;

                // SQL Query, used to get the max id known in the ORF table.
                String getorfID_Query = "SELECT MAX(idORF) as idORF from ORF";

                // Executing the query, saving results into resultSet_MaxID_idORF.
                ResultSet resultSet_MaxID_idORF = statement_maxIds.executeQuery(getorfID_Query);

                // Looping thru the resultSet_MaxID_idORF.
                while (resultSet_MaxID_idORF.next()) {
                    idORF = resultSet_MaxID_idORF.getInt("idORF");
                }

                // Adding plus one to the highest idORF in the database.
                idORF +=1;


                // SQL Query, used to insert idSequentie, dnaSequentie and fastaHeader in the table Sequentie.
                String insertSeqQuery = "INSERT INTO Sequentie(idSequentie, dnaSequentie, fastaHeader) VALUES(?,?,?)";


                // Preparing the query.
                preparedInsertIntoSequntieStatement = conn.prepareStatement(insertSeqQuery);

                // Defining the data types and values for correct use in database.
                preparedInsertIntoSequntieStatement.setInt  (1, idSequentie);
                preparedInsertIntoSequntieStatement.setString  (2, dnaSequentie);
                preparedInsertIntoSequntieStatement.setString     (3, fastaHeader);

                // Updating the table, resulting in a reload of the database.
                preparedInsertIntoSequntieStatement.executeUpdate();

                // Pre-defining values
                String orfName;
                String orfSeq;
                String RF;



                // name, orf, i, start, end
                for (int i = 0; i<orfs.size(); i++){

                    // Defining the insertable values from the orfs arraylist.
                    orfName = orfs.get(i).getOrfName();
                    orfSeq = orfs.get(i).getOrfSeq();
                    RF = orfs.get(i).getRf();

                    // SQL Query, used to insert data into the ORF Table.
                    String insertORFQuery = "INSERT INTO ORF(idORF, orf, readingFrame, Sequentie_idSequentie, ORFNaam) VALUES(?,?,?,?,?)"; //Query

                    // Defining the data types and values for correct use in the database.
                    preparedInsertIntoORFStatement = conn.prepareStatement(insertORFQuery);              //PreparedStmt2 wordt gekoppeld aan de ORF query
                    preparedInsertIntoORFStatement.setInt  (1, idORF);
                    preparedInsertIntoORFStatement.setString  (2, orfSeq);
                    preparedInsertIntoORFStatement.setString(3, RF);
                    preparedInsertIntoORFStatement.setInt    (4, idSequentie);
                    preparedInsertIntoORFStatement.setString(5, orfName);

                    // Updating the table, resulting in a reload of the database.
                    preparedInsertIntoORFStatement.executeUpdate();
                    idORF += 1;
                    }
                }

                // Closes connection with the database.
            conn.close();

        // Throws SQLExceptions in the command line.
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Faulty SQL Syntax");
        }
    }

    public void getFromDatabase(String fastaHeader){
        try {
            Class.forName("com.mysql.jdbc.Driver");

            // If driver not found, throws exception in commandline.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Pre-defining.
        Connection conn = null;
        PreparedStatement preparedInsertIntoSequntieStatement = null;
        PreparedStatement preparedInsertIntoORFStatement = null;

        try {
            // Making connection with the database.
            conn = DriverManager.getConnection(myUrl, user, password);

            // Pre-defining.
            Statement statement_fastaHeader;
            statement_fastaHeader = conn.createStatement();
            String fastaHeaderDB;
            String dnaSequentieDB;

            // SQL Query, used to get the fastaHeader and idSequentie from database.
            String getHeader = "SELECT fastaHeader, dnaSequentie from Sequentie";

            // Executing the query, saving results into resultSet_Header.
            ResultSet resultSet_Header= statement_fastaHeader.executeQuery(getHeader);

            // Looping thru the resultSet_Header.
            while (resultSet_Header.next()) {
                fastaHeaderDB = resultSet_Header.getString("fastaHeader");
                dnaSequentieDB = resultSet_Header.getString("dnaSequentie");

                // If a header is equal to the header you want to place in, make looper False.
                if (fastaHeaderDB.equals(fastaHeader)) {
                    this.dna = dnaSequentieDB;
                    this.head = fastaHeaderDB;

                }
            }

        }
        catch(SQLException e){
            e.getStackTrace();
            }
    }

    public void fetchHeaders() {
        fastaHeaders = new ArrayList<>();
        try {
            Class.forName("com.mysql.jdbc.Driver");

            // If driver not found, throws exception in commandline.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Pre-defining.
        Connection conn = null;
        PreparedStatement preparedInsertIntoSequntieStatement = null;
        PreparedStatement preparedInsertIntoORFStatement = null;

        try {
            // Making connection with the database.
            conn = DriverManager.getConnection(myUrl, user, password);

            // Pre-defining.
            Statement statement_fastaHeader;
            statement_fastaHeader = conn.createStatement();
            String fastaHeaderDB;
            String dnaSequentieDB;

            // SQL Query, used to get the fastaHeader and idSequentie from database.
            String getHeader = "SELECT fastaHeader, dnaSequentie from Sequentie";

            // Executing the query, saving results into resultSet_Header.
            ResultSet resultSet_Header= statement_fastaHeader.executeQuery(getHeader);

            // Looping thru the resultSet_Header.
            while (resultSet_Header.next()) {
                fastaHeaderDB = resultSet_Header.getString("fastaHeader");
                // If a header is equal to the header you want to place in, make looper False.
                fastaHeaders.add(fastaHeaderDB);
                }
        }
        catch(SQLException e){
            e.getStackTrace();
        }

    }

    public String getDna(){
        return this.dna;
    }

    public String getHead() {
        return head;
    }

    public ArrayList<String> getFastaHeaders() {
        return fastaHeaders;
    }
}