package net.jet3.booking101.ui.dev

import net.jet3.booking101.Toast
import net.jet3.booking101.component.Console
import net.jet3.booking101.component.ConsoleBottomBar
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.event.*
import javax.swing.JFrame
import javax.swing.JMenuItem
import javax.swing.JPopupMenu
import javax.swing.JScrollPane

class ApplicationConsole : JFrame("AppConsole") {
    var console: Console
    private val bottomBar: ConsoleBottomBar
    private var contextmenu: JPopupMenu? = null
    private var copy: JMenuItem? = null
    fun setupContextMenu() {
        contextmenu = JPopupMenu()
        copy = JMenuItem("Copy")
        copy!!.addActionListener {
            console.copy()
            Toast.success("Copied console to clipboard")
        }
        contextmenu!!.add(copy)
        console.addMouseListener(object : MouseAdapter() {
            override fun mousePressed(e: MouseEvent) {
                if (e.button == MouseEvent.BUTTON3) {
                    contextmenu!!.show(console, e.x, e.y)
                }
            }
        })
    }

    val log: String
        get() = console.getText()

    fun clearConsole() {
        console.setText("")
    }

    init {
        setSize(650, 400)
        minimumSize = Dimension(650, 400)
        defaultCloseOperation = HIDE_ON_CLOSE
        layout = BorderLayout()
        console = Console()
        bottomBar = ConsoleBottomBar()
        val scroll = JScrollPane(console, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER)
        add(scroll, BorderLayout.CENTER)
        add(bottomBar, BorderLayout.SOUTH)
        setupContextMenu()
        addComponentListener(object : ComponentAdapter() {
            override fun componentResized(e: ComponentEvent) {
                val c = e.component
                val width = c.width
                val height = c.height
                console.setSize(width, height - bottomBar.getHeight())
                bottomBar.setSize(width, bottomBar.getHeight())
            }

            override fun componentMoved(e: ComponentEvent) {
                val c = e.component
                val x = c.x
                val y = c.y
                console.setLocation(0, 0)
                bottomBar.setLocation(0, console.getHeight())
            }
        })
    }

    override fun setVisible(b: Boolean) {
        super.setVisible(b)
        if (b) {
            console.requestFocus()
            console.update(console.graphics)
        }
    }
}