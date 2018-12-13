package chat.chatapp.Register;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

import chat.chatapp.Resources.Constants;
import chat.chatapp.Resources.SaltPassword;

public class RegisterAccountRequest extends AsyncTask<String, Integer, String> {

    private JSONObject output = new JSONObject();
    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection connection = null;
        String result = "0";
        BufferedReader reader=null;

        String token = params[1];
        token = token.replace("\n","");

        int tokenInt = Integer.valueOf(token);
        tokenInt = tokenInt+1;
        token = String.valueOf(tokenInt);

        String accountName = params[2];
        String emailAddress = params[3];
        String password = params[4];
        String[] saltPassword = SaltPassword.GenerateSaltedHash(password);
        Log.d("passwordinhexa", saltPassword[0]);
        Log.d("saltinhexa", saltPassword[1]);
        Log.d("saltandpassinhexa", saltPassword[2]);

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
            jsonParam.put("token",token);
            jsonParam.put("accountName", accountName);
            jsonParam.put("emailAddress",emailAddress);
            jsonParam.put("saltValueInHexa",saltPassword[1]);
            jsonParam.put("saltpassInHexa", saltPassword[2]);
            jsonParam.put("whenCreated", Calendar.getInstance().getTime());

            //send the Request
            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            os.writeBytes(jsonParam.toString());
            os.flush();
            os.close();

            //get the Response
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + "\n");
            }

            //set the Return parameter (result) value
            result = sb.toString();
            //output = new JSONObject(result);

            conn.disconnect();
            Log.d("result : ",result);
            return result;
        } catch (MalformedURLException ex) { Log.e("LoginAccount, URL tag", ex.toString());}
        catch (IOException ex) {Log.e("LoginAccount HTTP tag",ex.toString());}
        catch (JSONException ex) {Log.e("JSONmaker",ex.toString());}
        return null;
    }
}
