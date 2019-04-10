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
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                dimTextView.text = " ${i + 4}"
                scacchiera.dim = i + 4
                scacchiera.isPlaying = false
                scacchiera.regenerateMatrix()
                scacchiera.invalidate()
            }
        })

        scacchiera.setOnVictoryListener {
            winText.text = " $it"
        }

        scacchiera.setOnMoveListener {
            numClickText.text = " $it"
        }

        playButton.setOnClickListener {
            scacchiera.isPlaying = true
        }
    }
}
