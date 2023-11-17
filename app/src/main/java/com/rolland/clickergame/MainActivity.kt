package com.rolland.clickergame

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {

    private lateinit var layout: ConstraintLayout
    private lateinit var firstUpgradeBtn: Button
    private lateinit var secondUpgradeBtn: Button
    private lateinit var thirdUpgradeBtn: Button
    private lateinit var fourthUpgradeBtn: Button
    private lateinit var fifthUpgradeBtn: Button
    private lateinit var testing_button: Button
    private lateinit var number: TextView

    private var click_multiplier = 1
    private var auto_clicker = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        layout = findViewById(R.id.parent_view)
        firstUpgradeBtn = findViewById(R.id.first_upgrade)
        secondUpgradeBtn = findViewById(R.id.second_upgrade)
        thirdUpgradeBtn = findViewById(R.id.third_upgrade)
        fourthUpgradeBtn = findViewById(R.id.fourth_upgrade)
        fifthUpgradeBtn = findViewById(R.id.fifth_upgrade)
        testing_button = findViewById(R.id.testing_button)
        number = findViewById(R.id.counter_view)

        val handler = Handler(Looper.getMainLooper())

        handler.post(object : Runnable {
            override fun run() {
                autoGenerateMoney(number)

                handler.postDelayed(this, 1000)
            }
        })
    }

    // Cursor -> +1$ per click (price 50) - done
    // Auto Click -> +1$ per second (price 125) - done
    // Mr. Clicker -> +5$ per click (price 500) - done
    // Money Farm -> +6$ per second (price 1100) - done
    // President Clicker -> +100$ per click (price 12000) - done


    override fun onResume() {
        super.onResume()

        layout.setOnClickListener() {
            generateMoney(number)
        }

        firstUpgradeBtn.setOnClickListener {
            if (number.text.toString().toInt() >= 50) {
                click_multiplier++
                number.text = (number.text.toString().toInt() - 50).toString()
            }
        }

        secondUpgradeBtn.setOnClickListener {
            if (number.text.toString().toInt() >= 125) {
                auto_clicker++
                number.text = (number.text.toString().toInt() - 125).toString()
            }
        }

        thirdUpgradeBtn.setOnClickListener {
            if (number.text.toString().toInt() >= 500) {
                click_multiplier += 5
                number.text = (number.text.toString().toInt() - 500).toString()
            }
        }

        fourthUpgradeBtn.setOnClickListener {
            if (number.text.toString().toInt() >= 1100) {
                auto_clicker += 6
                number.text = (number.text.toString().toInt() - 1100).toString()
            }
        }

        fifthUpgradeBtn.setOnClickListener {
            if (number.text.toString().toInt() >= 12000) {
                auto_clicker += 100
                number.text = (number.text.toString().toInt() - 12000).toString()
            }
        }

        testing_button.setOnClickListener {
            Toast.makeText(
                it.context,
                "Click multiplier: $click_multiplier | Auto-Clicker: $auto_clicker",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun autoGenerateMoney(numberView: TextView) {
        var temp = numberView.text.toString().toInt() + auto_clicker

        numberView.text = temp.toString()
    }

    private fun generateMoney(numberView: TextView) {
        var temp = numberView.text.toString().toInt() + click_multiplier

        numberView.text = temp.toString()
    }

}