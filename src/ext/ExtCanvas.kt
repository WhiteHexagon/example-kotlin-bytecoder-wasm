package ext

import de.mirkosertic.bytecoder.api.OpaqueProperty
import de.mirkosertic.bytecoder.api.web.HTMLCanvasElement

interface ExtCanvas : HTMLCanvasElement {
    @OpaqueProperty
    fun id(id: String)

    @OpaqueProperty
    fun width(width: Int)

    @OpaqueProperty
    fun height(height: Int)

}