package com.project.somnium

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.project.somnium.databinding.ActivityMainBinding

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

        val makeImage = Intent(this@MainActivity, MakeImageActivity::class.java)
        val writeDiary = Intent(this@MainActivity, WriteDiaryActivity::class.java)
        val readDiary = Intent(this@MainActivity, ReadDiary::class.java)

        binding.btnMakeImg.setOnClickListener {
            startActivity(makeImage)
        }

        binding.btnWriteDiary.setOnClickListener {
            startActivity(writeDiary)
        }

        binding.btnReadDiary.setOnClickListener {
            startActivity(readDiary)
        }


    }
}