package br.queiroz.a7minutesworkout.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.queiroz.a7minutesworkout.R
import br.queiroz.a7minutesworkout.databinding.ActivityFinishBinding

class FinishActivity : AppCompatActivity() {

    private var binding:ActivityFinishBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarFinishActivity)

        if (supportActionBar!=null){
//            set display back home arrow
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarFinishActivity?.setNavigationOnClickListener {
//            onBackPressed() is deprecated
//            Same action of click in button back android
            onBackPressedDispatcher.onBackPressed()
        }
        binding?.btnFinish?.setOnClickListener{
            finish()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
//        avoid memory leaks
        if (binding != null){
            binding = null
        }
    }
}