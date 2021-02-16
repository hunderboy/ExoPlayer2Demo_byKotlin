package kr.co.everex.exoplayerdemo1

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.Toast
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
import kr.co.everex.exoplayerdemo1.databinding.FragmentExoPlayerBinding


class ExoPlayerFragment : Fragment() {
    private var _binding: FragmentExoPlayerBinding? = null
    private val binding get() = _binding!! // 해당 속성은 onCreateView 와 onDestroyView 에서만 유효함

    var simpleExoPlayer: SimpleExoPlayer? = null
    private var flag = false



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExoPlayerBinding.inflate(inflater, container, false)
        val view = binding.root


        // 풀스크린 설정
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity!!.window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            activity!!.window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        // 풀스크린 버튼 설정
        val btfullScreen = binding.playerView.findViewById<ImageView>(R.id.bt_fullscreen)
        val videoUri = Uri.parse("https://i.imgur.com/7bMqysJ.mp4")
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

        simpleExoPlayer = context?.let {
            ExoPlayerFactory.newSimpleInstance(
                it, trackSelector, loadControl
            )
        }
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
            // 플레이어 상태 변화 메소드
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if (playbackState == Player.STATE_BUFFERING) {
                    // 버퍼링 할때, progress 바를 보여준다.
                    (activity as VideoSlideActivity).showProgressBar(true)
                } else if (playbackState == Player.STATE_READY) {
                    // 준비할때,  progress 바를 숨긴다.
                    (activity as VideoSlideActivity).showProgressBar(false)
                }
            }
        })

        btfullScreen.setOnClickListener {
            if (flag) { // flag = true 경우, 풀스크린
                btfullScreen.setImageDrawable(resources.getDrawable(R.drawable.ic_fullscreen))
                activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                flag = false
            } else {
                btfullScreen.setImageDrawable(resources.getDrawable(R.drawable.ic_fullscreen_exit))
                activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                flag = true
            }
        }


        return view
    }// onCreatView 끝





    override fun onPause() {
        super.onPause()
        // 준비 할때 비디오를 중단 시킨다
        simpleExoPlayer?.playWhenReady = false
        // Get playback state
        simpleExoPlayer?.playbackState
    }

    override fun onResume() {
        super.onResume()
        // 준비되면 바로 재생
        simpleExoPlayer?.playWhenReady = true
        // Get playback state
        simpleExoPlayer?.playbackState
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(activity, "Fragment onDestroy() 호출", Toast.LENGTH_SHORT).show()
        Log.e("ExoPlayerFragment : ","onDestroy() 호출")
    }

}