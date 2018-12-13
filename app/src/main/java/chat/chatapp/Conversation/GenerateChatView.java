package chat.chatapp.Conversation;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import chat.chatapp.R;

public class GenerateChatView extends LinearLayout {

    public GenerateChatView(Context context) {
        super(context);
    }

    public LinearLayout GenerateChatView(Context context) {
        //super(context);

        /*ScrollView scView = new ScrollView(context);
        scView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT));
*/
        LinearLayout lnLayout = new LinearLayout(context);
        LinearLayout.LayoutParams lnParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        lnParams.setMargins(0, Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,10,context.getResources().getDisplayMetrics())),0,0);
        lnParams.weight = 1;

        TextView textView = new TextView(context);
        textView.setBackgroundResource(R.drawable.rounded_corner);
        textView.setPadding(Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,16,context.getResources().getDisplayMetrics())),Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,16,context.getResources().getDisplayMetrics())),Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,16,context.getResources().getDisplayMetrics())),Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,16,context.getResources().getDisplayMetrics())));
        textView.setText("test message 1");
        textView.setTextColor(Color.WHITE);
        lnLayout.addView(textView,lnParams);

        //scView.addView(lnLayout);
        return lnLayout;
    }
}


/*


* <ScrollView
        android:layout_width="match_parent"
        android:layout_weight="20"
        android:layout_height="wrap_content">

        <RelativeLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content">

            <LinearLayout
                android:id="@+id/layout1"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:orientation="horizontal">

                <TextView
                    android:id="@+id/textView"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_marginTop="10dp"
                    android:layout_weight="6"
                    android:background="@drawable/rounded_corner"
                    android:padding="16dp"
                    android:text="Test message1"
                    android:textColor="#000" />

                <ImageView
                    android:id="@+id/imageView2"
                    android:layout_width="40dp"
                    android:layout_height="40dp"
                    android:layout_marginTop="0dp"
                    android:layout_weight="3"
                    android:src="@drawable/user_pacific" />

            </LinearLayout>

            <LinearLayout
                android:id="@+id/layout2"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_below="@+id/layout1"
                android:layout_marginTop="30dp"
                android:orientation="horizontal">

                <ImageView
                    android:id="@+id/imageView3"
                    android:layout_width="40dp"
                    android:layout_height="40dp"
                    android:layout_marginTop="0dp"
                    android:layout_weight="3"
                    android:src="@drawable/user_pratikshya" />

                <ImageView
                    android:id="@+id/imageView1"
                    android:layout_width="30dp"
                    android:layout_height="30dp"
                    android:layout_marginRight="-15dp"
                    android:layout_marginTop="6.5dp"
                    android:layout_weight="1"
                    android:src="@drawable/arrow_bg2" />

                <TextView
                    android:id="@+id/textView1"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_marginTop="10dp"
                    android:layout_weight="6"
                    android:background="@drawable/rounded_corner1"
                    android:padding="16dp"
                    android:text="Test message2"
                    android:textColor="#000" />

            </LinearLayout>

            <LinearLayout
                android:id="@+id/layout3"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_below="@+id/layout2"
                android:layout_marginTop="20dp"
                android:orientation="horizontal">

                <TextView
                    android:id="@+id/textView3"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_marginTop="10dp"
                    android:layout_weight="6"
                    android:background="@drawable/rounded_corner"
                    android:padding="16dp"
                    android:text="Test message3"
                    android:textColor="#000" />

                <ImageView
                    android:id="@+id/imageView5"
                    android:layout_width="40dp"
                    android:layout_height="40dp"
                    android:layout_marginTop="0dp"
                    android:layout_weight="3"
                    android:src="@drawable/user_pacific" />

            </LinearLayout>

            <LinearLayout
                android:id="@+id/layout4"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_below="@+id/layout3"
                android:layout_marginTop="30dp"
                android:orientation="horizontal">

                <ImageView
                    android:id="@+id/imageView6"
                    android:layout_width="40dp"
                    android:layout_height="40dp"
                    android:layout_marginTop="0dp"
                    android:layout_weight="3"
                    android:src="@drawable/user_pratikshya" />

                <TextView
                    android:id="@+id/textView5"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_marginTop="10dp"
                    android:layout_weight="6"
                    android:background="@drawable/rounded_corner1"
                    android:padding="16dp"
                    android:text="Test message4"
                    android:textColor="#000" />
            </LinearLayout>
        </RelativeLayout>
    </ScrollView>
*
* */