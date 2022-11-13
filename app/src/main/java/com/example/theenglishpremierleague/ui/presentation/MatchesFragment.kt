package com.example.theenglishpremierleague.ui.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.theenglishpremierleague.R
import com.example.theenglishpremierleague.databinding.FragmentMainBinding
import com.example.theenglishpremierleague.ui.data.local.Favorite
import com.example.theenglishpremierleague.ui.data.local.Images
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.HorizontalCalendarView
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.AUGUST
import java.util.Calendar.MAY

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
        // binding.recycler.layoutManager = FadeInLinearLayoutManager(context)
        viewModel.selectedDay.observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty()) {
                viewModel.getMatchesByFilter(convertDateFormat(getCurrentDate()))

                // by default open today in case any error happen is filter day
                val startDate = Calendar.getInstance()
                startDate.set(Calendar.MONTH, AUGUST)
                startDate.set(Calendar.DAY_OF_MONTH, 5)
                startDate.set(Calendar.YEAR, 2022)

                val endDate = Calendar.getInstance()
                endDate.set(Calendar.MONTH, MAY)
                endDate.set(Calendar.DAY_OF_MONTH, 28)
                endDate.set(Calendar.YEAR, 2023)

                val horizontalCalendar: HorizontalCalendar =
                    HorizontalCalendar.Builder(binding.root, binding.calendarView.id)
                        .range(startDate, endDate)
                        .datesNumberOnScreen(5)
                        .build()

                horizontalCalendar.calendarListener = object : HorizontalCalendarListener() {
                    override fun onDateSelected(date: Calendar?, position: Int) {
                        val selectedMonth = date?.get(Calendar.MONTH).toString()
                        val month = selectedMonth.toInt() + 1
                        val date =
                            date?.get(Calendar.YEAR).toString() + "-" + month + "-" + date?.get(
                                Calendar.DAY_OF_MONTH
                            ).toString()
                        viewModel.getMatchesByFilter(convertDateFormat(date))
                    }

                    override fun onCalendarScroll(
                        calendarView: HorizontalCalendarView,
                        dx: Int,
                        dy: Int
                    ) {
                    }

                    override fun onDateLongClicked(date: Calendar?, position: Int): Boolean {
                        return true
                    }

                }

            } else {
                viewModel.getMatchesByFilter(it)
                createCalendarWithSelectedDay(it)
            }

        })
        viewModel.text.observe(viewLifecycleOwner, Observer {
            // to check which view is selected now
            if (it.equals("1")) {
                // show dialog till  get response
                binding.statusLoadingWheel.visibility = View.VISIBLE
                binding.calendarView.visibility = View.VISIBLE

                viewModel.allMatchesList.observe(viewLifecycleOwner, Observer {
                    it?.let {
                        // hide dialog as list is ready
                        binding.statusLoadingWheel.visibility = View.GONE
                        // setup my adapter
                        if (it.isNotEmpty()) {
                            binding.noItems.visibility = View.GONE
                            binding.recycler.visibility = View.VISIBLE
                            var adapter = MatchAdapter(this@MatchesFragment, it, false, 1)
                            binding.recycler.adapter = adapter
                        } else {
                            // no item in get from server
                            binding.statusLoadingWheel.visibility = View.GONE
                            binding.recycler.visibility = View.INVISIBLE
                            binding.noItems.visibility = View.VISIBLE
                            binding.noItems.setText(activity?.resources?.getString(R.string.data_error))
                        }

                    }
                })
            } else {
                binding.calendarView.visibility = View.GONE
                viewModel.favList.observe(viewLifecycleOwner, Observer {
                    it?.let {
                        if (it.size > 0) {
                            binding.noItems.visibility = View.GONE
                            binding.recycler.visibility = View.VISIBLE
                            var adapter = MatchAdapter(this@MatchesFragment, it, true)
                            binding.recycler.adapter = adapter
                        } else {
                            binding.recycler.visibility = View.INVISIBLE
                            binding.noItems.visibility = View.VISIBLE
                            binding.noItems.setText(activity?.resources?.getString(R.string.no_fav))

                        }

                    }
                })
            }
        })

        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root

    }

    fun convertDateFormat(input: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        inputFormat.timeZone = TimeZone.getTimeZone("GMT")
        val date = inputFormat.parse(input)
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        outputFormat.timeZone = TimeZone.getDefault()
        return outputFormat.format(date)
    }

    fun createCalendarWithSelectedDay(input: String) {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        inputFormat.timeZone = TimeZone.getTimeZone("GMT")
        val date = inputFormat.parse(input)
        val outputFormatForYear = SimpleDateFormat("yyyy", Locale.US)
        outputFormatForYear.timeZone = TimeZone.getDefault()
        val year = outputFormatForYear.format(date).toInt()
        val outputFormatForMonth = SimpleDateFormat("MM", Locale.US)
        outputFormatForMonth.timeZone = TimeZone.getDefault()
        val month = outputFormatForMonth.format(date).toInt()
        val outputFormatForDay = SimpleDateFormat("dd", Locale.US)
        outputFormatForDay.timeZone = TimeZone.getDefault()
        val day = outputFormatForDay.format(date).toInt()
        // Calendar start
        val startDate = Calendar.getInstance()
        startDate.set(Calendar.MONTH, 8)
        startDate.set(Calendar.DAY_OF_MONTH, 5)
        startDate.set(Calendar.YEAR, 2022)
        // Calendar end
        val endDate = Calendar.getInstance()
        endDate.set(Calendar.MONTH, 5)
        endDate.set(Calendar.DAY_OF_MONTH, 28)
        endDate.set(Calendar.YEAR, 2023)
        // Calendar selected date
        val selectedDate = Calendar.getInstance()
        selectedDate.set(Calendar.MONTH, month-1)
        selectedDate.set(Calendar.DAY_OF_MONTH, day)
        selectedDate.set(Calendar.YEAR, year)

        val horizontalCalendar: HorizontalCalendar =
            HorizontalCalendar.Builder(binding.root, binding.calendarView.id)
                .range(startDate, endDate)
                .defaultSelectedDate(selectedDate)
                .datesNumberOnScreen(5)
                .build()

        horizontalCalendar.calendarListener = object : HorizontalCalendarListener() {
            override fun onDateSelected(date: Calendar?, position: Int) {
                val selectedMonth = date?.get(Calendar.MONTH).toString()
                val month = selectedMonth.toInt() + 1
                val date = date?.get(Calendar.YEAR).toString() + "-" + month + "-" + date?.get(
                    Calendar.DAY_OF_MONTH
                ).toString()
                viewModel.getMatchesByFilter(convertDateFormat(date))
            }

            override fun onCalendarScroll(calendarView: HorizontalCalendarView, dx: Int, dy: Int) {
            }

            override fun onDateLongClicked(date: Calendar?, position: Int): Boolean {
                return true
            }
        }


    }

    fun getCurrentDate(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val date = Date()
        return formatter.format(date)
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