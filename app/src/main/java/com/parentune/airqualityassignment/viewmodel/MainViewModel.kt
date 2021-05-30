package com.parentune.airqualityassignment.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.parentune.airqualityassignment.model.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    var _itemList: MutableLiveData<List<State>> = MutableLiveData()
    var itemList: LiveData<List<State>> = _itemList
}