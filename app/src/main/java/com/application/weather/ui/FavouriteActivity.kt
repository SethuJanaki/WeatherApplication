package com.application.weather.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.application.weather.R
import com.application.weather.databinding.FavoriteActivityBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavouriteActivity : AppCompatActivity() {

    private lateinit var binding: FavoriteActivityBinding
    private val viewModel by viewModel<FavoriteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FavoriteActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        viewModel.getFavorite()

        viewModel.weatherLiveData.observe(this) { data ->
            if (data?.isEmpty() == true) {
                binding.emptyLayout.visibility = View.VISIBLE
            } else {
                binding.emptyLayout.visibility = View.GONE
            }
            binding.back.setOnClickListener {
                finish()
            }
            binding.remove.setOnClickListener {
                getDialog()
            }
            if (data?.isNotEmpty() == true) {
                binding.favoriteText.text = getString(R.string.added_as_favorite, data.size)
            }
            binding.recyclerview.adapter = FavoriteAdapter().apply {
                itemsList = data ?: emptyList()
                clickListener = { item ->
                    item.name.let {
                       viewModel.deleteFavorite(it)
                    }
                }
            }
        }
    }

    private fun getDialog() {
        val dialog = AlertDialog.Builder(this).create()
        dialog.setTitle("Are you sure want to remove all the favourites?")
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "YES") { _, _ ->
            viewModel.deleteFavorites()
        }
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "NO") { _, _ ->
            dialog.dismiss()
        }
        dialog.show()
    }
}



