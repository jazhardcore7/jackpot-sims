package com.submission.myapplication

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.submission.myapplication.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var currentNumber = 0
    private var currentProbability = 0.01
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateUI()

        binding.incrementButton.setOnClickListener {
            currentNumber++
            checkJackpot()
            updateUI()
        }

        binding.restartButton.setOnClickListener {
            restartGame()
        }

    }

    private fun checkJackpot() {
        if (currentNumber > 10 && currentNumber % 2 == 1) {
            val random = Random.nextDouble()
            if (random <= currentProbability) {
                showJackpotDialog()
                binding.incrementButton.isEnabled = false
                binding.restartButton.visibility = View.VISIBLE
            } else {
                currentProbability = minOf(currentProbability + 0.01, 0.05)
            }
        }
    }

    private fun updateUI() {
        binding.numberText.text = getString(R.string.nomor, currentNumber)
        binding.oddEvenText.text = if (currentNumber % 2 == 0) "Genap" else "Ganjil"
        binding.probabilityText.text = "Probabilitas Jackpot: ${String.format("%.2f", currentProbability * 100)}%"
    }

    private fun showJackpotDialog() {
        AlertDialog.Builder(this)
            .setTitle("Jackpot!")
            .setMessage("Selamat! Anda 'Jackpot' pada angka $currentNumber")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun restartGame() {
        currentNumber = 0
        currentProbability = 0.01
        binding.incrementButton.isEnabled = true
        binding.restartButton.visibility = View.GONE
        updateUI()
    }
}