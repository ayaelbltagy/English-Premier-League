package com.example.theenglishpremierleague.ui.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.theenglishpremierleague.R
import com.example.theenglishpremierleague.databinding.OneMatchItemBinding
import com.example.theenglishpremierleague.ui.data.local.Favorite
import com.example.theenglishpremierleague.ui.data.local.Match
import java.text.SimpleDateFormat
import java.util.*


class MatchAdapter() : RecyclerView.Adapter<MatchAdapter.ViewHolder>() {


    private var isFavList = false
    private val FADE_DURATION = 1000 //FADE_DURATION in milliseconds
    private val index_row = -1
    private lateinit var fragment: MatchesFragment
    private var items: List<Match> = ArrayList()
    private var favList: List<Favorite> = ArrayList()

    constructor(fragment: MatchesFragment, items: List<Match>, b: Boolean,pageNumber :Int) : this() {
        isFavList = b
        this.fragment = fragment
        this.items = items
    }

    constructor(fragment: MatchesFragment, favList: List<Favorite>, b: Boolean) : this() {
        isFavList = b
        this.fragment = fragment
        this.favList = favList
    }


    class ViewHolder(@NonNull val itemBinding: OneMatchItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            OneMatchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        if (items.size > 0 && !isFavList) {
            // show list off all matches
            val item = items[position]
            holder.itemBinding.homeTeam.text = item.homeTeamName
            holder.itemBinding.awayTeam.text = item.awayTeamName
            if (item.status.equals("FINISHED")) {
                holder.itemBinding.result.text =
                    item.homeTeamScore + "" + "-" + "" + item.awayTeamScore
            } else {
                holder.itemBinding.result.text = convertDateFormat(item.date)
            }

            if (item.isFav) {
                //  holder.itemBinding.fav.setBackgroundResource(R.drawable.ic_favorite_red_24dp)
            }

            var homeImageURL = "https://crests.football-data.org/" + item.homeTeamId + ".png"
            Glide.with(fragment).load(homeImageURL).into(holder.itemBinding.homeImage)
              var awayImageURL = "https://crests.football-data.org/" + item.awayTeamId + ".png"
            Glide.with(fragment).load(awayImageURL).into(holder.itemBinding.awayImage)
          }

        else {
            // show only fav matches
            val favList = favList[position]
            holder.itemBinding.homeTeam.text = favList.homeTeamName
            holder.itemBinding.awayTeam.text = favList.awayTeamName
            if (favList.status.equals("FINISHED")) {
                holder.itemBinding.result.text =
                    favList.homeTeamScore + "" + "-" + "" + favList.awayTeamScore
            } else {
                holder.itemBinding.result.text = convertDateFormat(favList.date)
            }

            holder.itemBinding.fav.visibility = View.VISIBLE
            holder.itemBinding.fav.setBackgroundResource(R.drawable.ic_favorite_red_24dp)

            var homeImageURL = "https://crests.football-data.org/" + favList.homeTeamId + ".png"
            Glide.with(fragment).load(homeImageURL).into(holder.itemBinding.homeImage)
            var awayImageURL = "https://crests.football-data.org/" + favList.awayTeamId + ".png"
            Glide.with(fragment).load(awayImageURL).into(holder.itemBinding.awayImage)

        }


        // item click
        holder.itemBinding.root.setOnClickListener { view ->
            // index_row == position

            if (isFavList) {
                // remove this item from fav
                fragment.removeFromFav(favList[position].id)
            } else {
                if (!items[position].isFav) {
                    // holder.itemBinding.fav.setBackgroundResource(R.drawable.ic_favorite_red_24dp)
                    // add this item to fav list
                    val modelFav = Favorite(
                        items[position].id,
                        items[position].status,
                        items[position].date,
                        items[position].homeTeamScore,
                        items[position].awayTeamScore,
                        items[position].homeTeamName,
                        items[position].homeTeamId,
                        items[position].awayTeamName,
                        items[position].awayTeamId,
                        true
                    )
                    fragment.addToFav(modelFav)
                    // mark in all matches list that is item is fav only view
                    fragment.update(true, items[position].id)
                } else {
                    //  holder.itemBinding.fav.setBackgroundResource(R.drawable.ic_favorite_shadow_24dp)
                    fragment.update(false, items[position].id)

                }
            }
        }
    }


    override fun getItemCount(): Int {
        if (isFavList) {
            return favList.size
        } else {
            return items.size
        }
    }


    fun convertDateFormat(input: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        inputFormat.timeZone = TimeZone.getTimeZone("GMT")
        val date = inputFormat.parse(input)
        val outputFormat = SimpleDateFormat("hh:mm", Locale.US)
        outputFormat.timeZone = TimeZone.getDefault()
        return outputFormat.format(date)
    }

    private fun setFadeAnimation(view: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = FADE_DURATION.toLong()
        view.startAnimation(anim)
    }
}

