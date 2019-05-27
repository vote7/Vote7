package agh.vote7.poll.question

import agh.vote7.R
import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.children
import kotlinx.android.synthetic.main.question.view.*

class QuestionView(context: Context) : FrameLayout(context) {
    var question: String = ""
        set(value) {
            field = value
            contentTextView.text = value
        }

    var isOpen: Boolean = false
        set(value) {
            field = value
            recreateAnswers()
        }

    var answers: List<String> = emptyList()
        set(value) {
            field = value
            recreateAnswers()
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.question, this)
    }

    private fun recreateAnswers() {
        answersContainer.removeAllViews()

        if (isOpen) {
            answersContainer.addView(OpenAnswerView(context))
        } else {
            answers.forEach { answer ->
                ClosedAnswerView(context)
                    .apply {
                        content = answer
                        onClicked = { onAnswerClicked(this) }
                    }
                    .let { answersContainer.addView(it) }
            }
        }
    }

    private fun onAnswerClicked(answerView: ClosedAnswerView) {
        answersContainer.children.forEach {
            it as ClosedAnswerView
            it.isChecked = (it == answerView)
        }
    }
}
