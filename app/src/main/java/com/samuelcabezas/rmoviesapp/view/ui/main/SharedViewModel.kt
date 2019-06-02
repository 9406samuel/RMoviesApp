package com.samuelcabezas.rmoviesapp.view.ui.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class SharedViewModel: ViewModel(){
    val reloadData: MutableLiveData<Int> = MutableLiveData()
}