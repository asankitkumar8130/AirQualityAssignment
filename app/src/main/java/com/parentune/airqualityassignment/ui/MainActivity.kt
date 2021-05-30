package com.parentune.airqualityassignment.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import com.parentune.airqualityassignment.socket.EchoWebSocketListener
import com.parentune.airqualityassignment.R
import com.parentune.airqualityassignment.adapter.StateAdapter
import com.parentune.airqualityassignment.baseadapter.BaseAdapter
import com.parentune.airqualityassignment.databinding.ActivityMainBinding
import com.parentune.airqualityassignment.dialog.GraphDialog
import com.parentune.airqualityassignment.model.State
import com.parentune.airqualityassignment.viewmodel.MainViewModel
import com.skydoves.bindables.BindingActivity
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main), BaseAdapter.clickListener<State> {

    private val TAG = "MainActivity"

    @VisibleForTesting
    val mainViewModel: MainViewModel by viewModels()
    @Inject
    lateinit var echoWebSocketListener: EchoWebSocketListener

    private var aqiList : MutableList<Float> = mutableListOf()

    private val client: OkHttpClient by lazy {
        OkHttpClient()
    }

    private lateinit var ws: WebSocket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding {
            lifecycleOwner = this@MainActivity
            vm = mainViewModel
            adapter = StateAdapter(this@MainActivity)
        }
        echoWebSocketListener.setDataListeners(mainViewModel)
        initSocket()
    }

    private fun initSocket() {
        Handler(Looper.getMainLooper()).postDelayed(
            {
                val request: Request = Request.Builder()
                    .url("ws://city-ws.herokuapp.com/").build()
                ws = client.newWebSocket(request, echoWebSocketListener)
            }, 2000
        )
    }

    override fun onItemClick(position: Int, item: State?) {
        aqiList.clear()
        binding.adapter?.data?.forEach {
            if(it.city == item?.city){
                aqiList.add(it.aqi.substring(0, 5).toFloat())
            }
        }
        GraphDialog(this, aqiList).show()
    }
}