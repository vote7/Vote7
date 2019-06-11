package agh.vote7.poll.question

import agh.vote7.R
import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.question.view.*

abstract class AbstractQuestionView(context: Context) : FrameLayout(context) {
    var question: String = ""
        set(value) {
            field = value
            contentTextView.text = value
        }

    var status: String = ""
        set(value) {
            field = value
            statusTextView.text = value
            statusTextView.isVisible = value.isNotEmpty()
        }

    abstract var currentAnswer: String?

    var onAnswerChanged: (String?) -> Unit = {}

    abstract var isEditable: Boolean

    init {
        LayoutInflater.from(context).inflate(R.layout.question, this)
    }
}
