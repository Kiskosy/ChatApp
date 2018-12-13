package chat.chatapp.Conversation;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class GetMessageRequest extends AsyncTask<String, String, ArrayList<JSONObject>> {

    @Override
    protected ArrayList<JSONObject> doInBackground(String... urls) {
        try {
            URL url = new URL(urls[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuilder builder = new StringBuilder();
            String inputString;
            while ((inputString = bufferedReader.readLine()) != null) {
                builder.append(inputString + "\n");
            }

            ArrayList<JSONObject> jsonObjects = new ArrayList<JSONObject>();
            try {
                JSONArray jsonArray = new JSONArray(builder.toString());
                for (int i = 0; i < jsonArray.length(); i++){
                    jsonObjects.add(jsonArray.getJSONObject(i));
                }

            } catch (JSONException ex) { Log.d("JSONEXception",ex.toString()); }

            return jsonObjects;
        }
        catch (MalformedURLException ex) { Log.e("LoginAccount, URL tag", ex.toString());}
        catch (IOException ex) {Log.e("LoginAccount HTTP tag",ex.toString());}

        return null;
    }
}