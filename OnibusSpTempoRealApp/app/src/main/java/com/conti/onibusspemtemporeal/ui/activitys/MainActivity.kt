package com.conti.onibusspemtemporeal.ui.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.conti.onibusspemtemporeal.R
import com.conti.onibusspemtemporeal.databinding.ActivityMainBinding
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setupFloatingSearchButton()
    }

    private fun setupFloatingSearchButton() {

        binding.floatingSearchBus.setOnClickListener {
            val searchVisibility = findViewById<TextInputLayout>(R.id.material_layout)
            val linearLayoutLines = findViewById<CardView>(R.id.card_current_line)

            if(searchVisibility.isVisible){
                searchVisibility.isInvisible = true
                linearLayoutLines.visibility = View.VISIBLE
            }else{
                searchVisibility.isVisible = true
                linearLayoutLines.visibility = View.INVISIBLE
            }
        }
    }
}