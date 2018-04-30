package com.example.nurbekkabylbai.rotatingcars

import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rotate.setOnClickListener {
            val first: Drawable = firstImg.drawable
            val second: Drawable = secondImg.drawable
            val third: Drawable = thirdImg.drawable
            val fourth: Drawable = fourthImg.drawable

            firstImg.setImageDrawable(fourth)
            secondImg.setImageDrawable(first)
            thirdImg.setImageDrawable(second)
            fourthImg.setImageDrawable(third)
        }
    }
}
