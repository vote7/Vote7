package agh.vote7.main.profile

import agh.vote7.MainApplication
import agh.vote7.R
import agh.vote7.login.data.login.LoginDataSource
import agh.vote7.login.data.login.LoginRepository
import agh.vote7.login.data.model.LoggedInUser
import agh.vote7.login.ui.login.LoginActivity
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val name = view.findViewById<TextView>(R.id.textViewName)
        val email = view.findViewById<TextView>(R.id.textViewEmail)
        val logout = view.findViewById<MaterialButton>(R.id.buttonLogOut)

        val user = MainApplication.loggedInUser
        name.text = user.name + " " + user.surname
        email.text = user.email

        logout.setOnLongClickListener{
            LoginDataSource.logout()
            Log.e("logout", "xd")
            startActivity(Intent(context, LoginActivity::class.java))
            true
        }

        return view
    }
}
