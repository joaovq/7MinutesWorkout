package br.queiroz.a7minutesworkout.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import br.queiroz.a7minutesworkout.WorkOutApp
import br.queiroz.a7minutesworkout.adapter.HistoryAdapter
import br.queiroz.a7minutesworkout.data.HistoryDao
import br.queiroz.a7minutesworkout.data.HistoryEntity
import br.queiroz.a7minutesworkout.databinding.ActivityHistoryBinding
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {
    private var binding: ActivityHistoryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHistoryBinding.inflate(layoutInflater)

        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarHistory)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        val historyDao = (application as WorkOutApp).db.historyDao()

        binding?.toolbarHistory?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        getAllCompletedDates(historyDao)
    }

    private fun getAllCompletedDates(historyDao: HistoryDao) {
        lifecycleScope.launch {
            historyDao.fetchAllHistories().collect { history ->
                if (history.isNotEmpty()) {
                    val historyEntities = ArrayList(history)
                    setUpRecyclerViewHistories(historyEntities)
                } else {
                    binding?.tvNoDataAvailable?.visibility = View.VISIBLE
                    binding?.tvHistory?.visibility = View.INVISIBLE
                    binding?.rvHistory?.visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun setUpRecyclerViewHistories(histories: ArrayList<HistoryEntity>) {
        binding?.tvNoDataAvailable?.visibility = View.INVISIBLE
        binding?.tvHistory?.visibility = View.VISIBLE

        binding?.rvHistory?.apply {
            visibility = View.VISIBLE
            val snapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(this)
            layoutManager = LinearLayoutManager(this@HistoryActivity)
            setHasFixedSize(false)
            adapter = HistoryAdapter(histories)
        }
    }
}
