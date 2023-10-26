package com.example.homework2_tbc_it_academy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.homework2_tbc_it_academy.databinding.ActivityMainBinding
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {
    companion object {
        private const val RESULT_TEXT_KEY = "resultText"
        private const val RESULT_TEXT_COLOR_KEY = "resultTextColor"
    }

    private lateinit var binding: ActivityMainBinding

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(RESULT_TEXT_KEY, binding.result.text.toString())
        outState.putInt(RESULT_TEXT_COLOR_KEY, binding.result.currentTextColor)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState != null) {
            val savedResultText = savedInstanceState.getString(RESULT_TEXT_KEY)
            val savedResultTextColor = savedInstanceState.getInt(RESULT_TEXT_COLOR_KEY)
            binding.result.text = savedResultText
            binding.result.setTextColor(savedResultTextColor)
        }

        binding.linearLayout.setOnClickListener { binding.userInputEditText.clearFocus() }
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
        } catch (e: NumberFormatException) {
            binding.result.text = getText(R.string.warning)
            binding.result.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_light))
        }
    }
}