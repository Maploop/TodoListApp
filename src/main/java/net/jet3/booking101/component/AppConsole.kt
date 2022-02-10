package net.jet3.booking101.component

class AppConsole {
    var text: String

    init {
        text = ""
    }

    fun write(str: String) {
        val builder = StringBuilder(text)
        builder.append(str).append("\n")
        text = builder.toString()
    }

    fun clear() {
        text = ""
    }

    fun copy() {}
}