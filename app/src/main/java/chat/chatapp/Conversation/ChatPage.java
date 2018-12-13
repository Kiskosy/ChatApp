package chat.chatapp.Conversation;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toolbar;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

import chat.chatapp.Login.LoginAccountRequest;
import chat.chatapp.R;
import chat.chatapp.Resources.Constants;

public class ChatPage extends Activity {

    private String friendName;
    public String sendMessageContent = "";
    private EditText messageContentText;
    private String[] seperatedPassedObjects = new String[2];


    //@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_chatpage);
        String toGetObjects = getIntent().getStringExtra("toPassObjects");
        seperatedPassedObjects = toGetObjects.split(":");

        //String result = new SendMessageRequest().execute(Constants.SendMessageURL,"kosy","test2","sent","szeretem az őszt","2018/09/04 10:53:00").get();

        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("Hungarian"));

        try {
            String sendRequestResult = new RegisterUserMessageChat().execute(Constants.SendMessageURL, seperatedPassedObjects[0], seperatedPassedObjects[1], formatter.format(now.getTime())).get();
            Log.d("regUserMSGChatres",sendRequestResult);
        } catch (InterruptedException ex) { Log.d("log user chat", ex.toString()); }
        catch (ExecutionException ex) { Log.d("log user chat", ex.toString()); }

        //test1();
        //this.addContentView(createTV1());
        TableLayout parentTable = (TableLayout) findViewById(R.id.chatpageTL);
        parentTable.addView(createMenu());
        parentTable.addView(createTV1("kosy","agnes","sent","omg1","2018.08.17"));
        parentTable.addView(createTV1("kosy","agnes","received","omg2","2018.08.17"));
        parentTable.addView(createTV1("kosy","agnes","sent","omg1","2018.08.17"));
        parentTable.addView(createTV1("kosy","agnes","sent","omg1","2018.08.17"));
        parentTable.addView(createTV1("kosy","agnes","received","omg2","2018.08.17"));
        parentTable.addView(createTV1("kosy","agnes","sent","omg1","2018.08.17"));

        createSendMessage2();
        /*parentTable.addView(createTV2());
        parentTable.addView(createTV2());
        parentTable.addView(createTV1("omg"));*/
        //parentTable.addView(createSendMessage());


    }

    private LinearLayout createMenu(){
        LinearLayout parent = new LinearLayout(this);
        RelativeLayout.LayoutParams parentParams1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        parentParams1.addRule(RelativeLayout.ALIGN_TOP);
        parent.setLayoutParams(parentParams1);
        parent.setHorizontalGravity(LinearLayout.HORIZONTAL);

        Button button1 = new Button(this);
        button1.setLayoutParams(new RelativeLayout.LayoutParams(50, 50));
        button1.setTextColor(Color.BLACK);
        //button1.setText("back1");
        button1.setPadding(0,0,0,0);
        button1.setTextSize(12);
        button1.setBackgroundResource(R.drawable.ic_send_black_24dp);
        //button1.setBackgroundColor(Color.WHITE);
        parent.addView(button1);

        Button button2 = new Button(this);
        button2.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 50));
        button2.setTextColor(Color.BLACK);
        button2.setText("");
        button2.setPadding(0,0,0,0);
        button2.setTextSize(12);
        //button2.setBackgroundResource(R.drawable.rounded_corner);
        button2.setBackgroundColor(Color.WHITE);
        parent.addView(button2);

        Button button3 = new Button(this);
        button3.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 50));
        button3.setTextColor(Color.BLACK);
        button3.setText(friendName);
        button3.setPadding(0,0,0,0);
        button3.setTextSize(12);
        //button3.setBackgroundResource(R.drawable.ic_launcher_foreground);
        button3.setBackgroundColor(Color.WHITE);
        parent.addView(button3);

        Button button4 = new Button(this);
        button4.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 50));
        button4.setTextColor(Color.BLACK);
        button4.setText("");
        button4.setPadding(0,0,0,0);
        button4.setTextSize(12);
        button4.setBackgroundResource(R.drawable.border_below);
        button4.setBackgroundColor(Color.WHITE);
        parent.addView(button4);

        Button button5 = new Button(this);
        button5.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 50));
        button5.setTextColor(Color.BLACK);
        button5.setText("");
        button5.setPadding(0,0,0,0);
        button5.setTextSize(12);
        //button5.setBackgroundResource(R.drawable.rounded_corner);
        button5.setBackgroundColor(Color.WHITE);
        parent.addView(button5);

        //parent.setBackgroundResource(R.drawable.border_below);
        return parent;
    }

    //{ accountName : '" + accountName +"', targetAccount : '" + targetAccount +"', messageType : '" + messageType +"', messageContent : '" + messageContent + "', messageTime : '" + messageTime + "'
    private LinearLayout createTV1(String accountName, String targetAccount, String messageType, String messageContent, String messageTime) {

        if (messageType.equals("sent")) {
            //TableLayout parentTable = (TableLayout) findViewById(R.id.chatpageTL);
            LinearLayout parent = new LinearLayout(this);
            RelativeLayout.LayoutParams parentParams1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            parentParams1.addRule(RelativeLayout.ALIGN_TOP);
            parent.setLayoutParams(parentParams1);
            parent.setHorizontalGravity(LinearLayout.HORIZONTAL);
            parent.setGravity(Gravity.LEFT);
            //parent.setBackgroundResource(R.drawable.border);

            TextView text1 = new TextView(this);
            text1.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
            text1.setTextColor(Color.BLUE);
            text1.setText(messageContent);
            text1.setBackgroundResource(R.drawable.rounded_corner);
            text1.setPadding(10, 10, 10, 10);
            parent.addView(text1);
            return parent;
        } else {
            LinearLayout parent2 = new LinearLayout(this);
            RelativeLayout.LayoutParams parentParams2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            parentParams2.addRule(RelativeLayout.ALIGN_BOTTOM);
            parent2.setLayoutParams(parentParams2);
            parent2.setHorizontalGravity(LinearLayout.HORIZONTAL);
            parent2.setGravity(Gravity.RIGHT);
            //parent2.setBackgroundResource(R.drawable.rounded_corner);

            TextView text2 = new TextView(this);
            text2.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
            text2.setTextColor(Color.BLUE);
            text2.setText(messageContent);
            text2.setBackgroundResource(R.drawable.rounded_corner);
            text2.setPadding(10,10,10,10);
            parent2.addView(text2);

            return parent2;
        }
    }

    private LinearLayout createTV2() {

        LinearLayout parent2 = new LinearLayout(this);
        RelativeLayout.LayoutParams parentParams2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        parentParams2.addRule(RelativeLayout.ALIGN_BOTTOM);
        parent2.setLayoutParams(parentParams2);
        parent2.setHorizontalGravity(LinearLayout.HORIZONTAL);
        parent2.setGravity(Gravity.LEFT);
        //parent2.setBackgroundResource(R.drawable.rounded_corner);

        TextView text2 = new TextView(this);
        text2.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        text2.setTextColor(Color.BLUE);
        text2.setText("hello world 2");
        text2.setBackgroundResource(R.drawable.rounded_corner);
        text2.setPadding(10,10,10,10);
        parent2.addView(text2);

        return parent2;
    }

    private void createSendMessage2(){

        messageContentText = (EditText) findViewById(R.id.sendMessageExitText);
        messageContentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0){
                    messageContentText.setOnKeyListener(new View.OnKeyListener() {
                        public boolean onKey(View v, int keyCode, KeyEvent event) {
                            // If the event is a key-down event on the "enter" button
                            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                                // Perform action on key press

                                sendMessageContent = charSequence.toString();

                                InputMethodManager manager = (InputMethodManager) v.getContext()
                                        .getSystemService(INPUT_METHOD_SERVICE);
                                if (manager != null)
                                    manager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                                return true;
                            }
                            return false;
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        /*Button sendBTN = new Button(this);
        sendBTN.setLayoutParams(new RelativeLayout.LayoutParams(100, 70));
        sendBTN.setText("Send");
        sendBTN.setTextColor(Color.BLACK);
        sendBTN.setBackgroundResource(R.drawable.rounded_corner);*/

        Button sendBTN = (Button) findViewById(R.id.sendMessageButton);
        sendBTN.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                try {
                    Date now = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
                    formatter.setTimeZone(TimeZone.getTimeZone("Hungarian"));

                    String sendRequestResult = new SendMessageRequest().execute(Constants.SendMessageURL, seperatedPassedObjects[0], seperatedPassedObjects[1], "sent", sendMessageContent, formatter.format(now.getTime()) ).get();
                    //String receiveRequestResult = new SendMessageRequest().execute(Constants.SendMessageURL, seperatedPassedObjects[1], seperatedPassedObjects[0], "received", messageContent, formatter.format(now.getTime()) ).get();
                    Log.d("result ERR",sendRequestResult);
                    //Log.d("result ERR",receiveRequestResult);
                } catch (Exception ex) { Log.d("SendMessageRequest call", ex.toString()); };
            }
        });

    }

    private LinearLayout createSendMessage(){



        LinearLayout parent = new LinearLayout(this);
        RelativeLayout.LayoutParams parentParams1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        //parentParams1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        parent.setLayoutParams(parentParams1);
        parent.setHorizontalGravity(LinearLayout.HORIZONTAL);
        //parent.setGravity(Gravity.BOTTOM);

        //messageContentText = new EditText(this);
        //messageContentText.setLayoutParams(new RelativeLayout.LayoutParams(630, RelativeLayout.LayoutParams.WRAP_CONTENT));

        messageContentText = (EditText) findViewById(R.id.sendMessageExitText);
        messageContentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0){
                    messageContentText.setOnKeyListener(new View.OnKeyListener() {
                        public boolean onKey(View v, int keyCode, KeyEvent event) {
                            // If the event is a key-down event on the "enter" button
                            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                                // Perform action on key press

                                sendMessageContent = charSequence.toString();

                                InputMethodManager manager = (InputMethodManager) v.getContext()
                                        .getSystemService(INPUT_METHOD_SERVICE);
                                if (manager != null)
                                    manager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                                return true;
                            }
                            return false;
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        /*Button sendBTN = new Button(this);
        sendBTN.setLayoutParams(new RelativeLayout.LayoutParams(100, 70));
        sendBTN.setText("Send");
        sendBTN.setTextColor(Color.BLACK);
        sendBTN.setBackgroundResource(R.drawable.rounded_corner);*/

        Button sendBTN = (Button) findViewById(R.id.sendMessageButton);
        sendBTN.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                try {
                    Date now = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
                    formatter.setTimeZone(TimeZone.getTimeZone("Hungarian"));

                    String sendRequestResult = new SendMessageRequest().execute(Constants.SendMessageURL, seperatedPassedObjects[0], seperatedPassedObjects[1], "sent", sendMessageContent, formatter.format(now.getTime()) ).get();
                    //String receiveRequestResult = new SendMessageRequest().execute(Constants.SendMessageURL, seperatedPassedObjects[1], seperatedPassedObjects[0], "received", messageContent, formatter.format(now.getTime()) ).get();
                    Log.d("result ERR",sendRequestResult);
                    //Log.d("result ERR",receiveRequestResult);
                } catch (Exception ex) { Log.d("SendMessageRequest call", ex.toString()); };
            }
        });

        parent.addView(messageContentText);
        parent.addView(sendBTN);
        return parent;
    }

}