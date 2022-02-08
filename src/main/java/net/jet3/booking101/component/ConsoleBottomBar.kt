package net.jet3.booking101.component

import java.awt.BorderLayout
import java.awt.FlowLayout
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.awt.event.ActionEvent
import javax.swing.JButton
import javax.swing.JPanel

class ConsoleBottomBar : BottomBar() {
    private val clear = JButton("Clear")
    private val copy = JButton("Copy")
    private val upload = JButton("Upload")
    private val kill = JButton("Kill")
    private fun addActionListeners() {
        copy.addActionListener { e: ActionEvent? ->
            val clipboard = Toolkit.getDefaultToolkit().systemClipboard
            val selec = StringSelection("DWA");
            clipboard.setContents(selec, null)
        }
        clear.addActionListener { e: ActionEvent? ->

        }
    }

    init {
        addActionListeners()
        val leftSide = JPanel(FlowLayout(FlowLayout.LEFT, 5, 13))
        leftSide.add(clear)
        leftSide.add(copy)
        leftSide.add(upload)
        leftSide.add(kill)
        kill.isEnabled = false
        this.add(leftSide, BorderLayout.WEST)
    }
}