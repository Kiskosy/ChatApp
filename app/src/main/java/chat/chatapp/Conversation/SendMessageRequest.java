package chat.chatapp.Conversation;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SendMessageRequest extends AsyncTask<String, String, String> {

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
            jsonParam.put("accountName",params[1]);
            jsonParam.put("targetAccount", params[2]);
            jsonParam.put("messageType", params[3]);
            jsonParam.put("messageContent", params[4]);
            jsonParam.put("messageTime",params[5]);
            Log.d("json : ", jsonParam.toString());

            //send the Request

            //DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            BufferedWriter os = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
            os.write(jsonParam.toString());
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
            Log.d("sndmessage : ",builder.toString());
            return builder.toString();
        }
        catch (MalformedURLException ex) { Log.e("LoginAccount, URL tag", ex.toString());}
        catch (IOException ex) {Log.e("LoginAccount HTTP tag",ex.toString());}
        catch (JSONException ex) { Log.d("JSONEXception",ex.toString()); }

        return "nope";
    }
}

