package com.example.websocketchat.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Handler
import android.util.Log
import com.example.websocketchat.activity.presenter.DataPresenter
import com.example.websocketchat.activity.presenter.IDataPresenter

class CheckConnectionStatusChange(val context: Context, var presenter: DataPresenter, val iDataPresenter: IDataPresenter) {

    private var manager: ConnectivityManager? = null

    private var isFirst = true
    private val handler = Handler()
    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            Log.i("vvv", "connected to " + if (manager!!.isActiveNetworkMetered) "LTE" else "WIFI")

            if (!manager!!.isActiveNetworkMetered && !isFirst) {

                presenter.disconnect() //The presenter from MainActivity is disconnected at this step.
                presenter = DataPresenter(context, iDataPresenter)
                iDataPresenter.getPresenter(presenter)
                //The presenter created here is sent to MainActivity again. Otherwise, connection status will be onClosed().
                //Because MainActivity would not recognise the presenter created newly here. MainActivity would try to continue with the old -disconnected- presenter created in MainActivity.
            }

            isFirst = false

            // we've got a connection, remove callbacks (if we have posted any)
            handler.removeCallbacks(endCall)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            Log.i("vvv", "losing active connection")

            // Schedule an event to take place in a second
            handler.postDelayed(endCall, 1000)
        }
    }

    private val endCall = Runnable {
        // if execution has reached here - feel free to cancel the call
        // because no connection was established in a second
    }


    fun register() {
        manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        manager?.registerDefaultNetworkCallback(networkCallback)
    }

    fun unregister() {

        manager?.unregisterNetworkCallback(networkCallback)
        handler.removeCallbacks(endCall)
    }
}