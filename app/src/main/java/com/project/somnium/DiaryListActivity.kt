package com.project.somnium

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.somnium.databinding.ActivityDiaryListBinding
import com.project.somnium.diaryDb.DataBase
import com.project.somnium.diary_List_Recycler.DiaryListAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DiaryListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityDiaryListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val db = DataBase.getDatabase(this)
        val diaryDao = db.DiaryDataDao()

        binding.DiaryInventory.apply {
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            layoutManager = LinearLayoutManager(context)
        }

        lifecycleScope.launch(Dispatchers.IO) {
            val data = diaryDao.getAllData()

            withContext(Dispatchers.Main) {
                val adapter = DiaryListAdapter(data.toMutableList()){ diary -> withContext(Dispatchers.IO){
                    diaryDao.deleteByTile(diary.title)
                } }
                binding.DiaryInventory.adapter = adapter
            }
        }
    }
}