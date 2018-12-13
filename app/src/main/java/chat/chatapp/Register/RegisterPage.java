package chat.chatapp.Register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;

import chat.chatapp.Login.LoginPage;
import chat.chatapp.R;
import chat.chatapp.Resources.Constants;
import chat.chatapp.Register.RegisterAccountRequest;
import chat.chatapp.Resources.TokenRequest;

public class RegisterPage extends Activity {

    public String username = "";
    public String email = "";
    public String password = "";
    public String token = "";
    EditText UsernameEditTextR;
    EditText EmailEditTextR;
    EditText PasswordEditTextR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_registerpage);

        editTextvalue("username",R.id.UsernameEditTextR);
        editTextvalue("email",R.id.EmailEditTextR);
        editTextvalue("password",R.id.PasswordEditTextR);

        Button registerButton = (Button) findViewById(R.id.RegisterButtonR);
        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                try {
                    token = new TokenRequest().execute(Constants.GetTokenURL).get();
                    username = UsernameEditTextR.getText().toString();
                    email = EmailEditTextR.getText().toString();
                    password = PasswordEditTextR.getText().toString();
                    Log.e("BTN CALL", "RegisterButton has been called");
                    String result = new RegisterAccountRequest().execute(Constants.RegisterAccountURL, token, username, email, password).get();
                    result = result.replace("\"","");
                    result = result.replace("\n","");
                    //Log.d("result",Integer.valueOf(result).toString());
                    if(result.equals("200")){
                        Log.e("New Intent","LoginPage intent has been called");
                        Intent intent = new Intent(view.getContext(), LoginPage.class);
                        startActivity(intent);
                    }
                } catch (ExecutionException ex) { Log.e("Register call",ex.toString()); }
                catch (InterruptedException ex) { Log.e("Register call",ex.toString()); }
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
                        UsernameEditTextR = edittext;
                    }

                    if (modifiy.equals("email")) {
                        EmailEditTextR = edittext;
                    }

                    if (modifiy.equals("password")) {
                        PasswordEditTextR = edittext;
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
}
