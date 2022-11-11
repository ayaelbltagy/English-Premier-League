package com.example.theenglishpremierleague.ui.main

//import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.theenglishpremierleague.databinding.FragmentMainBinding
import com.example.theenglishpremierleague.ui.data.local.Images
import com.example.theenglishpremierleague.ui.data.local.Match
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.coroutines.runBlocking
import java.util.*


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
        // get list of local ids and send it to adapter to colored hart
        val idsList = mutableListOf<Long>()
        viewModel.localList.observe(viewLifecycleOwner, Observer {
            it?.let {
                for (i in 0 until it.size) {
                    if (!idsList.contains(it[i].id)) {
                        idsList.add(it[i].id)

                    }
                }
            }
        })
        viewModel.text.observe(viewLifecycleOwner, Observer {
            // to check which view is selected now
            if (it.equals("1")) {
                // show dialog till  get response
                binding.statusLoadingWheel.visibility = View.VISIBLE
                var imagesList = listOf<Images>()
                runBlocking {
                    imagesList = viewModel.getImagesfromdb()

                }

                viewModel.remoteList.observe(viewLifecycleOwner, Observer {
                    it?.let {
                        // hide dialog as list is ready
                        binding.statusLoadingWheel.visibility = View.GONE
                        // setup my adapter
                        if (it.isNotEmpty()) {

                            var adapter = MatchAdapter(this@MatchesFragment, it,imagesList, idsList, false)

                            binding.recycler.adapter = adapter
                        } else {
                            // no item in get from server
                            binding.statusLoadingWheel.visibility = View.GONE
                        }

                    }
                })
            } else {

                viewModel.localList.observe(viewLifecycleOwner, Observer {
                    it?.let {
                        var adapter = MatchAdapter(this@MatchesFragment, it, idsList, true)
                        binding.recycler.adapter = adapter
                    }
                })
            }
        })

        binding.lifecycleOwner = viewLifecycleOwner


        binding.view.setOnDateChangeListener(object : CalendarView.OnDateChangeListener {
            override fun onSelectedDayChange(p0: CalendarView, p1: Int, p2: Int, p3: Int) {
                val selectedDate = p1.toString()+"-"+(p2 + 1).toString()+"-"+p3.toString()
                viewModel.getListOfRefreshedMatches(selectedDate)

            }


        })


        return binding.root

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

    fun addToFav(item: Match) {
        runBlocking {
            viewModel.saveFixtures(item)
        }
    }

    fun removeFromFav(item: Match) {
        runBlocking {
            viewModel.removeSourceFromFav(item)
        }
    }

    fun update(item: Match) {
        runBlocking {
            viewModel.updateItemToFav(item)
        }
    }
}