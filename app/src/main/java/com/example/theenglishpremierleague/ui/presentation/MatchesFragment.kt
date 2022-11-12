package com.example.theenglishpremierleague.ui.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.theenglishpremierleague.databinding.FragmentMainBinding
import com.example.theenglishpremierleague.ui.data.local.Favorite
import com.example.theenglishpremierleague.ui.data.local.Images
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.coroutines.runBlocking
import java.util.*
import android.widget.Toast
import com.example.theenglishpremierleague.R
import com.example.theenglishpremierleague.ui.helpers.FadeInLinearLayoutManager
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.HorizontalCalendarView
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener






/**
 * A placeholder fragment containing a simple view.
 */
class MatchesFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel: MatchesViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        // prepare view model
        val application = requireNotNull(this.activity).application
        val viewModelFactory = ViewModelFactory(application)
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(MatchesViewModel::class.java).apply {
                setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
            }
         //  binding.recycler.layoutManager = FadeInLinearLayoutManager(context)

        viewModel.text.observe(viewLifecycleOwner, Observer {
            // to check which view is selected now
            if (it.equals("1")) {
                // show dialog till  get response
                binding.statusLoadingWheel.visibility = View.VISIBLE

                viewModel.allMatchesList.observe(viewLifecycleOwner, Observer {
                    it?.let {
                        // hide dialog as list is ready
                        binding.statusLoadingWheel.visibility = View.GONE
                        // setup my adapter
                        if (it.isNotEmpty()) {
                            var adapter = MatchAdapter(this@MatchesFragment, it, false,1)
                            binding.recycler.adapter = adapter
//                            adapter.sedItemClickedToView {
//                                    if (!it.isFav) {
//                                        // mark in all matches list that is item is fav only view
//                                        update(true, it.id)
//                                        // add this item to fav list
//                                        val modelFav = Favorite(
//                                            it.id,
//                                            it.status,
//                                            it.date,
//                                            it.homeTeamScore,
//                                            it.awayTeamScore,
//                                            it.homeTeamName,
//                                            it.homeTeamId,
//                                            it.awayTeamName,
//                                            it.awayTeamId,
//                                            true
//                                        )
//                                        addToFav(modelFav)
//
//                                    } else {
//                                        update(false, it.id)
//
//
//                                }
//                            }
                        } else {
                            // no item in get from server
                            binding.statusLoadingWheel.visibility = View.GONE
                        }

                    }
                })
            } else {

                viewModel.favList.observe(viewLifecycleOwner, Observer {
                    it?.let {
                        var adapter = MatchAdapter(this@MatchesFragment, it, true)
                        binding.recycler.adapter = adapter
                    }
                })
            }
        })



        binding.lifecycleOwner = viewLifecycleOwner
        // Calendar

        val startDate = Calendar.getInstance()
        startDate.add(Calendar.MONTH, -1)

        val endDate = Calendar.getInstance()
        endDate.add(Calendar.MONTH, 1)


        val horizontalCalendar: HorizontalCalendar =
            HorizontalCalendar.Builder(binding.root,binding.calendarView.id)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .build()

        horizontalCalendar.calendarListener = object : HorizontalCalendarListener() {
            override fun onDateSelected(date: Calendar?, position: Int) {
                Toast.makeText(requireContext(), date?.get(Calendar.DAY_OF_MONTH).toString(),Toast.LENGTH_LONG).show()
            }
            override fun onCalendarScroll(
                calendarView: HorizontalCalendarView,
                dx: Int, dy: Int
            ) {
            }

            override fun onDateLongClicked(date: Calendar?, position: Int): Boolean {
                return true
            }
        }

        return binding.root

    }



    // save to fav list
    fun addToFav(favMatch: Favorite) {
        runBlocking {
            viewModel.saveFixtures(favMatch)
        }

    }

    // remove only from fav list
    fun removeFromFav(id: Long) {
        runBlocking {
            viewModel.removeSourceFromFav(id)
        }
    }

    // update flag in all matches list
    fun update(flag: Boolean, id: Long) {
        runBlocking {
            viewModel.updateFlag(flag, id)
        }
    }

    // get images from online mood to offline mood
    fun saveImage(imageURL: String, Id: Long) {
        val model = Images(
            Id,
            imageURL,
        )
        runBlocking {
            viewModel.saveImages(model)
        }
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): MatchesFragment {
            return MatchesFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}