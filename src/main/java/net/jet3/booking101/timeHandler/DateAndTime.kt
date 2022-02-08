package net.jet3.booking101.timeHandler

import net.jet3.booking101.initalization.ApplicationInitalizer
import net.jet3.booking101.util.Util

enum class DateAndTime(var index: Int, var displayName: String) {
    JANUARY(1, Util.get(ApplicationInitalizer.lang, "january").toString()),
    FEBRUARY(2, Util.get(ApplicationInitalizer.lang, "february").toString()),
    MARCH(3, Util.get(ApplicationInitalizer.lang, "march").toString()),
    APRIL(4, Util.get(ApplicationInitalizer.lang, "april").toString()),
    MAY(5, Util.get(ApplicationInitalizer.lang, "may").toString()),
    JUNE(6, Util.get(ApplicationInitalizer.lang, "june").toString()),
    JULY(7, Util.get(ApplicationInitalizer.lang, "july").toString()),
    AUGUST(8, Util.get(ApplicationInitalizer.lang, "august").toString()),
    SEPTEMBER(9, Util.get(ApplicationInitalizer.lang, "september").toString()),
    OCTOBER(10, Util.get(ApplicationInitalizer.lang, "october").toString()),
    NOVEMBER(11, Util.get(ApplicationInitalizer.lang, "november").toString()),
    DECEMBER(12, Util.get(ApplicationInitalizer.lang, "december").toString());

    @JvmName("getDisplayName1")
    fun getDisplayName(): String {
        return displayName
    }

    @JvmName("getIndex1")
    fun getIndex(): Int {
        return index
    }

    fun getNext(): DateAndTime {
        return values()[(index + 1) % values().size]
    }

    fun getPrevious(): DateAndTime {
        return values()[(index - 1) % values().size]
    }

    fun getMonth(index: Int): DateAndTime? {
        for (month in values()) {
            if (month.index == index) {
                return month
            }
        }
        return null
    }
}