package chat.chatapp.Conversation;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class FriendListRequest extends AsyncTask<String, String, ArrayList<JSONObject>> {

    private String json = "UNDEFINED";
    private ArrayList<JSONObject> jsonObjects = new ArrayList<>();
    private ArrayList<JSONObject> str;

    @Override
    protected ArrayList<JSONObject> doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept","application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            //set JSON parameters, username and password which will be sent to the Login servlet
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("accountName", params[1]);

            //send the Request
            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            os.writeBytes(jsonParam.toString());
            os.flush();
            os.close();
            //int status = conn.getResponseCode();
            //Log.d("eddig eljut",String.valueOf(status));

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder builder = new StringBuilder();
            String inputString;
            while ((inputString = reader.readLine()) != null) {
                builder.append(inputString + "\n");
            }


            JSONObject jsonOutput = new JSONObject(builder.toString());

            //Log.d("temp", jsonOutput.toString());
            JSONArray result = jsonOutput.getJSONArray("friendDetails");
            for(int i = 0; i < result.length();i++){
                JSONObject tempO = result.getJSONObject(i);
                jsonObjects.add(tempO);
            }

            conn.disconnect();
        }
        catch (MalformedURLException ex) { Log.e("LoginAccount, URL tag", ex.toString());}
        catch (IOException ex) {Log.e("LoginAccount HTTP tag",ex.toString());}
        catch (JSONException ex) { Log.d("JSONEXception",ex.toString()); }

        str = jsonObjects;
        return jsonObjects;
    }
}
