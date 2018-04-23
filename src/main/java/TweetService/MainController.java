package TweetService;




import java.io.BufferedReader;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import TweetService.ProjectRepository;
import TweetService.Company;
import TweetService.Contract;
import TweetService.NumberOfContracts;
import TweetService.Project;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;
import org.apache.log4j.Logger;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller    // This means that this class is a Controller
@RequestMapping(path="/demo") // This means URL's start with /demo (after Application path)

public class MainController{
	@Autowired
	ProjectRepository projectRepository;


	
	
	public void testPostingToTwitter(String message) throws TwitterException{
		Twitter twitter = TwitterFactory.getSingleton();
		Status status = twitter.updateStatus(message);	
	}
    
	public static String html2text(String html) {
	    return Jsoup.parse(html).text();
	} 
	

	@GetMapping(path="/tweet4seller") // Map ONLY GET Requests
	public @ResponseBody String Tweet4Seller (@RequestParam String id)throws Exception {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request

		String url = new String();
	    String json = new String();
		// Load Project id 
		long projectId = 2368849 ;
	    
        
		//Convert seller.title.json to String
		HttpClient httpClient = HttpClientBuilder.create().build();
	    url = "http://platform.yourdatastories.eu/api/json-ld/component/aggregate.tcl?baseurl=http:%2F%2Fplatform.yourdatastories.eu%2Fproject-details&id="+id+"&lang=en&type=organisation.contract.seller.title";
		HttpGet getRequest = new HttpGet(url);
		getRequest.addHeader("accept", "application/json");
		HttpResponse response = httpClient.execute(getRequest);
		if (response.getStatusLine().getStatusCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
			   + response.getStatusLine().getStatusCode());
		}
		BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
		String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			//System.out.println(output);
			json = output;	
		}
		
		
		//Map json string to a Company Object 
		ObjectMapper mapper = new ObjectMapper();
		Company company = mapper.readValue(json,Company.class);
		//System.out.println(company.getData().getValue());
		//System.out.println(company.getData().getAspect());
	    String  seller = new String(company.getData().getValue());
		
	   
	    //Convert contracts.count.json to String
		httpClient = HttpClientBuilder.create().build();
		url = "http://platform.yourdatastories.eu/api/json-ld/component/aggregate.tcl?baseurl=http:%2F%2Fplatform.yourdatastories.eu%2Fproject-details&id="+id+"&lang=en&type=organisation.TED.contracts.count";
	    getRequest = new HttpGet(url);
		getRequest.addHeader("accept", "application/json");
	    response = httpClient.execute(getRequest);
		if (response.getStatusLine().getStatusCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
			   + response.getStatusLine().getStatusCode());
		}
	     br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
		while ((output = br.readLine()) != null) {
			//System.out.println(output);
			json = output;
		}
     
		//  Map json string to a NumberOfContracts Object 
		mapper = new ObjectMapper();
		NumberOfContracts contract = mapper.readValue(json,NumberOfContracts.class);
		//System.out.println(contract.getData().getValue());
		//System.out.println(contract.getData().getLabel());
		Integer contract_counter = new Integer(contract.getData().getValue());
		
	    
		//Convert contracts.top.json to String
	    url = "http://platform.yourdatastories.eu/api/json-ld/component/aggregate.tcl?baseurl=http:%2F%2Fplatform.yourdatastories.eu%2Fproject-details&id="+id+"&lang=en&type=organisation.TED.contracts.categories.top";
		getRequest = new HttpGet(url);
		getRequest.addHeader("top_category", "application/json");
		response = httpClient.execute(getRequest);
		if (response.getStatusLine().getStatusCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
			   + response.getStatusLine().getStatusCode());
		}
	    br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
		while ((output = br.readLine()) != null) {
			//System.out.println(output);
			json = output;
		}
		
		// Map json string to a Contract Object 
	     mapper = new ObjectMapper();
		Contract top_contract = mapper.readValue(json,Contract.class);
		//System.out.println(top_contract.getData().getValue());
		//System.out.println(top_contract.getData().getLabel());
	    String  top_category = new String(top_contract.getData().getValue());
		
	    
	  //Convert amount.total.json to String
		httpClient = HttpClientBuilder.create().build();
	    url = "http://platform.yourdatastories.eu/api/json-ld/component/aggregate.tcl?baseurl=http:%2F%2Fplatform.yourdatastories.eu%2Fproject-details&id="+id+"&lang=en&type=organisation.TED.contracts.amount.total";
		getRequest = new HttpGet(url);
		getRequest.addHeader("top_category", "application/json");
		response = httpClient.execute(getRequest);
		if (response.getStatusLine().getStatusCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
			   + response.getStatusLine().getStatusCode());
		}
	      br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
		while ((output = br.readLine()) != null) {
			//System.out.println(output);
			json = output;
		}
		
		// Find Contract Ammount. Map json string to a Contract Object 
	    mapper = new ObjectMapper();
	    Contract top_contract_ammount = mapper.readValue(json,Contract.class);
		//System.out.println(top_contract_ammount.getData().getValue());
		//System.out.println(top_contract_ammount.getData().getLabel());
	    String  contract_value = new String(top_contract_ammount.getData().getValue());
	    
	   
	    
	    //Convert contracts.buyer.top.json to String
		httpClient = HttpClientBuilder.create().build();
	    url = "http://platform.yourdatastories.eu/api/json-ld/component/aggregate.tcl?baseurl=http:%2F%2Fplatform.yourdatastories.eu%2Fproject-details&id="+id+"&lang=en&type=organisation.TED.contracts.buyer.top";
		getRequest = new HttpGet(url);
		getRequest.addHeader("top_", "application/json");
		response = httpClient.execute(getRequest);
		if (response.getStatusLine().getStatusCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
			   + response.getStatusLine().getStatusCode());
		}
	      br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
		while ((output = br.readLine()) != null) {
			//System.out.println(output);
			json = output;
		}
		
		// Find Contract Ammount. Map json string to a Contract Object 
	    mapper = new ObjectMapper();
	    Contract top_buyer_html = mapper.readValue(json,Contract.class);
		//System.out.println(top_buyer_html.getData().getValue());
		//System.out.println(top_buyer_html.getData().getLabel());
	    String  top_buyer = html2text (new String(top_buyer_html.getData().getValue()));
	    //System.out.println(top_buyer);
	    
	    //Tweet contract
	    String template = new String("@"+seller+" signed "+ contract_counter+ " contract(s) of total " + contract_value +". Most of them are "+ top_category + " selled to @"+ top_buyer);
	    System.out.println(template);
		Project n = new Project();
		n.setProjectId(projectId);
		projectRepository.save(n);
		
		testPostingToTwitter(template);
		System.out.println("Tweet posted!");
		
		return "Tweet Posted";
	}
	@GetMapping(path="/tweet4buyer") // Map ONLY GET Requests
	public @ResponseBody String Tweet4Buyer (@RequestParam String id)throws Exception {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request

		String url = new String();
	    String json = new String();
		// Load Project id 
		long projectId = 2368849 ;
	    
        
		//Convert seller.title.json to String
		HttpClient httpClient = HttpClientBuilder.create().build();
	    url = "http://platform.yourdatastories.eu/api/json-ld/component/aggregate.tcl?baseurl=http:%2F%2Fplatform.yourdatastories.eu%2Fproject-details&id="+id+"&lang=en&type=organisation.contract.buyer.title";
		HttpGet getRequest = new HttpGet(url);
		getRequest.addHeader("accept", "application/json");
		HttpResponse response = httpClient.execute(getRequest);
		if (response.getStatusLine().getStatusCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
			   + response.getStatusLine().getStatusCode());
		}
		BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
		String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			//System.out.println(output);
			json = output;	
		}
		
		
		//Map json string to a Company Object 
		ObjectMapper mapper = new ObjectMapper();
		Company company = mapper.readValue(json,Company.class);
		//System.out.println(company.getData().getValue());
		//System.out.println(company.getData().getAspect());
	    String  buyer = new String(company.getData().getValue());
		
	   
	    //Convert contracts.count.json to String
		httpClient = HttpClientBuilder.create().build();
		url = "http://platform.yourdatastories.eu/api/json-ld/component/aggregate.tcl?baseurl=http:%2F%2Fplatform.yourdatastories.eu%2Fproject-details&id="+id+"&lang=en&type=organisation.TED.buyer.contracts.count";
	    getRequest = new HttpGet(url);
		getRequest.addHeader("accept", "application/json");
	    response = httpClient.execute(getRequest);
		if (response.getStatusLine().getStatusCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
			   + response.getStatusLine().getStatusCode());
		}
	     br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
		while ((output = br.readLine()) != null) {
			//System.out.println(output);
			json = output;
		}
     
		//  Map json string to a NumberOfContracts Object 
		mapper = new ObjectMapper();
		NumberOfContracts contract = mapper.readValue(json,NumberOfContracts.class);
		//System.out.println(contract.getData().getValue());
		//System.out.println(contract.getData().getLabel());
		Integer contract_counter = new Integer(contract.getData().getValue());
		
	    
		//Convert contracts.top.json to String
	    url = "http://platform.yourdatastories.eu/api/json-ld/component/aggregate.tcl?baseurl=http:%2F%2Fplatform.yourdatastories.eu%2Fproject-details&id="+id+"&lang=en&type=organisation.TED.contracts.categories.top";
		getRequest = new HttpGet(url);
		getRequest.addHeader("top_category", "application/json");
		response = httpClient.execute(getRequest);
		if (response.getStatusLine().getStatusCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
			   + response.getStatusLine().getStatusCode());
		}
	    br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
		while ((output = br.readLine()) != null) {
			//System.out.println(output);
			json = output;
		}
		
		// Map json string to a Contract Object 
	     mapper = new ObjectMapper();
		Contract top_contract = mapper.readValue(json,Contract.class);
		//System.out.println(top_contract.getData().getValue());
		//System.out.println(top_contract.getData().getLabel());
	    String  top_category = new String(top_contract.getData().getValue());
		
	    
	  //Convert amount.total.json to String
		httpClient = HttpClientBuilder.create().build();
	    url = "http://platform.yourdatastories.eu/api/json-ld/component/aggregate.tcl?baseurl=http:%2F%2Fplatform.yourdatastories.eu%2Fproject-details&id="+id+"&lang=en&type=organisation.TED.buyer.contracts.amount.total";
		getRequest = new HttpGet(url);
		getRequest.addHeader("top_category", "application/json");
		response = httpClient.execute(getRequest);
		if (response.getStatusLine().getStatusCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
			   + response.getStatusLine().getStatusCode());
		}
	      br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
		while ((output = br.readLine()) != null) {
			//System.out.println(output);
			json = output;
		}
		
		// Find Contract Ammount. Map json string to a Contract Object 
	    mapper = new ObjectMapper();
	    Contract top_contract_ammount = mapper.readValue(json,Contract.class);
		//System.out.println(top_contract_ammount.getData().getValue());
		//System.out.println(top_contract_ammount.getData().getLabel());
	    String  contract_value = new String(top_contract_ammount.getData().getValue());
	    
	   
	    
	    //Convert contracts.buyer.top.json to String
		httpClient = HttpClientBuilder.create().build();
	    url = "http://platform.yourdatastories.eu/api/json-ld/component/aggregate.tcl?baseurl=http:%2F%2Fplatform.yourdatastories.eu%2Fproject-details&id="+id+"&lang=en&type=organisation.TED.buyer.contracts.seller.top";
		getRequest = new HttpGet(url);
		getRequest.addHeader("top_", "application/json");
		response = httpClient.execute(getRequest);
		if (response.getStatusLine().getStatusCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
			   + response.getStatusLine().getStatusCode());
		}
	      br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
		while ((output = br.readLine()) != null) {
			//System.out.println(output);
			json = output;
		}
		
		// Find Contract Ammount. Map json string to a Contract Object 
	    mapper = new ObjectMapper();
	    Contract top_seller_html = mapper.readValue(json,Contract.class);
		//System.out.println(top_buyer_html.getData().getValue());
		//System.out.println(top_buyer_html.getData().getLabel());
	    String  top_seller = html2text (new String(top_seller_html.getData().getValue()));
	    //System.out.println(top_buyer);
	    
	    //Tweet contract
	    String template = new String("@"+buyer+" signed "+ contract_counter+ " contract(s) of total " + contract_value +". Most of them  "+ top_category + " buyed from @"+ top_seller);
	    System.out.println(template);
		Project n = new Project();
		n.setProjectId(projectId);
		projectRepository.save(n);
		
		testPostingToTwitter(template);
		System.out.println("Tweet posted!");
		
		return "Tweet Posted";
	}
	
	
	

}
