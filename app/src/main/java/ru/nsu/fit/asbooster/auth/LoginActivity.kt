package ru.nsu.fit.asbooster.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import ru.nsu.fit.asbooster.R
import ru.nsu.fit.asbooster.auth.di.DaggerLoginActivityComponent
import ru.nsu.fit.asbooster.services.LoginInfo

class LoginActivity : AppCompatActivity(), LoginView {

    private lateinit var presenter: LoginPresenter
    private lateinit var viewHolder: ViewHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val component = DaggerLoginActivityComponent.builder()
            .activity(this)
            .build()
        setContentView(R.layout.activity_login)
        viewHolder = ViewHolder()
        presenter = component.getPresenter()
    }

    override val loginInfo get() = LoginInfo(
        viewHolder.loginField.text.toString(),
        viewHolder.passwordField.text.toString()
    )

    private fun initLoginClickListener() {
        viewHolder.loginButton.setOnClickListener {
            presenter.login()
        }
    }

    private inner class ViewHolder {
        val loginField: EditText = findViewById(R.id.field_login)
        val passwordField: EditText = findViewById(R.id.field_password)
        val loginButton: Button = findViewById(R.id.button_login)
    }

}