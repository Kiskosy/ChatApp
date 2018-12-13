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

public class GetFriendListRequest extends AsyncTask<String, String, String> {

    private static Socket socket;
    private static String ip = "94.21.181.64";
    private static PrintWriter printWriter;

    @Override
    protected String doInBackground(String... params) {

        String messageToPass = params[0];
        try {

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("accountName",params[1]);
            jsonParam.put("targetAccount", params[2]);
            jsonParam.put("messageT0ime",params[3]);
            Log.d("json : ", jsonParam.toString());

            Manager manager = new Manager(new URI(Constants.DefaultURL));
            socket = manager.socket("/UpdateConversation");
            ////socket = IO.socket("http://kosylocalpc.theworkpc.com:8080/testfsadasfasdasdsa");
            socket.on("UpdateConversation",test);
            socket.connect();
            socket.emit("RegisterUserMessageChat",jsonParam);

        } catch (Exception ex) { Log.d("ex",ex.toString()); }
        //socket.disconnect();

        return "Account does not exists";
    }

    private Emitter.Listener test = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.d("YOLO",args[0].toString());
        }
    };
}
