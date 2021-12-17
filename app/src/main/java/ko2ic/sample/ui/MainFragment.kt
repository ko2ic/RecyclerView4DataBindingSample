package ko2ic.sample.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ko2ic.recyclerview.adapter.ItemViewTypeProvider
import com.ko2ic.recyclerview.adapter.RecyclerViewAdapter
import com.ko2ic.viewmodel.CollectionItemViewModel
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.databinding.BindableItem
import ko2ic.sample.R
import ko2ic.sample.databinding.ListItemImageMainBinding
import ko2ic.sample.databinding.ListItemMainContentBinding
import ko2ic.sample.databinding.ListItemMainHeaderBinding
import ko2ic.sample.databinding.MainFragmentBinding

class MainFragment : Fragment(R.layout.main_fragment) {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = MainFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding.viewModel = viewModel
        (activity as? AppCompatActivity)?.supportActionBar?.title = "RecyclerView Sample"

        val itemViewTypeProvider = object : ItemViewTypeProvider {
            override fun getLayoutRes(modelCollectionItem: CollectionItemViewModel): Int {
                return when (modelCollectionItem) {
                    is HeaderViewModel -> R.layout.list_item_main_header
                    is CommentItemViewModel -> R.layout.list_item_main_content
                    is ImageMainViewModel -> R.layout.list_item_image_main
                    is ImageItemViewModel -> R.layout.list_item_image
                    else -> throw IllegalArgumentException("Unexpected layout")
                }
            }
        }

        binding.recyclerView.adapter = RecyclerViewAdapter(viewModel.viewModels, itemViewTypeProvider)
        { modelCollectionItem, view ->
            when (modelCollectionItem) {
                is ImageMainViewModel -> {
                    val imageMainBinding =
                        DataBindingUtil.findBinding<ListItemImageMainBinding>(view)
                    imageMainBinding?.recyclerView?.adapter = RecyclerViewAdapter(
                        modelCollectionItem.viewModels,
                        itemViewTypeProvider
                    )
                }

            }
        }

        val adapter = GroupieAdapter()
//        binding.recyclerView.adapter = adapter

        viewModel.items.observe(viewLifecycleOwner, Observer {
            val commentItemViewModels = it.map { ContentViewHolder(CommentItemViewModel(it)) }
            val section = Section()
            section.setHeader(HeaderViewHolder(HeaderViewModel))
            section.addAll(commentItemViewModels)
            adapter.add(section)
        })
    }

    class HeaderViewHolder(private val viewModel: HeaderViewModel) : BindableItem<ListItemMainHeaderBinding>() {
        override fun getLayout(): Int = R.layout.list_item_main_header
        override fun bind(viewBinding: ListItemMainHeaderBinding, position: Int) {
            viewBinding.viewModel = viewModel
        }
    }

    class ContentViewHolder(private val viewModel: CommentItemViewModel) : BindableItem<ListItemMainContentBinding>() {
        override fun getLayout(): Int = R.layout.list_item_main_content
        override fun bind(viewBinding: ListItemMainContentBinding, position: Int) {
            viewBinding.viewModel = viewModel
        }
    }
}