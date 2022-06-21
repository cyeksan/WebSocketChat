package com.example.websocketchat.activity.view

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.websocketchat.util.CheckConnectionStatusChange
import com.example.websocketchat.activity.model.Response
import com.example.websocketchat.activity.presenter.DataPresenter
import com.example.websocketchat.activity.presenter.IDataPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IDataPresenter {

    private var presenter: DataPresenter? = null
    private var checkConnectionStatusChange: CheckConnectionStatusChange? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.websocketchat.R.layout.activity_main)

        presenter = DataPresenter(this@MainActivity, this@MainActivity)
        checkConnectionStatusChange =
            CheckConnectionStatusChange(this, presenter!!, this)
        checkConnectionStatusChange?.register()

        sendBtn.setOnClickListener {

            if (TextUtils.isEmpty(edt2.text.toString())) {

                Toast.makeText(
                    this@MainActivity,
                    getString(com.example.websocketchat.R.string.id_cannot_be_blank),
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            presenter!!.send(edt2.text.toString(), edt1.text.toString())

        }
    }

    override fun success(response: Response) {

        runOnUiThread {
            if (TextUtils.isEmpty(tv.text)) {

                val txt = "${response.clientName}: ${response.message}"
                tv.text = txt
            } else {
                val txt = "${tv.text}\n${response.clientName}: ${response.message}"
                tv.text = txt
            }

        }
    }

    override fun getPresenter(presenter: DataPresenter) {
        this.presenter = presenter
    }

    override fun error(msg: String) {
        runOnUiThread { Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show() }
    Log.e("cansu", msg)
    }

    override fun onDestroy() {
        super.onDestroy()

        checkConnectionStatusChange?.unregister()

    }
}



