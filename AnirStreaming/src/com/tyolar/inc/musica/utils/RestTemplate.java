package com.tyolar.inc.musica.utils;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.tyolar.inc.musica.app2;

public class RestTemplate {

	public static String getResponse(String url, List<NameValuePair> headers) {

		// Create a new HttpClient and Post Header
		String responseBody=null;
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);

		try {
			httppost.setEntity(new UrlEncodedFormEntity(headers));
			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);
			 responseBody = EntityUtils.toString(response.getEntity());
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
		
		}
	
		return responseBody;
	}

}
