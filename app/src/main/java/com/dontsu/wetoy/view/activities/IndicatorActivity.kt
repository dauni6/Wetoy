package com.dontsu.wetoy.view.activities


import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.dontsu.wetoy.R
import com.dontsu.wetoy.util.MULTI_PERMISSION_CODE
import com.dontsu.wetoy.util.PermissionSupport
import com.dontsu.wetoy.util.requiredPermissions
import com.dontsu.wetoy.view.fragments.IndicatorOneFragment
import com.dontsu.wetoy.view.fragments.IndicatorThreeFragment
import com.dontsu.wetoy.view.fragments.IndicatorTwoFragment
import kotlinx.android.synthetic.main.activity_indicator.*

class IndicatorActivity : AppCompatActivity() {

    private lateinit var fragmentViewPagerAdapter: FragmentViewPagerAdapter

    private val indicatorOne =
        IndicatorOneFragment()
    private val indicatorTwo =
        IndicatorTwoFragment()
    private val indicatorThree =
        IndicatorThreeFragment()

    private var fmFlag = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_indicator)

        fragmentViewPagerAdapter = FragmentViewPagerAdapter(supportFragmentManager)
        viewPagerContainer.offscreenPageLimit = 3 //페이지 수 3개 제한(해도 안 해도 상관x)
        viewPagerContainer.adapter =fragmentViewPagerAdapter
        viewPagerContainer.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                indicator.selectDot(position)

                when(position) {
                    0 -> {
                        fmFlag = position
                        left_button.visibility = View.INVISIBLE
                        right_button.text = ">"
                    }
                    1 -> {
                        fmFlag = position
                        left_button.visibility = View.VISIBLE
                        left_button.text = "<"
                        right_button.text = ">"
                    } else -> {
                        fmFlag = position
                        left_button.visibility = View.VISIBLE
                        right_button.text = "시작하기"
                    }
                }
            }
        })

        indicator.createDotPanel(3, R.drawable.indicator_dot_off, R.drawable.indicator_dot_on, 0)
    }

    @Suppress("DEPRECATION")
    inner class FragmentViewPagerAdapter(fm : FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return when(position) {
                0 -> indicatorOne
                1 -> indicatorTwo
                else -> indicatorThree
            }
        }
        override fun getCount(): Int = 3
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
       when(requestCode) {
           MULTI_PERMISSION_CODE -> {
               if (grantResults.isNotEmpty()) {
                   for ((i, permission) in permissions.withIndex()) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) { //권한 획득 실패
                            Toast.makeText(this, "모든 권한 승인을 하지 않았습니다!!", Toast.LENGTH_SHORT).show()
                        }
                   }
               }
           }
       }
    }

    fun onRightButton(v: View) {
        when (fmFlag) {
            0 -> {
                viewPagerContainer.currentItem = 1
            }
            1 -> {
                viewPagerContainer.currentItem = 2
            }
            else -> {
                if (ContextCompat.checkSelfPermission(applicationContext, requiredPermissions[0]) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(applicationContext, requiredPermissions[1]) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(applicationContext, requiredPermissions[2]) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(applicationContext, requiredPermissions[3]) == PackageManager.PERMISSION_GRANTED) {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val per = PermissionSupport(this, applicationContext)
                    per.checkPermissions() //퍼미션체크하기
                }
            }
        }
    }

    fun onLeftButton(v: View) {
        when (fmFlag) {
            1 -> {
                viewPagerContainer.currentItem = 0
            }
            2 -> {
                viewPagerContainer.currentItem = 1
            }
        }
    }

    override fun onBackPressed() {
        when (fmFlag) {
            0 -> {
                super.onBackPressed() //종료
            }
            1 -> {
                viewPagerContainer.currentItem = 0
            }
            else -> {
                viewPagerContainer.currentItem = 1
            }
        }
    }

}