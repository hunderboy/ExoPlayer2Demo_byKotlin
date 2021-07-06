package kr.co.everex.exoplayerdemo1

import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.extractor.ExtractorsFactory

import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.BandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import kr.co.everex.exoplayerdemo1.databinding.ActivityFullscreenBinding


class FullscreenActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityFullscreenBinding
//
//    var simpleExoPlayer: SimpleExoPlayer? = null
//    private var flag = false
//
//    var fullscreen = false
//    var btfullScreen: ImageView? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityFullscreenBinding.inflate(layoutInflater)
//        val view = binding.root
//        setContentView(view) // 뷰 바인딩 적용 완료
//
//
//        /**
//         * Exoplyer ----------------------------------------------------------------------------------------
//         */
//        btfullScreen = binding.playerView.findViewById(R.id.bt_fullscreen_ver2)
//        val videoUri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/smartkneewalk.appspot.com/o/videos%2F%EC%83%98%ED%94%8C_%EB%8B%88%EC%8A%AC%EB%9D%BC%EC%9D%B4%EB%93%9C.mp4?alt=media&token=721934ba-f035-44c5-9795-a2ec9dc3dda3")
//
//        val loadControl: LoadControl = DefaultLoadControl()
//        val bandwidthMeter: BandwidthMeter = DefaultBandwidthMeter()
//
//        /**
//        TrackSelector
//        SimpleExoPlayer 생성 과정에서 두번째 인자로 전달된 클래스는 영상의 Track 정보를 세팅하는 역할을 한다.
//        이 정보라면 예를 들면 선호하는 오디오 언어는 어떤 것인지, 비디오 사이즈는 무엇인지,
//        비디오 bitrate는 어떤 것으로 할지 등등 이런 것들을 말한다.
//        이것도 Renderer와 동일하게 따로 커스터마이즈 할 수 있긴 하나 특별한 이유가 없다면,
//        라이브러리에서 기본으로 만들어 둔 것을 쓰는게 가장 좋다.
//         */
//        val trackSelector: TrackSelector = DefaultTrackSelector(
//            AdaptiveTrackSelection.Factory(bandwidthMeter)
//        )
//
//        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(
//            applicationContext, trackSelector, loadControl
//        )
//        val factory = DefaultHttpDataSourceFactory(
//            "exoplayer_video"
//        )
//        val extractorsFactory: ExtractorsFactory = DefaultExtractorsFactory()
//        val mediaSource: MediaSource = ExtractorMediaSource(
//            videoUri, factory, extractorsFactory, null, null
//        )
//
//        // 플레이어 세팅
//        binding.playerView.player = simpleExoPlayer
//        // keep Screen On
//        binding.playerView.keepScreenOn = true
//        // 미디어 준비
//        simpleExoPlayer!!.prepare(mediaSource)
//        // 준비 되었을때 자동 실행
//        simpleExoPlayer!!.playWhenReady = true
//
//        simpleExoPlayer!!.addListener(object : Player.EventListener {
//            // 플레이어 상태 변화 메소드
//            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
//                if (playbackState == Player.STATE_BUFFERING) {
//                    // 버퍼링 할때, progress 바를 보여준다.
//                    binding.progressBar.visibility = View.VISIBLE
//                } else if (playbackState == Player.STATE_READY) {
//                    // 준비할때,  progress 바를 숨긴다.
//                    binding.progressBar.visibility = View.GONE
//                }
//            }
//        })
//
////        binding.playerView.controllerAutoShow = false
//        binding.playerView.hideController()
//
//        btfullScreen?.setOnClickListener{
//            if (fullscreen) {
//                btfullScreen?.setImageDrawable(
//                    ContextCompat.getDrawable(
//                        this,
//                        R.drawable.ic_fullscreen
//                    )
//                )
//                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
//                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
//                val params = binding.playerView.layoutParams
//                params.width = MATCH_PARENT
//                params.height =  ((230 * applicationContext.resources.displayMetrics.density).toInt())
//                binding.playerView.layoutParams = params
//                binding.playerView.margin(top = 40F)
//                fullscreen = false
//            } else {
//                btfullScreen?.setImageDrawable(
//                    ContextCompat.getDrawable(
//                        this,
//                        R.drawable.ic_fullscreen_exit
//                    )
//                )
//                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
//                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
//
//                val params = binding.playerView.layoutParams
//                params.width = MATCH_PARENT
//                params.height = MATCH_PARENT
//                binding.playerView.layoutParams = params
//                binding.playerView.margin(top = 0F)
//                fullscreen = true
//            }
//        }
//
//
//
////        binding.playerView.setControllerVisibilityListener { visibility ->
////            //플레이어 컨트롤러 셋팅
////            playListBtn.setVisibility(visibility)
////        }
//
//
//    }// onCreate 끝
//
//
//    override fun onConfigurationChanged(newConfig: Configuration) {
//        super.onConfigurationChanged(newConfig)
//
//        // Checks the orientation of the screen
//        if (newConfig.orientation === Configuration.ORIENTATION_LANDSCAPE) {
////            Toast.makeText(this, "가로모드", Toast.LENGTH_SHORT).show()
//
//            btfullScreen?.setImageDrawable(
//                ContextCompat.getDrawable(
//                    this,
//                    R.drawable.ic_fullscreen_exit
//                )
//            )
//            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
//            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
//
//            val params = binding.playerView.layoutParams
//            params.width = MATCH_PARENT
//            params.height = MATCH_PARENT
//            binding.playerView.layoutParams = params
//            binding.playerView.margin(top = 0F)
//            fullscreen = true
//
//        } else if (newConfig.orientation === Configuration.ORIENTATION_PORTRAIT) {
////            Toast.makeText(this, "세로모드", Toast.LENGTH_SHORT).show()
//
//            btfullScreen?.setImageDrawable(
//                ContextCompat.getDrawable(
//                    this,
//                    R.drawable.ic_fullscreen
//                )
//            )
//            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
//            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
//            val params = binding.playerView.layoutParams
//            params.width = MATCH_PARENT
//            params.height =  ((230 * applicationContext.resources.displayMetrics.density).toInt())
//            binding.playerView.layoutParams = params
//            binding.playerView.margin(top = 40F)
//            fullscreen = false
//        }
//    }
//
//
//    override fun onPause() {
//        super.onPause()
//        // 준비 할때 비디오를 중단 시킨다
//        simpleExoPlayer?.playWhenReady = false
//        // Get playback state
//        simpleExoPlayer?.playbackState
//    }
//
//    override fun onRestart() {
//        super.onRestart()
//        // 준비되면 바로 재생
//        simpleExoPlayer?.playWhenReady = true
//        // Get playback state
//        simpleExoPlayer?.playbackState
//    }
//
//    // marginTop 컨트롤
//    fun View.margin(
//        left: Float? = null,
//        top: Float? = null,
//        right: Float? = null,
//        bottom: Float? = null
//    ) {
//        layoutParams<ViewGroup.MarginLayoutParams> {
//            left?.run { leftMargin = dpToPx(this) }
//            top?.run { topMargin = dpToPx(this) }
//            right?.run { rightMargin = dpToPx(this) }
//            bottom?.run { bottomMargin = dpToPx(this) }
//        }
//    }
//    inline fun <reified T : ViewGroup.LayoutParams> View.layoutParams(block: T.() -> Unit) {
//        if (layoutParams is T) block(layoutParams as T)
//    }
//    fun View.dpToPx(dp: Float): Int = context.dpToPx(dp)
//    fun Context.dpToPx(dp: Float): Int = TypedValue.applyDimension(
//        TypedValue.COMPLEX_UNIT_DIP,
//        dp,
//        resources.displayMetrics
//    ).toInt()



}