package ko2ic.sample.ui

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ko2ic.viewmodel.CollectionItemViewModel
import ko2ic.sample.dto.CommentItemDto
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
        //fetchList2()
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


    private val _items = MutableLiveData<List<CommentItemDto>>()
    val items: LiveData<List<CommentItemDto>> get() = _items

    private fun fetchList2() {
        CommentRepository().fetchComments().onEach {
            _items.value = it
        }.catch { cause ->
            // TODO エラー処理
            throw cause
        }.onCompletion {
            isLoadingForRefresh.set(false)
        }.launchIn(viewModelScope)
    }

}