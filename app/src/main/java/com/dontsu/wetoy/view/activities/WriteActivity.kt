package com.dontsu.wetoy.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.dontsu.wetoy.R
import com.dontsu.wetoy.databinding.ActivityWriteBinding

class WriteActivity : AppCompatActivity() {


    //Data binding
    private lateinit var binding: ActivityWriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@WriteActivity, R.layout.activity_write)
        setSupportActionBar(binding.writeActivityToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //뒤로가기 버튼

        val spinnerItems = resources.getStringArray(R.array.spinner_region_array)//스피너 아이템
        val spinnerAdapter = ArrayAdapter(this@WriteActivity, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        binding.spinnerRegion.adapter = spinnerAdapter //스피너 연결
        binding.spinnerRegion.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(position) {
                    
                }
            }

        }


    }




}