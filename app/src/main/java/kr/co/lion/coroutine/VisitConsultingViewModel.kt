package kr.co.lion.coroutine

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VisitConsultingViewModel() :ViewModel() {
    val editTextNameConsulting = MutableLiveData<String>()
    val editTextPurposeConsulting = MutableLiveData<String>()
    val editTextDateConsulting = MutableLiveData<String>()
    val editTextEtcConsulting = MutableLiveData<String>()
}