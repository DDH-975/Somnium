package com.project.somnium.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.somnium.R
import com.project.somnium.databinding.ActivityDiaryListBinding
import com.project.somnium.ui.adapter.DiaryListAdapter
import com.project.somnium.ui.viewModel.DiaryListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiaryListActivity : AppCompatActivity() {
    private val viewModel: DiaryListViewModel by viewModels()
    private lateinit var binding: ActivityDiaryListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDiaryListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.DiaryInventory.apply {
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            layoutManager = LinearLayoutManager(context)
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.allData.observe(this) { data ->
            val adapter = DiaryListAdapter(data.toMutableList()) { diary ->
                viewModel.deleteByTile(diary.title)
            }
            binding.DiaryInventory.adapter = adapter
        }
    }
}
