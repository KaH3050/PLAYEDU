// MainActivity.kt
package com.example.aprendizagemkids  // Certifique-se de que corresponde ao seu pacote real


import com.example.aprendizagemkids.R  // ✅ CORRETO! Use o nome do seu pacote
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var textQuestion: TextView? = null
    private var buttonOption1: Button? = null
    private var buttonOption2: Button? = null
    private var buttonOption3: Button? = null
    private var textScore: TextView? = null
    private var score = 0
    private var currentQuestionIndex = 0

    // Arrays de perguntas para matemática e português
    private val mathQuestions = arrayOf(
        Question("Quanto é 2 + 3?", arrayOf("5", "4", "6"), 0),
        Question("Quantos lados tem um triângulo?", arrayOf("3", "4", "5"), 0),
        Question("Quanto é 10 - 4?", arrayOf("5", "6", "7"), 1)
    )

    private val portugueseQuestions = arrayOf(
        Question("Qual é a primeira letra do alfabeto?", arrayOf("A", "B", "C"), 0),
        Question("Complete: O gato é ___", arrayOf("feliz", "felise", "felis"), 0),
        Question("Qual palavra está escrita corretamente?", arrayOf("caza", "casa", "kasa"), 1)
    )

    private var currentQuestions: Array<Question> = arrayOf()  // Inicializa como array vazio
    private var isMathMode: Boolean = true  // Já está correto, mas adicionei o tipo explicitamente


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa os componentes da interface
        textQuestion = findViewById(R.id.textQuestion)
        buttonOption1 = findViewById(R.id.buttonOption1)
        buttonOption2 = findViewById(R.id.buttonOption2)
        buttonOption3 = findViewById(R.id.buttonOption3)
        textScore = findViewById(R.id.textScore)

        // Define modo inicial para matemática
        currentQuestions = mathQuestions

        // Configure os listeners dos botões
        buttonOption1?.setOnClickListener { checkAnswer(0) }
        buttonOption2?.setOnClickListener { checkAnswer(1) }
        buttonOption3?.setOnClickListener { checkAnswer(2) }

        // Botões para alternar entre temas
        val buttonMath = findViewById<Button>(R.id.buttonMath)
        val buttonPortuguese = findViewById<Button>(R.id.buttonPortuguese)

        buttonMath.setOnClickListener {
            isMathMode = true
            currentQuestions = mathQuestions
            currentQuestionIndex = 0
            showQuestion()
        }

        buttonPortuguese.setOnClickListener {
            isMathMode = false
            currentQuestions = portugueseQuestions
            currentQuestionIndex = 0
            showQuestion()
        }

        // Mostra a primeira pergunta
        showQuestion()
    }

    private fun showQuestion() {
        if (currentQuestionIndex < currentQuestions.size) {
            val currentQuestion = currentQuestions[currentQuestionIndex]
            textQuestion?.text = currentQuestion.question
            buttonOption1?.text = currentQuestion.options[0]
            buttonOption2?.text = currentQuestion.options[1]
            buttonOption3?.text = currentQuestion.options[2]
            textScore?.text = "Pontos: $score"
        } else {
            // Todas as perguntas foram respondidas
            textQuestion?.text = "Parabéns! Você completou todas as perguntas!"
            buttonOption1?.visibility = View.INVISIBLE
            buttonOption2?.visibility = View.INVISIBLE
            buttonOption3?.visibility = View.INVISIBLE
        }
    }

    private fun checkAnswer(selectedOption: Int) {
        val currentQuestion = currentQuestions[currentQuestionIndex]
        if (selectedOption == currentQuestion.correctAnswerIndex) {
            // Resposta correta
            score += 10
            // Exibe feedback positivo
            showFeedback(true)
        } else {
            // Resposta incorreta
            showFeedback(false)
        }

        // Avança para a próxima pergunta
        currentQuestionIndex++

        // Mostra a próxima pergunta após um pequeno atraso
        textQuestion?.postDelayed({ this.showQuestion() }, 1000)
    }

    private fun showFeedback(isCorrect: Boolean) {
        if (isCorrect) {
            textQuestion?.text = "Correto! 🎉"
        } else {
            val currentQuestion = currentQuestions[currentQuestionIndex]
            textQuestion?.text = "Ops! A resposta correta é: " +
                    currentQuestion.options[currentQuestion.correctAnswerIndex]
        }
    }

    // Classe para representar uma pergunta
    private class Question(
        val question: String,
        val options: Array<String>,
        val correctAnswerIndex: Int
    )
}
