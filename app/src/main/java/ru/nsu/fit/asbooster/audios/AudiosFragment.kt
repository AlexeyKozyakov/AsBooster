package ru.nsu.fit.asbooster.audios

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
        val component = activity.component.audiosFragmentComponentBuilder()
            .fragment(this)
            .build()
        presenter = component.getPresenter()
        val view = inflater.inflate(R.layout.fragment_audios, container, false)
        viewHolder = AudiosViewHolder(view)
        presenter.init()
        return view
    }

    override fun setPlaceholderText(text: String) {
        viewHolder.placeholderText.text = text
    }

    private class AudiosViewHolder(root: View) {
        val placeholderText: TextView = root.findViewById(R.id.text_placeholder)
    }

}