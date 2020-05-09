package ext

import de.mirkosertic.bytecoder.api.OpaqueProperty
import de.mirkosertic.bytecoder.api.web.CanvasRenderingContext2D


interface ExtContext : CanvasRenderingContext2D {


    @OpaqueProperty
    fun setFont(f: String)

    fun fill()

    fun strokeRect(x: Float, y: Float, w: Float, h: Float)

    fun fillText(text: String, x: Float, y: Float)

    fun strokeText(text: String, x: Float, y: Float)

}