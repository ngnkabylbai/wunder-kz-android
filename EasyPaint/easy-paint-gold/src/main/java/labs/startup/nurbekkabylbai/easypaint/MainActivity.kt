package labs.startup.nurbekkabylbai.easypaint

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var strokeSize = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        paintView.init(metrics)


        plusButton.setOnClickListener {
            strokeSize += 4
            if(strokeSize > 60) strokeSize = 60
            setPaintBrushSizeFontSize()
        }
        minusButton.setOnClickListener {
            strokeSize -= 4
            if(strokeSize < 1) strokeSize = 1
            setPaintBrushSizeFontSize()
        }
        clearButton.setOnClickListener { paintView.clear() }

        eraserButton.setOnClickListener { paintView.isEraseMode = true }
        shareButton.setOnClickListener { paintView.share() }
        black.setOnClickListener { paintView.setDefColor(Color.BLACK)}
        green.setOnClickListener { paintView.setDefColor(Color.GREEN)}
        red.setOnClickListener { paintView.setDefColor(Color.RED)}
    }

    private fun setPaintBrushSizeFontSize() {
        sizeTextView.text = strokeSize.toString()
        paintView.setBrushSize(strokeSize)
    }

}
