package com.example.rpn_calculator

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.rpn_calculator.R.string
import com.example.rpn_calculator.databinding.ActivityMainBinding
import kotlin.math.pow
import kotlin.math.sqrt
import android.view.MotionEvent
import android.view.View
import android.view.GestureDetector
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var stack = mutableListOf("","","","")
    var undoStack = mutableListOf<String>()
    var round = 4;
    var colorScheme = "system"
    override fun onCreate(savedInstanceState: Bundle?) {
        loadPref()
        loadScheme()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        updateStackView()


        binding.n1.setOnClickListener() {
            stack[0] += "1"
            updateStackView()
        }
        binding.n2.setOnClickListener() {
            stack[0] += "2"
            updateStackView()
        }
        binding.n3.setOnClickListener() {
            stack[0] += "3"
            updateStackView()
        }
        binding.n4.setOnClickListener() {
            stack[0] += "4"
            updateStackView()
        }
        binding.n5.setOnClickListener() {
            stack[0] += "5"
            updateStackView()
        }
        binding.n6.setOnClickListener() {
            stack[0] += "6"
            updateStackView()
        }
        binding.n7.setOnClickListener() {
            stack[0] += "7"
            updateStackView()
        }
        binding.n8.setOnClickListener() {
            stack[0] += "8"
            updateStackView()
        }
        binding.n9.setOnClickListener() {
            stack[0] += "9"
            updateStackView()
        }
        binding.n0.setOnClickListener() {
            stack[0] += "0"
            updateStackView()
        }
        binding.dot.setOnClickListener() {
            if(stack[0] == "") {
                stack[0] += "0"
            }
            stack[0] += "."
            updateStackView()
        }
        binding.pm.setOnClickListener() {
            if(stack[0] != "") {
                if (stack[0].get(0).equals('-')) {
                    stack[0] = stack[0].substring(1)
                }
                else {
                    stack[0] = "-" + stack[0]
                }
                updateStackView()
            }
        }
        binding.enter.setOnClickListener() {
            undoStack = stack.toMutableList()
            enter()
        }
        binding.plus.setOnClickListener() {
            if(stack[1] == "" && stack[0] != "") {
                undoStack = stack.toMutableList()
                var tmp = stack[0].toFloat()
                tmp = tmp + tmp
                val tmp2 = String.format("%.${round}f",tmp)

                stack[0] = tmp2
            }
            if(stack[1] != "" && stack[0] != "") {
                undoStack = stack.toMutableList()
                var tmp = stack[0].toFloat()
                var tmp2 = stack[1].toFloat()
                tmp = tmp + tmp2
                val tmp3 = String.format("%.${round}f",tmp)

                stack[0] = tmp3
                stack[1] = ""
            }
            downStack()
            updateStackView()
        }
        binding.minus.setOnClickListener() {
            if(stack[1] != "" && stack[0] != "") {
                undoStack = stack.toMutableList()
                var tmp = stack[0].toFloat()
                var tmp2 = stack[1].toFloat()
                tmp = tmp2 - tmp
                val tmp3 = String.format("%.${round}f",tmp)

                stack[0] = tmp3
                stack[1] = ""
            }
            downStack()
            updateStackView()
        }
        binding.multi.setOnClickListener() {
            if(stack[1] != "" && stack[0] != "") {
                undoStack = stack.toMutableList()
                var tmp = stack[0].toFloat()
                var tmp2 = stack[1].toFloat()
                tmp = tmp2 * tmp
                val tmp3 = String.format("%.${round}f",tmp)

                stack[0] = tmp3
                stack[1] = ""
            }
            downStack()
            updateStackView()
        }
        binding.divi.setOnClickListener() {
            if(stack[1] != "" && stack[0] != "") {
                undoStack = stack.toMutableList()
                var tmp = stack[0].toFloat()
                var tmp2 = stack[1].toFloat()
                tmp = tmp2 / tmp
                val tmp3 = String.format("%.${round}f",tmp)

                stack[0] = tmp3
                stack[1] = ""
            }
            downStack()
            updateStackView()
        }
        binding.pow.setOnClickListener() {
            if(stack[1] != "" && stack[0] != "") {
                undoStack = stack.toMutableList()
                var tmp = stack[0].toFloat()
                var tmp2 = stack[1].toFloat()
                tmp = tmp2.pow(tmp)
                val tmp3 = String.format("%.${round}f",tmp)

                stack[0] = tmp3
                stack[1] = ""
            }
            downStack()
            updateStackView()
        }
        binding.rot.setOnClickListener() {
            if(stack[0] != "") {
                undoStack = stack.toMutableList()
                var tmp = stack[0].toFloat()
                tmp = sqrt(tmp)
                val tmp3 = String.format("%.${round}f",tmp)

                stack[0] = tmp3
            }
            if(stack[1] != "" && stack[0] == "") {
                undoStack = stack.toMutableList()
                var tmp = stack[1].toFloat()
                tmp = sqrt(tmp)
                val tmp3 = String.format("%.${round}f",tmp)

                stack[0] = tmp3
                stack[1] = ""
            }
            downStack()
            updateStackView()
        }
        binding.drop.setOnClickListener() {
            undoStack = stack.toMutableList()
            stack[0] = ""
            downStack()
            updateStackView()
        }
        binding.swap.setOnClickListener() {
            if(stack[0] != "" && stack[1] != "") {
                undoStack = stack.toMutableList()
                var tmp = stack[0].toFloat().toString()
                stack[0] = stack[1]
                stack[1] = tmp
            }
            else if(stack[0] != "" && stack[1] == "") {
                undoStack = stack.toMutableList()
                var tmp = stack[0].toFloat().toString()
                stack[0] = stack[1]
                stack[1] = tmp
            }
            else if(stack[0] == "" && stack[1] != "") {
                undoStack = stack.toMutableList()
                stack[0] = stack[1]
                stack[1] = "0.0"
            }
            updateStackView()
        }
        binding.cls.setOnClickListener() {
            clsFun()
    }
        binding.ac.setOnClickListener() {
            undoStack = stack.toMutableList()
            for(i in stack.size-1 downTo 0) {
                if(i > 3) {
                    stack.removeLast()
                }
                else {
                    stack[i] = ""
                }
            }
            updateStackView()
        }
        binding.undo.setOnClickListener() {
            undoFun()
        }
        binding.settings.setOnClickListener() {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

//        binding.settings.setBackgroundTin

    }


    fun updateStackView() {
        binding.s1.text = stack[0]
        binding.s2.text = stack[1]
        binding.s3.text = stack[2]
        binding.s4.text = stack[3]
    }

    fun enter() {
        if( stack[0] != "" || stack[1] != "") {
            if(stack[0] == "") {
                stack[0] = stack[1]
            }
            val tmp = stack[0].toFloat()
//            val tmp2 = tmp.toString()
            val tmp3 = String.format("%.${round}f",tmp)
            stack[0] = tmp3

            for (i in stack.size-1 downTo 0) {
                if(stack[i] != "") {
                    if(i == stack.size-1) {
                        stack.add(stack[i])
                    }
                    else {
                        stack[i+1] = stack[i]
                    }
                }
            }

            stack[0] = ""
            updateStackView()
        }
    }

    fun clsFun() {
        if(stack[0] != "") {
            stack[0] = stack[0].substring(0,stack[0].length-1)
        }
        updateStackView()
    }

    fun downStack() {
        for(i in 0..stack.size-2) {
            if(stack[i] == "") {
                stack[i] = stack[i+1]
                stack[i+1] = ""
            }
        }
        var lastIndex = stack.size-1
        if (lastIndex > 3 && stack[lastIndex] == "") {
            stack.removeLast()
        }
    }

    fun undoFun() {
        if(undoStack.size > 0) {
            stack = undoStack.toMutableList()
        }
        updateStackView()
    }

    fun loadPref() {
        val sp = PreferenceManager.getDefaultSharedPreferences(this)
        var cs = sp.getString("color_scheme","")
        if(cs != null) {
            colorScheme = cs
        }
        round = sp.getInt("round_value",0)
    }

    fun loadScheme() {
        when (colorScheme) {
            "system" -> {
                Log.d("LOG","SYS")
                setTheme(R.style.Theme_RPNCalculator)
            }
            "blue" -> {
                Log.d("LOG","BLUE")
                setTheme(R.style.BlueTheme)
            }
        }
    }

}