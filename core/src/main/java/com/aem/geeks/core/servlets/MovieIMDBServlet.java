package com.aem.geeks.core.servlets;

import static org.apache.sling.api.servlets.ServletResolverConstants.SLING_SERVLET_PATHS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

@Component(service = { Servlet.class }, property = { "sling.servlet.methods=" + HttpConstants.METHOD_GET,
		SLING_SERVLET_PATHS + "=" + "/bin/moviesIMDB" })

public class MovieIMDBServlet extends SlingAllMethodsServlet {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(MovieIMDBServlet.class);

	/*
	 * @Reference private MovieService movieService;
	 */
	
	private Gson gson = new Gson();

	@Override
	protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp)
			throws ServletException, IOException {
		String paramVal = req.getParameter("param");
		LOG.debug("Inside Get method");
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet getRequest = new HttpGet("https://imdb-scraper.p.rapidapi.com/search/title/Avenger/true");
		getRequest.addHeader("accept", "application/json");
		getRequest.addHeader("X-RapidAPI-Key", "3dff6be67cmsh32e65cb6f235984p119709jsn82f5da7b2964");
		HttpResponse httpResponse = httpClient.execute(getRequest);
		if (httpResponse.getStatusLine().getStatusCode() != 200) {
			LOG.debug("Inside the failure block");
			throw new RuntimeException("Failed : HTTP error code : " + httpResponse.getStatusLine().getStatusCode());
		} else {
			LOG.debug("Inside the success block");
			StringBuilder sb = new StringBuilder();
			LOG.debug("Before buffer reader");
			BufferedReader br = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
			LOG.debug("Response content-->" + httpResponse.getEntity().getContent());
			String jsonData = "";
			String output;
			while ((output = br.readLine()) != null) {
				LOG.debug("Reading json--->" + br.readLine());
				jsonData = jsonData+output;
				LOG.debug("jsondata-->"+jsonData);
				LOG.debug("output--->" + output);
			}
			
			String movieJsonString = this.gson.toJson(jsonData);
			LOG.debug("movieJsonString--->"+movieJsonString);
			PrintWriter out = resp.getWriter();
			try {
				out.println(movieJsonString);
			}
			finally {
		        out.close();
		    }
		}
	}

}
