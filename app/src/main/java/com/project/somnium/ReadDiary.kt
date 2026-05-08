package com.project.somnium

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.project.somnium.databinding.ActivityReadDiaryBinding
import com.project.somnium.viewModel.ReadDiaryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReadDiary : AppCompatActivity() {
    private lateinit var binding: ActivityReadDiaryBinding
    private val viewModel: ReadDiaryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadDiaryBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val requestLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            val updateId = it.data?.getIntExtra("id", -1) ?: -1

            observeViewModel(updateId)

        }

        val goToWriteIntent = Intent(this@ReadDiary, WriteDiaryActivity::class.java)

        val id = intent.getIntExtra("id", -1)

        observeViewModel(id)


        //수정 버튼
        binding.btnEdit.setOnClickListener {
            goToWriteIntent.putExtra("mode", "수정")
            goToWriteIntent.putExtra("id", id)
            requestLauncher.launch(goToWriteIntent)
        }
    }

    private fun observeViewModel(id: Int) {
        viewModel.selectById(id).observe(this) { data ->
            if (data.imgurl == "null") {
                listOf(binding.diaryImage, binding.tvDate).forEach { it.visibility = View.GONE }
                binding.tvDateNoImg.visibility = View.VISIBLE

                binding.tvDateNoImg.text = "${data.date}"
                binding.tvTitle.text = "${data.title}"
                binding.diaryContent.text = "${data.content}"

            } else {
                listOf(binding.diaryImage, binding.tvDate).forEach { it.visibility = View.VISIBLE }
                binding.tvDateNoImg.visibility = View.GONE

                binding.tvTitle.text = "${data.title}"
                binding.diaryContent.text = "${data.content}"
                binding.tvDate.text = "${data.date}"
                Glide.with(this)
                    .load("${data.imgurl}")
                    .into(binding.diaryImage)
            }
        }
    }
}