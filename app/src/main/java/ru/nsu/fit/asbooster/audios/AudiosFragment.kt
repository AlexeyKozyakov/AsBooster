package ru.nsu.fit.asbooster.audios

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import ru.nsu.fit.asbooster.MainActivity
import ru.nsu.fit.asbooster.R

/**
 * Fragment for interacting with VK audios.
 */
class AudiosFragment : Fragment(), AudiosView {

    private lateinit var viewHolder: AudiosViewHolder
    private lateinit var presenter: AudiosPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val activity = requireActivity() as MainActivity
        val component = activity.component.value.audiosFragmentComponentBuilder()
            .fragment(this)
            .build()
        presenter = component.getPresenter()
        val view = inflater.inflate(R.layout.fragment_audios, container, false)
        viewHolder = AudiosViewHolder(view)
        initLoginClickListener()
        presenter.onCreate()
        return view
    }

    override fun showProgress() {
        viewHolder.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        viewHolder.progressBar.visibility = View.GONE
    }

    override fun showLoginButton() {
        viewHolder.loginButton.visibility = View.VISIBLE
    }

    override fun hideLoginButton() {
        viewHolder.loginButton.visibility = View.GONE
    }

    private fun initLoginClickListener() {
        viewHolder.loginButton.setOnClickListener {
            presenter.login()
        }
    }

    private class AudiosViewHolder(root: View) {
        val progressBar: ProgressBar = root.findViewById(R.id.audios_progress_bar)
        val loginButton: Button = root.findViewById(R.id.button_audios_login)
    }

}