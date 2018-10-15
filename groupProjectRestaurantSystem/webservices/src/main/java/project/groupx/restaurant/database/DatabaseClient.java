/**
 * The class DatabaseClient is a class used by the server to communicate with the database.
 * It will give the server the ability to connect to the database and execute any command that
 * is given.
 */
package project.groupx.restaurant.database;

/**
 * @author Johannes Herforth.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

public class DatabaseClient {


  private PoolProperties prop = new PoolProperties();
  private DataSource pool = new DataSource();
  private static DatabaseClient instance;
  
  private DatabaseClient(){
    this.createPool();
  };
  
  public void createPool() {
    prop.setUrl("jdbc:postgresql://ec2-54-75-237-110.eu-west-1.compute.amazonaws.com:5432/d4p5pchqe16vsu?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory");
    prop.setDriverClassName("org.postgresql.Driver");
    prop.setUsername("cbxogyauomxmjz");
    prop.setPassword("6130127d6f1f481884be3217d564fb445931a40fbf6232cea28a67ff4be487a5");
    prop.setJmxEnabled(true);
    prop.setTestWhileIdle(false);
    prop.setTestOnBorrow(true);
    prop.setValidationQuery("SELECT 1");
    prop.setTestOnReturn(false);
    prop.setValidationInterval(30000);
    prop.setTimeBetweenEvictionRunsMillis(30000);
    prop.setMaxActive(18);
    prop.setInitialSize(5);
    prop.setMaxWait(10000);
    prop.setRemoveAbandonedTimeout(60);
    prop.setMinEvictableIdleTimeMillis(30000);
    prop.setMinIdle(5);
    prop.setMaxIdle(15);
    prop.setLogAbandoned(true);
    prop.setRemoveAbandoned(true);
    prop.setJdbcInterceptors(
      "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"+
      "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
    pool.setPoolProperties(prop);
    
    
  }
  
  public static DatabaseClient getInstance() {
    if(instance == null) {
      instance = new DatabaseClient();
    }
    return instance;
  }
  
  /**
   * connects the server client to the database with all the correct credentials.
   * @return a valid connection to use for updating the Database.
   */
  public static Connection connectToDatabase() {
    
    //Test Credentials, this MUST be updated.
    String user = "cbxogyauomxmjz";

    String password = "6130127d6f1f481884be3217d564fb445931a40fbf6232cea28a67ff4be487a5"; 

    // String database = "d4p5pchqe16vsu";

    System.out.println("-------- PostgreSQL " + "JDBC Connection Testing ------------");
    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {

      System.out
          .println("Where is your PostgreSQL JDBC Driver? " + "Include in your library path!");
      e.printStackTrace();
    }
    System.out.println("PostgreSQL JDBC Driver Registered!");

    Connection connection = null;
    try {
      connection = DriverManager.getConnection(
          "jdbc:postgresql://ec2-54-75-237-110.eu-west-1.compute.amazonaws.com:5432/d4p5pchqe16vsu?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory",
          user, password);
    } catch (SQLException e) {
      System.out.println("Connection Failed! Check output console");
      e.printStackTrace();
    }
    return connection;
  }
  
  /**
   * this method takes in a connection to a database and executes the given query to update the 
   * database.
   * @param connection connection to the database.
   * @param query query to execute on the database.
   * @return resultset of the given command.
   */
  public static ResultSet executeQuery(Connection connection, String query) {
    Statement st = null;
    try {
      st = connection.createStatement();
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }

    ResultSet rs = null;
    try {
      rs = st.executeQuery(query);
      //st.close();
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
    
    //System.out.println(rs.toString());

    return rs;
  }
  
  public Connection getConnection() {
    try {
      return pool.getConnection();
    } catch (SQLException e) {
      System.out.println("There was an error with connections");
      e.printStackTrace();
    }
    return null;
  }
  
}
