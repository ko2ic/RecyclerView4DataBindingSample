package ko2ic.sample.ui

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ko2ic.viewmodel.CollectionItemViewModel
import ko2ic.sample.repository.CommentRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach

class MainViewModel : ViewModel() {

    val viewModels = ObservableArrayList<CollectionItemViewModel>()

    val isLoadingForRefresh = ObservableField(false)

    init {
        fetchList()
    }

    fun onRefresh() {
        viewModels.clear()
        isLoadingForRefresh.set(true)
        fetchList()
    }

    private fun fetchList() {
        CommentRepository().fetchComments().onEach {
            val commentItemViewModels = it.map { CommentItemViewModel(it) }
            viewModels.add(HeaderViewModel)
            viewModels.add(ImageMainViewModel(viewModelScope))
            viewModels.addAll(commentItemViewModels)
        }.catch { cause ->
            // TODO エラー処理
            throw cause
        }.onCompletion {
            isLoadingForRefresh.set(false)
        }.launchIn(viewModelScope)
    }

//    fun fetchList2(): Flow<List<CommentItemDto>> {
//        return CommentRepository().fetchComments()
//    }
}