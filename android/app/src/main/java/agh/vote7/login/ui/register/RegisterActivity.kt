package agh.vote7.login.ui.register

import agh.vote7.R
import agh.vote7.login.ui.login.LoginActivity
import agh.vote7.login.ui.login.afterTextChanged
import agh.vote7.utils.DependencyProvider
import agh.vote7.utils.observeEvent
import agh.vote7.utils.viewModelProviderFactory
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        registerViewModel = ViewModelProviders.of(this, viewModelProviderFactory(DependencyProvider::registerViewModel))
            .get(RegisterViewModel::class.java)

        registerViewModel.registerFormState.observe(this, Observer {
            val registerState = it ?: return@Observer

            register.isEnabled = registerState.isDataValid

            name.error = registerState.nameError?.let(::getString)
            surname.error = registerState.surnameError?.let(::getString)
            email.error = registerState.emailError?.let(::getString)
            password.error = registerState.passwordError?.let(::getString)
        })

        registerViewModel.showToast.observeEvent(this, Observer {
            Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
        })

        registerViewModel.navigateToMainView.observeEvent(this, Observer {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        })

        name.afterTextChanged { registrationDataChanged() }
        surname.afterTextChanged { registrationDataChanged() }
        email.afterTextChanged { registrationDataChanged() }
        password.afterTextChanged { registrationDataChanged() }

        password.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                register()
            }
            false
        }

        register.setOnClickListener { register() }
    }

    private fun registrationDataChanged() {
        registerViewModel.registrationDataChanged(
            name.text.toString(),
            surname.text.toString(),
            email.text.toString(),
            password.text.toString()
        )
    }

    private fun register() {
        registerViewModel.register(
            name.text.toString(),
            surname.text.toString(),
            email.text.toString(),
            password.text.toString()
        )
    }
}



