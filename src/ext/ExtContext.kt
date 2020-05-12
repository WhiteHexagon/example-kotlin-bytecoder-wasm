package ext

import de.mirkosertic.bytecoder.api.OpaqueProperty
import de.mirkosertic.bytecoder.api.OpaqueReferenceType


interface ExtContext : OpaqueReferenceType {
    @OpaqueProperty
    fun setFillStyle(s: String)

    @OpaqueProperty
    fun setStrokeStyle(s: String)

    @OpaqueProperty
    fun setLineWidth(v: Float)

    @OpaqueProperty
    fun setFont(f: String)

    fun fillText(text: String, x: Float, y: Float)

    fun fillRect(x: Float, v: Float, w: Float, h: Float)
    fun strokeRect(x: Float, y: Float, w: Float, h: Float)

    fun save()
    fun restore()

    fun scale(v: Float, v1: Float)

    fun beginPath()
    fun closePath()

    fun moveTo(x: Float, y: Float)

    fun lineTo(x: Float, y: Float)

    fun arc(x: Float, y: Float, v2: Float, v3: Float, v4: Float, b: Boolean)

    fun stroke()

    fun fill()
}