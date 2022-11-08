package com.example.theenglishpremierleague.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.theenglishpremierleague.R
import com.example.theenglishpremierleague.databinding.OneMatchItemBinding
import com.example.theenglishpremierleague.ui.data.local.MatchEntity

class MatchAdapter(private var items: List<MatchEntity>) :
    RecyclerView.Adapter<MatchAdapter.ViewHolder>() {

    private var isFav: Boolean = false

    class ViewHolder(@NonNull val itemBinding: OneMatchItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = OneMatchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.itemBinding.result.text = item.status
        holder.itemBinding.fav.setOnClickListener { view ->
            if (!isFav) {
                isFav = true
                holder.itemBinding.fav.setBackgroundResource(R.drawable.ic_favorite_red_24dp)
                holder.itemBinding.fav.setSelected(true)
            } else {
                isFav = false
                holder.itemBinding.fav.setBackgroundResource(R.drawable.ic_favorite_shadow_24dp)
                holder.itemBinding.fav.setSelected(false)

            }

        }
    }


    override fun getItemCount(): Int {
        return items.size
    }

}