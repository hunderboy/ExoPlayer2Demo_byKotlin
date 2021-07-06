package kr.co.everex.exoplayerdemo1

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.extractor.ExtractorsFactory

import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.upstream.BandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import kr.co.everex.exoplayerdemo1.databinding.FragmentExoPlayerBinding


class ExoPlayerFragment : Fragment() {
//    private var _binding: FragmentExoPlayerBinding? = null
//    private val binding get() = _binding!! // 해당 속성은 onCreateView 와 onDestroyView 에서만 유효함
//
//    var simpleExoPlayer: SimpleExoPlayer? = null
//    private var flag = false
//
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = FragmentExoPlayerBinding.inflate(inflater, container, false)
//        val view = binding.root
//
//
//        // 풀스크린 설정
//        @Suppress("DEPRECATION")
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            activity!!.window.insetsController?.hide(WindowInsets.Type.statusBars())
//        } else {
//            activity!!.window.setFlags(
//                WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN
//            )
//        }
//        // 풀스크린 버튼 설정
//        val btfullScreen = binding.playerView.findViewById<ImageView>(R.id.bt_fullscreen)
//
//
//        val videoUri = Uri.parse("https://i.imgur.com/7bMqysJ.mp4")
//        val loadControl: LoadControl = DefaultLoadControl()
//        val bandwidthMeter: BandwidthMeter = DefaultBandwidthMeter()
//
//
//        val trackSelector: TrackSelector = DefaultTrackSelector(
//            AdaptiveTrackSelection.Factory(bandwidthMeter)
//        )
//
//        simpleExoPlayer = context?.let {
//            ExoPlayerFactory.newSimpleInstance(
//                it, trackSelector, loadControl
//            )
//        }
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
//        // 이벤트 리스너
//        simpleExoPlayer!!.addListener(object : Player.EventListener {
//            // 플레이어 상태 변화 메소드
//            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
//                if (playbackState == Player.STATE_BUFFERING) {
//                    // 버퍼링 할때, progress 바를 Show
//                    (activity as VideoSlideActivity).showProgressBar(true)
//                } else if (playbackState == Player.STATE_READY) {
//                    // 준비되었을때, progress 바를 Hide
//                    (activity as VideoSlideActivity).showProgressBar(false)
//                }
//            }
//        })
//
//        // 풀스크린
//        btfullScreen.setOnClickListener {
//            if (flag) { // flag = true 경우, 풀스크린
//                btfullScreen.setImageDrawable(resources.getDrawable(R.drawable.ic_fullscreen))
//                activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
//                flag = false
//            } else {
//                btfullScreen.setImageDrawable(resources.getDrawable(R.drawable.ic_fullscreen_exit))
//                activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
//                flag = true
//            }
//        }
//
//
//        return view
//    }// onCreatView 끝
//
//
//
//    // 비디오 플레이어 해제
//    private fun releasePlayer() {
//        if (simpleExoPlayer != null) {
//            // binding.playerView.player.setPlayer(null)
//            binding.playerView.player = null
//            simpleExoPlayer!!.release()
//            simpleExoPlayer = null;
//        }
//    }
//
//
//    fun stopVideo(){
//        // 준비 할때 비디오를 중단 시킨다
//        simpleExoPlayer?.playWhenReady = false
//        // Get playback state
//        simpleExoPlayer?.playbackState
//    }
//    fun restartVideo(){
//        // 준비되면 바로 재생
//        simpleExoPlayer?.playWhenReady = true
//        // Get playback state
//        simpleExoPlayer?.playbackState
//    }
//
//
//    override fun onPause() {
//        super.onPause()
//        stopVideo()
//        releasePlayer()
//    }
//    override fun onResume() {
//        super.onResume()
//        restartVideo()
//    }
//    override fun onDestroy() {
//        super.onDestroy()
//        Toast.makeText(activity, "Fragment onDestroy() 호출", Toast.LENGTH_SHORT).show()
//        Log.e("ExoPlayerFragment : ", "onDestroy() 호출")
//    }

}