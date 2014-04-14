package com.example.moodle4android;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;


import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore.Files;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Shader.TileMode;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import ar.com.daidalos.afiledialog.FileChooserDialog;
import  moodleObjects.*;

@SuppressLint("NewApi")
public class HomeworkContent extends Activity {

	TextView DescripcionTarea;
	TextView FechaFinalTarea;
	EditText NameFileTarea;
	File filetoUpload;
	infoHomeWork currentHomeWork;
	infoCourse currentCourse;
	int serverResponseCode = 0;
	int itemid = 0;
	int contexid = 0;
	ProgressDialog dialog = null;
	String upLoadServerUri = null;
	    
	    /**********  File Path *************/
	    String uploadFilePath = "/mnt/sdcard/";
	    String uploadFileName = "service_lifecycle.png";
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homework_content);
		Bundle b= getIntent().getBundleExtra("infoHomework");
		if(b!= null)
		{
			
			currentHomeWork = (infoHomeWork) b.getSerializable("infoHomework");
			currentCourse = (infoCourse)b.getSerializable("infoCourse");
			getActionBar().setTitle(currentHomeWork.getName());
			DescripcionTarea = (TextView)findViewById(R.id.HomeWorkDescription);
			DescripcionTarea.setGravity(Gravity.CENTER);
			FechaFinalTarea = (TextView)findViewById(R.id.textDateEnd);
			NameFileTarea = (EditText)findViewById(R.id.namefileSelected);
			DescripcionTarea.setText(currentHomeWork.getDescription());
			FechaFinalTarea.setText(currentHomeWork.getDateInit());
			
			
		}else
		{
			finish();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.homework_content, menu);
		return true;
	}
	
	
	public void OnBtnSelectdFileClicked(View v)
	{
		FileChooserDialog dialog = new FileChooserDialog(this);
		dialog.addListener(new FileChooserDialog.OnFileSelectedListener() {
			@Override
	         public void onFileSelected(Dialog source, File file) {
	             source.hide();
	             NameFileTarea.setText(file.getName());
	             filetoUpload = file;
	                }
			
			@Override
	         public void onFileSelected(Dialog source, File folder, String name) {
	             source.hide();
	             Toast toast = Toast.makeText(source.getContext(), "File created: " + folder.getName() + "/" + name, Toast.LENGTH_LONG);
	             toast.show();
	                }
	        });
		dialog.show();
		
		try{
		
			String url2 = "http://54.218.122.112/proxy/index.php/api/contextid/idCourse/"+currentCourse.getIdMoodleCourse()+"/assignName/"+currentHomeWork.getName()+"/userid/"+currentCourse.getCurrentUser().getIdMoodle()+"/format/json/";
			String json2 = readJSONFeed(url2);
			JSONObject jObject2 = new JSONObject(json2);
			System.out.println("el Json2 " + jObject2.toString());
			contexid = jObject2.getInt("contextid");
			System.out.println("id2 " + contexid);	
			
		String Url = "http://54.218.122.112/proxy/index.php/api/file/idCourse/" + currentCourse.getIdMoodleCourse() +"/assignName/" +currentHomeWork.getName()+"/userid/"+currentCourse.getCurrentUser().getIdMoodle()+"/time1/" + Long.toString(unixtimeCreate()) +"/time2/" + Long.toString(unixtimeCreate()) +"/format/json";
		String json = readJSONFeed(Url);
		JSONObject jObject = new JSONObject(json);
		System.out.println("el Json " + jObject.toString());
		itemid = jObject.getInt("itemid");
		System.out.println("id " + itemid);
		
		
		}catch(Exception Ex)
		{
			System.out.println("Error" + Ex.getMessage());
		}
		//
		
		
	}
	
	public void OnClickUploadFile(View v)
	{
		if(NameFileTarea.getText().toString().compareTo("")==0)
		{
			AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(HomeworkContent.this);
	   	 	dlgAlert.setMessage("Debe Selccionar un Archivo");
	        dlgAlert.setTitle("AVISO");
	        dlgAlert.setPositiveButton("OK", null);
	        dlgAlert.setCancelable(true);
	        dlgAlert.create().show();

	        dlgAlert.setPositiveButton("Ok",
	        new DialogInterface.OnClickListener() {
	        	public void onClick(DialogInterface dialog, int which) {
	        		return;
	            }
	        });
		}
		
		//bufferread
		try{
			BufferedReader br = new BufferedReader(new FileReader(filetoUpload.getAbsolutePath()));
			String everything;
			try {
				StringBuilder sb = new StringBuilder();
				String line = br.readLine();

				while (line != null) {
					sb.append(line);
					//sb.append(System.);
					line = br.readLine();
				}
				everything = sb.toString();
			} finally {
				br.close();
			}
			String contentHash=encryptPassword(everything);
			int n = 0;
			//String Url = obtainUrl(currentCourse.getCurrentUser().getUrl());
			String PostUrl = "/"+currentCourse.getCurrentUser().getContextID()+"/assignsubmission_file/submission_files/"+ Integer.toString(itemid)+"/" + filetoUpload.getName() ;
			String pathnameHash = encryptPassword(PostUrl);
			PostData(contentHash, pathnameHash);
			String Url = obtainUrl(currentCourse.getCurrentUser().getUrl());
         	upLoadServerUri = Url +"UploadToServer.php";
			String filePath = filetoUpload.getAbsolutePath().
	    	    	     substring(0,filetoUpload.getAbsolutePath().lastIndexOf(File.separator));
			final File newFile = new File(filePath+"/" +contentHash);
			newFile.createNewFile();
			copy(filetoUpload, newFile);
			uploadFileName = newFile.getName();
			uploadFilePath = newFile.getCanonicalPath();
			dialog = ProgressDialog.show(HomeworkContent.this, "", "Uploading file...", true);
		    
			new Thread(new Runnable() {
				  @Override
				  public void run()
				  {
					  uploadFile(newFile.getAbsolutePath());
					  newFile.delete();

				    runOnUiThread(new Runnable() {
				      @Override
				      public void run()
				      {
				    	  dialog.dismiss();
				      }
				    });
				  }
				}).start();
			
			
			
		}catch(Exception ex)
		{
			return;
		} 
		
	}
	
	
	private static String encryptPassword(String password) throws UnsupportedEncodingException {
        String sha1 = "";
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(password.getBytes("UTF-8"));
            sha1 = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sha1;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
    
    public void PostData(String ContentHash, String PatnameHash)
    {
    	 HttpClient httpclient = new DefaultHttpClient();
	     HttpPost httppost = new HttpPost("http://54.218.122.112/proxy/index.php/api/file/");
	     try
	     {
	    	 ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	            nameValuePairs.add(new BasicNameValuePair("idCourse", currentCourse.getIdMoodleCourse()));
	            nameValuePairs.add(new BasicNameValuePair("assignName", currentHomeWork.getName()));
	            nameValuePairs.add(new BasicNameValuePair("userid", currentCourse.getCurrentUser().getIdMoodle()));                        
	            nameValuePairs.add(new BasicNameValuePair("contenthash", ContentHash));            
	            nameValuePairs.add(new BasicNameValuePair("pathnamehash", PatnameHash));
	            nameValuePairs.add(new BasicNameValuePair("contextid", Integer.toString(contexid)));
	            nameValuePairs.add(new BasicNameValuePair("component", "assignsubmission_file"));
	            nameValuePairs.add(new BasicNameValuePair("filearea", "submission_files"));
	            nameValuePairs.add(new BasicNameValuePair("itemid", Integer.toString(itemid)));
	            nameValuePairs.add(new BasicNameValuePair("filepath", "/"));
	            nameValuePairs.add(new BasicNameValuePair("filename", filetoUpload.getName()));
	            nameValuePairs.add(new BasicNameValuePair("userid", currentCourse.getCurrentUser().getIdMoodle()));
	            nameValuePairs.add(new BasicNameValuePair("filesize", Long.toString(filetoUpload.length())));
	            nameValuePairs.add(new BasicNameValuePair("mimetype", "application/xm"));
	            nameValuePairs.add(new BasicNameValuePair("status", "0"));
	            nameValuePairs.add(new BasicNameValuePair("source", filetoUpload.getName()));
	            nameValuePairs.add(new BasicNameValuePair("author", currentCourse.getCurrentUser().getName()));
	            nameValuePairs.add(new BasicNameValuePair("license", "allrightsreserved"));
	            nameValuePairs.add(new BasicNameValuePair("timecreated", Long.toString(unixtimeCreate())));
	            nameValuePairs.add(new BasicNameValuePair("timemodified", Long.toString(unixtimeCreate())));
	            nameValuePairs.add(new BasicNameValuePair("sortorder", "0"));
	            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	            
	          
	            
	           
	            //end coursecontent
	            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	            StrictMode.setThreadPolicy(policy); 
	            // Execute HTTP Post Request
	            HttpResponse response = httpclient.execute(httppost);
	        	org.apache.http.Header[] headers = response.getAllHeaders();
	        	for (org.apache.http.Header header : headers) 
	        	{
	        		System.out.println("Key : " + header.getName() 
	        		      + " ,Value : " + header.getValue() +"value get: " );
	        	}
	        	
	     }catch(Exception ex)
	     {
	    	 
	     }
    }
    
    public String obtainUrl(String URL)
	{
		if(URL.compareTo("http://www.barcampsps.com/moodle/")==0)
		{
			return URL.substring(0, 26);
		}else
		{
			return URL;
		}
	}
    
    public String getType(String FileName)
    {
    	String ext="";
    	Pattern pattern = Pattern.compile("\\.([^.]*)$");
    	Matcher matcher = pattern.matcher(FileName);

    	if (matcher.find()) {
    	     ext = matcher.group(1);
    	}
    	return ext;
    }
    
    
    @SuppressLint("SimpleDateFormat")
	public long unixtimeCreate()
    {
    	try{
    		int gmtOffset = TimeZone.getDefault().getRawOffset();
            int dstOffset = TimeZone.getDefault().getDSTSavings();
            long unix_timestamp = (System.currentTimeMillis() + gmtOffset + dstOffset) / 1000;
            return unix_timestamp;
    	}catch(Exception ex)
    	{
    		return 0;
    	}
    	
    }
    
    public void copy(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }
    
    public int uploadFile(String sourceFileUri) {
        
  	  
  	  String fileName = sourceFileUri;

        HttpURLConnection conn = null;
        DataOutputStream dos = null;  
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024; 
        File sourceFile = new File(sourceFileUri); 
        
        if (!sourceFile.isFile()) {
      	  
	           
	           
	           Log.e("uploadFile", "Source File not exist :"
	        		               +uploadFilePath + "" + uploadFileName);
	           
	          
	           
	           return 0;
         
        }
        else
        {
	           try { 
	        	   
	            	 // open a URL connection to the Servlet
	               FileInputStream fileInputStream = new FileInputStream(sourceFile);
	               URL url = new URL(upLoadServerUri);
	               
	               // Open a HTTP  connection to  the URL
	               conn = (HttpURLConnection) url.openConnection(); 
	               conn.setDoInput(true); // Allow Inputs
	               conn.setDoOutput(true); // Allow Outputs
	               conn.setUseCaches(false); // Don't use a Cached Copy
	               conn.setRequestMethod("POST");
	               conn.setRequestProperty("Connection", "Keep-Alive");
	               conn.setRequestProperty("ENCTYPE", "multipart/form-data");
	               conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
	               conn.setRequestProperty("uploaded_file", fileName); 
	               
	               dos = new DataOutputStream(conn.getOutputStream());
	     
	               dos.writeBytes(twoHyphens + boundary + lineEnd); 
	               dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
	            		                     + fileName + "\"" + lineEnd);
	               
	               dos.writeBytes(lineEnd);
	     
	               // create a buffer of  maximum size
	               bytesAvailable = fileInputStream.available(); 
	     
	               bufferSize = Math.min(bytesAvailable, maxBufferSize);
	               buffer = new byte[bufferSize];
	     
	               // read file and write it into form...
	               bytesRead = fileInputStream.read(buffer, 0, bufferSize);  
	                 
	               while (bytesRead > 0) {
	            	   
	                 dos.write(buffer, 0, bufferSize);
	                 bytesAvailable = fileInputStream.available();
	                 bufferSize = Math.min(bytesAvailable, maxBufferSize);
	                 bytesRead = fileInputStream.read(buffer, 0, bufferSize);   
	                 
	                }
	     
	               // send multipart form data necesssary after file data...
	               dos.writeBytes(lineEnd);
	               dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
	     
	               // Responses from the server (code and message)
	               serverResponseCode = conn.getResponseCode();
	               String serverResponseMessage = conn.getResponseMessage();
	                
	               Log.i("uploadFile", "HTTP Response is : " 
	            		   + serverResponseMessage + ": " + serverResponseCode);
	               
	               if(serverResponseCode == 200){
	            	   
	                   runOnUiThread(new Runnable() {
	                        public void run() {
	                        	
	                        	String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
	                        		          +" http://www.androidexample.com/media/uploads/"
	                        		          +uploadFileName;
	                        	
	                        	
	                            Toast.makeText(HomeworkContent.this, "File Upload Complete.", 
	                            		     Toast.LENGTH_SHORT).show();
	                        }
	                    });                
	               }    
	               
	               //close the streams //
	               fileInputStream.close();
	               dos.flush();
	               dos.close();
	                
	          } catch (MalformedURLException ex) {
	        	  
	                
	              ex.printStackTrace();
	              
	              runOnUiThread(new Runnable() {
	                  public void run() {
	                	  
	                      Toast.makeText(HomeworkContent.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
	                  }
	              });
	              
	              Log.e("Upload file to server", "error: " + ex.getMessage(), ex);  
	          } catch (Exception e) {
	        	  
	               
	              e.printStackTrace();
	              
	              runOnUiThread(new Runnable() {
	                  public void run() {
	                	  
	                      Toast.makeText(HomeworkContent.this, "Got Exception : see logcat ", 
	                    		  Toast.LENGTH_SHORT).show();
	                  }
	              });
	              Log.e("Upload file to server Exception", "Exception : " 
	            		                           + e.getMessage(), e);  
	          }
	              
	          return serverResponseCode; 
	          
         } // End else block 
       } 
    
    public String readJSONFeed(String URL) {
        StringBuilder stringBuilder = new StringBuilder();
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URL);
        try {
            HttpResponse response = httpClient.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream inputStream = entity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                inputStream.close();
            } else {
                Log.d("JSON", "Failed to download file");
            }
        } catch (Exception e) {
            Log.d("readJSONFeed", e.getLocalizedMessage());
        }        
        return stringBuilder.toString();
    }

}
