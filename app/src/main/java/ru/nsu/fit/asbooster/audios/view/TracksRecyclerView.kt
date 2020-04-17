package ru.nsu.fit.asbooster.audios.view

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import ru.nsu.fit.asbooster.R

class TracksRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    var listener: Listener = object : Listener {}

    private var editable = false

    interface Listener {
        fun onMove(positionFrom: Int, positionTo: Int) = Unit

        fun onSwipe(position: Int) = Unit
    }

    init {
        processAttrs(attrs)

        setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context)

        if (editable) {
            initTouchHelper()
        }

        (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
    }

    private fun processAttrs(attrs: AttributeSet?) {
        val attrArray = context.obtainStyledAttributes(attrs, R.styleable.TracksRecyclerView)
        editable = attrArray.getBoolean(R.styleable.TracksRecyclerView_editable, false)
        attrArray.recycle()
    }

    private fun initTouchHelper() {
        val helper = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: ViewHolder,
                    target: ViewHolder
                ): Boolean {
                    listener.onMove(viewHolder.adapterPosition, target.adapterPosition)
                    return true
                }

                override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
                    listener.onSwipe(viewHolder.adapterPosition)
                }

            })
        helper.attachToRecyclerView(this)
    }

}
