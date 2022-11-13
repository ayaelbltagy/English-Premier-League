package com.example.theenglishpremierleague.ui.presentation

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.theenglishpremierleague.R
import com.example.theenglishpremierleague.databinding.OneMatchItemBinding
import com.example.theenglishpremierleague.ui.data.local.Favorite
import com.example.theenglishpremierleague.ui.data.local.Match
import java.text.SimpleDateFormat
import java.util.*


class MatchAdapter() : RecyclerView.Adapter<MatchAdapter.ViewHolder>() {


    private var isFavList = false
    private val FADE_DURATION = 1000 //FADE_DURATION in milliseconds
    private lateinit var fragment: MatchesFragment
    private var items: List<Match> = ArrayList()
    private var favList: List<Favorite> = ArrayList()

    constructor(
        fragment: MatchesFragment,
        items: List<Match>,
        b: Boolean,
        pageNumber: Int
    ) : this() {
        isFavList = b
        this.fragment = fragment
        this.items = items
    }

    constructor(fragment: MatchesFragment, favList: List<Favorite>, b: Boolean) : this() {
        isFavList = b
        this.fragment = fragment
        this.favList = favList
    }

    inner class ViewHolder(@NonNull val itemBinding: OneMatchItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        init {

            itemBinding.root.setOnClickListener {
                if (isFavList) {
                    // remove this item from fav
                    fragment.removeFromFav(favList[adapterPosition].id)
                    // to mark on that removed from fav list
                    fragment.update(false, favList[adapterPosition].id)
                } else {
                    if (!items[adapterPosition].isFav) {
                        // mark in all matches list that is item is fav only view
                        fragment.update(true, items[adapterPosition].id)
                        // add this item to fav list
                        val modelFav = Favorite(
                            items[adapterPosition].id,
                            items[adapterPosition].status,
                            items[adapterPosition].playingDate,
                            items[adapterPosition].homeTeamScore,
                            items[adapterPosition].awayTeamScore,
                            items[adapterPosition].homeTeamName,
                            items[adapterPosition].homeTeamId,
                            items[adapterPosition].awayTeamName,
                            items[adapterPosition].awayTeamId,
                            true
                        )
                        fragment.addToFav(modelFav)

                    } else {
                        // to mark on that removed from fav list
                        fragment.update(false, items[adapterPosition].id)
                        // remove this item from fav
                        fragment.removeFromFav(items[adapterPosition].id)
                    }
                }
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            OneMatchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        setFadeAnimation(holder.itemView)
        if (items.size > 0 && !isFavList) {
            holder.itemBinding.fav.visibility = View.VISIBLE
            // show list off all matches
            val item = items[position]
            holder.itemBinding.homeTeam.text = item.homeTeamName
            holder.itemBinding.awayTeam.text = item.awayTeamName
            if (item.status.equals("FINISHED")) {
                holder.itemBinding.result.text =
                    item.homeTeamScore + "" + "-" + "" + item.awayTeamScore
            } else {
                holder.itemBinding.result.text = convertDateFormat(item.playingDate)
            }

            if (item.isFav) {
                holder.itemBinding.fav.setBackgroundResource(R.drawable.ic_favorite_red_24dp)
            } else {
                holder.itemBinding.fav.setBackgroundResource(R.drawable.ic_favorite_shadow_24dp)

            }

            var homeImageURL = "https://crests.football-data.org/" + item.homeTeamId + ".png"
            var awayImageURL = "https://crests.football-data.org/" + item.awayTeamId + ".png"
            var homeImageURLSVG = "https://crests.football-data.org/" + item.homeTeamId + ".svg"
            var awayImageURLSVG = "https://crests.football-data.org/" + item.awayTeamId + ".svg"

            Glide.with(fragment)
                .load(homeImageURL)
                .listener(object : RequestListener<Drawable> {
                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable>?,
                        dataSource: com.bumptech.glide.load.DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        holder.itemBinding.homeImage.loadUrl(homeImageURLSVG)

                        return false
                    }
                })
                .into(holder.itemBinding.homeImage)

            Glide.with(fragment)
                .load(awayImageURL)
                .listener(object : RequestListener<Drawable> {
                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable>?,
                        dataSource: com.bumptech.glide.load.DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        holder.itemBinding.awayImage.loadUrl(awayImageURLSVG)
                        return false
                    }
                })
                .into(holder.itemBinding.awayImage)


        } else {
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

    fun ImageView.loadUrl(url: String) {

        val imageLoader = ImageLoader.Builder(this.context)
            .componentRegistry { add(SvgDecoder(this@loadUrl.context)) }
            .build()

        val request = ImageRequest.Builder(this.context)
            .crossfade(true)
            .crossfade(500)
            .data(url)
            .target(this)
            .build()

        imageLoader.enqueue(request)
    }
}

