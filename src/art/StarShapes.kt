package art

import ext.ExtContext
import java.util.*

class StarShapes(private val tx: Float, private val ty: Float, private val tw: Float, private val th: Float) {
    private data class Point(var x: Float, var y: Float, val dxv: Float, val dyv: Float)

    companion object {
        private const val pointCount = 60

        private const val black = "#000000"
        private const val white = "#FFFFFF"
        private const val gray = "#444444"
        private const val font = "8px sans-serif"
    }

    private val gridSizeX = tw / 8
    private val gridSizeY = th / 4
    private val maxLineLength = gridSizeX * .8f
    private val cols = (tw / gridSizeX).toInt()
    private val rows = (th / gridSizeY).toInt()

    private val rnd = Random()
    private val points = mutableListOf<Point>()

    private var frameNo = 0

    private val grid = Array(cols) { Array(rows) { mutableListOf<Point>() } }

    private val framesL = StringBuilder()

    init {
        //create some points
        for (i in 0 until pointCount) {
            val px = tx + (rnd.nextFloat() * tw)
            val py = ty + (rnd.nextFloat() * th)
            points.add(Point(px, py, (rnd.nextFloat() - .5f), (rnd.nextFloat() - .5f)))
        }
    }

    fun drawFrame(ctx: ExtContext) {
        ctx.save()

        frameNo++
        if (frameNo > 3000) {
            return
        }

        //clear canvas
        ctx.setFillStyle(black)
        ctx.setStrokeStyle(black)
        ctx.setLineWidth(1f)
        ctx.strokeRect(tx, ty, tw, th)
        ctx.fillRect(tx, ty, tw, th)

        //frames
        ctx.setLineWidth(.5f)
        ctx.setFillStyle(white)
        ctx.setFont(font)
        framesL.clear()
        framesL.append("frame=")
        framesL.append(frameNo)
        ctx.fillText(framesL.toString(), tx + 20f, ty + 20f)

        //build point groups
        for (x in 0 until cols) {
            for (y in 0 until rows) {
                grid[x][y].clear()
            }
        }
        for (i in 0 until points.size) {
            val gx = ((points[i].x - tx) / gridSizeX).toInt()
            val gy = ((points[i].y - ty) / gridSizeY).toInt()
            if (gx >= cols || gy >= rows || gx < 0 || gy < 0) {
                println("out of bounds:  gx=$gx ($cols) gy=$gy ($rows)")
                continue
            }
            grid[gx][gy].add(points[i])
        }

        //draw lines
        ctx.setLineWidth(1f)
        ctx.setStrokeStyle(gray)
        for (x in 0 until cols) {
            for (y in 0 until rows) {
                //TODO dont draw duplicates
                for (p1 in grid[x][y]) {
                    for (p2 in grid[x][y]) {
                        val d = Math.sqrt(((p1.x - p2.x) * (p1.x - p2.x)).toDouble() + ((p1.y - p2.y) * (p1.y - p2.y)))
                        if (d < maxLineLength) {
                            ctx.beginPath()
                            ctx.moveTo(p1.x, p1.y)
                            ctx.lineTo(p2.x, p2.y)
                            ctx.stroke()
                        }
                    }
                }
            }
        }

        //draw dots
        ctx.setFillStyle(white)
        for (i in 0 until points.size) {
            ctx.beginPath()
            ctx.arc(points[i].x, points[i].y, 1.0f, 0.0f, 2 * 3.14159265358979323846f, true)
            ctx.fill()
        }

        //update points
        for (i in 0 until points.size) {
            points[i].x = (points[i].x + tw + points[i].dxv) % tw
            points[i].y = (points[i].y + th + points[i].dyv) % th
        }
        ctx.restore()
    }

}
