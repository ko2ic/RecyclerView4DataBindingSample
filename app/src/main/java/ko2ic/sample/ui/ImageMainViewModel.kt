package ko2ic.sample.ui

import androidx.databinding.ObservableArrayList
import com.ko2ic.viewmodel.CollectionItemViewModel
import ko2ic.sample.repository.ImageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ImageMainViewModel(private val scope: CoroutineScope) : CollectionItemViewModel {

    val viewModels = ObservableArrayList<ImageItemViewModel>()

    init {
        fetchList()
    }

    private fun fetchList() {
        ImageRepository().fetchImages().onEach {
            val imageItemViewModels = it.map { ImageItemViewModel(it.url) }
            viewModels.addAll(imageItemViewModels)
        }.catch { cause ->
            // TODO エラー処理
            print(cause)
        }.launchIn(scope)
    }
}