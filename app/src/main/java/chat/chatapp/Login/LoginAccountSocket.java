package chat.chatapp.Login;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;

import io.socket.client.IO;
import io.socket.client.Manager;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class LoginAccountSocket extends AsyncTask<String, String, String> {

    private static Socket socket;
    private static String ip = "94.21.181.64";
    private static PrintWriter printWriter;

    @Override
    protected String doInBackground(String... params) {

        try {
            Manager manager = new Manager(new URI("http://kosylocalpc.theworkpc.com:8080"));
            socket = manager.socket("/test");
            //socket = IO.socket("http://kosylocalpc.theworkpc.com:8080/testfsadasfasdasdsa");
            socket.on("test",test);
            socket.connect();
            socket.emit("connection2","asdfghh");

        } catch (Exception ex) { Log.d("ex",ex.toString()); }
        //socket.disconnect();
        /*
        try {

            socket = new Socket("kosylocalpc.theworkpc.com",8080);
            printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.write("i have called you");
            printWriter.flush();
            printWriter.close();
            socket.close();

        } catch (UnknownHostException ex) {Log.d("LoginAccountSocket", ex.toString()); }
        catch (IOException ex) { Log.d("LoginAccountSocket", ex.toString()); }
        */
        return "Account does not exists";
    }

    private Emitter.Listener test = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.d("YOLO",args[0].toString());
        }
    };


}
