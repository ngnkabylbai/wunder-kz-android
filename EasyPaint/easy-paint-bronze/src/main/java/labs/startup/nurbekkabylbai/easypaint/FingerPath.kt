package labs.startup.nurbekkabylbai.easypaint

import android.graphics.*
/**
 * Created by Nurbek Kabylbay on 05.03.2018.
 */
class FingerPath(
        strokeWidth: Float,
        color: Int,
        val path: Path) {

    val paint = Paint()

    init {
        paint.strokeWidth = strokeWidth
        paint.isAntiAlias = true
        paint.color = color
        paint.style = Paint.Style.STROKE
        paint.strokeCap = Paint.Cap.ROUND
    }
}