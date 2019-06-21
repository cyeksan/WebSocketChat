package com.example.websocketchat.presenter

import com.example.websocketchat.model.Response
import com.example.websocketchat.model.Send
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class DataPresenter(
    private val iDataPresenter: IDataPresenter
) {
    private val gdaxUrl = "ws://192.168.2.85:8080"
    private var ws:WebSocket ?=null

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

            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {

            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: okhttp3.Response?) {


                iDataPresenter.error(t.message.toString())

            }
        }
        ws = okHttpClient.newWebSocket(requestCoinPrice, webSocketListenerCoinPrice)
        okHttpClient.dispatcher().executorService().shutdown()
    }

    fun send(id:String,message:String){
       val sendModel = Send().apply {
            this.id = id
            this.message = message
        }
        ws!!.send(Gson().toJson(sendModel))
    }
}