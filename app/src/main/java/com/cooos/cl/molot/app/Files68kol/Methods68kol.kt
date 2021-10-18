package com.cooos.cl.molot.app

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Environment
import android.util.Log
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

fun Context.checkPermissions68kol () {
    Dexter.withContext(this)
        .withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
        .withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
            }
            override fun onPermissionRationaleShouldBeShown(
                p0: MutableList<PermissionRequest>?,
                p1: PermissionToken?
            ) {
            }
        }).check()
}

fun Context.createTempFile68kol (): File {
    val date68kol = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
        .format(Date())
    val fileDir68kol = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile("TMP${date68kol}_68kol", ".jpg", fileDir68kol)
}

fun Context.checkInternet68kol (): Boolean {
    Log.d("TEST_URL", "Checking Internet")
    val connectivityManager68kol =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val networkCapabilities68kol =
            connectivityManager68kol.getNetworkCapabilities(connectivityManager68kol.activeNetwork)
                ?: return false
        return networkCapabilities68kol.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    } else {
        for (network68kol in connectivityManager68kol.allNetworks) {
            connectivityManager68kol.getNetworkInfo(network68kol)?.let {
                if (it.isConnected) return true
            }
        }
        return false
    }
}