/**
 * JUnit test class to test the ServerClient.
 */

/**
 * @author Johannes Herforth.
 */

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import client.app.logic.Ingredient;
import client.server.connection.ServerClient;

public class ServerClientTest {
  
  private ServerClient sercli;
  private final String getQuery = "http://shouganai.net:8080/restaurant/rest/ingredients/2";
  private final String postQuery = "http://shouganai.net:8080/restaurant/rest/ingredients";
  private final String deleteQuery = "http://shouganai.net:8080/restaurant/rest/ingredients/";
  private final String getAnswer = "<id>2</id>";
  private int idtoCheck = 0;
  private StringBuffer postXML = new StringBuffer();
  private final int postSuccess = 200;
  private final int deleteSuccess = 204;
  
  @Before
  public void setUp() throws Exception {
    sercli = new ServerClient();
  }

  @Test
  public void getHTTPTest() {
    assertTrue("Test1: Check if httpget receives correct info", sercli.httpGet(getQuery).toString().contains(getAnswer));
  }

  @Test
  public void postHTTPTest() {
    postXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
    postXML.append("<ingredient>");
    postXML.append("<expiryDate>20150606</expiryDate>");
    postXML.append("<id>2</id>");
    postXML.append("<name>potato</name>");
    postXML.append("<quantity>50</quantity>");
    postXML.append("</ingredient>");
    StringBuffer sreturn = sercli.httpPostSB(postQuery, postXML);
    Ingredient gotten = (Ingredient) sercli.convertXML(sreturn, Ingredient.class);
    idtoCheck = gotten.getId();
        
    assertEquals("Test2: check if httppost sends info correctly", postSuccess, sercli.httpPost(postQuery, postXML));
  }
  
  @Test
  public void deleteHTTPTest() {
    assertEquals("Test3: check if httpdelete deletes the correct ID", deleteSuccess, sercli.httpDelete(deleteQuery + idtoCheck));
  }
}
