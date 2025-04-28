package com.project.somnium

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.project.somnium.databinding.ActivityMainBinding
import com.project.somnium.diaryDb.DataBase
import com.project.somnium.diary_List_Recycler.DiaryListAdapter
import com.project.somnium.thumbnail_Recycler.ThumbnailAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val goToMakeImageIntent = Intent(this@MainActivity, MakeImageActivity::class.java)
        val goToWriteDiaryIntent = Intent(this@MainActivity, WriteDiaryActivity::class.java)
        val goToReadDiaryIntent = Intent(this@MainActivity, DiaryListActivity::class.java)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val snapHelper = LinearSnapHelper()

        val db = DataBase.getDatabase(this)
        val diaryDao = db.DiaryDataDao()

        binding.recyclerThumbnail.apply {
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
            setLayoutManager(layoutManager)
        }

        snapHelper.attachToRecyclerView(binding.recyclerThumbnail)

        lifecycleScope.launch(Dispatchers.IO) {
            val data = diaryDao.getDataDesc()
            withContext(Dispatchers.Main) {
                val adapter = ThumbnailAdapter(data.toMutableList())
                binding.recyclerThumbnail.adapter = adapter
            }
        }

        binding.btnMakeImg.setOnClickListener {
            startActivity(goToMakeImageIntent)
        }

        binding.btnWriteDiary.setOnClickListener {
            startActivity(goToWriteDiaryIntent)
        }

        binding.btnReadDiary.setOnClickListener {
            startActivity(goToReadDiaryIntent)
        }


    }
}