package agh.vote7.poll

import agh.vote7.data.model.Question
import agh.vote7.poll.question.QuestionView
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class QuestionRecyclerViewAdapter : RecyclerView.Adapter<QuestionRecyclerViewAdapter.ViewHolder>() {
    var questions: List<Question> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = QuestionView(parent.context)
        view.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = questions[position]
        holder.view.question = item.content
        holder.view.setOnClickListener { /* TODO */ }
    }

    override fun getItemCount(): Int = questions.size

    class ViewHolder(val view: QuestionView) : RecyclerView.ViewHolder(view)
}
