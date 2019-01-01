package com.demo.admin.scheduler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import de.codecentric.boot.admin.server.web.client.HttpHeadersProvider;
import io.netty.util.internal.StringUtil;

@Component
public class ScheduledTasks {
	
	@Value( "${security.oauth2.client.clientId}" )
  	private String clientId;
 
    @Value( "${security.oauth2.client.clientSecret}" )
  	private String clientSecret;
 
    @Value( "${security.oauth2.client.accessTokenUri}" )
  	private String accesstokenURI;
    
    //@Value( "${security.oauth2.client.accessTokenUri}" )
  	private String addon = "?grant_type=client_credentials&scope=read+write";

    private final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 60000)
    public void rehenerateAccessToken() {
    	AccessTokenWrapper.getInstance().setAccessToken(getaAccessToken());
        log.info("Access Token Regenerated at {}", dateFormat.format(new Date()));
    }
    
    private String getaAccessToken(){
		try {
			URL url = new URL(accesstokenURI+addon);
			String authString = clientId+":"+clientSecret;
			String authEncoded = "Basic "+javax.xml.bind.DatatypeConverter.printBase64Binary(authString.getBytes("UTF-8"));
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setRequestProperty("Authorization",authEncoded);
			OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
			out.write("");
			out.close();
			if(conn.getResponseCode()!=200){
				System.out.println("Failed "+ conn.getResponseCode());
			}
			
			StringBuilder sb = new StringBuilder();

		    String line;
		    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		    while ((line = br.readLine()) != null) {
		        sb.append(line);
		    }
		    
		    JSONObject json = new JSONObject(sb.toString());
		    System.out.println(json);

			
			return json.getString("access_token");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return StringUtil.EMPTY_STRING;
    }
    
    @Bean
    @Scheduled(fixedRate = 60000)
	public HttpHeadersProvider customHttpHeadersProvider() {
    	log.info("Access Token refreshed at {}", dateFormat.format(new Date()));
	    return  instance -> {
	        HttpHeaders httpHeaders = new HttpHeaders();
	        httpHeaders.add("Authorization", "Bearer "+AccessTokenWrapper.getInstance().getAccessToken());
	        return httpHeaders;
	    };
	}

}