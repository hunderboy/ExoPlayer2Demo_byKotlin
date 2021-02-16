package kr.co.everex.exoplayerdemo1

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
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

    private var mAdapter: VideoSlideActivity.ViewsSliderAdapter? = null
    private var layouts: IntArray? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoSlideBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view) // 뷰 바인딩 적용 완료

        init() // 초기화 메소드 실행
    }// onCreate 끝


    private fun init() {
        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = intArrayOf(
            R.layout.slide_one,
            R.layout.slide_two,
            R.layout.slide_three,
            R.layout.slide_four
        )
        mAdapter = ViewsSliderAdapter()
        binding.viewPagerSlideVideo.adapter = mAdapter
        binding.viewPagerSlideVideo.registerOnPageChangeCallback(pageChangeCallback)
        binding.viewPagerSlideVideo.orientation = ViewPager2.ORIENTATION_VERTICAL
        binding.btnNext.setOnClickListener { v ->
            // checking for last page
            // if last page home screen will be launched
            val current: Int = getItem(+1)
            if (current < layouts!!.size) {
                // move to next screen
                binding.viewPagerSlideVideo.currentItem = current
            } else {
                launchHomeScreen()
            }
        }
        binding.buttonPrevious.setOnClickListener { v ->
            // checking for last page
            // if last page home screen will be launched
            val current: Int = getItem(-1)
            if (current >= 0) {
                // move to previous screen
                binding.viewPagerSlideVideo.currentItem = current
            } else {
                launchHomeScreen()
            }
        }

    }// 초기화 종료

    // 현재 아이템 포지션
    private fun getItem(i: Int): Int {
        return binding.viewPagerSlideVideo.currentItem + i
    }
    // 프래그먼트 종료
    private fun launchHomeScreen() {
        Toast.makeText(this, "뷰페이저 종료", Toast.LENGTH_SHORT).show()
        finish()
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


    inner class ViewsSliderAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(viewType, parent, false)
            return SliderViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {}
        override fun getItemViewType(position: Int): Int {
            return layouts!!.get(position)
        }

        override fun getItemCount(): Int {
            return layouts!!.size
        }

        inner class SliderViewHolder(view: View?) : RecyclerView.ViewHolder(
            view!!
        ) {
            var title: TextView? = null
            var year: TextView? = null
            var genre: TextView? = null
        }
    }


    fun showProgressBar(status : Boolean){
        if (status){ // 버퍼링 중
            binding.progressBarInSlide.visibility = View.VISIBLE
        } else{ // 버퍼링 끝
            binding.progressBarInSlide.visibility = View.GONE
        }
    }

}