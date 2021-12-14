package ko2ic.sample.ui

import com.ko2ic.viewmodel.CollectionItemViewModel
import ko2ic.sample.dto.CommentItemDto

class CommentItemViewModel(dto: CommentItemDto): CollectionItemViewModel {
    val email: String = dto.email
    val body: String = dto.body
}