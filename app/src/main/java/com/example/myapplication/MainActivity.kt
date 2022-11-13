package com.example.myapplication

import android.content.DialogInterface
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.fantasy.doubledatepicker.DoubleDateSelectDialog
import com.fantasy.doubledatepicker.DoubleDateSelectDialog.OnDateSelectFinished
import com.ycuwq.datepicker.date.DatePickerDialogFragment


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.btn).setOnClickListener {
            val datePickerDialogFragment =  DatePickerDialogFragment();
            datePickerDialogFragment.show(fragmentManager, "DatePickerDialogFragment");
        }
    }
}