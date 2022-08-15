package br.com.aj.message.appaiko.util

import android.os.CountDownTimer
import android.os.Handler
import android.os.SystemClock
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.Projection
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker


class MyTimeCuston {
    companion object {
        val time: Long = 60000


        fun animateMarker(
            marker: Marker, toPosition: LatLng,
            hideMarker: Boolean,
            mGoogleMapObject:GoogleMap
        ) {
            val handler = Handler()
            val start = SystemClock.uptimeMillis()
            val proj: Projection = mGoogleMapObject.projection
            val startPoint: android.graphics.Point = proj.toScreenLocation(marker.position)
            val startLatLng: LatLng = proj.fromScreenLocation(startPoint)
            val duration: Long = 500
            val interpolator: Interpolator = LinearInterpolator()
            handler.post(object : Runnable {
                override fun run() {
                    val elapsed = SystemClock.uptimeMillis() - start
                    val t: Float = interpolator.getInterpolation(
                        elapsed.toFloat() /
                                duration
                    )
                    val lng: Double = t * toPosition.longitude + (1 - t) *
                            startLatLng.longitude
                    val lat: Double = t * toPosition.latitude + (1 - t) *
                            startLatLng.latitude
                    marker.position = LatLng(lat, lng)
                    if (t < 1.0) {
                        // Post again 16ms later.
                        handler.postDelayed(this, 16)
                    } else {
                        marker.isVisible = !hideMarker
                    }
                }
            })
        }

    }
     class Timer(miliis:Long,val  render:(Int)->Unit) : CountDownTimer(miliis,1){
        var millisUntilFinished:Long = 0
        override fun onFinish() {
        }
        override fun onTick(millisUntilFinished: Long) {
            this.millisUntilFinished = millisUntilFinished
            val passTime = MyTimeCuston.time - millisUntilFinished
            val sec = passTime / 1000 % 60
            val tic: Float = 100f/60f * sec
             render.invoke( tic.toInt() )
        }
    }
}