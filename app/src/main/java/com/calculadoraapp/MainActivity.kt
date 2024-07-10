package com.calculadoraapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.calculadoraapp.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    var lastNumeric = false
    var stateError = false
    var lastDot = false

    private lateinit var expression : Expression
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }

    fun onAllclearClick(view: View) {

        binding.dataTv.text = ""
        binding.resultTv.text = ""
        stateError = false
        lastDot = false
        lastNumeric = false
        binding.resultTv.visibility = View.GONE

    }
    fun onResultClick(view: View) {

        onEqual()
        binding.dataTv.text = binding.resultTv.text.toString().drop(1)
    }
    fun onOperadorClick(view: View) {

        if (stateError){
            binding.dataTv.text = (view as Button) .text
            stateError = false

        }else{
         binding.dataTv.append((view as Button).text)
        }

        lastNumeric = true
        onEqual()
    }


    fun onBackClick(view: View) {

        binding.dataTv.text = binding.dataTv.text.toString().dropLast(1)

        try{

            val lastChar = binding.dataTv.text.toString().last()

            if(lastChar.isDigit()){

                onEqual()
            }

        }catch (e: Exception ){

            binding.resultTv.text=""
            binding.resultTv.visibility = View.GONE
            Log.e("error",e.toString() )
        }
    }
    fun onClearClick(view: View) {

        binding.dataTv.text = ""
        lastNumeric = false


    }

    fun onEqual(){

        if(lastNumeric && !stateError){
            val txt = binding.dataTv.text.toString()

            expression = ExpressionBuilder(txt).build()

            try {

                val result = expression.evaluate()

                binding.resultTv.visibility = View.VISIBLE

                binding.resultTv.text = "=" + result.toString()

            }catch (ex: ArithmeticException){

                Log.e("info_app",ex.toString() )
                binding.resultTv.text = "Error"
                stateError = true
                lastNumeric = false
            }
        }

    }

    fun onOperadoresClick(view: View) {

        if(!stateError && lastNumeric){
            binding.dataTv.append((view as Button).text)
            lastDot = false
            lastNumeric = false
            onEqual()
        }
    }

}