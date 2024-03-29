package com.example.mark.dpitest;

import android.content.Context;
import android.content.res.Resources;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tx = (TextView)findViewById(R.id.text_px);
        final TextView ta = (TextView)findViewById(R.id.text_area);
        TextView dimenvalue = (TextView)findViewById(R.id.textvalue);
        //好山好水好寂寞。真脏真乱真快活
        dimenvalue.setText("count X:" + getResources().getInteger(R.integer.main_chat_placeholder_critical)
                + "\n" + getScreenPx(getWindowManager().getDefaultDisplay())
                + "\n" + getMultiDisplayInfo()
        );

        float width10 = getResources().getDimension(R.dimen.width_10);

        tx.setText("10dp = "+width10+"px"+",INT:"+(Build.VERSION.SDK_INT)+",android:"+Build.VERSION.RELEASE);

        SeekBar seek = (SeekBar)findViewById(R.id.seek);
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.v("MainActivity", "progress:"+progress);

                Resources r = getResources();
                float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, progress, r.getDisplayMetrics());

                ViewGroup.LayoutParams params = ta.getLayoutParams();
                ta.setText(progress+"dp");
                params.height = (int)px;
                params.width = (int)px;
                ta.setLayoutParams(params);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //getMultiDisplayInfo();
    }

    public String getScreenPx(Display display) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        return displayMetrics.toString();
    }

    public String getMultiDisplayInfo() {
        String result = "";
        DisplayManager manager = (DisplayManager) getSystemService(Context.DISPLAY_SERVICE);
        for (Display display : manager.getDisplays()) {
            result += getScreenPx(display)+"\n";
        }
        return result;
    }
}
