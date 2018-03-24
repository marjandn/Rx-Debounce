package dnejad.marjan.rxdebounce

import android.app.Activity
import android.support.annotation.LayoutRes
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

/**
 * Created by Marjan.Dnejad
 * on 3/21/2018.
 */


fun Activity.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT)

fun log(message: String) = Log.e("MARJAN MESSAGE", message)

fun ViewGroup.inflate(@LayoutRes layout: Int, attachRoot: Boolean = false): View
        = LayoutInflater.from(context).inflate(layout,this,attachRoot)