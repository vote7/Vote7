package agh.vote7.main.profile

import agh.vote7.R
import agh.vote7.login.ui.login.LoginActivity
import agh.vote7.utils.observeEvent
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)

        viewModel.name.observe(this, Observer {
            textViewName.text = it
        })

        viewModel.email.observe(this, Observer {
            textViewEmail.text = it
        })

        viewModel.groupNames.observe(this, Observer { groups ->
            textViewGroups.text = groups.joinToString(separator = ", ")
                .takeIf { it.isNotEmpty() }
                ?: "(no groups)"
        })

        viewModel.snackbar.observe(this, Observer {
            Snackbar.make(view!!, it, Snackbar.LENGTH_SHORT).show()
        })

        viewModel.loading.observe(this, Observer { loading ->
            view!!.isInvisible = loading
        })

        viewModel.navigateToLoginView.observeEvent(this, Observer {
            startActivity(Intent(context, LoginActivity::class.java))
        })

        buttonLogOut.setOnClickListener {
            viewModel.onLogOutClicked()
        }
    }
}
