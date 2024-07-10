package com.calculadoraapp

import android.os.Bundle
import android.util.Log
import android.view.View
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

    fun onAllclearClick(view: View) {}
    fun onResultClick(view: View) {}
    fun onOperadorClick(view: View) {}
    fun onBackClick(view: View) {}
    fun onClearClick(view: View) {}

    fun OnEqual(){

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

}