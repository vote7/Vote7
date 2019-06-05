package agh.vote7.poll.question

import android.content.Context
import kotlinx.android.synthetic.main.question.view.*

class OpenQuestionView(context: Context) : AbstractQuestionView(context) {
    private val answerView = OpenAnswerView(context)
        .also {
            it.onTextChanged = { onAnswerChanged(currentAnswer) }
            answersContainer.addView(it)
        }

    override var isEditable: Boolean
        get() = answerView.isEditable
        set(value) {
            answerView.isEditable = value
        }

    override var currentAnswer: String?
        get() = answerView.text
        set(value) {
            if (answerView.text != value) {
                answerView.text = value ?: ""
            }
        }
}
