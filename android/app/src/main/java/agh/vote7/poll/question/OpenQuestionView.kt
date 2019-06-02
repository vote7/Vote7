package agh.vote7.poll.question

import android.content.Context
import kotlinx.android.synthetic.main.question.view.*

class OpenQuestionView(context: Context) : AbstractQuestionView(context) {
    private val answerView = OpenAnswerView(context)
        .also {
            it.onSubmitted = { onSubmitted(answer) }
            answersContainer.addView(it)
        }

    override var isEditable: Boolean
        get() = answerView.isEditable
        set(value) {
            answerView.isEditable = value
        }

    var answer: String
        get() = answerView.text
        set(value) {
            answerView.text = value
        }

    var onSubmitted: (String) -> Unit = {}
}
