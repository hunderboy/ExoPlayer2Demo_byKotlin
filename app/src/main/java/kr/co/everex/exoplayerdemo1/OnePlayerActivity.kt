package kr.co.everex.exoplayerdemo1

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ImageView
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.extractor.ExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.upstream.BandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import kr.co.everex.exoplayerdemo1.databinding.ActivityMainBinding
import kr.co.everex.exoplayerdemo1.databinding.ActivityOnePlayerBinding

class OnePlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnePlayerBinding

    var simpleExoPlayer: SimpleExoPlayer? = null
    private var flag = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnePlayerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view) // 뷰 바인딩 적용 완료


        // 풀스크린 설정
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        // 풀스크린 버튼 설정
        val btfullScreen = binding.playerView.findViewById<ImageView>(R.id.bt_fullscreen)
        val videoUri = Uri.parse("https://i.imgur.com/7bMqysJ.mp4")


        val loadControl: LoadControl = DefaultLoadControl()
        val bandwidthMeter: BandwidthMeter = DefaultBandwidthMeter()


        val trackSelector: TrackSelector = DefaultTrackSelector(
            AdaptiveTrackSelection.Factory(bandwidthMeter)
        )

        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(
            applicationContext, trackSelector, loadControl
        )
        val factory = DefaultHttpDataSourceFactory(
            "exoplayer_video"
        )
        val extractorsFactory: ExtractorsFactory = DefaultExtractorsFactory()
        val mediaSource: MediaSource = ExtractorMediaSource(
            videoUri, factory, extractorsFactory, null, null
        )

        // 플레이어 세팅
        binding.playerView.player = simpleExoPlayer
        // keep Screen On
        binding.playerView.keepScreenOn = true
        // 미디어 준비
        simpleExoPlayer!!.prepare(mediaSource)
        // 준비 되었을때 자동 실행
        simpleExoPlayer!!.playWhenReady = true

        simpleExoPlayer!!.addListener(object : Player.EventListener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if (playbackState == Player.STATE_BUFFERING) {
                    // 버퍼링 할때, progress 바를 보여준다.
                    binding.progressBarCircle.visibility = View.VISIBLE
                } else if (playbackState == Player.STATE_READY) {
                    // 준비할때,  progress 바를 숨긴다.
                    binding.progressBarCircle.visibility = View.GONE
                }
            }
        })

        btfullScreen.setOnClickListener {
            if (flag) { // flag = true 경우, 풀스크린
                btfullScreen.setImageDrawable(resources.getDrawable(R.drawable.ic_fullscreen))
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                flag = false
            } else {
                btfullScreen.setImageDrawable(resources.getDrawable(R.drawable.ic_fullscreen_exit))
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                flag = true
            }
        }


        binding.buttonVideo1.setOnClickListener {
            stopVideo()
            val videoUri = Uri.parse("https://i.imgur.com/rqd1nIR.mp4")
            swichingVideo(videoUri,factory,extractorsFactory)
        }
        binding.buttonVideo2.setOnClickListener {
            stopVideo()
            val videoUri = Uri.parse("https://i.imgur.com/zJCMuEd.mp4")
            swichingVideo(videoUri,factory,extractorsFactory)
        }
        binding.buttonVideo3.setOnClickListener {
            stopVideo()
            val videoUri = Uri.parse("https://i.imgur.com/47Ws6TU.mp4")
            swichingVideo(videoUri,factory,extractorsFactory)
        }
    }// onCreate 끝



    fun swichingVideo(
        videoUri: Uri,
        factory: DefaultHttpDataSourceFactory,
        extractorsFactory: ExtractorsFactory
    ) {
        val mediaSource: MediaSource = ExtractorMediaSource(
            videoUri, factory, extractorsFactory, null, null
        )
        simpleExoPlayer!!.prepare(mediaSource)
        // 준비 되었을때 자동 실행
        simpleExoPlayer!!.playWhenReady = true
    }

    fun stopVideo() {
        // 준비 할때 비디오를 중단 시킨다
        simpleExoPlayer?.playWhenReady = false
        // Get playback state
        simpleExoPlayer?.playbackState
    }

    override fun onPause() {
        super.onPause()
        stopVideo() // 비디오 중단
    }

    override fun onRestart() {
        super.onRestart()
        // 준비되면 바로 재생
        simpleExoPlayer?.playWhenReady = true
        // Get playback state
        simpleExoPlayer?.playbackState
    }


}