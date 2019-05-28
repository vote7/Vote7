package agh.vote7.poll.question

import agh.vote7.R
import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.answer_open.view.*

class OpenAnswerView(context: Context) : FrameLayout(context) {
    var text: String
        get() = editText.text.toString()
        set(value) {
            editText.setText(value)
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.answer_open, this)
    }
}

