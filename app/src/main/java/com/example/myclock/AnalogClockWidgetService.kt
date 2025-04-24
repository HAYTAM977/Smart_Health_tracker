package com.example.myclock

import android.appwidget.AppWidgetManager
import android.content.Context
import android.graphics.*
import android.widget.RemoteViews
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.*
import java.util.concurrent.TimeUnit
import androidx.work.Constraints

object AnalogClockWidgetService {

    fun updateClockWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        val rotationAngle = (System.currentTimeMillis() / 60000) % 360  // Calculate the rotation for the minute hand
        val bitmap = createRotatedClockBitmap(rotationAngle)  // Create a bitmap with the rotated clock

        val views = RemoteViews(context.packageName, R.layout.analog_clock_widget)
        views.setImageViewBitmap(R.id.clockCircle, bitmap)  // Update the clock image in the widget with the new bitmap

        appWidgetManager.updateAppWidget(appWidgetId, views)  // Apply the updated views to the widget
    }

    private fun createRotatedClockBitmap(rotationAngle: Long): Bitmap {
        val width = 200
        val height = 200
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)

        // Draw black circle (clock face)
        paint.color = Color.BLACK
        paint.style = Paint.Style.FILL
        canvas.drawCircle(width / 2f, height / 2f, width / 2f - 10, paint)

        // Draw the red line (minute hand) at the correct angle
        val calendar = Calendar.getInstance()
        val minutes = calendar.get(Calendar.MINUTE)
        val angle = Math.toRadians((minutes * 6 - 90 + rotationAngle) % 360.0).toFloat()

        val centerX = width / 2f
        val centerY = height / 2f
        val handLength = width / 2.5f

        paint.color = Color.RED
        paint.strokeWidth = 5f
        paint.style = Paint.Style.STROKE

        val endX = centerX + handLength * Math.cos(angle.toDouble()).toFloat()
        val endY = centerY + handLength * Math.sin(angle.toDouble()).toFloat()
        canvas.drawLine(centerX, centerY, endX, endY, paint)

        return bitmap  // Return the bitmap with the rotated minute hand
    }

    // Optionally schedule widget updates at a regular interval, e.g., every minute
    fun scheduleWidgetUpdate(context: Context) {
        val workRequest = PeriodicWorkRequestBuilder<WidgetUpdateWorker>(15, TimeUnit.MINUTES)
            .setConstraints(Constraints.Builder().setRequiresBatteryNotLow(true).build())
            .build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "widget_update",
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }
}
