package agh.vote7.poll.question

import agh.vote7.R
import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.answer_open.view.*

class OpenAnswerView(context: Context) : FrameLayout(context) {
    var text: String
        get() = editText.text.toString()
        set(value) {
            editText.setText(value)
        }

    var onTextChanged: (String) -> Unit = {}

    var isEditable: Boolean
        get() = editText.isEnabled
        set(value) {
            editText.isEnabled = value
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.answer_open, this)

        editText.addTextChangedListener(afterTextChanged = {
            onTextChanged(text)
        })
    }
}
