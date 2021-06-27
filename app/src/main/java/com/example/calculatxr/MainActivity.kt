package com.example.calculatxr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    var lastNum:Boolean=false
    var lastDot:Boolean=false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onDigit(view: View){
        val displayScreen=findViewById<TextView>(R.id.displayScreen)
        displayScreen.append((view as Button).text)
        lastNum=true
    }
    fun onClear(view: View){
        val displayScreen=findViewById<TextView>(R.id.displayScreen)
        displayScreen.text=""
        lastNum=false
        lastDot=false
    }

    fun onDecimalPoint(view: View){
        if(lastNum && !lastDot){
            val displayScreen=findViewById<TextView>(R.id.displayScreen)
            displayScreen.append(".")
            lastNum=false
            lastDot=true
        }
    }

    private fun isOperatorAdded(value:String):Boolean{
        return if(value.startsWith("-")){
            false
        }
        else
        {
            value.contains("/")||value.contains("*")||value.contains("-")||value.contains("+")
        }
    }

    fun onOperator(view: View){
        val displayScreen=findViewById<TextView>(R.id.displayScreen)
        if(lastNum&&!isOperatorAdded(displayScreen.text.toString())){
            displayScreen.append((view as Button).text)
            lastNum=false
            lastDot=false
        }
    }



    fun onEqual(view: View){
        val displayScreen=findViewById<TextView>(R.id.displayScreen)
        if(lastNum){
            var tvValue=displayScreen.text.toString()
            var prefix=""
            try{
                if (tvValue.startsWith("-")){
                    prefix="-"
                    tvValue=tvValue.substring(1)
                }
                if (tvValue.contains("-")){
                    val splitValue=tvValue.split("-")
                    var one=splitValue[0]
                    var two=splitValue[1]
                    if(!prefix.isEmpty()){
                        one=prefix+one
                    }
                    displayScreen.text=removeZeroAfterDot((one.toDouble()-two.toDouble()).toString())
                }

                else if(tvValue.contains("+")){
                    val splitValue=tvValue.split("+")
                    var one=splitValue[0]
                    var two=splitValue[1]
                    if(!prefix.isEmpty()){
                        one=prefix+one
                    }
                    displayScreen.text=removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                }

                else if(tvValue.contains("*")){
                    val splitValue=tvValue.split("*")
                    var one=splitValue[0]
                    var two=splitValue[1]
                    if(!prefix.isEmpty()){
                        one=prefix+one
                    }
                    displayScreen.text=removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                }

                else if(tvValue.contains("/")){
                    val splitValue=tvValue.split("/")
                    var one=splitValue[0]
                    var two=splitValue[1]
                    if(!prefix.isEmpty()){
                        one=prefix+one
                    }
                    displayScreen.text=removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                }

            }catch (e:ArithmeticException){e.printStackTrace()}

        }
    }

    private fun removeZeroAfterDot(result:String):String{
        var value=result
        if(value.endsWith(".0")){
            value=value.substring(0,value.length-2)
        }
        return value
    }

}