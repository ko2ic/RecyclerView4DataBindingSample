package com.ko2ic.recyclerview.adapter

import androidx.databinding.library.baseAdapters.BR
import com.ko2ic.viewmodel.CollectionItemViewModel

interface ItemViewTypeProvider {

    fun getLayoutRes(modelCollectionItem: CollectionItemViewModel): Int

    fun getBindingVariableId(modelCollectionItem: CollectionItemViewModel) = BR.viewModel
}