package com.examples.fredyrincon.httpengine;

/**
 * Created by fredyrincon on 3/10/2014.
 */

import android.util.Base64;
import android.util.Log;

import com.example.fredyrincon.model.AddressModel;
import com.example.fredyrincon.model.ContactModel;
import com.example.fredyrincon.model.CurrencyBitModel;
import com.example.fredyrincon.model.UserModel;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;


public class CoinJarAndroidDAO {

    private static final String TAG = "Class_CoinJarAndroidDAO";

    private String apiEndpoint;
    private String checkoutEndpoint;
    private String apiKey;
    private String checkoutUser;
    private String checkoutSecret;
    private String orderURL;

    private Gson gson;


    //Definition of the URL To Call
    public CoinJarAndroidDAO(String user, String secret, String apikey, boolean sandbox) {
        if(sandbox) {
            apiEndpoint = "https://secure.sandbox.coinjar.io/api/v1/";
            checkoutEndpoint = "https://checkout.sandbox.coinjar.io/api/v1/";
            orderURL = "https://checkout.sandbox.coinjar.io/orders/";
        } else {
            apiEndpoint = "https://api.coinjar.io/v1/";
            checkoutEndpoint = "https://checkout.coinjar.io/api/v1/";
            orderURL = "https://checkout.coinjar.io/orders/";
        }

        //Set the parameters
        this.apiKey = apikey;
        this.checkoutUser = user;
        this.checkoutSecret = secret;

        gson = new Gson();
    }

    //Retrieve account information
    public String accountInformation() {
        return simpleRequestGet("account", null , apiKey);
    }

    //Retrieve account information
    public UserModel accountInformationAdvanced() {
        UserModel userData = null;
        try {
            String jsonResponse =  simpleRequestGet("account", null , apiKey);

            JsonParser jParser = new JsonParser();
            JsonObject jObject = (JsonObject) jParser.parse(jsonResponse);
            JsonElement elem = jObject.get("user");

            userData = gson.fromJson(elem.toString(), UserModel.class);
            //userList = gson.fromJson(jsonResponse, new TypeToken<List<UserModel>>(){}.getType());
            //{"user":{"uuid":"808a9aea-3781-4af4-baa1-e853a207ef91","email":"fredyrincon@hotmail.com","full_name":"Fredy Rincon","available_balance":"0.0","unconfirmed_balance":"0.0","confirmed":true}}
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return userData;
    }

    // List bitcoin addresses
    public String listBitcoinAddresses(int limit, int offset) {
        HashMap <String, String> parameter = new HashMap<String, String>();
        parameter.put("limit", String.valueOf(limit));
        parameter.put("offset", String.valueOf(offset));
        return simpleRequestGet("bitcoin_addresses", parameter , apiKey);
    }

    // List bitcoin addresses
    public ArrayList <AddressModel> listBitcoinAddressesAdvanced(int limit, int offset) {
        HashMap <String, String> parameter = new HashMap<String, String>();
        parameter.put("limit", String.valueOf(limit));
        parameter.put("offset", String.valueOf(offset));

        ArrayList <AddressModel> bitCoinList = null;
        try {
            String jsonResponse =  simpleRequestGet("bitcoin_addresses", parameter , apiKey);

            JsonParser jParser = new JsonParser();
            JsonObject jObject = (JsonObject) jParser.parse(jsonResponse);
            JsonElement elem = jObject.get("bitcoin_addresses");

            bitCoinList = gson.fromJson(elem.toString(), new TypeToken<ArrayList <AddressModel>>(){}.getType());
            //{"bitcoin_addresses":[{"address":"1J6r2jEGrvL6JSNiVL8RrhmYT6tVTsWeZg","label":"My Address label 1","total_received":"0.0","total_confirmed":"0.0"},{"address":"1Hjd9buxmFQJxAepFGj9eGZxdgHKYLuSvT","label":null,"total_received":"0.0","total_confirmed":"0.0"}]}
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return bitCoinList;
    }

    //Retrieve bitcoin address
    public String bitcoinAddress(String addressId ) {
        return simpleRequestGet("bitcoin_addresses/" + addressId, null , apiKey);
    }

    public AddressModel bitcoinAddressAdvanced(String addressId) {
        AddressModel addressData = null;
        try {
            String jsonResponse =  simpleRequestGet("bitcoin_addresses/" + addressId, null , apiKey);

            JsonParser jParser = new JsonParser();
            JsonObject jObject = (JsonObject) jParser.parse(jsonResponse);
            JsonElement elem = jObject.get("bitcoin_address");

            addressData = gson.fromJson(elem.toString(), AddressModel.class);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return addressData;
    }

    //List contacts
    public String listContacts (int limit, int offset) {
        HashMap <String, String> parameter = new HashMap<String, String>();
        parameter.put("limit", String.valueOf(limit));
        parameter.put("offset", String.valueOf(offset));
        return simpleRequestGet("contacts", parameter , apiKey);
    }

    //List contacts
    public ArrayList <ContactModel> listContactsAdvanced(int limit, int offset) {
        HashMap <String, String> parameter = new HashMap<String, String>();
        parameter.put("limit", String.valueOf(limit));
        parameter.put("offset", String.valueOf(offset));

        ArrayList <ContactModel> contactList = null;
        try {
            String jsonResponse =  simpleRequestGet("contacts", parameter , apiKey);

            JsonParser jParser = new JsonParser();
            JsonObject jObject = (JsonObject) jParser.parse(jsonResponse);
            JsonElement elem = jObject.get("contacts");

            contactList = gson.fromJson(elem.toString(), new TypeToken<ArrayList <ContactModel>>(){}.getType());
            //{"contacts":[{"uuid":"a5a969e8-34d3-4bd7-adf2-4c90a5e58a90","name":"Jhon Smit","payee_name":"fredyrincon@hotmail.com","payee_type":"WALLET","created_at":"2014-10-03T15:27:32.000Z","updated_at":"2014-10-03T15:27:32.000Z"},{"uuid":"0b03ef19-a94d-469c-8740-cec4dadf005c","name":"Erwin Rincon","payee_name":"fredyrincon@hotmail.com","payee_type":"WALLET","created_at":"2014-10-03T15:27:12.000Z","updated_at":"2014-10-03T15:27:12.000Z"}]}
         } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return contactList;
    }

    //Retrieve contact by the $uuid
    public String contact (String uuid) {
        return simpleRequestGet("contacts/" + uuid, null , apiKey);
    }

    //Retrieve contact by the $uuid
    public ContactModel contactAdvanced(String uuid) {
        ContactModel contactData = null;
        try {
            String jsonResponse =  simpleRequestGet("contacts/" + uuid, null , apiKey);

            JsonParser jParser = new JsonParser();
            JsonObject jObject = (JsonObject) jParser.parse(jsonResponse);
            JsonElement elem = jObject.get("contact");

            contactData = gson.fromJson(elem.toString(), ContactModel.class);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return contactData;
    }

    //Retrieve the rate of conversion between currencies
    // @param $currency could be : BTC, USD, AUD, NZD, CAD, EUR, GBP, SGD, HKD, CHF, JPY
    public String fairRate (String currency) {
        return simpleRequestGet("fair_rate/" + currency.toUpperCase(), null , apiKey);
    }

    //Retrieve the rate of conversion between currencies
    // @param $currency could be : BTC, USD, AUD, NZD, CAD, EUR, GBP, SGD, HKD, CHF, JPY
    public String fairRateA (String currency) {
        return simpleRequestGet("fair_rate/" + currency.toUpperCase(), null , apiKey);
    }

    //Retrieve the rate of conversion between currencies
    // @param $currency could be : BTC, USD, AUD, NZD, CAD, EUR, GBP, SGD, HKD, CHF, JPY
    public CurrencyBitModel fairRateAdvanced(String currency) {
        CurrencyBitModel currencyData = null;
        try {
            String jsonResponse =  simpleRequestGet("fair_rate/" + currency.toUpperCase(), null , apiKey);
            currencyData = gson.fromJson(jsonResponse, CurrencyBitModel.class);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return currencyData;
    }

    public String simpleRequestGet(String actionString, HashMap <String, String> parameter, String userAuth) {
        StringBuffer stringBuffer = new StringBuffer("");
        BufferedReader bufferedReader = null;
        String resultJson = "";

        try {

            String requestParameters = "";

            if (parameter != null) {
                for (String key : parameter.keySet()) {
                    requestParameters += "&" + key + "=" + parameter.get(key);
                }
            }

            String getURL = apiEndpoint + actionString + ".json?" + requestParameters;
            HttpUriRequest request = new HttpGet(getURL); // Or HttpPost(), depends on your needs

            String credentials = userAuth;
            String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            request.addHeader("Authorization","Basic " + base64EncodedCredentials);

            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse httpResponse = httpclient.execute(request);
            InputStream inputStream = httpResponse.getEntity().getContent();
            bufferedReader = new BufferedReader(new InputStreamReader(
                    inputStream));

            String readLine = bufferedReader.readLine();
            while (readLine != null) {
                stringBuffer.append(readLine);
                stringBuffer.append("\n");
                readLine = bufferedReader.readLine();
            }

        } catch (Exception e) {

        }
        resultJson = stringBuffer.toString();
        return resultJson;
    }
}
