package art

import de.mirkosertic.bytecoder.api.web.AnimationFrameCallback
import de.mirkosertic.bytecoder.api.web.Window
import ext.ExtCanvas
import ext.ExtContext
import ext.ExtDiv
import ext.ExtWindow

class Dom {
    data class Paper(val canvas: ExtCanvas, val ctx: ExtContext, val width: Int, val height: Int)

    private val window = Window.window()!! as ExtWindow
    private val document = window.document()
    private val scale = window.devicePixelRatio
    private val app = (document.getElementById("app") as ExtDiv)
    private var animationCallback: AnimationFrameCallback? = null

    fun createCanvasWithID(id: String): Paper {
        app.style("float:left; width:100%; height:100%;")
        val cw = app.clientWidth()
        val ch = app.clientHeight()
        val actualWidth = (cw * scale).toInt()
        val actualHeight = (ch * scale).toInt()
        val canvas = (document.createElement("canvas") as ExtCanvas).apply {
            id(id)
            width(actualWidth)
            height(actualHeight)
            style().setProperty("width", "" + cw + "px")
            style().setProperty("height", "" + ch + "px")
            style().setProperty("background-color", "#777777")
        }

        val ctx = canvas.getContext("2d") as ExtContext
        ctx.scale(scale, scale)
        app.appendChild(canvas)
        return Paper(canvas, ctx, cw, ch)
    }

    fun startAnimation(cb: SceneRenderer) {
        animationCallback = AnimationFrameCallback { aElapsedTime ->
            cb.draw(aElapsedTime)
            window.requestAnimationFrame(animationCallback)
        }
        window.requestAnimationFrame(animationCallback)
    }

}