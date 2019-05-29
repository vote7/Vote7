package agh.vote7.poll.question

import agh.vote7.R
import android.content.Context
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.answer_open.view.*

class OpenAnswerView(context: Context) : FrameLayout(context) {
    var text: String
        get() = editText.text.toString()
        set(value) {
            editText.setText(value)
        }

    var onSubmitted: () -> Unit = {}

    var isEditable: Boolean
        get() = editText.isEnabled
        set(value) {
            editText.isEnabled = value
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.answer_open, this)

        editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                onSubmitted()
                true
            } else {
                false
            }
        }
    }
}

