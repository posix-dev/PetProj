package me.uptop.prox.ui.routing

import androidx.fragment.app.Fragment
import me.uptop.prox.ui.OneFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screen {

    class OneScreen : SupportAppScreenWithTag() {
        override fun getFragment(): Fragment = OneFragment()
        override fun getTag(): String = OneFragment::class.java.name
    }

    abstract class SupportAppScreenWithTag : SupportAppScreen() {
        abstract fun getTag(): String?
    }
}