package com.project.somnium

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.project.somnium.databinding.ActivityMainBinding
import com.project.somnium.thumbnail_Recycler.ThumbnailAdapter
import com.project.somnium.viewModel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
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
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]

        binding.recyclerThumbnail.apply {
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
            setLayoutManager(layoutManager)
        }

        snapHelper.attachToRecyclerView(binding.recyclerThumbnail)

        observeViewModel()

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

    private fun observeViewModel() {
        viewModel.thumbnailData.observe(this) { data ->
            val adapter = ThumbnailAdapter(data.toList())
            binding.recyclerThumbnail.adapter = adapter
        }
    }
}