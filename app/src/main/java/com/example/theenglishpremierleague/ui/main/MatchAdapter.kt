package com.example.theenglishpremierleague.ui.main

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.theenglishpremierleague.R
import com.example.theenglishpremierleague.databinding.OneMatchItemBinding
import com.example.theenglishpremierleague.ui.data.local.MatchEntity
import java.text.SimpleDateFormat
import java.util.*

class MatchAdapter(val fragment: MatchesFragment, private var items: List<MatchEntity>) :
    RecyclerView.Adapter<MatchAdapter.ViewHolder>() {
    private var isFav: Boolean = false
    private var idsLocalList = listOf<Long>()
    private var isFromLocal = false
    private var row_index = -1
    private var row = -2

    constructor(
        fragment: MatchesFragment,
        items: List<MatchEntity>,
        idsList: MutableList<Long>,
        b: Boolean
    ) : this(fragment, items) {
        idsLocalList = idsList
        isFromLocal = b
    }


    class ViewHolder(@NonNull val itemBinding: OneMatchItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            OneMatchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        row = position
        val item = items[position]
        holder.itemBinding.homeTeam.text = item.homeTeamName
        holder.itemBinding.awayTeam.text = item.awayTeamName
        if (item.status.equals("FINISHED")) {
            holder.itemBinding.result.text = item.homeTeamScore + "" + "_" + "" + item.awayTeamScore
        } else {
            holder.itemBinding.result.text = convertDateFormat(item.date)
        }


        if (isFromLocal) {
            holder.itemBinding.fav.setBackgroundResource(R.drawable.ic_favorite_red_24dp)
        } else {
            // to mark items already saved before
            if (idsLocalList.size > 0) {
                for (i in 0 until idsLocalList.size) {
                    if (idsLocalList[i] == item.id) {

                            holder.itemBinding.fav.setBackgroundResource(R.drawable.ic_favorite_red_24dp)

                    }

                }
            }
        }


        // item click
        holder.itemBinding.root.setOnClickListener { view ->
            row_index = position
            notifyDataSetChanged()

        }
        // clicked
        if (row_index == position) {
           // holder.itemBinding.fav.setBackgroundResource(R.drawable.ic_favorite_red_24dp)
            if (idsLocalList.size > 0) {
                for (id in 0 until idsLocalList.size) {
                    if (idsLocalList[id] == item.id) {
                        if (isFromLocal) {
                            // found item so will remove from local db
                            fragment.removeFromFav(item)

                        } else {
                            // fragment.removeFromFav(item)
                            Log.i("fromLocaltest3", "test3")

                            holder.itemBinding.fav.setBackgroundResource(R.drawable.ic_favorite_shadow_24dp)
                        }
                    } else {
                        Log.i("fromLocaltest3", "test")

                        // item not found so will added
                        fragment.addToFav(item)
                        holder.itemBinding.fav.setBackgroundResource(R.drawable.ic_favorite_red_24dp)
                    }
                }
            } else {
                fragment.addToFav(item)
                holder.itemBinding.fav.setBackgroundResource(R.drawable.ic_favorite_red_24dp)
            }

        }
        // not clicked
        else {


        }

    }


    override fun getItemCount(): Int {
        return items.size
    }


    fun convertDateFormat(input: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        inputFormat.timeZone = TimeZone.getTimeZone("GMT")
        val date = inputFormat.parse(input)
        val outputFormat = SimpleDateFormat("hh:mm", Locale.US)
        outputFormat.timeZone = TimeZone.getDefault()
        return outputFormat.format(date)
    }
}

