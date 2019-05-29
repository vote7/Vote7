package agh.vote7.poll.question

import agh.vote7.R
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.answer_closed.view.*

class ClosedAnswerView(context: Context) : FrameLayout(context) {
    var content: String = ""
        set(value) {
            field = value
            checkBox.text = value
        }

    var isChecked: Boolean = false
        set(value) {
            field = value
            checkBox.isChecked = value
            checkBox.isBold = value
        }

    var onClicked: () -> Unit = {}

    var isEditable: Boolean
        get() = checkBox.isEnabled
        set(value) {
            checkBox.isEnabled = value
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.answer_closed, this)
        setOnClickListener { onClicked() }
    }
}

private var TextView.isBold: Boolean
    get() = this.typeface.isBold
    @SuppressLint("WrongConstant")
    set(value) {
        if (this.typeface.isBold != value) {
            this.typeface = Typeface.create(this.typeface, this.typeface.style xor Typeface.BOLD)
        }
    }
