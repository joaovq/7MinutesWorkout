package br.queiroz.a7minutesworkout.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.queiroz.a7minutesworkout.R
import br.queiroz.a7minutesworkout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_7MinutesWorkout)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

//        val flStartButton :FrameLayout= findViewById(R.id.flStart)
        binding?.flStart.let {
            it?.setOnClickListener {
                val intent = Intent(this, ExerciseActivity::class.java)
                startActivity(intent)
            }
        }
        binding?.flBmi?.apply {
            setOnClickListener {
                val intent = Intent(
                    this@MainActivity,
                    BMIActivity::class.java,
                )
                startActivity(intent)
            }
        }

        binding?.flHistory.let {
            it?.setOnClickListener {
                val intent = Intent(
                    this@MainActivity,
                    HistoryActivity::class.java,
                )
                startActivity(intent)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        Avoid memory leaks
        /*

        memory leak é um fenômeno que ocorre quando um programa
        de computador gerencia incorretamente
        alocações de memória de maneira que certa
        memória não é liberada quando não é mais necessária.

         */
        binding = null
    }
}
