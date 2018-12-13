package chat.chatapp.Login;

import android.os.AsyncTask;
import android.os.Debug;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.Console;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class LoginAccountRequest extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... params) {
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
            jsonParam.put("password", params[2]);

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

            conn.disconnect();
            return builder.toString();
        }
        catch (MalformedURLException ex) { Log.e("LoginAccount, URL tag", ex.toString());}
        catch (IOException ex) {Log.e("LoginAccount HTTP tag",ex.toString());}
        catch (JSONException ex) { Log.d("JSONEXception",ex.toString()); }

        return "Account does not exists";
    }
}
