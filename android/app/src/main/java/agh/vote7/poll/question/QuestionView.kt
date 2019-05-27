package agh.vote7.poll.question

import agh.vote7.R
import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.question.view.*

class QuestionView(context: Context) : FrameLayout(context) {
    var question: String = ""
        set(value) {
            field = value
            contentTextView.text = value
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.question, this)
    }
}
