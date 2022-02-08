package net.jet3.booking101.component

import java.awt.Color
import javax.swing.JTextPane
import javax.swing.text.*

class Console : JTextPane() {
    private val attrs = SimpleAttributeSet()
    fun setColor(c: Color?): Console {
        StyleConstants.setForeground(attrs, c)
        return this
    }

    fun setBold(b: Boolean): Console {
        StyleConstants.setBold(attrs, b)
        return this
    }

    override fun getScrollableTracksViewportWidth(): Boolean {
        return true
    }

    fun write(str: String) {
        try {
            this.document.insertString(
                this.document.length, """
     $str
     
     """.trimIndent(), null
            )
            this.caretPosition = this.document.length
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    // https://stackoverflow.com/a/13375811
    internal inner class WrapEditorKit : StyledEditorKit() {
        var defaultFactory: ViewFactory = WrapColumnFactory()
        override fun getViewFactory(): ViewFactory {
            return defaultFactory
        }
    }

    internal inner class WrapColumnFactory : ViewFactory {
        override fun create(elem: Element): View {
            val kind = elem.name
            if (kind != null) {
                when (kind) {
                    AbstractDocument.ContentElementName -> return WrapLabelView(elem)
                    AbstractDocument.ParagraphElementName -> return ParagraphView(elem)
                    AbstractDocument.SectionElementName -> return BoxView(elem, View.Y_AXIS)
                    StyleConstants.ComponentElementName -> return ComponentView(elem)
                    StyleConstants.IconElementName -> return IconView(elem)
                }
            }

            // default to text display
            return LabelView(elem)
        }
    }

    internal inner class WrapLabelView(elem: Element?) : LabelView(elem) {
        override fun getMinimumSpan(axis: Int): Float {
            return when (axis) {
                X_AXIS -> 0f
                Y_AXIS -> super.getMinimumSpan(axis)
                else -> throw IllegalArgumentException("Invalid axis: $axis")
            }
        }
    }

    init {
        isEditable = false
        this.editorKit = WrapEditorKit()
    }
}