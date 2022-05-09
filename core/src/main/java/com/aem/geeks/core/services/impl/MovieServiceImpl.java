package com.aem.geeks.core.services.impl;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aem.geeks.core.services.MovieService;

public class MovieServiceImpl implements MovieService {

	private static final Logger LOG = LoggerFactory.getLogger(MovieServiceImpl.class);
	
	@Override
	public String getMovieList() {
		
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
	        HttpGet getRequest = new HttpGet("https://imdb-scraper.p.rapidapi.com/search/title/Avenger/true");
	        getRequest.addHeader("accept", "application/json");
	        getRequest.addHeader("X-RapidAPI-Key", "3dff6be67cmsh32e65cb6f235984p119709jsn82f5da7b2964");
	        HttpResponse httpResponse = httpClient.execute(getRequest);
	        if (httpResponse.getStatusLine().getStatusCode() != 200) {
	        	LOG.debug("Inside the failure block");
	            throw new RuntimeException("Failed : HTTP error code : " + httpResponse.getStatusLine().getStatusCode());
	          }
	        else {
	        	LOG.debug("Inside the success block");
	        }
		}
		
		catch (Exception e)
        {
            e.printStackTrace() ; 
        }
		return null;
		// TODO Auto-generated method stub
		
	}

}
