package com.dngwjy.githubfavourite.abstraction

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class RvAdapter<T, B : ViewBinding>(
        private var data: List<T>,
        private val listener: (T) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var binding: B
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = getViewBinding(parent, viewType)
        return viewHolder(binding, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as Binder<T>).bindData(data[position], listener, position)
    }

    override fun getItemCount(): Int = data.size
    abstract fun getViewBinding(parent: ViewGroup, viewType: Int): B
    override fun getItemViewType(position: Int): Int {
        return layoutId(position, data[position])
    }

    protected abstract fun layoutId(position: Int, obj: T): Int
    abstract fun viewHolder(binding: B, viewType: Int): RecyclerView.ViewHolder
    interface Binder<T> {
        fun bindData(data: T, listen: (T) -> Unit, position: Int)
    }

    fun refreshData(newData: List<T>) {
        data = newData
        notifyDataSetChanged()
    }
}