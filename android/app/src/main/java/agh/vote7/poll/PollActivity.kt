package agh.vote7.poll

import agh.vote7.R
import agh.vote7.utils.DependencyProvider
import agh.vote7.utils.getViewModel
import agh.vote7.utils.observeEvent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.navArgs
import kotlinx.android.synthetic.main.activity_poll.*

class PollActivity : AppCompatActivity() {
    private val args by navArgs<PollActivityArgs>()

    private lateinit var viewModel: PollViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poll)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val questionAdapter = QuestionRecyclerViewAdapter(this)
        questionRecyclerView.adapter = questionAdapter

        viewModel = getViewModel { DependencyProvider.pollViewModel(args.pollId) }

        viewModel.title.observe(this, Observer {
            toolbar.title = it
        })

        viewModel.questions.observe(this, Observer {
            questionAdapter.questions = it
        })

        viewModel.showSnackbar.observeEvent(this, Observer {
            Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.showConfirmationModal.observeEvent(this, Observer {
            AlertDialog.Builder(this)
                .setMessage(it.content)
                .setCancelable(true)
                .setPositiveButton("OK") { _, _ -> it.onConfirmClicked() }
                .setNegativeButton("Cancel", null)
                .show()
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return true
    }
}
