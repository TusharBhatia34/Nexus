package com.example.nexus

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NexusViewModel @Inject constructor(
    private val response: Response,
    val networkConnectivityObserver: NetworkConnectivityObserver

): ViewModel() {



    private val _responseState = MutableStateFlow("")
   val responseState =_responseState.asStateFlow()

   private val _chatHistory = MutableStateFlow<List<String>>(emptyList())
   val chatHistory = _chatHistory.asStateFlow()

    private var _isDarkTheme =MutableStateFlow(false)
    var isDarkTheme = _isDarkTheme.asStateFlow()

   fun response(prompt:String){

_chatHistory.value=_chatHistory.value.plus(prompt)

      viewModelScope.launch {
      _responseState.value=  response.response(prompt)
         _chatHistory.value=_chatHistory.value.plus(responseState.value)
   }
}

    fun changeTheme(){
_isDarkTheme.value = !_isDarkTheme.value
    }

    fun clearChat(){
        _chatHistory.value = emptyList()
    }

}