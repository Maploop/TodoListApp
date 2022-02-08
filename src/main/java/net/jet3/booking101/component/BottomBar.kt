package net.jet3.booking101.component

import javax.swing.BorderFactory
import javax.swing.BoxLayout
import javax.swing.JPanel

abstract class BottomBar : JPanel() {
    init {
        layout = BoxLayout(this, BoxLayout.X_AXIS)
        border = BorderFactory.createEmptyBorder(0, 0, 0, 0)
    }
}