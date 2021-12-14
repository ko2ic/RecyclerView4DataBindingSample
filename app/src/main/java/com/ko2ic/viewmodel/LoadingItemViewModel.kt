package com.ko2ic.viewmodel

class LoadingItemViewModel private constructor() : CollectionItemViewModel {

    private object Holder {
        val INSTANCE = LoadingItemViewModel()
    }

    companion object {
        val instance: LoadingItemViewModel by lazy { Holder.INSTANCE }
    }
}