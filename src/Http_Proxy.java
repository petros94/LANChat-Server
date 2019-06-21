import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

public class Http_Proxy extends Backend_Proxy{

	private String USER_AGENT = "Mozilla/5.0";
	private String url;
	private HttpClient client;
	
	public Http_Proxy (String from, String to, String server_url) {
		super(from, to);
		final HttpParams httpParams = new BasicHttpParams();
	    HttpConnectionParams.setConnectionTimeout(httpParams, 1000);
		client = new DefaultHttpClient(httpParams);
		url = server_url;
	}
	
	public String get_from() {
		return sender;
	}
	
	public String get_to() {
		return receiver;
	}
	
	public String send (String message) throws Exception {
		String s = url;
		HttpPut put = new HttpPut(s);

		try {
	
			// add header
			put.setHeader("User-Agent", USER_AGENT);
	
			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			urlParameters.add(new BasicNameValuePair("officeName", receiver));
			urlParameters.add(new BasicNameValuePair("message", message));
	
	
			put.setEntity(new UrlEncodedFormEntity(urlParameters));
	
			HttpResponse response = client.execute(put);
			System.out.println("\nSending 'PUT' request to URL : " + s);
			System.out.println("Post parameters : " + put.getEntity());
			System.out.println("Response Code : " + 
	                                    response.getStatusLine().getStatusCode());
	
			BufferedReader rd = new BufferedReader(
	                        new InputStreamReader(response.getEntity().getContent()));
	
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			rd.close();
			EntityUtils.consume(response.getEntity());

	
			System.out.println(result.toString());
			
			return result.toString();
		} catch (ConnectTimeoutException e) {
//			put.releaseConnection();
			System.out.println("Backend Proxy Error");
			throw e;
		}
	}
	
//	public String test_session(String[] parameters) throws Exception {
//		try {
//			String from = parameters[0];
//			String to = parameters[1];
//			HttpGet request = new HttpGet(url + "/"+ from + "/" + to);
//	
//			// add request header
//			request.addHeader("User-Agent", USER_AGENT);
//	
//			HttpResponse response = client.execute(request);
//	
//			System.out.println("\nSending 'GET' request to URL : " + url + "/"+ from + "/" + to);
//			System.out.println("Response Code : " + 
//	                       response.getStatusLine().getStatusCode());
//	
//			BufferedReader rd = new BufferedReader(
//	                       new InputStreamReader(response.getEntity().getContent()));
//	
//			StringBuffer result = new StringBuffer();
//			String line = "";
//			while ((line = rd.readLine()) != null) {
//				result.append(line);
//			}
//	
//			return result.toString();
//		} catch (Exception e) {
//			return "Backend Proxy Error";
//		}
//		
//	}
	
	public String receive() throws Exception {
		HttpGet request = new HttpGet(url + "?officeName=" + receiver);
		try {
	
			// add request header
			request.addHeader("User-Agent", USER_AGENT);
			
	
			HttpResponse response = client.execute(request);
	
			System.out.println("\nSending 'GET' request to URL : " + url + "/"+ receiver + "/" + sender);
			System.out.println("Response Code : " + 
	                       response.getStatusLine().getStatusCode());
	
			BufferedReader rd = new BufferedReader(
	                       new InputStreamReader(response.getEntity().getContent()));
	
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			rd.close();
			EntityUtils.consume(response.getEntity());

	
			return result.toString();
		} catch (ConnectTimeoutException e) {
//			request.releaseConnection();
			System.out.println("Backend Proxy Error");
			throw e;
		}
		
	}

@Override
String test_session(String[] test_parameters) throws Exception {
	// TODO Auto-generated method stub
	return null;
}

@Override
String create_session(String[] session_parameters) throws Exception {
	// TODO Auto-generated method stub
	return null;
}

@Override
String start_session() throws Exception {
	// TODO Auto-generated method stub
	return null;
}

	
	// HTTP POST request
//	public String create_session(String[] session_parameters) throws Exception {
//		String from = session_parameters[0];
//		String to = session_parameters[1];
//
//		try {
//			HttpPost post = new HttpPost(url);
//	
//			// add header
//			post.setHeader("User-Agent", USER_AGENT);
//	
//			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
//			urlParameters.add(new BasicNameValuePair("from", from));
//			urlParameters.add(new BasicNameValuePair("to", to));
//			urlParameters.add(new BasicNameValuePair("message", "init session"));
//	
//	
//			post.setEntity(new UrlEncodedFormEntity(urlParameters));
//		
//	
//			HttpResponse response = client.execute(post);
//			System.out.println("\nSending 'POST' request to URL : " + url);
//			System.out.println("Post parameters : " + post.getEntity());
//			System.out.println("Response Code : " + 
//	                                    response.getStatusLine().getStatusCode());
//	
//			BufferedReader rd = new BufferedReader(
//	                        new InputStreamReader(response.getEntity().getContent()));
//	
//			StringBuffer result = new StringBuffer();
//			String line = "";
//			while ((line = rd.readLine()) != null) {
//				result.append(line);
//			}
//	
//			System.out.println(result.toString());
//			return result.toString();
//		} catch (Exception e) {
//			return "Backend Proxy Error"; 
//		}
//
//	}
//	
//	public String start_session() throws Exception {
//		String[] args = new String[] {sender, receiver};
//		if (check_session(sender, receiver) == false) {
//			System.out.println("From - to not found: Initializing chat");
//			String ret;
//			try {
//				ret = create_session(args);
//				String ret_key = (new json_parser()).parse_key(ret);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		else {
//			System.out.println("From - to FOUND!");	
//			String key = get_Key(sender, receiver);
//		}
//		
//		if (check_session(receiver, sender) == false) {
//			System.out.println("To - From not found: Initializing chat");
//			try {
//				args = new String[] {receiver, sender};
//				create_session(args);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		else {
//			System.out.println("To - from FOUND!");			
//		}
//		return "Session started";
//		
//	}
//	
//	public boolean check_session(String id, String to) {
//		String[] args = new String[] {id, to};
//		try {
//			String ret = test_session(args);
//			json_parser parser = new json_parser();
//			String ret_from = parser.parse_from(ret);
//			System.out.println(ret_from);
//			if (!ret_from.equals("error")) {
//				return true;
//			}
//			return false;
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return false;
//		}
//	}
//	
//	public String get_Key(String from, String to) {
//		String[] args = new String[] {from, to};
//		String key = "";
//		try {
//			String ret;
//			ret =  test_session(args);
//			json_parser parser = new json_parser();
//			key = parser.parse_key(ret);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return key;
//	}

	
}


