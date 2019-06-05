package agh.vote7.poll.question

import android.content.Context
import kotlinx.android.synthetic.main.question.view.*

class ClosedQuestionView(context: Context) : AbstractQuestionView(context) {
    override var isEditable: Boolean = false
        set(value) {
            field = value
            recreateAnswers()
        }

    var answers: List<String> = emptyList()
        set(value) {
            field = value
            recreateAnswers()
        }

    override var currentAnswer: String? = null
        set(value) {
            field = value
            recreateAnswers()
        }

    private fun recreateAnswers() {
        answersContainer.removeAllViews()

        for (answer in answers) {
            ClosedAnswerView(context).also {
                it.content = answer
                it.isEditable = isEditable
                it.isChecked = (it.content == currentAnswer)
                it.onClicked = {
                    currentAnswer = answer
                    onAnswerChanged(it.content)
                }

                answersContainer.addView(it)
            }
        }
    }
}
