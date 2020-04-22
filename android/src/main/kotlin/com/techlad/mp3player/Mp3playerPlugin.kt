package com.techlad.mp3player

import android.content.BroadcastReceiver
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar


/** Mp3playerPlugin */
public class Mp3playerPlugin : FlutterPlugin, MethodCallHandler, EventChannel.StreamHandler {

    private var applicationContext: Context? = null
    private val chargingStateChangeReceiver: BroadcastReceiver? = null
    private var methodChannel: MethodChannel? = null
//    private var eventChannel: EventChannel? = null

    override fun onAttachedToEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        onAttachedToEngine(binding.getApplicationContext(), binding.getBinaryMessenger());
//        val channel = MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "mp3player")
//
//        context = flutterPluginBinding.applicationContext
//        channel.setMethodCallHandler(Mp3playerPlugin());
    }

    private fun onAttachedToEngine(applicationContext: Context, messenger: BinaryMessenger) {
        this.applicationContext = applicationContext
        this.methodChannel = MethodChannel(messenger, "mp3player")
//        this.eventChannel = EventChannel(messenger, "plugins.flutter.io/charging")
//        this.eventChannel?.setStreamHandler(this)
        this.methodChannel?.setMethodCallHandler(this)
    }

    // This static function is optional and equivalent to onAttachedToEngine. It supports the old
    // pre-Flutter-1.12 Android projects. You are encouraged to continue supporting
    // plugin registration via this function while apps migrate to use the new Android APIs
    // post-flutter-1.12 via https://flutter.dev/go/android-project-migration.
    //
    // It is encouraged to share logic between onAttachedToEngine and registerWith to keep
    // them functionally equivalent. Only one of onAttachedToEngine or registerWith will be called
    // depending on the user's project. onAttachedToEngine or registerWith must both be defined
    // in the same class.
    companion object {
        @JvmStatic
        fun registerWith(registrar: Registrar) {
            val channel = MethodChannel(registrar.messenger(), "mp3player")
            channel.setMethodCallHandler(Mp3playerPlugin())

            val instance = Mp3playerPlugin()
            instance.onAttachedToEngine(registrar.context(), registrar.messenger())
        }
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        if (call.method == "getPlatformVersion") {
            result.success("Android ${android.os.Build.VERSION.RELEASE}")
        } else if (call.method == "play") {

            val mediaPlayer: MediaPlayer = MediaPlayer.create(applicationContext, Uri.parse("https://file-examples.com/wp-content/uploads/2017/11/file_example_MP3_700KB.mp3"))
            mediaPlayer.setOnBufferingUpdateListener(object : MediaPlayer.OnBufferingUpdateListener {
                override fun onBufferingUpdate(mp: MediaPlayer?, percent: Int) {
                    Log.e("UPDATE", percent.toString())
                }
            })

            mediaPlayer.setOnCompletionListener {
                Log.e("UPDATE", "OnComplete")
            }
            mediaPlayer.setOnErrorListener({ mp, what, extra ->

                Log.e("UPDATE", "OnError")
                return@setOnErrorListener true
            })
            mediaPlayer.setOnInfoListener { mp, what, extra ->

                return@setOnInfoListener true
            }
            mediaPlayer.start()

//            player.setDataSource("https://file-examples.com/wp-content/uploads/2017/11/file_example_MP3_700KB.mp3")
//            player.setOnPreparedListener(object : MediaPlayer.OnPreparedListener {
//                override fun onPrepared(mp: MediaPlayer?) {
//                    result.success("It is playing")
//
//                    player.start()
//                }
//            })
//
//            player.setOnErrorListener(object : MediaPlayer.OnErrorListener {
//                override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
//
//                    result.error("101", "Error oucour while playing", null)
//                    return false
//                }
//            })
//
//            player.prepare()

        } else {
            result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        applicationContext = null;
        methodChannel?.setMethodCallHandler(null);
        methodChannel = null;
//        eventChannel?.setStreamHandler(null);
//        eventChannel = null;
    }

    override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {

    }

    override fun onCancel(arguments: Any?) {
        //To change body of created functions use File | Settings | File Templates.
    }
}
