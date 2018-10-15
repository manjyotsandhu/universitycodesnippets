/**
 * The Class ServerClient is created to give all other classes an easy way to communicate with the RESTFUL WebService.
 * it gives the other objects the opportunity to make GET, POST and DELETE requests as well as converting the 
 * receiving XML into java objects.
 */
package client.server.connection;

/**
 * @author Johannes Herforth.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;


public class ServerClient {

  HttpClient client;
  CloseableHttpClient httpclient;
  HttpGet request;
  HttpDelete delete;
  HttpPost post;
  HttpPut put;
  CloseableHttpResponse response;
  String responseString;
  
  /**
   * Constructor to create a simple ServerClient object.
   */
  public ServerClient() {
    httpclient = HttpClients.createDefault();
  }
  
  /**
   * Takes a query and posts a get request to it.
   * @param query destination URI for GET request.
   * @return a StringBuffer of the XML returned by the server.
   */
  public StringBuffer httpGet(String query) {
    StringBuffer textView = new StringBuffer();
    request = new HttpGet(query);
    HttpResponse response = null;
    
    try {
      response = httpclient.execute(request);
    } catch (ClientProtocolException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    // Get the response
    BufferedReader rd = null;
    try {
      rd = new BufferedReader
              (new InputStreamReader(
              response.getEntity().getContent()));
    } catch (UnsupportedOperationException | IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    String line = "";
    try {
      while ((line = rd.readLine()) != null) {
              textView.append(line + "\n");
      }
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    return textView;
  }
  
  /**
   * takes an XML StringBuffer and POSTS it to the given query.
   * @param query destination URI for POST request. 
   * @param postXML XML of the object to post to the server.
   * @return HTTP status code (200 if success).
   */
  public int httpPost(String query, StringBuffer postXML) {
    post = new HttpPost(query);
    post.setHeader(HTTP.CONTENT_TYPE,
        "application/XML;charset=UTF-8");
    HttpEntity entity = null;
    try {
      entity = new StringEntity(postXML.toString());
    } catch (UnsupportedEncodingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    post.setEntity(entity);
    try {
      response = httpclient.execute(post);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    return response.getStatusLine().getStatusCode();
  }
  
  /**
   * takes an XML StringBuffer and POSTS it to the given query.
   * @param query destination URI for POST request. 
   * @param postXML XML of the object to post to the server.
   * @return returns a string buffer of requested auth.
   */
  public StringBuffer httpPostSB(String query, StringBuffer postXML) {
    StringBuffer returnSB = new StringBuffer();
    post = new HttpPost(query);
    post.setHeader(HTTP.CONTENT_TYPE,
        "application/XML;charset=UTF-8");
    HttpEntity entity = null;
    try {
      entity = new StringEntity(postXML.toString());
    } catch (UnsupportedEncodingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    post.setEntity(entity);
    try {
      response = httpclient.execute(post);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    

    // Get the response
    BufferedReader rd = null;
    try {
      rd = new BufferedReader
              (new InputStreamReader(
              response.getEntity().getContent()));
    } catch (UnsupportedOperationException | IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    String line = "";
    try {
      while ((line = rd.readLine()) != null) {
              returnSB.append(line + "\n");
      }
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    
    return returnSB;
  }
  
  
  /**
   * takes a query and attempts to delete the contents on that URI
   * @param query destination URI for DELETE request.
   * @return HTTP status code (204 is success).
   */
  public int httpDelete(String query) {
    delete = new HttpDelete(query);
    
    try {
      response = httpclient.execute(delete);
    } catch (ClientProtocolException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    return response.getStatusLine().getStatusCode();
  }
  
  /**
   * takes an XML StringBuffer and PUTS it to the given query.
   * @param query destination URI for PUT request. 
   * @param xmlPut XML of the object to put to the server.
   * @return returns the HTTP status code
   */
  public int httpPut(String query, StringBuffer xmlPut) {
    put = new HttpPut(query);
    put.setHeader(HTTP.CONTENT_TYPE,"application/XML;charset=UTF-8");
    HttpEntity entity = null;
    try {
      entity = new StringEntity(xmlPut.toString());
    } catch (UnsupportedEncodingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    put.setEntity(entity);
    try {
      response = httpclient.execute(put);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    return response.getStatusLine().getStatusCode();
  }
  
  /**
   * takes an XML StringBuffer and PUTS it to the given query.
   * @param query destination URI for PUT request. 
   * @param xmlPut XML of the object to put to the server.
   * @return returns StringBuffer of the object returned
   */
  public StringBuffer httpPutSB(String query, StringBuffer xmlPut) {
    StringBuffer returnSB = new StringBuffer();
    put = new HttpPut(query);
    put.setHeader(HTTP.CONTENT_TYPE,"application/XML;charset=UTF-8");
    HttpEntity entity = null;
    try {
      entity = new StringEntity(xmlPut.toString());
    } catch (UnsupportedEncodingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    put.setEntity(entity);
    try {
      response = httpclient.execute(put);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    // Get the response
    BufferedReader rd = null;
    try {
      rd = new BufferedReader
              (new InputStreamReader(
              response.getEntity().getContent()));
    } catch (UnsupportedOperationException | IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    String line = "";
    try {
      while ((line = rd.readLine()) != null) {
              returnSB.append(line + "\n");
      }
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    
    return returnSB;

  }
  
  /**
   * takes in an XML string buffer and the given class name and attempts to convert the XML
   * the given object.
   * @param parse XML to convert to a java object.
   * @param className class the XML should be converted into.
   * @return the new Java object.
   */
  public Object convertXML(StringBuffer parse, Class<?> className) {
    Object returnobj = null;
    try {
      returnobj = className.getConstructor();
    } catch (NoSuchMethodException | SecurityException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    
    JAXBContext jaxbContext;
    //className = className + ".class";

    try {
      jaxbContext = JAXBContext.newInstance(className);
      
      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
      returnobj = jaxbUnmarshaller.unmarshal( new StreamSource( new StringReader( parse.toString() ) ));
      
    } catch (JAXBException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return returnobj;
  }
  
}
