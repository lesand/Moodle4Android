package moodleObjects;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class GoogleAuthentication {
private final String gaeAppBaseUrl = "http://nimbits1.appspot.com/";
private final String gaeAppLoginUrl = gaeAppBaseUrl + "_ah/login";
private final String googleLoginUrl = "https://www.google.com/accounts/ClientLogin";
private final String service = "ah"; 
private String Token = "";
private Cookie authCookie = null;


//sample function that makes an http request and downloads
//the result providing the auth cookie.
	public String GetLoggedIn() throws IOException
	{

		String result;
		String destUrl = "http://app.nimbits.com/nimbits/Service/user"; 
		URL url = new URL(destUrl);
		URLConnection conn = url.openConnection ();
		conn.addRequestProperty("Cookie",authCookie.getName() + "=" + authCookie.getValue());
		
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		StringBuffer sb = new StringBuffer();
		String line;
		while ((line = rd.readLine()) != null)
		{
			sb.append(line);
		}
		rd.close();
		result = sb.toString();
		return result;
	}
	
	public boolean GetLoged() throws IOException
	{
		URL url = new URL("https://www.googleapis.com/oauth2/v1/userinfo?access_token="
		        + Token);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		int serverCode = con.getResponseCode();
		//successful query
		if (serverCode == 200) {
		    
		    return true;
		//bad token, invalidate and get a new one
		} else if (serverCode == 401) {
		    
		    return false;
		//unknown error, do something else
		} else {
		    Log.e("Server returned the following error code: " + serverCode, null);
		    return false;
		}
		
	}

//Constructor creates the cookie
	public GoogleAuthentication(String googleAccount, String googlePassword)
	{
		if (authCookie == null)
		{

			try {
					String authToken = GetToken(googleAccount,googlePassword);
					authCookie = getAuthCookie(authToken);
					Token = authToken;
			} catch (ClientProtocolException e) {
			//	TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}


	private String GetToken(String googleAccount, String googlePassword) throws MalformedURLException, ProtocolException, UnsupportedEncodingException, IOException
	{
		String token = null;
		HttpURLConnection h = GetConnection(googleAccount,googlePassword);
		token= extractAuthTokenFromResponse(h);
		return token;
	}


	private Cookie getAuthCookie(String authToken) throws ClientProtocolException, IOException {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		Cookie retObj = null;
		String cookieUrl = gaeAppLoginUrl + "?continue=" 
				+ URLEncoder.encode(gaeAppBaseUrl,"UTF-8") + "&auth=" + URLEncoder.encode 
				(authToken,"UTF-8"); 
		HttpGet httpget = new HttpGet(cookieUrl);
		HttpResponse response = httpClient.execute(httpget);
		if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK ||
				response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_NO_CONTENT) {

			if (httpClient.getCookieStore().getCookies().size() > 0) {
				retObj= httpClient.getCookieStore().getCookies().get(0);
			}

		}
		return retObj;

	}

	private HttpURLConnection GetConnection(String username, String password)throws MalformedURLException, IOException, ProtocolException,UnsupportedEncodingException {
		HttpURLConnection urlConnection;
		URL url = new URL(googleLoginUrl);
		urlConnection = (HttpURLConnection) url.openConnection();
		urlConnection.setRequestMethod("POST");
		urlConnection.setDoInput(true);
		urlConnection.setDoOutput(true);
		urlConnection.setUseCaches(false);
		urlConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		StringBuilder content = new StringBuilder();
		content.append("Email=").append(username);
		content.append("&Passwd=").append(password);
		content.append("&service=").append(service);

		OutputStream outputStream = urlConnection.getOutputStream();
		outputStream.write(content.toString().getBytes("UTF-8"));
		outputStream.close();
		return urlConnection;
	}

	private String extractAuthTokenFromResponse(HttpURLConnection urlConnection)throws IOException {
		int responseCode = urlConnection.getResponseCode();
		System.out.println(responseCode);
		StringBuffer resp = new StringBuffer(); 
		if (responseCode == HttpURLConnection.HTTP_OK) {
			InputStream inputStream = urlConnection.getInputStream();

			BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
			String line; 


			while ((line = rd.readLine()) != null) { 

				if(line.startsWith("Auth="))
				{
					resp.append(line.substring(5)); 

				}

			} 

			rd.close(); 


		}
		return resp.toString();
	}
}