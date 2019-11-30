package ru.nsu.fit.asbooster.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import ru.nsu.fit.asbooster.App
import ru.nsu.fit.asbooster.R
import ru.nsu.fit.asbooster.services.LoginInfo

class LoginActivity : AppCompatActivity(), LoginView {

    private lateinit var presenter: LoginPresenter
    private lateinit var viewHolder: ViewHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val component = (application as App).component.value
            .loginActivityComponentBuilder()
            .activity(this)
            .build()
        setContentView(R.layout.activity_login)
        viewHolder = ViewHolder()
        presenter = component.getPresenter()
        initLoginClickListener()
    }

    override val loginInfo get() = LoginInfo(
        viewHolder.loginField.text.toString(),
        viewHolder.passwordField.text.toString()
    )

    override fun showMessage(@StringRes stringRes: Int) {
        Snackbar
            .make(viewHolder.content, stringRes, Snackbar.LENGTH_SHORT)
            .show()
    }

    override fun showProgress() {
        viewHolder.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        viewHolder.progressBar.visibility = View.GONE
    }

    private fun initLoginClickListener() {
        viewHolder.loginButton.setOnClickListener {
            presenter.login()
        }
    }

    private inner class ViewHolder {
        val content: View = findViewById(android.R.id.content)
        val loginField: EditText = findViewById(R.id.field_login)
        val passwordField: EditText = findViewById(R.id.field_password)
        val loginButton: Button = findViewById(R.id.button_login)
        val progressBar: ProgressBar = findViewById(R.id.progress_bar_login)
    }

}