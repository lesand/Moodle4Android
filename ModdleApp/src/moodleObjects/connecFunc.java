package moodleObjects;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.xpath.XPathExpressionException;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

@SuppressLint("NewApi")
@TargetApi(Build.VERSION_CODES.GINGERBREAD)


public  class connecFunc 
{

	
	private static connecFunc INSTANCE = null;
	 
    // Private constructor suppresses 
    private connecFunc(){}
 
    // creador sincronizado para protegerse de posibles problemas  multi-hilo
    // otra prueba para evitar instanciaci—n mœltiple 
    private synchronized static void createInstance() {
        if (INSTANCE == null) { 
            INSTANCE = new connecFunc();
        }
    }
 
    public static connecFunc getInstance() {
        createInstance();
        return INSTANCE;
    }	
	public String xml;
	
	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public  void  tryConnect(String _param,String _funcName) throws MalformedURLException, IOException ,XPathExpressionException
	{
        String token = "224e408537832a7add2c4138fabfd686";
        String domainName = "http://www.barcampsps.com/moodle";
        /// REST RETURNED VALUES FORMAT
        //String restformat = "xml"; //Also possible in Moodle 2.2 and later: 'json'
         //Setting it to 'json' will fail all calls on earlier Moodle version
        String restformat = "json";
        if (restformat.equals("json"))
        {          
            restformat = "&moodlewsrestformat=" + restformat;
        } else 
        {
            restformat = "";
        }
        /// PARAMETERS - NEED TO BE CHANGED IF YOU CALL A DIFFERENT FUNCTION
        //String functionName = "core_enrol_get_enrolled_users";
        //String functionName = "core_enrol_get_users_courses";
        String functionName =_funcName;
        //String functionName="core_course_get_contents";
        //String urlParameters ="courseid=3";
        String urlParameters =_param;
        //String urlParameters ="userid=3";

        /// REST CALL
        Log.d("Conn","before the conn");
        //this was add to get the url getOutputStream working 
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy); 
        
        //Send request        
        String serverurl = domainName + "/webservice/rest/server.php" + "?wstoken=" + token + "&wsfunction=" + functionName;
        HttpURLConnection con = (HttpURLConnection) new URL(serverurl).openConnection();
        
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestProperty("Content-Language", "en-US");
        con.setDoOutput(true);
        con.setUseCaches (false);
        con.setDoInput(true);
        try{
           DataOutputStream wr = new DataOutputStream (con.getOutputStream ());        	
           wr.writeBytes(urlParameters);
           wr.flush ();
           wr.close ();
        }catch(Exception ex)
        {
        	Log.e("shit","no sirve la shit");
        	Log.e("Conn2",ex.getMessage());
        } 
        //Get Response
        InputStream is =con.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));        
        String line;
        StringBuilder response = new StringBuilder();        
        while((line = rd.readLine()) != null) 
        {
            response.append(line);
            response.append('\r');
        }
        rd.close();      
        xml=response.toString();
	  //return response.toString(); 	
	}

	
	
	
}
