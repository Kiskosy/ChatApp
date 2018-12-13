package chat.chatapp.Conversation;

import android.graphics.Bitmap;

import java.util.Date;

public class Message {

    String message;
    Bitmap avatar;
    Date time;
    boolean AccountIsMine;

    public String getMessage() {
        return message;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public Date getTime() {
        return time;
    }

    public boolean isAccountIsMine() {
        return AccountIsMine;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setAccountIsMine(boolean accountIsMine) {
        AccountIsMine = accountIsMine;
    }
}
