package com.example.websocketchat.activity.presenter

import com.example.websocketchat.activity.model.Response

interface IDataPresenter {

    fun success(response: Response)
    fun error(msg: String)
    fun getPresenter(presenter: DataPresenter)
}