package com.example.periodicalarmmanager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.periodicalarmmanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var alarmManager: AlarmManager
    private lateinit var intent: Intent
    private lateinit var repeatAlarmIntent: PendingIntent
    private val ALARM_TYPE = "ALARM_TYPE"
    private val ALARM_TYPE_REPEAT = "ALARM_TYPE_REPEAT"
    private val ALARM_DESCRIPTION = "ALARM_DESCRIPTION"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        intent = Intent(this, MyReceiver::class.java)

        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        repeatAlarmIntent = PendingIntent.getBroadcast(
            this, 200, intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        binding.btnStart.setOnClickListener {
            intent.putExtra(ALARM_TYPE, ALARM_TYPE_REPEAT)
            intent.putExtra(ALARM_DESCRIPTION, "Repeat alarm")

            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis(), 60000,
                repeatAlarmIntent
            )

            Toast.makeText(this, "Alarm set.", Toast.LENGTH_SHORT).show()
        }

        binding.btnStop.setOnClickListener {
            alarmManager.cancel(repeatAlarmIntent)
            Toast.makeText(this, "Alarm cancelled.", Toast.LENGTH_SHORT).show()
        }
    }
}