package art

class Entry {

    private val dom = Dom()

    init {
        val paper = dom.createCanvasWithID("canvas")
        val stars = StarShapes(0f, 0f, paper.width.toFloat(), paper.height.toFloat())
        dom.startAnimation(SceneRenderer {
            stars.drawFrame(paper.ctx)
        })
    }


    companion object {
        @JvmStatic
        fun main(args: Array<String>?) {
            Entry()
        }

    }
}
