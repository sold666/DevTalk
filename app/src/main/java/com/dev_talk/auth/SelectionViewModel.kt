package com.dev_talk.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SelectionViewModel : ViewModel() {

    private var _selectedTagsCount = MutableLiveData(0)
    val selectedTagsCount: LiveData<Int> = _selectedTagsCount

    private var _selectedProfessionsCount = MutableLiveData(0)
    val selectedProfessionsCount: LiveData<Int> = _selectedProfessionsCount

    fun setSelectedTagsCount(count: Int) {
        _selectedTagsCount.value = count
    }

    fun setSelectedProfessionsCount(count: Int) {
        _selectedProfessionsCount.value = count
    }

    fun resetSelectedProfessionsCount() {
        _selectedProfessionsCount.value = 0
    }

    fun resetSelectedTagsCount() {
        _selectedTagsCount.value = 0
    }
}
