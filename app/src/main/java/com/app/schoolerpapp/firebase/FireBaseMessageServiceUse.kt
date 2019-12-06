package com.app.schoolerpapp.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.provider.Settings
import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONException
import org.json.JSONObject
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.app.schoolerpapp.MyApplication
import com.app.schoolerpapp.R
import com.app.schoolerpapp.session.MySharedPreferences
import com.app.schoolerpapp.ui.activity.DashBoardActivity
import com.app.schoolerpapp.utility.Constant
import java.util.*
import javax.inject.Inject


class FireBaseMessageServiceUse : FirebaseMessagingService() {
    private var mNotificationManager: NotificationManager? = null
    private var mBuilder: NotificationCompat.Builder? = null
    private var jsonobject: JSONObject? = null
    private var count = 0
    @Inject
    lateinit var mySharedPreferences: MySharedPreferences

    override fun onNewToken(s: String) {
        super.onNewToken(s)
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Log.e(TAG, "Refreshed token 2222: " + refreshedToken!!)
        (application as MyApplication).getComponent().inject(this)

        mySharedPreferences.setSharedPreferenceString(this, Constant.fcmtoken, refreshedToken)
//        if (InternetConnection.isNetworkAvailable(this))
//            sendTokenServer(refreshedToken)
    }

    //    private void sendTokenServer(String refreshedToken) {
    //        APIService mAPIService = ApiUtils.getApiService(activity);
    //        Post post = new Post();
    //        PostData postData = new PostData();
    //        postData.setDeviceToken(refreshedToken);
    //        postData.setDeviceType("android");
    //        String uniqueID = UUID.randomUUID().toString();
    //        postData.setDeviceId(uniqueID);
    //        post.setFunctionName(ApiFunctions.ADDDEVICE);
    //        post.setData(postData);
    //
    //        Gson gson = new Gson();
    //        String json = gson.toJson(post);
    //
    //        Log.e(TAG, "sending request to server " + json);
    //        mAPIService.sendToken(post).enqueue(new Callback<ResponseData>() {
    //            final Gson gson = new Gson();
    //
    //            @Override
    //            public void onResponse(Call<ResponseData> call,
    //                                   Response<ResponseData> response) {
    //                if (response.isSuccessful()) {
    //                    if (response.body().getCode() == 1) {
    //
    //                    }
    //                }
    //
    //            }
    //
    //            @Override
    //            public void onFailure(Call<ResponseData> call, Throwable t) {
    //            }
    //        });
    //
    //    }


    fun createNotification(
        title: String,
        message: String,
        type: String,
        activitydata: String
    ) {
        val now = Date()
        val uniqueId =
            now.getTime()//use date to generate an unique id to differentiate the notifications.
        /**Creates an explicit intent for an Activity in your app */
        var resultIntent: Intent

        resultIntent = Intent(this, DashBoardActivity::class.java)
        resultIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        resultIntent.putExtra("notification", "notification")
        resultIntent.putExtra("type", type)
        resultIntent.putExtra("taskid", activitydata)


        val resultPendingIntent = PendingIntent.getActivity(
            this,
            uniqueId.toInt() /* Request code */, resultIntent,
            PendingIntent.FLAG_ONE_SHOT
        )
        //        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0,
        //                resultIntent, 0);

        mBuilder = NotificationCompat.Builder(this)
        mBuilder!!.setSmallIcon(R.drawable.ic_launcher_background)
        mBuilder!!.setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
            .setContentIntent(resultPendingIntent)

        mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel =
                NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    "NOTIFICATION_CHANNEL_NAME",
                    importance
                )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.vibrationPattern =
                longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            assert(mNotificationManager != null)
            mBuilder!!.setChannelId(NOTIFICATION_CHANNEL_ID)
            mNotificationManager!!.createNotificationChannel(notificationChannel)
        }
        assert(mNotificationManager != null)
        mNotificationManager!!.notify(uniqueId.toInt() /* Request Code */, mBuilder!!.build())
        count++
    }


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.e(TAG, "Refreshed token 2222: " + "notification come......")

        Log.e("mSocket.connect1", remoteMessage!!.data.toString())

        try {

            jsonobject = JSONObject(remoteMessage.data as Map<*, *>)
            Log.e("jsonobject..ff", jsonobject.toString())
            val title = jsonobject!!.getString("title")
            val subTitle = jsonobject!!.getString("message")
            val type = jsonobject!!.getString("type")

            if (jsonobject!!.has("activitydata")) {
                val activitydata = jsonobject!!.getString("activitydata")
                var time = 1000
                // this will run in the main thread
                createNotification(title, subTitle, type, activitydata)
            } else {
                var time = 1000
                // this will run in the main thread
                createNotification(title, subTitle, type, "")
            }


        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

    companion object {
        private val TAG = "MyFirebaseMessaging"
        val NOTIFICATION_CHANNEL_ID = "10001"
    }
}
