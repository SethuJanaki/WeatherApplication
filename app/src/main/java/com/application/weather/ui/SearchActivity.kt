package com.application.weather.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.application.weather.R
import com.application.weather.databinding.SearchActivityBinding

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: SearchActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = SearchActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.recyclerview.adapter = SearchAdapter().apply {
            itemsList = resources.getStringArray(R.array.cities).toList()
            clickListener = { item ->
                intent.putExtra("City", item)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
        binding.clear.setOnClickListener {
           binding.text.setText("")
        }
        binding.back.setOnClickListener {
            finish()
        }
        binding.text.doAfterTextChanged { text ->
            binding.recyclerview.apply {
                (adapter as SearchAdapter).itemsList =
                    resources.getStringArray(R.array.cities).toList().filter {
                        it.startsWith(text.toString(), true)
                    }
            }
        }

    }
}