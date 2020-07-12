import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;

public class VerifyRecaptcha {
	public static final String url = "https://www.google.com/recaptcha/api/siteverify";
	public static final String secret = "6Lc566kZAAAAACBTQThvTUvcACzs8mv-YaQ58qCN";
	private final static String USER_AGENT = "Mozilla/5.0";
	
	public static boolean verify(String gRecaptchaResponse) throws IOException{
		if(gRecaptchaResponse==null||"".equals(gRecaptchaResponse)) {
			System.out.println("Entering if-part");
			return false;
		}
		try {
			URL obj = new URL(url);
			
			HttpsURLConnection con = (HttpsURLConnection)obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("User_Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			String postParams = "secret=" + secret + "&response=" + gRecaptchaResponse;
			
			con.setDoOutput(true);
			DataOutputStream output = new DataOutputStream(con.getOutputStream());
			output.writeBytes(postParams);
			
			output.flush();
			output.close();
			
			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : "+url);
			System.out.println("Post Parameters : " + postParams);
			System.out.println("Response Code : " + responseCode);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			
			String line;
			StringBuffer response = new StringBuffer();
			
			while((line=br.readLine())!=null) {
				response.append(line);
			}
			
			br.close();
			
			System.out.println(response.toString());
			
			return true;
			
		}catch(Exception e) {

			System.out.println("Entering else-part");
			return false;
		}
	}
}
