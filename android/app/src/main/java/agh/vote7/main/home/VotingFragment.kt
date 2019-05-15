package agh.vote7.main.home

import agh.vote7.R
import agh.vote7.data.VotingRepository
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView

class VotingFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_voting_list, container, false) as RecyclerView

        view.adapter = VotingRecyclerViewAdapter(
            VotingRepository.items,
            onClick = {
                Navigation.findNavController(this.view!!).navigate(R.id.votingActivity)
            }
        )

        return view
    }
}
