package chat.chatapp.Resources;

import android.content.Context;
import android.util.DisplayMetrics;

public class Constants {


    //188.143.29.245
    public static String DefaultURL = "http://kosylocalpc.theworkpc.com:8080";
    public static String LoginAccountURL = "http://kosylocalpc.theworkpc.com:8080/LoginAccount";
    public static String RegisterAccountURL = "http://kosylocalpc.theworkpc.com:8080/RegisterAccount";
    public static String GetTokenURL = "http://kosylocalpc.theworkpc.com:8080/GetToken";
    public static String SendMessageURL = "http://kosylocalpc.theworkpc.com:8080/SendMessages";
    public static String GetMessageURL = "http://kosylocalpc.theworkpc.com:8080/GetMessages";
    public static String FriendListURL = "http://kosylocalpc.theworkpc.com:8080/GetFriendList";

    public float[] getDisplayMetrics(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        float[] result = new float[2];
        result[0] = dpHeight;
        result[1] = dpWidth;
        return result;
    }

}
