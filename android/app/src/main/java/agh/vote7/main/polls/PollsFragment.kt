package agh.vote7.main.polls

import agh.vote7.R
import agh.vote7.data.model.PollStatus
import agh.vote7.utils.DependencyProvider
import agh.vote7.utils.getViewModel
import agh.vote7.utils.observeEvent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_polls.*

abstract class PollsFragment : Fragment() {
    private lateinit var viewModel: PollsViewModel

    protected abstract val pollStatus: PollStatus

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_polls, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = getViewModel { DependencyProvider.pollsViewModel(pollStatus) }

        val pollAdapter = PollRecyclerViewAdapter(onClick = viewModel::onPollClicked)
        pollRecyclerView.adapter = pollAdapter

        viewModel.polls.observe(this, Observer {
            pollAdapter.polls = it
        })

        viewModel.showSnackbar.observeEvent(this, Observer {
            Snackbar.make(view!!, it, Snackbar.LENGTH_SHORT).show()
        })

        viewModel.navigateToPollView.observeEvent(this, Observer {
            findNavController()
                .navigate(OpenPollsFragmentDirections.actionPollsFragmentToPollActivity(it))
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause()
    }
}
