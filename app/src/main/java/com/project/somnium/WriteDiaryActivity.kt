package com.project.somnium

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.project.somnium.databinding.ActivityWriteDiaryBinding
import com.project.somnium.diaryDb.DataBase
import com.project.somnium.diaryDb.DiaryDao
import com.project.somnium.diaryDb.DiaryDataClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

class WriteDiaryActivity : AppCompatActivity() {
    private val date = LocalDate.now()
    private lateinit var title: String
    private lateinit var content: String
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityWriteDiaryBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val db: DataBase = DataBase.getDatabase(this@WriteDiaryActivity)
        val diaryDao: DiaryDao = db.DiaryDataDao()

        val intent2 = Intent(this@WriteDiaryActivity, ReadDiary::class.java)
        val intent = Intent(this@WriteDiaryActivity, MainActivity::class.java)


        val pickImageLauncher = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            uri?.let {
                imageUri = it
                Glide.with(this)
                    .load("${imageUri}")
                    .into(binding.imgSelectedIMG)


                binding.bntDeleteIMG.visibility = View.VISIBLE
            }
        }


        binding.btnAddImage.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        //이미지 삭제
        binding.bntDeleteIMG.setOnClickListener {
            Glide.with(this).clear(binding.imgSelectedIMG)
            binding.imgSelectedIMG.setImageDrawable(null)
            binding.bntDeleteIMG.visibility = View.GONE
            imageUri = null
        }


        //뒤로가기 버튼
        binding.imgBtnBack.setOnClickListener {
            startActivity(intent)
            finish()
        }


        //완료 버튼
        binding.btnComplete.setOnClickListener {
            title = binding.etTitle.text.toString()
            content = binding.etContent.text.toString()

            if (title.isNotBlank() && content.isNotBlank()) {

                saveData(diaryDao, intent2) // 데이터 저장
                imageUri = null

            } else if (title.isBlank() && content.isNotBlank()) {
                Toast.makeText(this, "제목을 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else if (title.isNotBlank() && content.isBlank()) {
                Toast.makeText(this, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else if (title.isBlank() && content.isBlank()) {
                Toast.makeText(this, "제목과 내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }


    //DB 저장
    fun saveData(diaryDao: DiaryDao, intent: Intent) {
        val data = DiaryDataClass(
            date = date.toString(), content = content,
            title = title, imgurl = imageUri.toString()
        )
        lifecycleScope.launch(Dispatchers.IO) {
            diaryDao.insertData(data)

            var test = diaryDao.getAllData()
            Log.d("data Test", "data : $test")
            withContext(Dispatchers.Main) {
                Toast.makeText(this@WriteDiaryActivity, "저장되었습니다.", Toast.LENGTH_SHORT).show()
                startActivity(intent)
                finish()
            }
        }
    }

}