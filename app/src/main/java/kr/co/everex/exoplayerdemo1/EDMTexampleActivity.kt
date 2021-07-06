package kr.co.everex.exoplayerdemo1

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.util.MimeTypes
import com.google.android.exoplayer2.util.Util
import kr.co.everex.exoplayerdemo1.databinding.ActivityEdmtexampleBinding

class EDMTexampleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEdmtexampleBinding

    private var player: SimpleExoPlayer? = null
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition : Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEdmtexampleBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view) // 뷰 바인딩 적용 완료
        // 초기화 실행
        initPlayer()
    }

    private fun initPlayer() {
        player = SimpleExoPlayer.Builder(this).build()
        binding.videoView.player = player

        val videoUrl = "https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4"

        val url =

        val mediaItem = MediaItem.Builder()
            .setUri(getString(videoUrl.toUri())
            .setMimeType(MimeTypes.APPLICATION_MPD)
            .build()
        player.setMediaItem(mediaItem)

        player!!.prepare()
        player!!.playWhenReady = playWhenReady
        player!!.seekTo(currentWindow, playbackPosition)
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
            initPlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        if (Util.SDK_INT < 24 || player == null) {
            initPlayer()
            hideSystemUi()
        }
    }

    private fun hideSystemUi() {
        binding.videoView.setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LOW_PROFILE
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        )
    }

    override fun onPause() {
        if (Util.SDK_INT < 24) { releasePlayer() }
        super.onPause()
    }

    override fun onStop() {
        if (Util.SDK_INT < 24) { releasePlayer() }
        super.onStop()
    }

    private fun releasePlayer() {
        if(player != null ){
            playWhenReady = player!!.playWhenReady
            playbackPosition = player!!.currentPosition
            currentWindow =  player!!.currentWindowIndex
            player!!.release()
            player = null
        }
    }
}