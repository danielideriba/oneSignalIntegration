package com.android.onesignalintegration

import com.huawei.hms.push.RemoteMessage
import com.onesignal.HmsMessageServiceOneSignal
import com.onesignal.OneSignalHmsEventBridge


class OneSignalHmsMessageService: HmsMessageServiceOneSignal() {
    override fun onNewToken(token: String?) {
        // ...
        // Forward event on to OneSignal SDK
        OneSignalHmsEventBridge.onNewToken(this, token!!)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        // ...
        // Forward event on to OneSignal SDK
        OneSignalHmsEventBridge.onMessageReceived(this, message)
    }
}