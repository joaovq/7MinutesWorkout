package br.queiroz.a7minutesworkout.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.queiroz.a7minutesworkout.WorkOutApp
import br.queiroz.a7minutesworkout.data.HistoryDao
import br.queiroz.a7minutesworkout.data.HistoryEntity
import br.queiroz.a7minutesworkout.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {

    private var binding: ActivityFinishBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarFinishActivity)

        if (supportActionBar != null) {
//            set display back home arrow
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarFinishActivity?.setNavigationOnClickListener {
//            onBackPressed() is deprecated
//            Same action of click in button back android
            onBackPressedDispatcher.onBackPressed()
        }
        binding?.btnFinish?.setOnClickListener {
            finish()
        }

        val historyDao = (application as WorkOutApp).db.historyDao()

        addDateToDatabase(historyDao)
    }

    private fun addDateToDatabase(historyDao: HistoryDao) {
        val calendar = Calendar.getInstance()
        val dateTime = calendar.time
        Log.e("Date: ", "" + dateTime)

        val simpleDateFormat = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val date = simpleDateFormat.format(dateTime)

        Log.e("Formatted Date: ", "" + date)

        lifecycleScope.launch {
            historyDao.insertHistory(HistoryEntity(date))
            Log.i("add - ", "history - $date")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        avoid memory leaks
        if (binding != null) {
            binding = null
        }
    }
}
