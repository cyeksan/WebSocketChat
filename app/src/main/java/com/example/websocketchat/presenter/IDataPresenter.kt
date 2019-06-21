package com.example.websocketchat.presenter

import com.example.websocketchat.model.Response

interface IDataPresenter {

    fun success(response: Response)
    fun error(msg: String)
}