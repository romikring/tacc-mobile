package co.tula.tacc.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.util.Log;
import co.tula.tacc.entity.Project;


final public class TaccConnector
{
    /** The tag used to log to adb console. */
    private static final String TAG = "TaccConnector";
    /** POST parameter name for the user's account name */
    public static final String PARAM_USERNAME = "email";
    /** POST parameter name for the user's password */
    public static final String PARAM_PASSWORD = "password";
    /** POST parameter name for the user's authentication token */
    public static final String PARAM_AUTH_TOKEN = ".ASPXAUTH";
    /** POST parameter name for the client's last-known sync state */
    public static final String PARAM_SYNC_STATE = "syncstate";
    /** Timeout (in ms) we specify for each http request */
    public static final int HTTP_REQUEST_TIMEOUT_MS = 30 * 1000;
    
    /** Base URL for the v2 Sample Sync Service */
    public static final String BASE_URL = "https://tacc-qa.tula.co/api";
    /** URI for authentication service */
    public static final String AUTH_URI = BASE_URL + "/employee";
    /** Employees full list */
    public static final String EMPLOYEE_LIST = BASE_URL + "/employee";
    /** Positions list */
    public static final String POSITION_LIST = BASE_URL + "/positions";
    /** Projects list */
    public static final String PROJECT_LIST = BASE_URL + "/project";
    /** Project details */
    public static final String PROJECT_DETAILS = PROJECT_LIST + "/%d";
    /** Status list */
    public static final String STATUS_LIST = BASE_URL + "/status";
    /** Timesheets: 1: EMPLOYEE ID; 2: START(UNIX TS); 3: END(UNIX TS) */
    public static final String TIMESHEETS = BASE_URL + "/timesheet/%d/%d/%d";
    
    private static String mCookie;

    private TaccConnector() {
    }
    
    /**
     * Configures the httpClient to connect to the URL provided.
     */
    public static HttpClient getHttpClient() {
        HttpClient httpClient = new DefaultHttpClient();
        final HttpParams params = httpClient.getParams();
        HttpConnectionParams.setConnectionTimeout(params, HTTP_REQUEST_TIMEOUT_MS);
        HttpConnectionParams.setSoTimeout(params, HTTP_REQUEST_TIMEOUT_MS);
        ConnManagerParams.setTimeout(params, HTTP_REQUEST_TIMEOUT_MS);
        
        return httpClient;
    }
    
    /**
     * Connects to the SampleSync test server, authenticates the provided
     * username and password.
     *
     * @param username The server account username
     * @param password The server account password
     * @return String The authentication token returned by the server (or null)
     */
    public static boolean authenticate(String username, String password) {

        final HttpResponse resp;
        final ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair(PARAM_USERNAME, username));
        params.add(new BasicNameValuePair(PARAM_PASSWORD, password));
        final HttpEntity entity;
        try {
            entity = new UrlEncodedFormEntity(params);
        } catch (final UnsupportedEncodingException e) {
            // this should never happen.
            throw new IllegalStateException(e);
        }
        Log.i(TAG, "Authenticating to: " + AUTH_URI);
        final HttpPost post = new HttpPost(AUTH_URI);
        post.addHeader(entity.getContentType());
        post.setEntity(entity);
        try {
            resp = getHttpClient().execute(post);
            
            String authToken = null;
            if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            	
            	Header h = resp.getLastHeader("Set-Cookie");
            	if (h != null) {
            		mCookie = h.getValue();
            	}
            	
                InputStream istream = (resp.getEntity() != null) ? resp.getEntity().getContent()
                        : null;
                if (istream != null) {
                    BufferedReader ireader = new BufferedReader(new InputStreamReader(istream));
                    authToken = ireader.readLine().trim();
                }
            }
            if ((authToken != null) && (authToken.length() > 0)) {
                Log.v(TAG, "Successful authentication");
                return true;
            } else {
            	mCookie = null;
                Log.e(TAG, "Error authenticating: " + resp.getStatusLine());
                return false;
            }
        } catch (final IOException e) {
            Log.e(TAG, "IOException when getting authtoken", e);
            return false;
        } finally {
            Log.v(TAG, "getAuthtoken completing");
        }
    }
    
    /**
     * Returns ArrayList of Project instances
     * 
     * @return {@link ArrayList}
     */
    public static ArrayList<Project> loadProjects()
    {
    	ArrayList<Project> list = new ArrayList<Project>();
    	
    	final HttpResponse responce;
        final HttpGet get = new HttpGet(PROJECT_LIST);
        get.addHeader("Cookie", mCookie);
        
        try {
        	Log.d(TAG, "Get Request to: " + PROJECT_LIST);
            responce = getHttpClient().execute(get);
            
            if (responce.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            	Log.d(TAG, "Request failed, code: " + responce.getStatusLine().getStatusCode());
            	
            	return list;
            }
            
            final String xml = EntityUtils.toString(responce.getEntity());
            if (xml == null || xml == "") {
            	Log.d(TAG, "XML string is empty");
            	return list;
            }
            final Document dom = TaccDomHelper.getDomElement(xml);
            if (dom == null) {
            	Log.d(TAG, "DOM Document is null object");
            	return list;
            }
            
            NodeList nodes = dom.getElementsByTagName("project");
            int count = nodes.getLength();
            long id;
        	String name;
            for (int i=0; i < count; ++i) {
            	Element project = (Element) nodes.item(i);
            	
            	id = Long.parseLong(TaccDomHelper.getNodeValue(project.getElementsByTagName("id").item(0)));
            	name = TaccDomHelper.getNodeValue(project.getElementsByTagName("name").item(0));
            	
            	if (id <= 0 || null == name)
            		continue;
            	
            	list.add(new Project(id, name));
            }
            
            
        } catch (final IOException e) {
            Log.e(TAG, "IOException when getting authtoken", e);
            
            return list;
        } finally {
            Log.v(TAG, "getAuthtoken completing");
        }
    	
    	return list;
    }
}
