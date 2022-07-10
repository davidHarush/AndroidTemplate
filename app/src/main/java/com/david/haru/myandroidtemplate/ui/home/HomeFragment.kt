package com.david.haru.myandroidtemplate.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.TransitionInflater
import com.david.haru.myandroidtemplate.R
import com.david.haru.myandroidtemplate.databinding.ListItemBinding
import com.david.haru.myandroidtemplate.network.MovieItem
import com.david.haru.myandroidtemplate.network.getTransitionName
import com.david.haru.myandroidtemplate.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_fragment.view.*
import kotlinx.coroutines.flow.collectLatest
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
            viewHome = inflater.inflate(R.layout.main_fragment, container, false)
            setRecyclerView()

        }
        return viewHome

    }

    private fun setRecyclerView() {
        val homeAdapter =  HomeAdapter { data, binding ->
            onClick(data, binding)
        }
        viewHome?.recyclerView?.apply {
            adapter = homeAdapter
            layoutManager = GridLayoutManager(context, 2)
        }

        lifecycleScope.launch {
            mainViewModel.feed.collectLatest { moviesPagingData ->
                homeAdapter.submitData(moviesPagingData)
            }
        }
    }

    private fun onClick(movie: MovieItem, binding: ListItemBinding) {
        val transitionName = movie.getTransitionName()
        binding.image.transitionName = transitionName + "image"
        binding.title.transitionName = transitionName + "title"
        val extras = FragmentNavigatorExtras(
            binding.image to binding.image.transitionName,
            binding.title to binding.title.transitionName
        )
        findNavController().navigate(
            R.id.DetailsFragment,
            bundleOf("arg_movie" to movie),
            null, // NavOptions
            extras
        )


    }
}


