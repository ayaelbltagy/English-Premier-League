package com.example.theenglishpremierleague.ui.main

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.theenglishpremierleague.databinding.OneMatchItemBinding
import com.example.theenglishpremierleague.ui.data.local.MatchEntity
import com.google.android.material.snackbar.Snackbar

class MatchAdapter(private var items: List<MatchEntity>) :
    RecyclerView.Adapter<MatchAdapter.ViewHolder>() {


    class ViewHolder(@NonNull val itemBinding: OneMatchItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            OneMatchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.itemBinding.name.text = item.status
        holder.itemBinding.fav.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
           Log.i("adapterID",item.id.toString())
            favBtn.setBackgroundResource(R.drawable.ic_favorite_red_24dp)
            favBtn.setSelected(true)}


    }


    override fun getItemCount(): Int {
        return items.size
    }

    class ClickListener(val clickListener: (pro: MatchEntity) -> Unit) {
        fun onItemClick(pro: MatchEntity) = clickListener(pro)
    }
}