package kr.co.everex.exoplayerdemo1

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
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
import kr.co.everex.exoplayerdemo1.databinding.ActivityVideoSlideBinding

class VideoSlideActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVideoSlideBinding

    private val playerFragment = ExoPlayerFragment()
    val fragList = arrayOf(
        playerFragment,
        playerFragment,
        playerFragment,
        playerFragment,
        playerFragment,
        playerFragment
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoSlideBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view) // 뷰 바인딩 적용 완료

        init() // 초기화 메소드 실행
    }// onCreate 끝


    private fun init() {

        val mAdapter = ScreenSlidePagerAdapter(this)
        binding.viewPagerSlideVideo.adapter = mAdapter
        binding.viewPagerSlideVideo.registerOnPageChangeCallback(pageChangeCallback)
        binding.viewPagerSlideVideo.orientation = ViewPager2.ORIENTATION_VERTICAL

        // 클릭 리스너
        binding.btnNext.setOnClickListener { v ->
            slideNextPage()
        }
        binding.buttonPrevious.setOnClickListener { v ->
            slidePreviousPage()
        }

    }// 초기화 종료



    // 어댑터
    inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int {
            return fragList.size
        }
        override fun createFragment(position: Int): Fragment {
            return fragList[position]
        }
    }



    private fun slideNextPage(){
        val next: Int = getItem(+1)
        if (next < fragList!!.size) {
            // move to next screen
            binding.viewPagerSlideVideo.currentItem = next
        } else {
            Toast.makeText(applicationContext, "마지막 화면 입니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun slidePreviousPage(){
        val previous: Int = getItem(-1)
        if (previous >= 0) {
            // move to previous screen
            binding.viewPagerSlideVideo.currentItem = previous
        } else {
            Toast.makeText(applicationContext, "첫 화면 입니다.", Toast.LENGTH_SHORT).show()
        }
    }




    /**
     * ViewPager page change listener
     */
    var pageChangeCallback: ViewPager2.OnPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            Toast.makeText(applicationContext, "포지션 = $position", Toast.LENGTH_SHORT).show()
        }
    }


    fun showProgressBar(status : Boolean){
        if (status){ // 버퍼링 중
            binding.progressBarInSlide.visibility = View.VISIBLE
        } else{ // 버퍼링 끝
            binding.progressBarInSlide.visibility = View.GONE
        }
    }

    private fun getItem(i: Int): Int {
        return binding.viewPagerSlideVideo.currentItem + i
    }
}