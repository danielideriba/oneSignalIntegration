package com.android.onesignalintegration

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.huawei.hms.aaid.HmsInstanceId
import com.huawei.hms.common.ApiException
import com.huawei.hms.push.HmsMessaging
import com.onesignal.OneSignal
import com.onesignal.OneSignal.OSSMSUpdateError
import com.onesignal.OneSignal.OSSMSUpdateHandler
import org.json.JSONObject




class MainActivity : AppCompatActivity() {

    private val ONESIGNAL_APP_ID = "c3f29099-c8ff-46db-a83d-cbc013fb8510"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Logging set to help debug issues, remove before releasing your app.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)


        getToken()

        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.sendEmail).setOnClickListener {
            val smsNumber = "+56936152918"

            OneSignal.setSMSNumber(smsNumber, object : OSSMSUpdateHandler {
                override fun onSuccess(result: JSONObject) {
                    // SMS successfully synced with OneSignal
                    Log.d("TAG", "RESULT---$result")
                }

                override fun onFailure(error: OSSMSUpdateError) {
                    // Error syncing SMS, check error.getType() and error.getMessage() for details
                    Log.d("TAG", "ERROR---$error")
                }
            })
        }
    }

    private fun getToken() {
        // Create a thread.
        object : Thread() {
            override fun run() {
                try {
                    // Obtain the app ID from the agconnect-services.json file.
                    val appId = "106271507"

                    // Set tokenScope to HCM.
                    val tokenScope = "HCM"
                    val token = HmsInstanceId.getInstance(this@MainActivity).getToken(appId, tokenScope)
                    Log.i("TAG", "get token:$token")

                    // Check whether the token is null.
                    if (!TextUtils.isEmpty(token)) {
                        sendRegTokenToServer(token)
                    }
                } catch (e: ApiException) {
                    Log.e("TAG", "get token failed, $e")
                }
            }
        }.start()
    }

    private fun sendRegTokenToServer(token: String?) {
        Log.i("TAG", "sending token to server. token:$token")
    }
}