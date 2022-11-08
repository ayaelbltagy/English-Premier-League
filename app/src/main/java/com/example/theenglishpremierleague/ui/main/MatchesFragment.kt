package com.example.theenglishpremierleague.ui.main

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.theenglishpremierleague.databinding.FragmentMainBinding

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
        val application = requireNotNull(this.activity).application
        val viewModelFactory = ViewModelFactory(application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MatchesViewModel::class.java)
                .apply {
                    setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
                }

        // show dialog till api get response
        binding.statusLoadingWheel.visibility = View.VISIBLE
        viewModel.remoteList.observe(viewLifecycleOwner, Observer {
            it?.let {
                // hide dialog as list is ready
                binding.statusLoadingWheel.visibility = View.GONE
                // setup my adapter
                var adapter = MatchAdapter(it)
                binding.recycler.adapter = adapter

            }
        })
        binding.lifecycleOwner = viewLifecycleOwner

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
}