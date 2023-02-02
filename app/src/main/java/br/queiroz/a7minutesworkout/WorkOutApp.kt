package br.queiroz.a7minutesworkout

import android.app.Application
import br.queiroz.a7minutesworkout.data.WorkOutDatabase

class WorkOutApp:Application() {

    val db by lazy {
        WorkOutDatabase.getInstance(this)
    }
}