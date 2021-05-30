package com.parentune.airqualityassignment.binding

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View.GONE
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.parentune.airqualityassignment.adapter.StateAdapter
import com.parentune.airqualityassignment.model.State
import com.skydoves.whatif.whatIfNotNullAs
import com.skydoves.whatif.whatIfNotNullOrEmpty

object RecyclerViewBinding {

    private const val TAG = "RecyclerViewBinding"

    @JvmStatic
    @BindingAdapter("adapter")
    fun bindAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>?) {
        view.adapter = adapter?.apply {
            stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }
    }

    @JvmStatic
    @BindingAdapter("adapterList", "lodingTxt")
    fun bindAdapterList(view: RecyclerView?, avatarList: MutableList<State>?, loadingtxt : AppCompatTextView) {
        this.run {
            view?.setHasFixedSize(true)
            avatarList.whatIfNotNullOrEmpty { itemList ->
                view?.adapter.whatIfNotNullAs<StateAdapter?> { adapter ->
                    Log.e(TAG, "observerData: $itemList")
                    loadingtxt.visibility = GONE
                    itemList.forEach {
                        adapter?.data?.add(0, it)
                    }
                    Handler(Looper.getMainLooper()).postDelayed({
                        adapter?.notifyDataSetChanged()
                    },100)
                }
            }
        }
    }
}