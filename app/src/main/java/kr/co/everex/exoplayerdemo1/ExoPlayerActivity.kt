package kr.co.everex.exoplayerdemo1

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
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
import kr.co.everex.exoplayerdemo1.databinding.ActivityExoPlayerBinding


class ExoPlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExoPlayerBinding

    var simpleExoPlayer: SimpleExoPlayer? = null
    private var flag = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExoPlayerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view) // 뷰 바인딩 적용 완료


        /**
         * Exoplyer ----------------------------------------------------------------------------------------
         */
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

        val DefaultTimeBar = binding.playerView.findViewById<com.google.android.exoplayer2.ui.DefaultTimeBar>(R.id.exo_progress)

        /**
         * 테스트 영상 URI 설정
         내부 영상 재생시 설정하는 uri path
        val uri = Uri.parse("android.resource://$packageName/raw/sample")
        val uri = Uri.parse("android.resource://sample_video/sampleVideo1.mp4")
         */
        val videoUri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/everexfirebase.appspot.com/o/%E1%84%89%E1%85%A2%E1%86%B7%E1%84%91%E1%85%B3%E1%86%AF_%E1%84%82%E1%85%B5%E1%84%89%E1%85%B3%E1%86%AF%E1%84%85%E1%85%A1%E1%84%8B%E1%85%B5%E1%84%83%E1%85%B3.mp4?alt=media&token=f576e14a-6edb-4dfe-bae5-9d6f39a274ab")

        val loadControl: LoadControl = DefaultLoadControl()
        val bandwidthMeter: BandwidthMeter = DefaultBandwidthMeter()

        /**
        TrackSelector
            SimpleExoPlayer 생성 과정에서 두번째 인자로 전달된 클래스는 영상의 Track 정보를 세팅하는 역할을 한다.
            이 정보라면 예를 들면 선호하는 오디오 언어는 어떤 것인지, 비디오 사이즈는 무엇인지,
            비디오 bitrate는 어떤 것으로 할지 등등 이런 것들을 말한다.
            이것도 Renderer와 동일하게 따로 커스터마이즈 할 수 있긴 하나 특별한 이유가 없다면,
            라이브러리에서 기본으로 만들어 둔 것을 쓰는게 가장 좋다.
         */
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
//            override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {}
//            override fun onSeekProcessed() {}
//            override fun onTracksChanged(trackGroups: TrackGroupArray?, trackSelections: TrackSelectionArray?) {}
//            override fun onPlayerError(error: ExoPlaybackException?) {} override fun onLoadingChanged(isLoading: Boolean) {}
//            override fun onPositionDiscontinuity(reason: Int) {}
//            override fun onRepeatModeChanged(repeatMode: Int) {}
//            override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {}
//            override fun onTimelineChanged(timeline: Timeline?, manifest: Any?, reason: Int) {}
            // 플레이어 상태 변화 메소드
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if (playbackState == Player.STATE_BUFFERING) {
                    // 버퍼링 할때, progress 바를 보여준다.
                    binding.progressBar.visibility = View.VISIBLE
                } else if (playbackState == Player.STATE_READY) {
                    // 준비할때,  progress 바를 숨긴다.
                    binding.progressBar.visibility = View.GONE
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

    }// onCreate 끝

    override fun onPause() {
        super.onPause()
        // 준비 할때 비디오를 중단 시킨다
        simpleExoPlayer?.playWhenReady = false
        // Get playback state
        simpleExoPlayer?.playbackState
    }

    override fun onRestart() {
        super.onRestart()
        // 준비되면 바로 재생
        simpleExoPlayer?.playWhenReady = true
        // Get playback state
        simpleExoPlayer?.playbackState
    }


}