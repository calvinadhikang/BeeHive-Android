package com.example.beehive.utils

import android.app.Activity
import android.app.DownloadManager
import android.net.Uri
import android.os.Environment
import androidx.core.content.getSystemService
import com.example.beehive.activities.MainActivity

class DownloadHelper {
    companion object{
        fun download(activity:Activity,url:String,fileOriName:String,outputFileName:String,extension:String){
            val request: DownloadManager.Request = DownloadManager.Request(Uri.parse(url))
            request.setTitle(fileOriName)
            request.setDescription("Downloading $fileOriName")
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.allowScanningByMediaScanner()
            request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                "$outputFileName.$extension"
            )
            val manager: DownloadManager? = activity?.getSystemService()
            manager!!.enqueue(request)
            (activity as MainActivity).showModal("Success download file result!"){}

        }
    }
}