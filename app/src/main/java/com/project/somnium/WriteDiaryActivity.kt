package com.project.somnium

import android.app.Activity
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
    private lateinit var binding: ActivityWriteDiaryBinding

    private lateinit var goToListIntent : Intent
    private lateinit var goToMainIntent : Intent
    private lateinit var returnToReadIntent : Intent

    private val pickImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                imageUri = data?.data
                imageUri?.let {

                    contentResolver.takePersistableUriPermission(
                        it,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )

                    // 이미지 로드
                    Glide.with(this)
                        .load(it)
                        .into(binding.imgSelectedIMG)
                    binding.bntDeleteIMG.visibility = View.VISIBLE
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteDiaryBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        goToListIntent = Intent(this@WriteDiaryActivity, DiaryListActivity::class.java)
        goToMainIntent = Intent(this@WriteDiaryActivity, MainActivity::class.java)
        returnToReadIntent = Intent(this@WriteDiaryActivity, ReadDiary::class.java)


        val db: DataBase = DataBase.getDatabase(this@WriteDiaryActivity)
        val diaryDao: DiaryDao = db.DiaryDataDao()

        val mode = intent.getStringExtra("mode")
        val id = intent.getIntExtra("id", -1)

        if (mode == "수정") {
            editedRequest(diaryDao, id)
        }


        val imgSelectIntent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
        }


        binding.btnAddImage.setOnClickListener {
            pickImage.launch(imgSelectIntent)
        }

        // 이미지 삭제
        binding.bntDeleteIMG.setOnClickListener {
            Glide.with(this).clear(binding.imgSelectedIMG)
            binding.imgSelectedIMG.setImageDrawable(null)
            binding.bntDeleteIMG.visibility = View.GONE
            imageUri = null
        }

        // 뒤로가기 버튼
        binding.imgBtnBack.setOnClickListener {
            startActivity(goToMainIntent)
            finish()
        }

        // 완료 버튼
        binding.btnComplete.setOnClickListener {
            clickBtnComplete(diaryDao, mode, id)
        }
    }


    //완료 버튼 클릭시 실행될 메서드
    fun clickBtnComplete(diaryDao: DiaryDao, mode: String?, id: Int) {
        title = binding.etTitle.text.toString()
        content = binding.etContent.text.toString()

        if (title.isNotBlank() && content.isNotBlank()) {
            saveData(diaryDao, mode, id) // 데이터 저장
        } else if (title.isBlank() && content.isNotBlank()) {
            Toast.makeText(this, "제목을 입력해주세요.", Toast.LENGTH_SHORT).show()
        } else if (title.isNotBlank() && content.isBlank()) {
            Toast.makeText(this, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
        } else if (title.isBlank() && content.isBlank()) {
            Toast.makeText(this, "제목과 내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
    }

    // DB 저장
    fun saveData(diaryDao: DiaryDao, mode: String?, id: Int) {
        if (mode.isNullOrBlank()) {
            val data = DiaryDataClass(
                date = date.toString(),
                content = content,
                title = title,
                imgurl = imageUri?.toString() ?: "null" // imageUri가 null일 경우 빈 문자열로 처리
            )
            lifecycleScope.launch(Dispatchers.IO) {
                diaryDao.insertData(data)

                var test = diaryDao.getAllData()
                Log.d("data Test", "data : $test")
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@WriteDiaryActivity, "저장되었습니다.", Toast.LENGTH_SHORT).show()
                    startActivity(goToListIntent)
                    finish()
                }
            }
        } else {
            lifecycleScope.launch(Dispatchers.IO) {
                diaryDao.updateByID(id = id, title, content, imageUri?.toString() ?: "null")
                var test = diaryDao.getAllData()
                Log.d("data Test", "data : $test")

                withContext(Dispatchers.Main) {
                    Toast.makeText(this@WriteDiaryActivity, "수정되었습니다.", Toast.LENGTH_SHORT).show()
                    returnToReadIntent.putExtra("id", id)
                    setResult(Activity.RESULT_OK, returnToReadIntent)
                    finish()
                }
            }
        }
    }


    //수정 요청 시 ID로 선택된 일기의 데이터를 UI에 반영
    fun editedRequest(diaryDao: DiaryDao, id: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            val data = diaryDao.selectByID(id)

            withContext(Dispatchers.Main) {
                binding.tvMode.setText("일기 수정")
                binding.etTitle.setText("${data.title}")
                binding.etContent.setText("${data.content}")

                Glide.with(binding.root)
                    .load("${data.imgurl}")
                    .into(binding.imgSelectedIMG)

                binding.bntDeleteIMG.visibility = View.VISIBLE
            }
        }
    }


}

