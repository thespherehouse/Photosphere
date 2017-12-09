package com.suhel.photosphere.DataModels.Adapters

import android.support.v7.widget.RecyclerView
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.suhel.photosphere.DataModels.Types.BaseType
import com.suhel.photosphere.Views.ViewHolders.BaseViewHolder
import java.util.*

abstract class BaseAdapter<out T : Query, M : BaseType, V : BaseViewHolder<M>>(val query: T, val ascending: Boolean = false) : RecyclerView.Adapter<V>() {

    private val listener = object : ChildEventListener {

        override fun onCancelled(error: DatabaseError?) {
        }

        override fun onChildMoved(snapshot: DataSnapshot?, prevChildName: String?) {
        }

        override fun onChildChanged(snapshot: DataSnapshot?, prevChildName: String?) {
            snapshot?.let {
                updateElement(elementForDataSnapshot(it))
            }
        }

        override fun onChildAdded(snapshot: DataSnapshot?, prevChildName: String?) {
            snapshot?.let {
                if (ascending)
                    addElementAtEnd(elementForDataSnapshot(it))
                else
                    addElementAtStart(elementForDataSnapshot(it))
            }
        }

        override fun onChildRemoved(snapshot: DataSnapshot?) {
            snapshot?.let {
                removeElement(elementForDataSnapshot(it))
            }
        }

    }
    protected var data = ArrayList<M>()

    override fun getItemCount(): Int = data.size

    override fun getItemId(position: Int): Long = position.toLong()

    override fun onBindViewHolder(holder: V, position: Int) {
        holder.bindData(this.data[position])
    }

    private fun addElementAtEnd(element: M) {
        data.add(element)
        this.notifyItemInserted(data.size - 1)
    }

    private fun addElementAtStart(element: M) {
        data.add(0, element)
        this.notifyItemInserted(0)
    }

    private fun addElements(elements: Collection<M>, position: Int = 0) {
        data.addAll(position, elements)
        this.notifyItemRangeInserted(position, elements.count())
    }

    private fun removeElement(element: M) {
        val position = this.data.indexOfFirst { it.id == element.id }
        if (position != -1) {
            this.data.removeAt(position)
            this.notifyItemRemoved(position)
        }
        this.notifyDataSetChanged()
    }

    private fun removeElement(position: Int = 0) {
        if (position in 0 until this.data.size) {
            data.removeAt(position)
            this.notifyItemRemoved(position)
        }
    }

    private fun updateElement(element: M) {
        val position = this.data.indexOfFirst { it.id == element.id }
        if (position != -1) {
            this.data[position].copyFrom(element)
            this.notifyDataSetChanged()
        }
    }

    fun clear() {
        data.clear()
        this.notifyDataSetChanged()
    }

    fun attachDataSource() {
        this.clear()
        this.query.addChildEventListener(this.listener)
    }

    fun detachDataSource() {
        this.query.removeEventListener(this.listener)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        super.onAttachedToRecyclerView(recyclerView)
        attachDataSource()
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView?) {
        super.onDetachedFromRecyclerView(recyclerView)
        detachDataSource()
    }

    abstract fun elementForDataSnapshot(dataSnapshot: DataSnapshot): M

}