package agh.vote7.main.home


import agh.vote7.R
import agh.vote7.data.model.Poll
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_poll.view.*

class PollRecyclerViewAdapter(
    private val onClick: (Poll) -> Unit
) : RecyclerView.Adapter<PollRecyclerViewAdapter.ViewHolder>() {

    var polls: List<Poll> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_poll, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = polls[position]
        holder.itemView.title.text = item.name
        holder.itemView.description.text = item.description
        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }

    override fun getItemCount(): Int = polls.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
