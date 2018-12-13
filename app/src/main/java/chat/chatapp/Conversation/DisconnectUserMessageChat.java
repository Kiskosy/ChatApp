package chat.chatapp.Conversation;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.PrintWriter;
import java.net.URI;

import chat.chatapp.Resources.Constants;
import io.socket.client.Manager;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class DisconnectUserMessageChat extends AsyncTask<String, String, String> {

    private static Socket socket;
    private static String ip = "94.21.181.64";
    private static PrintWriter printWriter;

    @Override
    protected String doInBackground(String... params) {

        try {

            Manager manager = new Manager(new URI(Constants.DefaultURL));
            socket = manager.socket("/DisconnectUsers");
            socket.connect();
            socket.emit("DisconnectALLUserMessageChat");
            Thread.sleep(5000);//5 seconds
            socket.disconnect();

        } catch (Exception ex) { Log.d("ex",ex.toString()); }


        return "Couldnt disconnect users";
    }

}
