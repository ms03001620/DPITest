package com.example.mark.dpitest

import android.content.res.Configuration
import android.hardware.display.DisplayManager
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.Display
import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tx = findViewById<View>(R.id.text_px) as TextView
        val ta = findViewById<View>(R.id.text_area) as TextView
        val dimenvalue = findViewById<View>(R.id.textvalue) as TextView

        dimenvalue.text = makeInfo()

        val width10 = resources.getDimension(R.dimen.width_10)

        tx.text =
            "10dp = " + width10 + "px" + ",INT:" + (Build.VERSION.SDK_INT) + ",android:" + Build.VERSION.RELEASE

        val seek = findViewById<View>(R.id.seek) as SeekBar
        seek.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                Log.v("MainActivity", "progress:$progress")

                val r = resources
                val px = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    progress.toFloat(),
                    r.displayMetrics
                )

                val params = ta.layoutParams
                ta.text = progress.toString() + "dp"
                params.height = px.toInt()
                params.width = px.toInt()
                ta.layoutParams = params
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })

        //getMultiDisplayInfo();
    }

    fun makeInfo(): String{
        val sb = StringBuilder()

        sb.append("count X:${resources.getInteger(R.integer.main_chat_placeholder_critical)}")
        sb.append("${getScreenPx(windowManager.defaultDisplay)}")
        sb.append("${multiDisplayInfo}")
        sb.append("isNight:${isNightMode()}")

        return sb.toString()
    }

    fun getScreenPx(display: Display): String {
        val displayMetrics = DisplayMetrics()
        display.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels
        return displayMetrics.toString()
    }

    val multiDisplayInfo: String
        get() {
            var result = ""
            val manager =
                getSystemService(DISPLAY_SERVICE) as DisplayManager
            for (display in manager.displays) {
                result += getScreenPx(display) + "\n"
            }
            return result
        }


    private fun isNightMode(): Boolean {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }
}
