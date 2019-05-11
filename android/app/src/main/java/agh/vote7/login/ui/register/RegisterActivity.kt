package agh.vote7.login.ui.register

import agh.vote7.R
import agh.vote7.login.ui.login.LoginActivity
import agh.vote7.login.ui.login.afterTextChanged
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.*
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), LifecycleOwner {

    private lateinit var registerViewModel: RegisterViewModel
    private var lifecycle: LifecycleRegistry = LifecycleRegistry(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        lifecycle.markState(Lifecycle.State.INITIALIZED)

        setContentView(R.layout.activity_login)

        val name = findViewById<EditText>(R.id.name)
        val surname = findViewById<EditText>(R.id.surnname)
        val email = findViewById<EditText>(R.id.registerEmail)
        val password = findViewById<EditText>(R.id.registerPassword)
        val register = findViewById<Button>(R.id.register)
        val loading = findViewById<ProgressBar>(R.id.loading)


        registerViewModel = ViewModelProviders.of(this, RegisterViewModelVactory())
            .get(RegisterViewModel::class.java)

        registerViewModel.registerFormState.observe(this@RegisterActivity, Observer {
            val registerState = it ?: return@Observer

            register.isEnabled = registerState.isDataValid

            if (registerState.nameError != null)
                name.error = getString(registerState.nameError)
            if (registerState.surnameError != null)
                surname.error = getString(registerState.surnameError)
            if (registerState.emailError != null)
                email.error = getString(registerState.emailError)
            if (registerState.passwordError != null)
                password.error = getString(registerState.passwordError)
        })


        registerViewModel.registerResult.observe(this@RegisterActivity, Observer {
            val registerResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (registerResult.error != null)
                showRegisterFailed(registerResult.error)
            if (registerResult.success != null)
                updateUiWithUser(registerResult.success)

            setResult(Activity.RESULT_OK)
            finish()
        })

        name.afterTextChanged {
            registerViewModel.registrationDataChanged(
                name.text.toString(),
                surnname.text.toString(),
                email.text.toString(),
                password.text.toString()
            )
        }
        surname.afterTextChanged {
            registerViewModel.registrationDataChanged(
                name.text.toString(),
                surnname.text.toString(),
                email.text.toString(),
                password.text.toString()
            )
        }
        email.afterTextChanged {
            registerViewModel.registrationDataChanged(
                name.text.toString(),
                surnname.text.toString(),
                email.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                registerViewModel.registrationDataChanged(
                    name.text.toString(),
                    surnname.text.toString(),
                    email.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener{_, actionId, _ ->
                when (actionId){
                EditorInfo.IME_ACTION_DONE ->
                registerViewModel.register(
                    name.text.toString(),
                    surnname.text.toString(),
                    email.text.toString(),
                    password.text.toString()
                )
                }
                false
            }

            register.setOnClickListener{
                loading.visibility = View.VISIBLE
                registerViewModel.register(
                    name.text.toString(),
                    surnname.text.toString(),
                    email.text.toString(),
                    password.text.toString())
                val intent = Intent(context, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        lifecycle.markState(Lifecycle.State.CREATED)
        lifecycle.markState(Lifecycle.State.STARTED)
    }

    private fun updateUiWithUser(model: RegisteredUserView) {
        val welcome = "Registered successfully - "
        val displayName = model.displayName
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showRegisterFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }

    override fun getLifecycle(): Lifecycle {
        return lifecycle
    }

}



