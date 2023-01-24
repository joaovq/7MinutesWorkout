package br.queiroz.a7minutesworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.queiroz.a7minutesworkout.activities.ExerciseActivity
import br.queiroz.a7minutesworkout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding:ActivityMainBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

//        val flStartButton :FrameLayout= findViewById(R.id.flStart)
        binding?.flStart.let {
            it?.setOnClickListener{
                val intent = Intent(this, ExerciseActivity::class.java)
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