package com.dontsu.wetoy.util

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.marginBottom
import androidx.core.view.marginRight

class CircleIndicator: LinearLayout {

    private var mContext: Context? = null

    private var mDefaultCircle: Int = 0
    private var mSelectCircle: Int = 0

    private var imageDot: MutableList<ImageView> = mutableListOf()
    private lateinit var selectedText:TextView

    // 4.5dp 를 픽셀 단위로 바꿉니다.
    private val temp = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, 4.5f, resources.displayMetrics)

    constructor(context: Context) : super(context) { //context는 MainActivity, 그 액티비티의 xml에서 만들어지기 때문에 자동으로 context가 MainActivity가 된다.
        mContext = context
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) { //AttributeSet은 XML 태그에 넣은 속성들

        mContext = context
    }

    /**
     * 기본 점 생성
     * @param count 점의 갯수
     * @param defaultCircle 기본 점의 이미지
     * @param selectCircle 선택된 점의 이미지
     * @param position 선택된 점의 포지션
     */
    fun createDotPanel(count: Int, defaultCircle: Int, selectCircle: Int, position: Int) {

        this.removeAllViews()

        mDefaultCircle = defaultCircle
        mSelectCircle = selectCircle

        for (i in 0 until count) { //until은 count - 1 을 의미 => 0..count - 1 로 적어도 똑같은 결과.

            imageDot.add(ImageView(mContext).apply { setPadding(temp.toInt(), 0, temp.toInt(), 0) })
            this.addView(imageDot[i]) //CircleIndicator 클래스는 LinearLayout, 즉 LinearLayout에 점들을 추가시킴
        }

        //인덱스 선택
        selectDot(position)
    }

    /**
     * 선택된 점 표시
     * @param position
     */
    fun selectDot(position: Int) {

        for (i in imageDot.indices) {

            if (i == position) { //선택된 점: 보라색

                imageDot[i].setImageResource(mSelectCircle)

            } else { //기본 점 : 회색

                imageDot[i].setImageResource(mDefaultCircle)
            }

        }
    }





}

