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

    var answers: List<String> = emptyList()
        set(value) {
            field = value
            answersContainer.removeAllViews()
            value.forEach { answer ->
                ClosedAnswerView(context)
                    .apply {
                        content = answer
                        onClicked = { onAnswerClicked(this) }
                    }
                    .let { answersContainer.addView(it) }
            }
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.question, this)
    }

    private fun onAnswerClicked(answerView: ClosedAnswerView) {
        answersContainer.children.forEach {
            it as ClosedAnswerView
            it.isChecked = (it == answerView)
        }
    }
}
