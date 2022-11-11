package com.example.theenglishpremierleague.ui.main

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.theenglishpremierleague.R
import com.example.theenglishpremierleague.databinding.OneMatchItemBinding
import com.example.theenglishpremierleague.ui.data.local.Images
import com.example.theenglishpremierleague.ui.data.local.Match
import java.text.SimpleDateFormat
import java.util.*

class MatchAdapter(val fragment: MatchesFragment, private var items: List<Match>) :
    RecyclerView.Adapter<MatchAdapter.ViewHolder>() {
    private var idsLocalList = listOf<Long>()
    private var isFromLocal = false
    private lateinit var images: List<Images>


    constructor(
        fragment: MatchesFragment,
        items: List<Match>,
        idsList: MutableList<Long>,
        b: Boolean
    ) : this(fragment, items) {
        idsLocalList = idsList
        isFromLocal = b
    }

    constructor(
        matchesFragment: MatchesFragment,
        items: List<Match>,
        imagesList: List<Images>,
        idsList: MutableList<Long>,
        b: Boolean
    )
            : this(matchesFragment, items) {
        idsLocalList = idsList
        isFromLocal = b
        images = imagesList
    }




    class ViewHolder(@NonNull val itemBinding: OneMatchItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            OneMatchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val item = items[position]
        holder.itemBinding.homeTeam.text = item.homeTeamName
        holder.itemBinding.awayTeam.text = item.awayTeamName
        if (item.status.equals("FINISHED")) {
            holder.itemBinding.result.text = item.homeTeamScore + "" + "-" + "" + item.awayTeamScore
        } else {
            holder.itemBinding.result.text = convertDateFormat(item.date)
        }

        if (item.isFav) {
            holder.itemBinding.fav.setBackgroundResource(R.drawable.ic_favorite_red_24dp)
        }

        Log.i("found",images.size.toString())

        for (id in 0 until images.size) {
            if (images[id].id == item.homeTeamId) {
                val media = images[id].crest
                if (media !== null) {
                    Glide.with(fragment)
                        .load(media)
                        .into(holder.itemBinding.homeImage)
                } else {

                    //  imageview.setImageResource(R.drawable.ic_launcher_background)
                }
            }
            else{

            }

        }

        // item click
        holder.itemBinding.fav.setOnClickListener { view ->


//                if (idsLocalList.size > 0) {
//                    for (id in 0 until idsLocalList.size) {
//                        if (idsLocalList[id] == item.id) {
//                            if (isFromLocal) {
//                                // found item so will remove from local db
//                                fragment.removeFromFav(item)
//
//                            } else {
//                                fragment.removeFromFav(item)
//                                holder.itemBinding.fav.setBackgroundResource(R.drawable.ic_favorite_shadow_24dp)
//                            }
//                        } else {
//
//                            // item not found so will added
//                            if(item.isFav == false){
//                                fragment.addToFav(item)
//                                holder.itemBinding.fav.setBackgroundResource(R.drawable.ic_favorite_red_24dp)
//                                fragment.update(item)
//                            }
//
//                        }
//                    }
//                } else {
//                    if(item.isFav == false){
//                        fragment.addToFav(item)
//                        holder.itemBinding.fav.setBackgroundResource(R.drawable.ic_favorite_red_24dp)
//                        fragment.update(item)
//                    }
//
//                }


            if (!item.isFav) {
                //  holder.itemBinding.fav.setBackgroundResource(R.drawable.ic_favorite_red_24dp)
                fragment.addToFav(item)
                fragment.update(item)
            }

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

