package com.project.somnium

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.project.somnium.databinding.ActivityReadDiaryBinding
import com.project.somnium.diaryDb.DataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReadDiary : AppCompatActivity() {
    private lateinit var goToWriteIntent : Intent
    private lateinit var binding : ActivityReadDiaryBinding

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
            val id = intent.getIntExtra("id", -1)

            lifecycleScope.launch(Dispatchers.IO){
                val db = DataBase.getDatabase(this@ReadDiary)
                val diaryDao = db.DiaryDataDao()
                val data = diaryDao.selectByID(id)

                withContext(Dispatchers.Main){
                    setWidget(data.title, data.content, data.imgurl, data.date)
                }
            }

        }

        goToWriteIntent = Intent(this@ReadDiary, WriteDiaryActivity::class.java)

        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")
        val imgUrl = intent.getStringExtra("imgUrl")
        val date = intent.getStringExtra("date")
        val id = intent.getIntExtra("id", -1)



        setWidget(title, content, imgUrl, date)


        binding.btnEdit.setOnClickListener {
            goToWriteIntent.putExtra("mode", "수정")
            goToWriteIntent.putExtra("id", id)
            requestLauncher.launch(goToWriteIntent)
        }

    }

    //위젯 설정
    fun setWidget(title: String?, content: String?, imgUrl: String?, date: String?){
        if (imgUrl == "null") {
            listOf(binding.diaryImage, binding.tvDate).forEach { it.visibility = View.GONE }
            binding.tvDateNoImg.visibility = View.VISIBLE

            binding.tvDateNoImg.text = "${date}"
            binding.tvTitle.text = "${title}"
            binding.diaryContent.text = "${content}"

        } else {
            listOf(binding.diaryImage, binding.tvDate).forEach { it.visibility = View.VISIBLE }
            binding.tvDateNoImg.visibility = View.GONE

            binding.tvTitle.text = "${title}"
            binding.diaryContent.text = "${content}"
            binding.tvDate.text = "${date}"
            Glide.with(this)
                .load("${imgUrl}")
                .into(binding.diaryImage)
        }
    }
}