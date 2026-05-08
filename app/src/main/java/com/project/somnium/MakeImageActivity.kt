package com.project.somnium

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.somnium.databinding.ActivityMakeImageBinding
import com.project.somnium.makeImg_Recycler.Adapter
import com.project.somnium.makeImg_Recycler.RecyclerDataModel
import com.project.somnium.viewModel.MakeImageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MakeImageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMakeImageBinding
    private lateinit var recyclerAdapter: Adapter
    private val recyclerDataModel = ArrayList<RecyclerDataModel>()
    private lateinit var linearLayoutManager: LinearLayoutManager

    private val viewModel: MakeImageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMakeImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        linearLayoutManager = LinearLayoutManager(this)

        binding.chatRecyclerView.apply {
            addItemDecoration(
                DividerItemDecoration(
                    applicationContext,
                    DividerItemDecoration.VERTICAL
                )
            )
            layoutManager = linearLayoutManager
            recyclerAdapter = Adapter(recyclerDataModel)
            adapter = recyclerAdapter
        }

        observeViewModel()

        binding.btnSend.setOnClickListener {
            val inputText = binding.etDreamInput.text.toString()

            if (inputText.isNotBlank()) {
                binding.etDreamInput.text.clear()
                Toast.makeText(this, "이미지 생성중입니다.", Toast.LENGTH_SHORT).show()
                viewModel.generateImage(inputText)
            } else {
                Toast.makeText(this, "꿈 내용을 입력해주세요!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeViewModel() {
        viewModel.imageData.observe(this) { data ->
            recyclerDataModel.add(data)
            recyclerAdapter.notifyDataSetChanged()
        }

        viewModel.errorMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}