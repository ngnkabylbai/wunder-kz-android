package labs.startup.nurbekkabylbai.calculatorgold

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*


enum class Operation {
    PLUS, MINUS, DIV, MUL, AC, RES, PERCENT
}

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var operation: Operation = Operation.RES
    private var firstNumber = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button0.setOnClickListener(this)
        button1.setOnClickListener(this)
        button2.setOnClickListener(this)
        button3.setOnClickListener(this)
        button4.setOnClickListener(this)
        button5.setOnClickListener(this)
        button6.setOnClickListener(this)
        button7.setOnClickListener(this)
        button8.setOnClickListener(this)
        button9.setOnClickListener(this)
        divButton.setOnClickListener(this)
        plusButton.setOnClickListener(this)
        minusButton.setOnClickListener(this)
        resButton.setOnClickListener(this)
        multButton.setOnClickListener(this)
        acButton.setOnClickListener(this)
        percentButton.setOnClickListener(this)
        delButton.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.button0 -> {
                if(operation == Operation.RES)
                    clean()

                result.append("0")
                result.text = getFormattedString(convertToInt(result.text.toString()))
            }
            R.id.button1 -> {
                if(operation == Operation.RES)
                    clean()
                result.append("1")
                result.text = getFormattedString(convertToInt(result.text.toString()))
            }
            R.id.button2 ->{
                if(operation == Operation.RES)
                    clean()

                result.append("2")
                result.text = getFormattedString(convertToInt(result.text.toString()))
            }
            R.id.button3 -> {
                if(operation == Operation.RES)
                    clean()

                result.append("3")
                result.text = getFormattedString(convertToInt(result.text.toString()))
            }
            R.id.button4 -> {
                if(operation == Operation.RES)
                    clean()

                result.append("4")
                result.text = getFormattedString(convertToInt(result.text.toString()))
            }
            R.id.button5 -> {
                if(operation == Operation.RES)
                    clean()

                result.append("5")
                result.text = getFormattedString(convertToInt(result.text.toString()))
            }
            R.id.button6 -> {
                if(operation == Operation.RES)
                    clean()

                result.append("6")
                result.text = getFormattedString(convertToInt(result.text.toString()))
            }
            R.id.button7 -> {
                if(operation == Operation.RES)
                    clean()

                result.append("7")
                result.text = getFormattedString(convertToInt(result.text.toString()))
            }
            R.id.button8 -> {
                if(operation == Operation.RES)
                    clean()

                result.append("8")
                result.text = getFormattedString(convertToInt(result.text.toString()))
            }
            R.id.button9 -> {
                if(operation == Operation.RES)
                    clean()

                result.append("9")
                result.text = getFormattedString(convertToInt(result.text.toString()))
            }
            R.id.acButton -> {
                clean()
            }
            R.id.divButton -> {
                if(operation == Operation.RES) {
                    clean()
                }

                if(result.text.isNotEmpty()) {
                    operation = Operation.DIV
                    firstNumber = convertToInt(result.text.toString())
                    result.text = ""
                    calculate.append(getFormattedString(firstNumber) + "/")
                }
            }
            R.id.minusButton -> {
                if(operation == Operation.RES) {
                    clean()
                }

                if(result.text.isNotEmpty()) {
                    operation = Operation.MINUS
                    firstNumber = convertToInt(result.text.toString())
                    result.text = ""
                    calculate.append(getFormattedString(firstNumber) + "-")
                }
            }
            R.id.multButton -> {
                if(operation == Operation.RES) {
                    clean()
                }

                if(result.text.isNotEmpty()) {
                    operation = Operation.MUL
                    firstNumber = convertToInt(result.text.toString())
                    result.text = ""
                    calculate.append(getFormattedString(firstNumber) + "*")
                }
            }
            R.id.plusButton -> {
                if(operation == Operation.RES){
                    clean()
                }

                if(result.text.isNotEmpty()) {
                    operation = Operation.PLUS
                    firstNumber = convertToInt(result.text.toString())
                    result.text = ""
                    calculate.append(getFormattedString(firstNumber) + "+")
                }
            }
            R.id.percentButton-> {
                if(operation == Operation.RES){
                    clean()
                }

                if(result.text.isNotEmpty()) {
                    operation = Operation.PERCENT
                    firstNumber = convertToInt(result.text.toString())
                    result.text = ""
                    calculate.append(getFormattedString(firstNumber) + "%")
                }
            }
            R.id.delButton -> {
                if(operation == Operation.RES){
                    clean()
                }

                if(result.text.isNotEmpty()) {
                    firstNumber = convertToInt(result.text.toString().substring(0, result.text.length-1))
                    result.text = getFormattedString(firstNumber)
                }
            }
            R.id.resButton -> {
                if(calculate.text.isNotEmpty()) {
                    val secondNumber = convertToInt(result.text.toString())
                    calculate.append(secondNumber.toString())
                    when(operation) {
                        Operation.PLUS -> {
                            val res = firstNumber + secondNumber
                            result.text = getFormattedString(res)
                        }
                        Operation.MINUS -> {
                            val res = firstNumber - secondNumber
                            result.text = getFormattedString(res)
                        }
                        Operation.DIV -> {
                            val res = firstNumber / secondNumber
                            result.text = getFormattedString(res)
                        }
                        Operation.MUL -> {
                            val res = firstNumber * secondNumber
                            result.text = getFormattedString(res)
                        }
                        Operation.PERCENT -> {
                            val res = firstNumber * (secondNumber/100f)
                            result.text = getFormattedString(res.toInt())
                        } else -> {}
                    }
                    operation = Operation.RES
                }
            }
        }
    }

    private fun convertToInt(value: String): Int {
        return try {
            val result = value.replace("\\s".toRegex(), "")
            result.toInt()
        } catch (e: NumberFormatException) {
            -1
        }
    }

    private fun getFormattedString(value: Int): String {
        var result = ""
        val strValue = value.toString().reversed()
        var counter = 0

        for(i in 0 until strValue.length) {
            result += strValue[i]
            if((++counter) %3 == 0 && i != strValue.length - 1)
                result += ' '
        }

        return result.reversed()
    }

    private fun clean() {
        operation = Operation.AC
        calculate.text = ""
        result.text = ""
    }
}
