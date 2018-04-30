package labs.startup.nurbekkabylbai.easypaint

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import android.R.attr.bitmap
import android.net.Uri
import android.provider.MediaStore.Images
import android.content.Intent
import android.os.Environment
import android.support.v4.content.ContextCompat.startActivity
import java.io.File
import java.io.FileOutputStream
import android.support.v4.content.ContextCompat.startActivity
import android.graphics.Bitmap




/**
 * Created by Nurbek Kabylbay on 05.03.2018.
 */
class PaintView(context: Context, attributeSet: AttributeSet?): View(context, attributeSet) {

    private var defColor = Color.BLACK
    private var strokeSize = 10f
    private var backGroundColor = Color.WHITE
    private val tolerance = 0f
    private var mX = 0f
    private var mY = 0f
    private lateinit var mPath: Path
    private val paths = ArrayList<FingerPath>()
    private lateinit var mBitmap: Bitmap
    private lateinit var mCanvas: Canvas

    var isEraseMode = false

    fun init(metrics: DisplayMetrics) {
        val width = metrics.widthPixels
        val height = metrics.heightPixels

        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mBitmap)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.save()
        mCanvas.drawColor(backGroundColor)

        for(fingerPath in paths) {
            mCanvas.drawPath(fingerPath.path, fingerPath.paint)
        }

        canvas.drawBitmap(mBitmap, 0f, 0f, null)
        canvas.restore()
    }

    private fun startTouch(x: Float, y: Float) {
        mPath = Path()
        val fingerPath = FingerPath(strokeSize, if(isEraseMode) Color.WHITE else defColor , mPath)
        paths.add(fingerPath)
        mPath.reset()
        mPath.moveTo(x, y)

        mX = x
        mY = y
        invalidate()
    }

    private fun moveTouch(x: Float, y: Float) {
        val dx = Math.abs(x - mX)
        val dy = Math.abs(y - mY)

        if(dx >= tolerance && dy >= tolerance) {
            mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2)
            mX = x
            mY = y
        }
        invalidate()
    }

    private fun upTouch() {
        mPath.lineTo(mX, mY)
        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when(event.action) {
            MotionEvent.ACTION_DOWN -> startTouch(x, y)
            MotionEvent.ACTION_MOVE -> moveTouch(x, y)
            MotionEvent.ACTION_UP -> upTouch()
            else -> {}
        }
        return true
    }

    fun clear() {
        paths.clear()
        invalidate()
    }

    fun setBrushSize(strokeSize: Int) {
        this.strokeSize = strokeSize.toFloat()
    }

    fun setDefColor(color: Int) {
        defColor = color
        isEraseMode = false
    }

    fun share() {

        try {
            val file = File(context.externalCacheDir, "easypaint.png")
            val fOut = FileOutputStream(file)
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut)
            fOut.flush()
            fOut.close()
            file.setReadable(true, false)
            val intent = Intent(android.content.Intent.ACTION_SEND)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file))
            intent.type = "image/png"
            context.startActivity(Intent.createChooser(intent, "Share image via"))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}