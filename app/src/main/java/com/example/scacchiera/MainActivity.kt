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
                scacchiera.isPlaying = false
                scacchiera.regenerateMatrix()
                scacchiera.invalidate()
            }
        })

        scacchiera.setOnVictoryListener {
            winText.text = String.format(" %d", it)
        }

        scacchiera.setOnMoveListener {
            numClickText.text = String.format(" %d", it)
        }

        playButton.setOnClickListener {
            if (scacchiera.isPlaying) {
                scacchiera.regenerateMatrix()
            } else {
                scacchiera.isPlaying = true
            }
        }
    }
}
