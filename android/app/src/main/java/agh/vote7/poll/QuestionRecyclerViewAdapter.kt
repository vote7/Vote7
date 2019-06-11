package agh.vote7.poll

import agh.vote7.poll.question.AbstractQuestionView
import agh.vote7.poll.question.ClosedQuestionView
import agh.vote7.poll.question.OpenQuestionView
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class QuestionRecyclerViewAdapter(
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<QuestionRecyclerViewAdapter.ViewHolder>() {
    var questions: List<QuestionViewModel> = emptyList()
        set(value) {
            val diff = computeDiff(field, value)
            field = value
            diff.dispatchUpdatesTo(this)
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
        questionViewModel.status.observe(lifecycleOwner, Observer {
            holder.view.status = it
        })
        questionViewModel.isEditable.observe(lifecycleOwner, Observer {
            holder.view.isEditable = it
        })
        questionViewModel.currentAnswer.observe(lifecycleOwner, Observer {
            holder.view.currentAnswer = it
        })
        holder.view.onAnswerChanged = questionViewModel::onAnswerChanged

        if (holder.view is ClosedQuestionView) {
            holder.view.answers = questionViewModel.closedAnswers
        }
    }

    class ViewHolder(val view: AbstractQuestionView) : RecyclerView.ViewHolder(view)

    companion object {
        private const val VIEW_TYPE_OPEN_QUESTION_VIEW = 1
        private const val VIEW_TYPE_CLOSED_QUESTION_VIEW = 2
    }
}

private fun <T> computeDiff(oldItems: List<T>, newItems: List<T>): DiffUtil.DiffResult =
    DiffUtil.calculateDiff(object : DiffUtil.Callback() {
        override fun getOldListSize(): Int =
            oldItems.size

        override fun getNewListSize(): Int =
            newItems.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldItems[oldItemPosition] == newItems[newItemPosition]

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            areItemsTheSame(oldItemPosition, newItemPosition)
    })