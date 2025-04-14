package com.project.somnium

import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.somnium.databinding.ActivityMakeImageBinding
import com.project.somnium.makeImgRecycler.Adapter
import com.project.somnium.makeImgRecycler.RecyclerDataModel
import com.project.somnium.retrofit.GptRequest


class MakeImageActivity : AppCompatActivity() {
    private lateinit var inputText: String
    private val linearLayoutManager = LinearLayoutManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMakeImageBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerDataModel = ArrayList<RecyclerDataModel>()
        val recyclerAdapter = Adapter(recyclerDataModel)

        binding.chatRecyclerView.apply {
            addItemDecoration(
                DividerItemDecoration(
                    applicationContext,
                    DividerItemDecoration.VERTICAL
                )
            )
            layoutManager = linearLayoutManager
            adapter = recyclerAdapter
        }

        binding.btnSend.setOnClickListener {
            inputText = binding.etDreamInput.text.toString()

            if (inputText.isNotBlank()) {
                binding.etDreamInput.text.clear()
                Toast.makeText(this,"이미지 생성중입니다.",Toast.LENGTH_SHORT).show()
                val request = GptRequest(inputText, object : GptRequest.ApiCallback  {
                    override fun onDataReceived(data: RecyclerDataModel) {
                        recyclerDataModel.add(data)
                        recyclerAdapter.notifyDataSetChanged()
                    }
                })

                request.gptRequest()
            } else {
                Toast.makeText(this, "꿈 내용을 입력해주세요!", Toast.LENGTH_SHORT).show()
            }

        }


    }

}