package agh.vote7


import agh.vote7.dummy.Voting
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_voting.view.*

class VotingRecyclerViewAdapter(
    private val values: List<Voting>,
    private val onClick: (Voting) -> Unit
) : RecyclerView.Adapter<VotingRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_voting, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.titleView.text = item.title
        holder.view.setOnClickListener {
            onClick(item)
        }
    }

    override fun getItemCount(): Int = values.size

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val titleView: TextView = view.title
    }
}
