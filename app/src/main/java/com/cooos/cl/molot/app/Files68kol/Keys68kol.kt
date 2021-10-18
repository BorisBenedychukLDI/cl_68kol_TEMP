package com.cooos.cl.molot.app

import android.util.Base64

const val CODED_BINOM_KEY_68kol = "aHR0cHM6Ly9ndWlkZXMuY29kZXBhdGguY29tL2FuZHJvaWQvYW5pbWF0aW9ucw=="
const val CODED_ONESIGNAL_KEY_68kol = ""
const val SP_KEY_68kol = "SP_68kol"
const val SP_LAST_PAGE_KEY_68kol = "Last_Page_68kol"
const val SP_BINOM_PAGE_KEY_68kol = "webURL_68kol"
const val INTERNET_KEY_68kol = "activity_68kol"

fun String.decodebase6468kol () = String(Base64.decode(this, Base64.DEFAULT))