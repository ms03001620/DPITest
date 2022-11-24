package com.example.mark.dpitest;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
        dimenvalue.setText("count X:"+getResources().getInteger(R.integer.main_chat_placeholder_critical));

        float width10 = getResources().getDimension(R.dimen.width_10);

        tx.setText("10dp = "+width10+"px");

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


    }
}
