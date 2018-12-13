package chat.chatapp.Conversation;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import chat.chatapp.Login.LoginAccountRequest;
import chat.chatapp.R;
import chat.chatapp.Resources.Constants;

public class FriendList extends Activity {

    private String username = "";
    private List<friendDetails> friends = new ArrayList<friendDetails>();

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_friendlist);
        username = getIntent().getStringExtra("accountName");

        new DisconnectUserMessageChat().execute();

        try {
            Log.e("activity call", "New activity has been called");
            ArrayList<JSONObject> result = new FriendListRequest().execute(Constants.FriendListURL, username).get();
            //Log.d("result",result.toString());

            JSONObject friendListJSON = result.get(0);
            JSONObject base64strOfImageJSON = result.get(1);

            String friendListTemp = friendListJSON.get("friendList").toString();
            String[] friendList = friendListTemp.split(",");
            String base64strOfImage = base64strOfImageJSON.get("base64strOfImage").toString();

            //byte byteArray[] = Base64.getMimeDecoder().decode(base64strOfImage);


            TableLayout parentTable = (TableLayout) findViewById(R.id.friendlistTL);
            for (int i = 0; i < friendList.length; i++){
                byte byteArray[] = Base64.getMimeDecoder().decode(base64strOfImage);
                if(i == friendList.length-1){
                    friendList[i] = friendList[i].substring(0, friendList[i].length()-1);
                }
                friends.add(new friendDetails(friendList[i],byteArray));
                parentTable.addView(createFriendObjects(friends.get(i).friendName,friends.get(i).avatar));
            }




        } catch ( InterruptedException ex ) { Log.d("activity call ex", ex.toString()); }
        catch (ExecutionException ex ) { Log.d("activity call ex", ex.toString()); }
        catch (JSONException ex) { Log.d("activity call ex", ex.toString()); }
        //catch (JsonParseException ex) { Log.d("activity call ex", ex.toString()); }
        //catch (JsonMappingException ex) { Log.d("activity call ex", ex.toString()); }
        //catch (IOException ex) { Log.d("activity call ex", ex.toString()); }

        //TableLayout parentTable = (TableLayout) findViewById(R.id.friendlistTL);
        //parentTable.addView(createFriendObjects());

    }

    public static class friendDetails {

        private String friendName;
        private byte avatar[];

        public friendDetails(String friendName, byte avatar[]) {
            this.friendName = friendName;
            this.avatar = avatar;
        }

        public String getFriendName() { return friendName; }

        public void setFriendName(String friendName) { this.friendName = friendName; }

        public byte[] getAvatar() { return avatar; }

        public void setAvatar(byte avatar[]) { this.avatar = avatar; }
    }

    private LinearLayout createFriendObjects(final String friendName, byte byteArray[]) {

        //TableLayout parentTable = (TableLayout) findViewById(R.id.chatpageTL);
        LinearLayout parent = new LinearLayout(this);
        RelativeLayout.LayoutParams parentParams1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        parentParams1.addRule(RelativeLayout.ALIGN_TOP);
        parent.setLayoutParams(parentParams1);
        parent.setHorizontalGravity(LinearLayout.HORIZONTAL);
        parent.setGravity(Gravity.LEFT);

        Button button1 = new Button(this);
        button1.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,80));
        button1.setTextSize(12);
        button1.setTextColor(getResources().getColor(R.color.White));
        button1.setText(friendName);
        button1.setBackgroundResource(R.drawable.rounded_corner);
        button1.setPadding(10,10,10,10);

        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent( view.getContext(), ChatPage.class);
                String toPassObjects = username + ":" + friendName;
                intent.putExtra("toPassObjects", toPassObjects);
                startActivity(intent);
            };
        });
        ImageView avatar = new ImageView(this);
        avatar.setLayoutParams(new RelativeLayout.LayoutParams(80,80));
        //File image = new File("/res/drawable/sunnykicsi");
        /*try {
            //byte[] imageInBytes = Files.readAllBytes(image.toPath());
        } catch (IOException ex) { Log.d("image",ex.toString()); }*/
        //avatar.setBackgroundResource(R.drawable.sunnykicsi);
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        avatar.setImageBitmap(Bitmap.createScaledBitmap(bmp, 80,100,false));
        avatar.setRotation(270);

        parent.addView(avatar);
        parent.addView(button1);
        return parent;
    }
}
