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
    private lateinit var kingClickerBtn: Button
    private lateinit var moneyFactoryBtn: Button
    private lateinit var emperorClickerBtn: Button
    private lateinit var moneyPyramidBtn: Button
    private lateinit var popeClickerBtn: Button
    private lateinit var moneyTempleBtn: Button
    private lateinit var godClickerBtn: Button
    private lateinit var moneyPowerPlantBtn: Button

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
        kingClickerBtn = findViewById(R.id.king_clicker_btn)
        moneyFactoryBtn = findViewById(R.id.money_factory_btn)
        emperorClickerBtn = findViewById(R.id.emperor_clicker_btn)
        moneyPyramidBtn = findViewById(R.id.money_pyramid_btn)
        popeClickerBtn = findViewById(R.id.pope_clicker_btn)
        moneyTempleBtn = findViewById(R.id.money_temple_btn)
        godClickerBtn = findViewById(R.id.god_clicker_btn)
        moneyPowerPlantBtn = findViewById(R.id.money_power_plant_btn)

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

                    checkForClickCountAchievement()

                    particleManager // container is the parent ViewGroup for particles
                        .color(particleColor)// color sampling
                        .particleNum(getParticleCount())// how many particles
                        .anchor(x, y)// use current touch position as the anchor of the animation
                        .shape(Shape.HOLLOW_PENTACLE)// pentacle particle
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
            if (score.text.toString().toLong() >= 50) {
                clickPowerValue++
                score.text = (score.text.toString().toLong() - 50).toString()

                updatePowers()
                checkForClickPowerAchievements()
            }
        }

        autoClickerBtn.setOnClickListener {
            if (score.text.toString().toLong() >= 125) {
                autoPowerValue++
                score.text = (score.text.toString().toLong() - 125).toString()

                updatePowers()
                checkForAutoPowerAchievements()
            }
        }

        mrClickerBtn.setOnClickListener {
            if (score.text.toString().toLong() >= 500) {
                clickPowerValue += 5
                score.text = (score.text.toString().toLong() - 500).toString()

                updatePowers()
                checkForClickPowerAchievements()
            }
        }

        moneyFarmBtn.setOnClickListener {
            if (score.text.toString().toLong() >= 1100) {
                autoPowerValue += 6
                score.text = (score.text.toString().toLong() - 1100).toString()

                updatePowers()
                checkForAutoPowerAchievements()
            }
        }

        presidentClickerBtn.setOnClickListener {
            if (score.text.toString().toLong() >= 12000) {
                clickPowerValue += 100
                score.text = (score.text.toString().toLong() - 12000).toString()

                updatePowers()
                checkForClickPowerAchievements()
            }
        }

        moneyPumpBtn.setOnClickListener {
            if (score.text.toString().toLong() >= 100000) {
                autoPowerValue += 200
                score.text = (score.text.toString().toLong() - 100000).toString()

                updatePowers()
                checkForAutoPowerAchievements()
            }
        }

        kingClickerBtn.setOnClickListener {
            if (score.text.toString().toLong() >= 90000) {
                clickPowerValue += 1000
                score.text = (score.text.toString().toLong() - 90000).toString()

                updatePowers()
                checkForClickPowerAchievements()
            }
        }

        moneyFactoryBtn.setOnClickListener {
            if (score.text.toString().toLong() >= 315000) {
                autoPowerValue += 5000
                score.text = (score.text.toString().toLong() - 315000).toString()

                updatePowers()
                checkForAutoPowerAchievements()
            }
        }

        emperorClickerBtn.setOnClickListener {
            if (score.text.toString().toLong() >= 22000000) {
                clickPowerValue += 9000
                score.text = (score.text.toString().toLong() - 22000000).toString()

                updatePowers()
                checkForClickPowerAchievements()
            }
        }

        moneyPyramidBtn.setOnClickListener {
            if (score.text.toString().toLong() >= 81000000) {
                autoPowerValue += 100000
                score.text = (score.text.toString().toLong() - 81000000).toString()

                updatePowers()
                checkForAutoPowerAchievements()
            }
        }

        popeClickerBtn.setOnClickListener {
            if (score.text.toString().toLong() >= 300000000) {
                clickPowerValue += 25000
                score.text = (score.text.toString().toLong() - 300000000).toString()

                updatePowers()
                checkForClickPowerAchievements()
            }
        }

        moneyTempleBtn.setOnClickListener {
            if (score.text.toString().toLong() >= 900000000) {
                autoPowerValue += 200000
                score.text = (score.text.toString().toLong() - 900000000).toString()

                updatePowers()
                checkForAutoPowerAchievements()
            }
        }

        godClickerBtn.setOnClickListener {
            if (score.text.toString().toLong() >= 1800000000) {
                clickPowerValue += 100000
                score.text = (score.text.toString().toLong() - 1800000000).toString()

                updatePowers()
                checkForClickPowerAchievements()
            }
        }

        moneyPowerPlantBtn.setOnClickListener {
            if (score.text.toString().toLong() >= 3000000000) {
                autoPowerValue += 1000000
                score.text = (score.text.toString().toLong() - 3000000000).toString()

                updatePowers()
                checkForAutoPowerAchievements()
            }
        }
    }

    private fun autoGenerateMoney(scoreView: TextView) {
        val temp = scoreView.text.toString().toLong() + autoPowerValue

        "$temp".also { scoreView.text = it }

    }

    private fun generateMoney(scoreView: TextView) {
        val temp = scoreView.text.toString().toLong() + clickPowerValue

        "$temp".also { scoreView.text = it }
    }

    private fun updatePowers() {
        "$$clickPowerValue / click".also { clickPowerValueView.text = it }
        "$$autoPowerValue / second".also { autoPowerValueView.text = it }
    }

    private fun getParticleCount(): Int {
        if (clickPowerValue >= 500000)
            return 5
        else if (clickPowerValue >= 50000)
            return 4
        else if (clickPowerValue >= 5000)
            return 3
        else if (clickPowerValue >= 500)
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
            10000000 -> showClickPowerAchievement()
            100000000 -> showClickPowerAchievement()
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
            10000000 -> showAutoPowerAchievement()
            100000000 -> showAutoPowerAchievement()
        }
    }

    // TODO:
    //      1. remove the appbar from being possible to click and make score

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

    private fun checkForClickCountAchievement() {
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
            1000000 -> showClickAchievement()
        }
    }

    private fun showClickAchievement() {
        Toast.makeText(
            applicationContext,
            "Achievement Unlocked! You clicked $userClickCount times!", Toast.LENGTH_SHORT
        ).show()
    }
}