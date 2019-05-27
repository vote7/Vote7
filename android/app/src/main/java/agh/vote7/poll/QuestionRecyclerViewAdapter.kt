package agh.vote7.poll

import agh.vote7.R
import agh.vote7.data.model.Question
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.question.view.*

class QuestionRecyclerViewAdapter : RecyclerView.Adapter<QuestionRecyclerViewAdapter.ViewHolder>() {
    var questions: List<Question> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.question, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = questions[position]
        holder.contentTextView.text = item.content
        holder.view.setOnClickListener { /* TODO */ }
    }

    override fun getItemCount(): Int = questions.size

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val contentTextView: TextView = view.contentTextView
    }
}
