package com.example.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.calculatorapp.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.ArithmeticException
import java.lang.Exception
import javax.xml.xpath.XPathExpression

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var lastNumeric = false
    var stateError =false
    var lastDot = false

    private lateinit var expression: Expression

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onAllclearClick(view: View) {

        binding.dataTv.text = ""
        binding.resultTv.text = ""
        lastDot = false
        stateError = false
        lastNumeric = false
        binding.resultTv.visibility = View.GONE
        
        
    }



    fun onEqualClick(view: View) {
        
        onEqual()
        binding.dataTv.text = binding.resultTv.text.toString().drop(n = 1)
    }



    fun onBackClick(view: View) {
        
        binding.dataTv.text = binding.dataTv.text.toString().dropLast(n=1)
        try {
            val lastchar = binding.dataTv.text.toString().last()
            
            if(lastchar.isDigit()){
                onEqual()
            }
        }catch (e : Exception){
            
            binding.resultTv.text = ""
            binding.resultTv.visibility = View.GONE
            Log.e("Last char error",e.toString())
        }
        
    }



    fun onClearClick(view: View) {

        binding.dataTv.text = ""
        lastNumeric = false

    }



    fun onDigitClick(view: View) {

        if(stateError){

            binding.dataTv.text = (view as Button).text
            stateError = false
        }
        else{
            binding.dataTv.append((view as Button).text)
        }

        lastNumeric = true
        onEqual()
    }



    fun onOperatorClick(view: View) {
        
        if(!stateError && lastNumeric){
            
            binding.dataTv.append((view as Button).text)
            lastDot = false
            lastNumeric = false
            onEqual() 
        }
        
    }

    fun onEqual (){
        if(lastNumeric && !stateError){
            val txt =binding.dataTv.text.toString()

            expression = ExpressionBuilder(txt).build()
            try {

                val result = expression.evaluate()

                binding.resultTv.visibility = View.VISIBLE
                binding.resultTv.text = "=" + result.toString()


            }catch (ex : ArithmeticException){
                Log.e("evaluate error", ex.toString())
                binding.resultTv.text = "Error"
                stateError = true
                lastNumeric = false
            }
        }
    }


}