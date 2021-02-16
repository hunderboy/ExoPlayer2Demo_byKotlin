package kr.co.everex.exoplayerdemo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import kr.co.everex.exoplayerdemo1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view) // 뷰 바인딩 적용 완료

        binding.playButton1.setOnClickListener{
            val intent = Intent(this, ExoPlayerActivity::class.java)
            startActivity(intent)
        }
        binding.playButton2.setOnClickListener{
            val intent = Intent(this, VideoSlideActivity::class.java)
            startActivity(intent)
        }
        binding.playButton3.setOnClickListener{ // 위아래 슬라이드 컨트롤
            val intent = Intent(this, VerticalViewPagerActivity::class.java)
            startActivity(intent)
        }
        binding.playButton4.setOnClickListener{ // 1개의 Player에서 영상변경 하기
            val intent = Intent(this, OnePlayerActivity::class.java)
            startActivity(intent)
        }



    }


}