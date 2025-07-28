package com.example.mark.dpitest

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.hardware.display.DisplayManager
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.Display
import android.view.View
import android.view.WindowManager
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import kotlin.math.pow
import kotlin.math.sqrt


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tx = findViewById<View>(R.id.text_px) as TextView
        val ta = findViewById<View>(R.id.text_area) as TextView
        val dimenvalue = findViewById<View>(R.id.textvalue) as TextView

        dimenvalue.text = makeInfo().apply {
            print(this)
        }

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
        sb.append(getScreenPx(getAppDisplay()))
        sb.append(multiDisplayInfo)
        sb.append("isNight:${isNightMode()}")
        sb.append("\nInch:${getScreenInch(this)}")
        sb.append("\nGooglePlayServicesAvailable:${isGmsAvailable(this)}")

        return sb.toString()
    }

    fun getAppDisplay() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        display!!
    } else {
        windowManager.defaultDisplay
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

    fun getScreenInch(activity: Activity): Double {
        // 获取 WindowManager 服务
        val windowManager = activity.getSystemService(WINDOW_SERVICE) as WindowManager
            ?: return 0.0

        // 获取 DisplayMetrics 对象
        val displayMetrics = DisplayMetrics()

        // 获取 Display 对象
        val display = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity.display
        } else {
            windowManager.defaultDisplay
        }

        if (display == null) {
            return 0.0
        }

        // 获取真实的屏幕指标
        display.getRealMetrics(displayMetrics)

        // 获取像素信息
        val widthPixels = displayMetrics.widthPixels
        val heightPixels = displayMetrics.heightPixels

        // 获取物理 DPI 信息
        val xdpi = displayMetrics.xdpi
        val ydpi = displayMetrics.ydpi


        // 检查 DPI 值是否有效
        if (xdpi <= 0 || ydpi <= 0) {
            return 0.0
        }

        // 计算物理宽度和高度（英寸）
        val widthInches = widthPixels.toDouble() / xdpi
        val heightInches = heightPixels.toDouble() / ydpi

        // 计算对角线长度
        val diagonalInches = sqrt(widthInches.pow(2.0) + heightInches.pow(2.0))

        return diagonalInches
    }

}

/**
 * 检查设备上 Google Play Services 是否可用。
 * @return true 如果服务可用。
 */
fun isGmsAvailable(context: Context): Boolean {
    val googleApiAvailability = GoogleApiAvailability.getInstance()
    val resultCode = googleApiAvailability.isGooglePlayServicesAvailable(context)
    // ConnectionResult.SUCCESS 表示服务可用
    return resultCode == ConnectionResult.SUCCESS
}
