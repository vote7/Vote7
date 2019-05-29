package agh.vote7.poll

import agh.vote7.poll.question.AbstractQuestionView
import agh.vote7.poll.question.ClosedQuestionView
import agh.vote7.poll.question.OpenQuestionView
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView

class QuestionRecyclerViewAdapter(
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<QuestionRecyclerViewAdapter.ViewHolder>() {
    var questions: List<QuestionViewModel> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = questions.size

    override fun getItemViewType(position: Int): Int =
        if (questions[position].isOpen) {
            VIEW_TYPE_OPEN_QUESTION_VIEW
        } else {
            VIEW_TYPE_CLOSED_QUESTION_VIEW
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = when (viewType) {
            VIEW_TYPE_OPEN_QUESTION_VIEW -> OpenQuestionView(parent.context)
            VIEW_TYPE_CLOSED_QUESTION_VIEW -> ClosedQuestionView(parent.context)
            else -> throw AssertionError()
        }
        view.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val questionViewModel = questions[position]

        holder.view.question = questionViewModel.content
        questionViewModel.isEditable.observe(lifecycleOwner, Observer {
            holder.view.isEditable = it
        })

        when (holder.view) {
            is ClosedQuestionView -> {
                holder.view.answers = questionViewModel.closedAnswers
                holder.view.onAnswerClicked = questionViewModel::onClosedAnswerClicked
                questionViewModel.selectedClosedAnswer.observe(lifecycleOwner, Observer {
                    holder.view.selectedAnswer = it
                })
            }
            is OpenQuestionView -> {
                holder.view.onSubmitted = questionViewModel::onOpenAnswerSubmitted
            }
        }
    }


    class ViewHolder(val view: AbstractQuestionView) : RecyclerView.ViewHolder(view)

    companion object {
        private const val VIEW_TYPE_OPEN_QUESTION_VIEW = 1
        private const val VIEW_TYPE_CLOSED_QUESTION_VIEW = 2
    }
}
