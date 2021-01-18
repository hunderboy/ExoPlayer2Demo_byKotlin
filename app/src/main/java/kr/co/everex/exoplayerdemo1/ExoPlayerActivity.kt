package kr.co.everex.exoplayerdemo1

import android.media.session.PlaybackState
import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.extractor.ExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.BandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import kr.co.everex.exoplayerdemo1.databinding.ActivityExoPlayerBinding
import com.google.android.exoplayer2.Player.EventListener as Exoplayer2PlayerEventListener


class ExoPlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExoPlayerBinding

//    var playerView: PlayerView? = null
//    var playerView: ProgressBar? = null
//    var playerView: I? = null

    var simpleExoPlayer: SimpleExoPlayer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExoPlayerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view) // 뷰 바인딩 적용 완료

        // 풀스크린 설정
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val videoUri = Uri.parse("https://i.imgur.com/7bMqysJ.mp4")


        val loadControl: LoadControl = DefaultLoadControl()
        val bandwidthMeter : BandwidthMeter = DefaultBandwidthMeter()
        val trackSelector: TrackSelector = DefaultTrackSelector(
            AdaptiveTrackSelection.Factory(
                bandwidthMeter
            )
        )

        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(
            this, trackSelector, loadControl
        )
        val factory : DefaultHttpDataSourceFactory = DefaultHttpDataSourceFactory(
            "exoplayer_video"
        )
        val extractorsFactory : ExtractorsFactory = DefaultExtractorsFactory()
        val mediaSource : MediaSource = ExtractorMediaSource(
            videoUri, factory, extractorsFactory, null, null
        )

        // 플레이어 세팅
        binding.playerView.player = simpleExoPlayer
        // keep Screen On
        binding.playerView.keepScreenOn = true
        // 미디어 준비
        simpleExoPlayer!!.prepare(mediaSource)
        //
        simpleExoPlayer!!.playWhenReady = true
        simpleExoPlayer!!.addListener({
            override fun onPlayerStateChanged(boolean : Boolean, playbackState: PlaybackState){

            }
        })


    }


}