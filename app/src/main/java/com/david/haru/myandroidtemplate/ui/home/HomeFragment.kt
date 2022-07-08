package com.david.haru.myandroidtemplate.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.TransitionInflater
import com.david.haru.myandroidtemplate.R
import com.david.haru.myandroidtemplate.network.getTransitionName
import com.david.haru.myandroidtemplate.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_fragment.view.*

@AndroidEntryPoint
class HomeFragment : Fragment() {


    private val mainViewModel: MainViewModel by activityViewModels<MainViewModel>()

    //    private var binding by autoCleared<MainFragmentBinding>()
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
            setObserves(viewHome!!)
        }
        return viewHome

    }

    private fun setObserves(viewHome: View) {
        mainViewModel.movies
            .observe(
                requireActivity()
            ) { movies ->
                viewHome.recyclerView.apply {
                    adapter = HomeAdapter { data, binding ->
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
                    }.apply {
                        submitList(movies.results)
                    }
                    layoutManager = GridLayoutManager(context, 2)
                    visibility = View.VISIBLE
                }
            }

        mainViewModel.onErr
            .observe(
                requireActivity()
            ) {
                viewHome.recyclerView.visibility = View.GONE

            }
    }


}