package com.example.websocketchat.activity.presenter

import android.content.Context
import com.example.websocketchat.R
import com.example.websocketchat.activity.model.Response
import com.example.websocketchat.activity.model.Send
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class DataPresenter(
    val context: Context,
    private val iDataPresenter: IDataPresenter
) {
    private val gdaxUrl = "ws://10.0.2.2:8080"
    private var ws: WebSocket? = null

    init {

        getMessage()

    }

    private fun getMessage() {
        val okHttpClient = OkHttpClient()
        val requestCoinPrice: Request = Request.Builder().url(gdaxUrl).build()


        val webSocketListenerCoinPrice: WebSocketListener = object : WebSocketListener() {

            override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {

            }

            override fun onMessage(webSocket: WebSocket, text: String) {

                iDataPresenter.success(Gson().fromJson(text, Response::class.java))
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {

            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {

                iDataPresenter.error(context.getString(R.string.connection_closing))

            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {

                iDataPresenter.error(context.getString(R.string.connection_closed))

            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: okhttp3.Response?) {
                iDataPresenter.error(t.cause?.message.toString())

            }
        }
        ws = okHttpClient.newWebSocket(requestCoinPrice, webSocketListenerCoinPrice)
        okHttpClient.dispatcher.executorService.shutdown()
    }

    fun send(clientName: String, message: String) {
        val sendModel = Send().apply {
            this.clientName = clientName
            this.message = message
        }
        ws!!.send(Gson().toJson(sendModel))
    }

    fun disconnect() {

        ws?.close(1000, null)
    }
}
