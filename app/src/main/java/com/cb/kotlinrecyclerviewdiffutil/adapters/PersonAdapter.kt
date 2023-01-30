package com.cb.kotlinrecyclerviewdiffutil.adapters

import android.content.ContentValues.TAG
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cb.kotlinrecyclerviewdiffutil.databinding.ItemRowBinding
import com.cb.kotlinrecyclerviewdiffutil.model.Person

class PersonAdapter : RecyclerView.Adapter<PersonAdapter.ViewHolder>() {


    private lateinit var binding: ItemRowBinding

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        fun setData(person: Person) {
            binding.apply {
                tvId.text = person.id.toString()
                tvName.text = person.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(items[position])
        holder.setIsRecyclable(false)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {

            val bundle = payloads[0] as Bundle


            Log.d(TAG, "onBindViewHolder: ${bundle.getInt("ID")}")

            if (bundle.containsKey("NAME")) {
                val name = bundle.getString("NAME")
                binding.tvName.text = name
            }
        }
    }

    class MyDifferCallback(
        private var oldItems: List<Person>,
        private var newItems: List<Person>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldItems.size
        }

        override fun getNewListSize(): Int {
            return newItems.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItems[oldItemPosition].id == newItems[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItems[oldItemPosition].name == newItems[newItemPosition].name
        }


        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            val oldItem = oldItems[oldItemPosition]
            val newItem = newItems[newItemPosition]

            return if (oldItem.name == newItem.name) {
                super.getChangePayload(oldItemPosition, newItemPosition)
            } else {
                val diff = Bundle()
                diff.putString("NAME", newItem.name)
                diff.putInt("ID", newItem.id)
                diff
            }
        }
    }

    private var items = mutableListOf<Person>()

    fun setItems(newItems: List<Person>) {
        val result = DiffUtil.calculateDiff(MyDifferCallback(items, newItems))
        items.clear()
        items.addAll(newItems)
        result.dispatchUpdatesTo(this)
    }


}