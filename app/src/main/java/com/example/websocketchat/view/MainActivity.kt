package com.example.websocketchat.view

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.websocketchat.R
import com.example.websocketchat.model.Response
import com.example.websocketchat.presenter.DataPresenter
import com.example.websocketchat.presenter.IDataPresenter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), IDataPresenter {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val presenter = DataPresenter(this@MainActivity)


        sendBtn.setOnClickListener {

            if(TextUtils.isEmpty(edt2.text.toString())) {

                Toast.makeText(this@MainActivity, getString(R.string.id_cannot_be_blank), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            presenter.send(edt2.text.toString(), edt1.text.toString())
        }
    }

    override fun success(response: Response) {

        runOnUiThread {
            if (TextUtils.isEmpty(tv.text)) {

                val txt = "${response.id}: ${response.message}"
                tv.text = txt
            }
            else {
                val txt = "${tv.text} + \"\\n${response.id}: ${response.message}\""
                tv.text = txt
            }

        }
    }

    override fun error(msg: String) {
        runOnUiThread { Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show() }
    }

}



