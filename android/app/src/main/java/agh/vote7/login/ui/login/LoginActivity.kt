package agh.vote7.login.ui.login

import agh.vote7.R
import agh.vote7.login.ui.register.RegisterActivity
import agh.vote7.main.MainActivity
import agh.vote7.utils.DependencyProvider
import agh.vote7.utils.observeEvent
import agh.vote7.utils.viewModelProviderFactory
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginViewModel = ViewModelProviders.of(this, viewModelProviderFactory(DependencyProvider::loginViewModel))
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            username.error = loginState.usernameError?.let(::getString)
            password.error = loginState.passwordError?.let(::getString)
        })

        loginViewModel.loadingVisible.observe(this, Observer {
            loading.isVisible = it
            groupForm.isVisible = !it
        })

        loginViewModel.showToast.observeEvent(this, Observer {
            Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
        })

        loginViewModel.navigateToRegisterView.observe(this, Observer {
            startActivity(Intent(this, RegisterActivity::class.java))
        })

        loginViewModel.navigateToMainView.observe(this, Observer {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        })

        username.afterTextChanged { loginDataChanged() }
        password.afterTextChanged { loginDataChanged() }

        password.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                login()
            }
            false
        }

        login.setOnClickListener { login() }

        goToRegister.setOnClickListener { loginViewModel.onRegisterClicked() }
    }

    private fun loginDataChanged() {
        loginViewModel.loginDataChanged(
            username.text.toString(),
            password.text.toString()
        )
    }

    private fun login() {
        loginViewModel.login(
            username.text.toString(),
            password.text.toString()
        )
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}
