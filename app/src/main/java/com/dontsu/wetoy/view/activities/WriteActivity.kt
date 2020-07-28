package com.dontsu.wetoy.view.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.dontsu.wetoy.R
import com.dontsu.wetoy.databinding.ActivityWriteBinding
import com.dontsu.wetoy.util.CustomWriteAddKeywordDialog
import com.dontsu.wetoy.util.CustomWriteDialogInterface
import com.dontsu.wetoy.util.REQUEST_CODE_PHOTO
import com.dontsu.wetoy.viewmodel.WriteViewModel
import kotlinx.android.synthetic.main.activity_write.*

class WriteActivity : AppCompatActivity(), CustomWriteDialogInterface {


    //ViewModel
    lateinit var viewModel: WriteViewModel

    //Data binding
    private lateinit var binding: ActivityWriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@WriteActivity, R.layout.activity_write)
        setSupportActionBar(binding.writeActivityToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //뒤로가기 버튼

        viewModel = ViewModelProvider(this@WriteActivity).get(WriteViewModel::class.java)
        viewModel?.let {
            binding.lifecycleOwner = this@WriteActivity
            binding.viewModel = viewModel//viewmodel 과 연결 시켜주기
        }

        val spinnerItems = resources.getStringArray(R.array.spinner_region_array)//스피너 아이템
        val spinnerAdapter = ArrayAdapter(this@WriteActivity, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        binding.spinnerRegion.adapter = spinnerAdapter //스피너 연결
        binding.spinnerRegion.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Log.d("WriteActivity", "스피너 선택 지역 ${binding.spinnerRegion.adapter.getItem(position)}")
                viewModel.toyRegion.value = binding.spinnerRegion.adapter.getItem(position).toString()
            }
        }

        //장난감 이미지
        write_photo.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_CODE_PHOTO)
        }

        //키워드 갯수 클릭
        write_keyword_count.setOnClickListener {
            //갯수 클릭하면 키워드 리스트 보여주기
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_PHOTO) {
            viewModel.toyImageUri.value = data?.data.toString()
        }
    }

    //키워드 추가
    fun onAddKeyword(v: View) {
        viewModel.requestAddKeyword(this@WriteActivity)
    }
    
    //키워드 입력 
    override fun onAddKeywordOkayClicked(customDialog: CustomWriteAddKeywordDialog, keyword: String) {
        Log.d("WriteActivity", "onAddKeywordOkayClicked() 실행")
        viewModel.addKeywordArray(keyword)
        customDialog.cancel()
    }
    
    //키워드 입력 취소
    override fun onAddKeywordCancelClicked(customDialog: CustomWriteAddKeywordDialog) {
        Log.d("WriteActivity", "onAddKeywordCancelClicked() 실행")
        customDialog.cancel()
    }

    //작성 글 파이어베이스 업로드
    fun onUpload(v: View) {
        var proceed = true
        if (binding.writeTitle.text.isNullOrEmpty()) {
            Toast.makeText(this, "제목을 입력해 주세요.", Toast.LENGTH_SHORT).show()
            proceed = false
        }

        if (binding.writeContent.text.isNullOrEmpty()) {
            Toast.makeText(this, "내용을 입력해 주세요.", Toast.LENGTH_SHORT).show()
            proceed = false
        }

        if (viewModel.toyKeywords.value?.size == 0) {
            Toast.makeText(this, "최소 하나의 키워드를 추가해 주세요.", Toast.LENGTH_SHORT).show()
            proceed = false
        }

        if (viewModel.toyImageUri.value?.toString().isNullOrEmpty()) {
            Toast.makeText(this, "장난감 이미지를 추가해 주세요.", Toast.LENGTH_SHORT).show()
            proceed = false
        }

        if (proceed) {
            //성공 데이터 파이어베이스에 저장하기
        }



    }


}