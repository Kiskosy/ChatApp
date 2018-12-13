package chat.chatapp.Resources;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class TokenRequest extends AsyncTask <String, Integer, String>{

    @Override
    protected String doInBackground(String... urls) {
        try {
            URL url = new URL(urls[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();
            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "", line;
            while ((line = rd.readLine()) != null) {
                result += line + "\n";
            }
            //int token = Integer.parseInt(result);
            Log.d("something:",result);
            return result;
        }
        catch (MalformedURLException ex) { Log.e("TokenRequest, URL tag", ex.toString());}
        catch (IOException ex) {Log.e("TokenRequest HTTP tag",ex.toString());}

        return null;
    }

}
