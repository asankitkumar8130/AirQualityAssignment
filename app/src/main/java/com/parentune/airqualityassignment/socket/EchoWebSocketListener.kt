package com.parentune.airqualityassignment.socket

import android.util.Log
import com.parentune.airqualityassignment.model.State
import com.parentune.airqualityassignment.viewmodel.MainViewModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.util.*
import javax.inject.Inject

class EchoWebSocketListener @Inject constructor(
): WebSocketListener() {

    private val NORMAL_CLOSURE_STATUS = 1000
    private val TAG = "EchoWebSocketListener"

    lateinit var dataListener : MainViewModel
    fun setDataListeners(dataListener: MainViewModel){
        this.dataListener = dataListener
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        webSocket.send("join")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        try {
            val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            val listType = Types.newParameterizedType(List::class.java, State::class.java)
            val adapter: JsonAdapter<List<State>> = moshi.adapter(listType)
            val stateList = adapter.fromJson(text)
            val stateList2 : MutableList<State> = mutableListOf()
            stateList?.forEach { it ->
                val state = State(
                    it.city,
                    it.aqi,
                    Calendar.getInstance().timeInMillis
                )
                stateList2.add(state)
            }
            dataListener._itemList.postValue(stateList2)
        }catch (e: Exception) {
            Log.e(TAG, "Exception: ${e.message}")
        }
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
    }

}