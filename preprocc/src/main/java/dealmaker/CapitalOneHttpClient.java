package dealmaker;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import dealmaker.entity.Account;
import dealmaker.entity.Transaction;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.List;

public class CapitalOneHttpClient {
    private String apiURL="https://sandbox.capitalone.co.uk/developer-services-platform-pr/api/data";

    // one instance, reuse
    private final CloseableHttpClient httpClient = HttpClients.createDefault();




    public void createAccounts(String key, String quantity) throws Exception{
        HttpPost post = new HttpPost(apiURL+"/accounts/create");
        post.addHeader("Authorization", "Bearer "+key);
        post.addHeader("Content-Type", "application/json");
        post.addHeader("version","1.0");

        // add request parameter, form parameters
        StringEntity se = new StringEntity("{\"quantity\": "+quantity+"}");
        post.setEntity(se);
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {

            System.out.println(EntityUtils.toString(response.getEntity()));
        }

    }
    public void createTransactions(String key, String accountId, int quantity)throws Exception{
        HttpPost post = new HttpPost(apiURL+"/transactions/accounts/"+accountId+"/create");
        post.addHeader("Authorization", "Bearer "+key);
        post.addHeader("Content-Type", "application/json");
        post.addHeader("version","1.0");

        // add request parameter, form parameters
        StringEntity se = new StringEntity("{\"quantity\": "+quantity+"}");
        post.setEntity(se);
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {

            System.out.println(EntityUtils.toString(response.getEntity()));
        }
    }

    public List<Account> getAllAccounts(String key) throws Exception {

        List<Account> accounts = new ArrayList<Account>();

        HttpGet request = new HttpGet(apiURL+"/accounts");
        // add request headers
        request.addHeader("Authorization", "Bearer "+key);
        request.addHeader("Content-Type", "application/json");
        request.addHeader("version","1.0");

        try (CloseableHttpResponse response = httpClient.execute(request)) {

            // Get HttpResponse Status
            System.out.println(response.getStatusLine().toString());

            HttpEntity entity = response.getEntity();
            Header headers = entity.getContentType();
            System.out.println(headers);

            if (entity != null) {
                String result = EntityUtils.toString(entity);
                JsonParser jsonParser = new JsonParser();
                JsonObject jo = (JsonObject)jsonParser.parse(result);
                JsonArray accountsJA = jo.getAsJsonArray("Accounts");

                System.out.println(result);
                Type accountListType = new TypeToken<ArrayList<Account>>(){}.getType();
                Gson gson = new Gson();
                //JSON JsonObject to Java object
                accounts = gson.fromJson(accountsJA, accountListType);
            }

        }

        return accounts;
    }

    public List<Transaction> getAllTransactionsForID(String key, String accountId)throws Exception{
        List<Transaction> transactions = new ArrayList<Transaction>();


        HttpGet request = new HttpGet(apiURL+"/transactions/accounts/"+accountId+"/transactions");
        // add request headers
        request.addHeader("Authorization", "Bearer "+key);
        request.addHeader("Content-Type", "application/json");
        request.addHeader("version","1.0");

        try (CloseableHttpResponse response = httpClient.execute(request)) {

            // Get HttpResponse Status
            System.out.println(response.getStatusLine().toString());

            HttpEntity entity = response.getEntity();
            Header headers = entity.getContentType();
            System.out.println(headers);


            if (entity != null) {
                String result = EntityUtils.toString(entity);
                JsonParser jsonParser = new JsonParser();
                JsonObject jo = (JsonObject)jsonParser.parse(result);
                JsonArray trasnsactionsJA = jo.getAsJsonArray("Transactions");

                System.out.println(result);
                Type transListType = new TypeToken<ArrayList<Transaction>>(){}.getType();
                Gson gson = new Gson();
                //JSON JsonObject to Java object
                transactions = gson.fromJson(trasnsactionsJA, transListType);
            }

        }
        return transactions;
    }

    public void close() throws IOException {
        httpClient.close();
    }


    private void sendPost() throws Exception {

        HttpPost post = new HttpPost("https://httpbin.org/post");

        // add request parameter, form parameters
        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("username", "abc"));
        urlParameters.add(new BasicNameValuePair("password", "123"));
        urlParameters.add(new BasicNameValuePair("custom", "secret"));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {

            System.out.println(EntityUtils.toString(response.getEntity()));
        }

    }

}

