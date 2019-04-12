package com.example.scacchiera

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dimSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                dimTextView.text = String.format(" %d", i + 4)
                scacchiera.dim = i + 4
                resetScacchiera()
            }
        })

        scacchiera.setOnVictoryListener {
            winTextView.text = String.format(" %d", it)
        }

        scacchiera.setOnMoveListener {
            numClickTextView.text = String.format(" %d", it)
        }

        playButton.setOnClickListener {
            if (scacchiera.isPlaying) {
                scacchiera.regenerateMatrix()
            } else {
                scacchiera.isPlaying = true
            }
        }

        difficultyTextView.setOnClickListener {
            when (scacchiera.difficulty) {
                ScacchieraView.Difficulty.EASY -> {
                    scacchiera.difficulty = ScacchieraView.Difficulty.MEDIUM
                    difficultyTextView.text = getString(R.string.medium)
                }
                ScacchieraView.Difficulty.MEDIUM -> {
                    scacchiera.difficulty = ScacchieraView.Difficulty.HARD
                    difficultyTextView.text = getString(R.string.hard)
                }
                ScacchieraView.Difficulty.HARD -> {
                    scacchiera.difficulty = ScacchieraView.Difficulty.EASY
                    difficultyTextView.text = getString(R.string.easy)
                }
            }

            resetScacchiera()
        }
    }

    fun resetScacchiera() {
        scacchiera.isPlaying = false
        scacchiera.regenerateMatrix()
        scacchiera.invalidate()
    }
}
