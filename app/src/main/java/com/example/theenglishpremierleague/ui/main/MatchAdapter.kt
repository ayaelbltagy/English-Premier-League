package com.example.theenglishpremierleague.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.theenglishpremierleague.R
import com.example.theenglishpremierleague.databinding.OneMatchItemBinding
import com.example.theenglishpremierleague.ui.data.local.MatchEntity
import java.text.SimpleDateFormat
import java.util.*

class MatchAdapter(private var items: List<MatchEntity>, private val viewModel: MatchesViewModel) :
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
        holder.itemBinding.homeTeam.text = item.homeTeamName
        holder.itemBinding.awayTeam.text = item.awayTeamName
        if(item.status.equals("FINISHED")){
          holder.itemBinding.result.text = item.homeTeamScore+""+"_"+""+item.awayTeamScore
        }
        else{
            holder.itemBinding.result.text = convertDateFormat(item.date)
        }
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

    fun convertDateFormat(input:String) : String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        inputFormat.timeZone = TimeZone.getTimeZone("GMT")
        val date = inputFormat.parse(input)
        val outputFormat = SimpleDateFormat("hh:mm", Locale.US)
        outputFormat.timeZone = TimeZone.getDefault()
        return outputFormat.format(date)
    }
}