package kr.co.everex.exoplayerdemo1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import kr.co.everex.exoplayerdemo1.databinding.ActivityVideoSlideBinding



class VideoSlideActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVideoSlideBinding

    // 해당 프래그먼트 모두 동일한 프래그먼트
    val videoFragmentList = arrayListOf<ExoPlayerFragment>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoSlideBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view) // 뷰 바인딩 적용 완료

        init() // 초기화 메소드 실행
    }// onCreate 끝


    private fun init() {
        for (i in 0..5){
            val exoFragment = ExoPlayerFragment()
            videoFragmentList.add(exoFragment)
        }

        val mAdapter = ScreenSlidePagerAdapter(this)
        binding.viewPager.adapter = mAdapter
        binding.viewPager.registerOnPageChangeCallback(pageChangeCallback)
        binding.viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
        binding.viewPager.isUserInputEnabled = false // 터치 컨트롤 불가


        binding.btnNext.setOnClickListener { v -> // 다음
            // ExoPlayerFragment 의 영상을 일시정지 및 초기화 시켜야 한다.
//            videoFragmentList[0].stopVideo()
            slideNextPage()
        }
        binding.buttonPrevious.setOnClickListener { v -> // 이전


            slidePreviousPage()
        }
    }// 초기화 종료


    // 해당 함수 호출을, ExoPlayerFragment 쪽에서 먼저 일시정지 및 데이터 해제를 한후에 적용한 후에 호출
    private fun slideNextPage() {
        val next: Int = getItem(+1)
        if (next < videoFragmentList!!.size) {
            // 다음 프래그먼트 이동

            binding.viewPager.currentItem = next
        } else {
            Toast.makeText(applicationContext, "마지막 화면 입니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun slidePreviousPage() {
        val previous: Int = getItem(-1)
        if (previous >= 0) {
            // move to previous screen
            binding.viewPager.currentItem = previous
        } else {
            Toast.makeText(applicationContext, "첫 화면 입니다.", Toast.LENGTH_SHORT).show()
        }
    }


    /**
     * ViewPager page change listener
     */
    var pageChangeCallback: OnPageChangeCallback = object : OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
        }
    }


    fun showProgressBar(status: Boolean) {
        if (status) { // 버퍼링 중
            binding.progressBarInSlide.visibility = View.VISIBLE
        } else { // 버퍼링 끝
            binding.progressBarInSlide.visibility = View.GONE
        }
    }


    private fun getItem(i: Int): Int {
        return binding.viewPager.currentItem + i
    }

    // 어댑터
    inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int {
            return videoFragmentList.size
        }
        override fun createFragment(position: Int): Fragment {
            return videoFragmentList[position]
        }
    }

}