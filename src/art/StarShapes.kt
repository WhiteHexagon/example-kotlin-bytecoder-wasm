package art

import ext.ExtContext
import java.util.*

class StarShapes(private val tx: Float, private val ty: Float, private val tw: Float, private val th: Float) {
    private data class Point(var x: Float, var y: Float, val dxv: Float, val dyv: Float)

    private val pointCount = 60

    private val gridSize = tw / 8
    private val maxLineLength = gridSize * .8f
    private val cols = (tw / gridSize).toInt()
    private val rows = (th / gridSize).toInt()

    private val minX = tx
    private val minY = ty
    private val maxX = tx + (cols * gridSize)
    private val maxY = ty + (rows * gridSize)

    private val rnd = Random()
    private val points = mutableListOf<Point>()

    private val pointGroups = mutableMapOf<String, MutableList<Point>>()

    private var frameNo = 0

    init {
        //create some points
        for (i in 0 until pointCount) {
            val px = tx + (rnd.nextFloat() * tw)
            val py = ty + (rnd.nextFloat() * th)
            points.add(Point(px, py, (rnd.nextFloat() - .5f), (rnd.nextFloat() - .5f)))
        }

        //create point groups
        for (x in 0 until cols) {
            for (y in 0 until rows) {
                pointGroups["$x,$y"] = mutableListOf()
            }
        }
    }

    fun drawFrame(ctx: ExtContext) {
        ctx.save()

        frameNo++
        if (frameNo > 600) {
            return
        }

        //clear canvas
        ctx.setFillStyle("#000000")
        ctx.setStrokeStyle("#000000")
        ctx.setLineWidth(1f)
        ctx.strokeRect(tx, ty, tw, th)
        ctx.fillRect(tx, ty, tw, th)

        ctx.setLineWidth(.5f)
        ctx.setFillStyle("#FFFFFF")

        ctx.setFont("8px sans-serif")
        ctx.fillText("frame=$frameNo", tx + 20f, ty + 20f)

        //build point groups
        for (pg in pointGroups) {
            pg.value.clear()
        }
        for (i in 0 until points.size) {
            val gx = ((points[i].x - tx) / gridSize).toInt()
            val gy = ((points[i].y - ty) / gridSize).toInt()
            if (gx > cols || gy > rows || gx < 0 || gy < 0) {
                //println("out of bounds:  gx=$gx gy=$gy")
                continue
            }
            if (pointGroups["$gx,$gy"] == null) {
                //println("null, huh?  gx=$gx gy=$gy")
                continue
            } else {
                pointGroups["$gx,$gy"]!!.add(points[i])
            }
        }

        //draw lines
        ctx.setLineWidth(1f)
        ctx.setStrokeStyle("#444444")
        for (x in 0 until cols) {
            for (y in 0 until rows) {
                //TODO dont draw duplicates
                for (p1 in pointGroups["$x,$y"]!!) {
                    for (p2 in pointGroups["$x,$y"]!!) {
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
        ctx.setFillStyle("#FFFFFF")
        for (i in 0 until points.size) {
            ctx.beginPath()
            ctx.arc(points[i].x.toDouble(), points[i].y.toDouble(), 1.0, 0.0, 2 * Math.PI, true)
            ctx.fill()
        }

        //update points
        for (i in 0 until points.size) {
            points[i].x += points[i].dxv
            points[i].y += points[i].dyv
            if (points[i].x >= maxX) {
                points[i].x -= tw
            }
            if (points[i].x < minX) {
                points[i].x += tw
            }
            if (points[i].y > maxY) {
                points[i].y -= th
            }
            if (points[i].y < minY) {
                points[i].y += th
            }
        }
        ctx.restore()
    }


}
