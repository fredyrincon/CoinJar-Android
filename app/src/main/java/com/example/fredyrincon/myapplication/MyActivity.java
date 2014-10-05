package com.example.fredyrincon.myapplication;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.fredyrincon.model.AddressModel;
import com.example.fredyrincon.model.ContactModel;
import com.example.fredyrincon.model.CurrencyBitModel;
import com.example.fredyrincon.model.UserModel;
import com.examples.fredyrincon.httpengine.CoinJarAndroidDAO;

import java.util.ArrayList;


public class MyActivity extends Activity {

    private static final String TAG = "Class_MyActivity";

    private CoinJarAndroidDAO coinJarDAO;
    private String userConfigData = "fredyrincon@hotmail.com";
    private String userConfigAPI = "DUs6qsxuyyCrFN7me5ciq9MiSfjs4bqmimfBCdj9xqVndXyr";
    private String userConfigSecret = "";

    private TextView textViewResult;

    private String completeResultString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        textViewResult = (TextView) findViewById(R.id.textViewResult);

        //Initialized CoinJAr
        coinJarDAO = new CoinJarAndroidDAO(userConfigData, userConfigSecret, userConfigAPI , false);
        getAccountData(null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Function to get the information of the account
    public void getAccountData(View v) {
        completeResultString = "";
        LongOperation coinJarTask = new LongOperation();
        coinJarTask.execute();
    }

    //Function to display the result
    public void displayResultCoinJar() {
        textViewResult.setText("Result \r\n" + completeResultString);
    }

    //Function to be executed in background AsyncTask
    private class LongOperation extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            boolean successProcess = true;

            //-------------------------------------------------------------------------------------------
            String addressID = "";
            String contactID = "";
            //--------------------------------------------------------------------------------------------

            UserModel user = coinJarDAO.accountInformationAdvanced();
            if (user != null) {
                Log.i(TAG, "Response accountInformationAdvanced: " + user.getUuid() + " " + user.getFull_name());
                completeResultString = "User Account " + "Response accountInformationAdvanced: " + user.getUuid() + " " + user.getFull_name() + "\r\n";
            }

            String responseUserJson = coinJarDAO.accountInformation();
            Log.i(TAG, "Response responseUserJson: " + responseUserJson);

            //--------------------------------------------------------------------------------------------

            ArrayList <AddressModel> addressList = coinJarDAO.listBitcoinAddressesAdvanced(100, 0);

            if (addressList != null) {
                for (AddressModel each : addressList) {
                    Log.i(TAG, "Response listBitcoinAddressesAdvanced: " + each.getAddress() + " " + each.getLabel());
                    addressID = each.getAddress();
                    completeResultString = completeResultString + " Response listBitcoinAddressesAdvanced: " + each.getAddress() + " " + each.getLabel() + "\r\n";
                }
            }

            String responseAddressListJson = coinJarDAO.listBitcoinAddresses(100, 0);
            Log.i(TAG, "Response responseAddressListJson: " + responseAddressListJson);

            //--------------------------------------------------------------------------------------------

            AddressModel addressData = coinJarDAO.bitcoinAddressAdvanced(addressID);
            if (addressData != null) {
                Log.i(TAG, "Response bitcoinAddressAdvanced: " + addressData.getAddress() + " " + addressData.getLabel());
            }

            String responseAddressInfoJson = coinJarDAO.bitcoinAddress(addressID);
            Log.i(TAG, "Response responseAddressInfoJson: " + responseAddressInfoJson);

            //--------------------------------------------------------------------------------------------

            ArrayList <ContactModel> contactList = coinJarDAO.listContactsAdvanced(100, 0);

            if (contactList != null) {
                for (ContactModel each : contactList) {
                    Log.i(TAG, "Response listContactsAdvanced: " + each.getUuid() + " " + each.getName() + " " + each.getPayee_name());
                    contactID = each.getUuid();
                    completeResultString = completeResultString + " Response listContactsAdvanced: " + each.getUuid() + " " + each.getName() + " " + each.getPayee_name() + "\r\n";
                }
            }

            String responseContactListJson = coinJarDAO.listContacts(100, 0);
            Log.i(TAG, "Response responseContactListJson: " + responseContactListJson);

            //--------------------------------------------------------------------------------------------

            ContactModel contactData = coinJarDAO.contactAdvanced(contactID);

            if (contactData != null) {
                Log.i(TAG, "Response contactAdvanced: " + contactData.getUuid() + " " + contactData.getName() + " " + contactData.getPayee_name());
            }

            String responseContactInfoJson = coinJarDAO.contact(contactID);
            Log.i(TAG, "Response responseContactInfoJson: " + responseContactInfoJson);

            //--------------------------------------------------------------------------------------------

            String[] currencyId = {"BTC", "USD", "AUD", "NZD", "CAD", "EUR", "GBP", "SGD", "HKD", "CHF", "JPY"};

            for (int i = 0 ; i < currencyId.length ; i++) {
                String eachCurrency = currencyId [i];
                String responseEachCurrencyJson = coinJarDAO.fairRate(eachCurrency);
                Log.i(TAG, "Response responseEachCurrencyJson: " + responseEachCurrencyJson);
            }

            for (int i = 0 ; i < currencyId.length ; i++) {
                String eachCurrency = currencyId [i];
                CurrencyBitModel currencyData = coinJarDAO.fairRateAdvanced(eachCurrency);
                currencyData.setCurrencyId(eachCurrency);

                Log.i(TAG, "Response fairRateAdvanced: " + currencyData.getCurrencyId() + " " + currencyData.getBid() + " " + currencyData.getAsk() + " " + currencyData.getSpot());
                completeResultString = completeResultString + " Response fairRateAdvanced: " + currencyData.getCurrencyId() + " " + currencyData.getBid() + " " + currencyData.getAsk() + " " + currencyData.getSpot() + "\r\n";
            }

            //--------------------------------------------------------------------------------------------

            return successProcess;

        }

        @Override
        protected void onPostExecute(Boolean result) {
            displayResultCoinJar();
        }

        @Override
        protected void onPreExecute() {
        }

    }
}
