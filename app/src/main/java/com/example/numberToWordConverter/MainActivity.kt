package com.example.numberToWordConverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.numberToWordConverter.databinding.ActivityMainBinding

class MainActivity: AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.linearLayout.setOnClickListener {
            binding.userInputEditText.clearFocus()
        }
        binding.convertButton.setOnClickListener {
            getResult()
            binding.userInputEditText.clearFocus()
        }
    }

    private fun getResult() {
        try {
            val input = binding.userInputEditText.text.toString().toInt()
            if (input > 1000 || input < 0) {
                binding.result.text = getText(R.string.warning)
                binding.result.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_light))
            } else {
                val result = numberToWord(input)
                binding.result.text = getString(R.string.result, result)
                binding.result.setTextColor(ContextCompat.getColor(this, R.color.seed))
            }
        } catch (e: Exception) {
            binding.result.text = getText(R.string.warning)
            binding.result.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_light))
        }
    }
}