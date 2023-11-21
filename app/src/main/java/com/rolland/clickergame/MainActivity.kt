package com.rolland.clickergame

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bullfrog.particle.IParticleManager
import com.bullfrog.particle.Particles
import com.bullfrog.particle.animation.ParticleAnimation
import com.bullfrog.particle.particle.configuration.Shape


class MainActivity : AppCompatActivity() {

    private lateinit var layout: ConstraintLayout
    private lateinit var score: TextView
    private lateinit var cursorBtn: Button
    private lateinit var autoClickerBtn: Button
    private lateinit var mrClickerBtn: Button
    private lateinit var moneyFarmBtn: Button
    private lateinit var presidentClickerBtn: Button
    private lateinit var moneyPumpBtn: Button
    private lateinit var clickPowerValueView: TextView
    private lateinit var autoPowerValueView: TextView

    private val particleColor = Color.parseColor("#41fdfe")

    private var clickPowerValue = 1
    private var autoPowerValue = 0
    private var userClickCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        layout = findViewById(R.id.parent_view)
        score = findViewById(R.id.score_view)
        cursorBtn = findViewById(R.id.cursor_btn)
        autoClickerBtn = findViewById(R.id.auto_clicker_btn)
        mrClickerBtn = findViewById(R.id.mr_clicker_btn)
        moneyFarmBtn = findViewById(R.id.money_farm_btn)
        presidentClickerBtn = findViewById(R.id.president_clicker_btn)
        moneyPumpBtn = findViewById(R.id.money_pump_btn)
        clickPowerValueView = findViewById(R.id.click_power_value)
        autoPowerValueView = findViewById(R.id.auto_power_value)

        val handler = Handler(Looper.getMainLooper())

        handler.post(object : Runnable {
            override fun run() {
                autoGenerateMoney(score)

                handler.postDelayed(this, 1000)
            }
        })
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onResume() {
        super.onResume()

        val handleTouch = OnTouchListener { _, event ->
            val particleManager: IParticleManager = Particles.with(this, layout)
            val x = event.x.toInt()
            val y = event.y.toInt()

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    generateMoney(score)

                    userClickCount++
                    checkForSecondaryAchievement()

                    particleManager // container is the parent ViewGroup for particles
                        .color(particleColor)// color sampling
                        .particleNum(getParticleCount())// how many particles
                        .anchor(x, y)// use touch position as the anchor of the animation
                        .shape(Shape.HOLLOW_PENTACLE)// circle particle
                        .radius(2, 15)// random radius from 2 to 15
                        .anim(ParticleAnimation.EXPLOSION)// using explosion animation
                        .start()

                    val handler = Handler(Looper.getMainLooper())
                    val runnable = Runnable { particleManager.remove() }

                    handler.postDelayed(runnable, 1000)
                }

                MotionEvent.ACTION_MOVE -> {

                }

                MotionEvent.ACTION_UP -> {

                }
            }
            true
        }

        layout.setOnTouchListener(handleTouch)

        cursorBtn.setOnClickListener {
            if (score.text.toString().toInt() >= 50) {
                clickPowerValue++
                score.text = (score.text.toString().toInt() - 50).toString()

                updatePower()
                checkForClickPowerAchievements()
            }
        }

        autoClickerBtn.setOnClickListener {
            if (score.text.toString().toInt() >= 125) {
                autoPowerValue++
                score.text = (score.text.toString().toInt() - 125).toString()

                updatePower()
                checkForAutoPowerAchievements()
            }
        }

        mrClickerBtn.setOnClickListener {
            if (score.text.toString().toInt() >= 500) {
                clickPowerValue += 5
                score.text = (score.text.toString().toInt() - 500).toString()

                updatePower()
                checkForClickPowerAchievements()
            }
        }

        moneyFarmBtn.setOnClickListener {
            if (score.text.toString().toInt() >= 1100) {
                autoPowerValue += 6
                score.text = (score.text.toString().toInt() - 1100).toString()

                updatePower()
                checkForAutoPowerAchievements()
            }
        }

        presidentClickerBtn.setOnClickListener {
            if (score.text.toString().toInt() >= 12000) {
                clickPowerValue += 100
                score.text = (score.text.toString().toInt() - 12000).toString()

                updatePower()
                checkForClickPowerAchievements()
            }
        }

        moneyPumpBtn.setOnClickListener {
            if (score.text.toString().toInt() >= 100000) {
                autoPowerValue += 200
                score.text = (score.text.toString().toInt() - 100000).toString()

                updatePower()
                checkForAutoPowerAchievements()
            }
        }
    }
    // might break when number reaches Int limit (2147483647)

    private fun autoGenerateMoney(scoreView: TextView) {
        val temp = scoreView.text.toString().toInt() + autoPowerValue

        scoreView.text = temp.toString()

    }

    private fun generateMoney(scoreView: TextView) {
        val temp = scoreView.text.toString().toInt() + clickPowerValue

        scoreView.text = temp.toString()
    }

    private fun updatePower() {
        autoPowerValueView.text = autoPowerValue.toString()
        clickPowerValueView.text = clickPowerValue.toString()
    }

    private fun getParticleCount(): Int {
        if (clickPowerValue >= 5000)
            return 5
        else if (clickPowerValue >= 1000)
            return 3
        else if (clickPowerValue >= 100)
            return 2

        return 1
    }

    private fun checkForClickPowerAchievements() {
        when (clickPowerValue) {
            100 -> showClickPowerAchievement()
            500 -> showClickPowerAchievement()
            2500 -> showClickPowerAchievement()
            10000 -> showClickPowerAchievement()
            50000 -> showClickPowerAchievement()
            100000 -> showClickPowerAchievement()
            500000 -> showClickPowerAchievement()
            1000000 -> showClickPowerAchievement()
        }
    }


    private fun checkForAutoPowerAchievements() {
        when (autoPowerValue) {
            100 -> showAutoPowerAchievement()
            500 -> showAutoPowerAchievement()
            2500 -> showAutoPowerAchievement()
            10000 -> showAutoPowerAchievement()
            50000 -> showAutoPowerAchievement()
            100000 -> showAutoPowerAchievement()
            500000 -> showAutoPowerAchievement()
            1000000 -> showAutoPowerAchievement()
        }
    }


    private fun showClickPowerAchievement() {
        Toast.makeText(
            applicationContext,
            "Achievement Unlocked! $clickPowerValue Click Power!", Toast.LENGTH_SHORT
        ).show()
    }

    private fun showAutoPowerAchievement() {
        Toast.makeText(
            applicationContext,
            "Achievement Unlocked! $autoPowerValue Auto Power!",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun checkForSecondaryAchievement() {
        when (userClickCount) {
            100 -> showClickAchievement()
            500 -> showClickAchievement()
            1000 -> showClickAchievement()
            5000 -> showClickAchievement()
            10000 -> showClickAchievement()
            25000 -> showClickAchievement()
            50000 -> showClickAchievement()
            100000 -> showClickAchievement()
            250000 -> showClickAchievement()
            500000 -> showClickAchievement()
        }
    }

    private fun showClickAchievement() {
        Toast.makeText(
            applicationContext,
            "Achievement Unlocked! You clicked $userClickCount times!", Toast.LENGTH_SHORT
        ).show()
    }
}