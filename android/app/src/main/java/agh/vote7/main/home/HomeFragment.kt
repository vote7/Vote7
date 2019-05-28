package agh.vote7.main.home

import agh.vote7.R
import agh.vote7.utils.DependencyProvider
import agh.vote7.utils.observeEvent
import agh.vote7.utils.viewModelProviderFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelProviderFactory(DependencyProvider::homeViewModel))
            .get()

        val pollAdapter = PollRecyclerViewAdapter(onClick = viewModel::onPollClicked)
        pollRecyclerView.adapter = pollAdapter

        viewModel.polls.observe(this, Observer {
            pollAdapter.polls = it
        })

        viewModel.showSnackbar.observeEvent(this, Observer {
            Snackbar.make(view!!, it, Snackbar.LENGTH_SHORT).show()
        })

        viewModel.navigateToPollView.observeEvent(this, Observer {
            Navigation.findNavController(view!!).navigate(R.id.votingActivity)
        })
    }
}
