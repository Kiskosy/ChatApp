package chat.chatapp.Login;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import chat.chatapp.Conversation.ChatPage;
import chat.chatapp.Conversation.FriendList;
import chat.chatapp.R;
import chat.chatapp.Register.RegisterAccountRequest;
import chat.chatapp.Register.RegisterPage;
import chat.chatapp.Resources.AccountDetails;
import chat.chatapp.Resources.Constants;
import chat.chatapp.Resources.SaltPassword;
import chat.chatapp.Resources.TokenRequest;

public class LoginPage extends AppCompatActivity {

    public String username = "";
    public String password = "";
    EditText UsernameEditText;
    EditText PasswordEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //set up full screen
        /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        setContentView(R.layout.layout_loginpage);

        editTextvalue("username",R.id.UsernameEditTextL);
        editTextvalue("password",R.id.PasswordEditTextL);

        callLoginButton();
        callRegisterButton();
        //testTCP();

        //LoginAccountSocket test = new LoginAccountSocket();
        //test.execute();
        Log.d("asdasd","dsadsadsa");
        /*LoginAccountSocket myClient = new LoginAccountSocket("94.21.181.64",8080);
        try {
            String temp = myClient.execute().get();
            Log.d("socketTemp",temp);
        } catch (Exception ex) { Log.d("socketEx",ex.toString()); }*/
    }



    void callLoginButton(){
        Button loginButton = (Button) findViewById(R.id.LoginButtonL);
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                try {

                    Log.e("BTN CALL", "LoginButton has been called");
                    String result = new LoginAccountRequest().execute(Constants.LoginAccountURL, username, password).get();
                    result = result.replace("\"","");
                    result = result.replace("\n","");
                    if (result.equals("SUCCESS")) {
                        //GetAccountDetails.....
                        //AccountDetails accountDetails = convertJsonToArrayList(null);
                       // Log.d("accountDetails", accountDetails.toString());
                        Intent intent = new Intent(view.getContext(), FriendList.class);
                        intent.putExtra("accountName",username);
                        startActivity(intent);
                    }
                } catch (Exception ex) { Log.d("LoginRequest call",ex.toString()); }
                //catch (InterruptedException ex) { Log.d("Token call",ex.toString()); }
            }
        });
    }

    void callRegisterButton(){
        Button registerButton = (Button) findViewById(R.id.RegisterButtonL);
        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.e("BTN CALL","RegisterButton has been called");
                Intent intent = new Intent(view.getContext(), RegisterPage.class);
                startActivity(intent);
                //new RegisterAccountRequest().execute(Constants.RegisterAccountURL);
            }
        });
    }

    private void editTextvalue(final String modifiy, int input){

        final EditText edittext = (EditText) findViewById(input);
        edittext.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press

                    if (modifiy.equals("username")) {
                        username = edittext.getText().toString();
                    }
                    if (modifiy.equals("password")) {
                        password = edittext.getText().toString();
                    }

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

    private void setObjectSizes(){
        float[] DPofScreen = (new Constants()).getDisplayMetrics(this);
        Button loginButton = (Button) findViewById(R.id.LoginButtonL);
        Button registerButton = (Button) findViewById(R.id.RegisterButtonL);
        //Button loginButton = (Button) findViewById(R.id.LoginButtonL);

    }

    private AccountDetails convertJsonToArrayList(JSONObject input) {

        AccountDetails accountDetails = new AccountDetails(Integer.valueOf(input.optString("token")),input.optString("accountName"),input.optString("emailAddress"),input.optString("whenCreated"));
        return accountDetails;

    }

    private TextView mTextViewReplyFromServer;
    private EditText mEditTextSendMessage;

}