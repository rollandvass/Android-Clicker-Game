package com.rolland.clickergame

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Base64
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bullfrog.particle.IParticleManager
import com.bullfrog.particle.Particles
import com.bullfrog.particle.animation.ParticleAnimation
import com.bullfrog.particle.particle.configuration.Shape


class MainActivity : AppCompatActivity() {

    private val particleColor = Color.parseColor("#41fdfe")

    private lateinit var layout: ConstraintLayout
    private lateinit var firstUpgradeBtn: Button
    private lateinit var secondUpgradeBtn: Button
    private lateinit var thirdUpgradeBtn: Button
    private lateinit var fourthUpgradeBtn: Button
    private lateinit var fifthUpgradeBtn: Button
    private lateinit var sixthUpgradeBtn: Button
    private lateinit var number: TextView
    private lateinit var clickPower: TextView
    private lateinit var autoPower: TextView

    private var clickMultiplier = 1
    private var autoClicker = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        layout = findViewById(R.id.parent_view)
        number = findViewById(R.id.counter_view)
        firstUpgradeBtn = findViewById(R.id.first_upgrade_btn)
        secondUpgradeBtn = findViewById(R.id.second_upgrade_btn)
        thirdUpgradeBtn = findViewById(R.id.third_upgrade_btn)
        fourthUpgradeBtn = findViewById(R.id.fourth_upgrade_btn)
        fifthUpgradeBtn = findViewById(R.id.fifth_upgrade_btn)
        sixthUpgradeBtn = findViewById(R.id.sixth_upgrade_btn)
        clickPower = findViewById(R.id.click_power_value)
        autoPower = findViewById(R.id.auto_power_value)


        val handler = Handler(Looper.getMainLooper())

        handler.post(object : Runnable {
            override fun run() {
                autoGenerateMoney(number)

                handler.postDelayed(this, 1000)
            }
        })
    }

    // FEATURE 1: make an icon that on touch shows a hanging layout with the shop (buttons)
    // FEATURE 2: make the particle shape the current click power number

    @SuppressLint("ClickableViewAccessibility")
    override fun onResume() {
        super.onResume()

        val handleTouch = OnTouchListener { _, event ->
            val particleManager: IParticleManager = Particles.with(this, layout)
            val x = event.x.toInt()
            val y = event.y.toInt()

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    generateMoney(number)

                    particleManager // container is the parent ViewGroup for particles
                        .color(particleColor)// color sampling
                        .particleNum(getParticleCount())// how many particles
                        .anchor(x, y)// use touch position as the anchor of the animation
                        .shape(Shape.HOLLOW_PENTACLE)// circle particle
                        .radius(2, 15)// random radius from 2 to 15
                        .anim(ParticleAnimation.EXPLOSION)// using explosion animation
                        .start()

                    val handler = Handler(Looper.getMainLooper())
                    val runnable = Runnable { particleManager.cancel() }

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

        firstUpgradeBtn.setOnClickListener {
            if (number.text.toString().toInt() >= 50) {
                clickMultiplier++
                number.text = (number.text.toString().toInt() - 50).toString()

                updatePower()
            }
        }

        secondUpgradeBtn.setOnClickListener {
            if (number.text.toString().toInt() >= 125) {
                autoClicker++
                number.text = (number.text.toString().toInt() - 125).toString()

                updatePower()
            }
        }

        thirdUpgradeBtn.setOnClickListener {
            if (number.text.toString().toInt() >= 500) {
                clickMultiplier += 5
                number.text = (number.text.toString().toInt() - 500).toString()

                updatePower()
            }
        }

        fourthUpgradeBtn.setOnClickListener {
            if (number.text.toString().toInt() >= 1100) {
                autoClicker += 6
                number.text = (number.text.toString().toInt() - 1100).toString()

                updatePower()
            }
        }

        fifthUpgradeBtn.setOnClickListener {
            if (number.text.toString().toInt() >= 12000) {
                clickMultiplier += 100
                number.text = (number.text.toString().toInt() - 12000).toString()

                updatePower()
            }
        }

        sixthUpgradeBtn.setOnClickListener {
            if (number.text.toString().toInt() >= 100000) {
                autoClicker += 200
                number.text = (number.text.toString().toInt() - 100000).toString()

                updatePower()
            }
        }
    }

    private fun autoGenerateMoney(numberView: TextView) {
        val temp = numberView.text.toString().toInt() + autoClicker

        numberView.text = temp.toString()

    }

    private fun generateMoney(numberView: TextView) {
        val temp = numberView.text.toString().toInt() + clickMultiplier

        numberView.text = temp.toString()
    }

    // updates the power of clicks and autogenerate
    private fun updatePower() {
        autoPower.text = autoClicker.toString()
        clickPower.text = clickMultiplier.toString()
    }

    private fun getParticleCount(): Int {
        if (clickMultiplier.toString().toInt() > 50) {
            return 2
        } else if (clickMultiplier.toString().toInt() > 500) {
            return 3
        } else if (clickMultiplier.toString().toInt() > 5000) {
            return 5
        }
        return 1
    }

}