package com.david.haru.myandroidtemplate.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.TransitionInflater
import com.david.haru.myandroidtemplate.R
import com.david.haru.myandroidtemplate.databinding.ListItemBinding
import com.david.haru.myandroidtemplate.gone
import com.david.haru.myandroidtemplate.network.MovieItem
import com.david.haru.myandroidtemplate.network.getTransitionName
import com.david.haru.myandroidtemplate.ui.main.MainViewModel
import com.david.haru.myandroidtemplate.ui.main.UiState
import com.david.haru.myandroidtemplate.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_fragment.view.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels<MainViewModel>()
    private var viewHome: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        sharedElementReturnTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (viewHome == null) {
            //prevent redraw the fragment
            viewHome = inflater.inflate(R.layout.main_fragment, container, false)
            setRecyclerView()
        }
        return viewHome

    }

    private fun setRecyclerView() {

        lifecycleScope.launch {
            // repeatOnLifecycle launches the block in a new coroutine every time the
            // lifecycle is in the STARTED state (or above) and cancels it when it's STOPPED.
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Trigger the flow and start listening for values.
                // Note that this happens when lifecycle is STARTED and stops
                // collecting when the lifecycle is STOPPED
                mainViewModel.uiState.collect { data ->
                    // New value received
                    when (data) {
                        is UiState.Success -> {
                            viewHome?.progressBar?.gone()
                            viewHome?.recyclerView?.apply {
                                adapter = HomeAdapter { data, binding ->
                                    onClick(data, binding)
                                }.apply {
                                    submitList(data.movies.results)
                                }

                                layoutManager = GridLayoutManager(context, 2)
                            }

                        }
                        is UiState.Error -> {
                            viewHome?.progressBar?.gone()
                            Toast.makeText(context, data.exception.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                        is UiState.Loading -> {
                            viewHome?.progressBar?.visible()
                        }
                    }
                }
            }
        }
    }

    private fun onClick(data: MovieItem, binding: ListItemBinding) {
        val transitionName = data.getTransitionName()
        binding.image.transitionName = transitionName + "image"
        binding.title.transitionName = transitionName + "title"
        val extras = FragmentNavigatorExtras(
            binding.image to binding.image.transitionName,
            binding.title to binding.title.transitionName
        )
        findNavController().navigate(
            R.id.DetailsFragment,
            bundleOf("arg_movie" to data),
            null, // NavOptions
            extras
        )
    }


}