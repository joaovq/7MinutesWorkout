package br.queiroz.a7minutesworkout.activities

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import br.queiroz.a7minutesworkout.Constants
import br.queiroz.a7minutesworkout.R
import br.queiroz.a7minutesworkout.adapter.ExerciseStatusAdapter
import br.queiroz.a7minutesworkout.databinding.ActivityExerciseBinding
import br.queiroz.a7minutesworkout.databinding.DialogCustomBackConfirmationBinding
import br.queiroz.a7minutesworkout.model.ExerciseModel
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var binding:ActivityExerciseBinding? = null
    private var restTimer:CountDownTimer? = null
    private var restProgress = 0
    private var exerciseTimer:CountDownTimer? = null
    private var exerciseProgress = 0
    private var exerciseList:ArrayList<ExerciseModel>? = null
    private var currentExerciseposition = -1
    private var restTimerDuration = 10L
    private var execiseTimerDuration = 30L
    private var tts: TextToSpeech? = null
    private var player: MediaPlayer? = null

    private var exerciseAdapter: ExerciseStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExercise)

        if (supportActionBar!=null){
//            set display back home arrow
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        exerciseList = Constants.defaultExerciseList()

//        Initialization TextToSpeech
        tts = TextToSpeech(this, this)


        binding?.toolbarExercise?.setNavigationOnClickListener {
            showCustomBackConfirmationDialog()
        }

        setupRestView()
        setupExerciseStatusRecyclerView()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        showCustomBackConfirmationDialog()
//        super.onBackPressed()
    }

    private fun showCustomBackConfirmationDialog(){
        val customDialogBack = Dialog(this)

        val bindingDialog = DialogCustomBackConfirmationBinding
            .inflate(layoutInflater)
        customDialogBack.setContentView(bindingDialog.root)
        customDialogBack.setCanceledOnTouchOutside(false)
        bindingDialog.btnYes.setOnClickListener {
                this@ExerciseActivity.finish()
                customDialogBack.dismiss()
        }
        bindingDialog.btnNo.setOnClickListener{
            customDialogBack.dismiss()
        }

        customDialogBack.setCancelable(true)
        customDialogBack.show()
    }

    private fun setupExerciseStatusRecyclerView(){
//        SetUp RecyclerView for show total exercises in list
//        Configure LayoutManager
        binding?.rvExerciseStatus?.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL, false
        )
//        Set Has fixeSize for performance
        binding?.rvExerciseStatus?.setHasFixedSize(true)
//        Instance of adapter
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
//      create adapter
        binding?.rvExerciseStatus?.adapter = exerciseAdapter
    }

    private fun setupRestView(){

        try {
//            Add a sound when start exercise
//            Link for sound in resources
            val soundURI = Uri
                .parse("android.resource://br.queiroz.a7minutesworkout/" +
                        R.raw.press_start
                )
            player = MediaPlayer.create(applicationContext, soundURI)
            player?.isLooping = false
            player?.start()
        }catch (e:Exception){
            e.printStackTrace()
        }


        if (binding?.flExerciseView?.isVisible == true){
//            if Exercise View is visible, do it
            binding?.flExerciseView?.visibility = View.GONE
            binding?.flRestView?.visibility = View.VISIBLE
            binding?.tvTitle?.visibility = View.VISIBLE
            binding?.tvExerciseName?.visibility = View.INVISIBLE
            binding?.ivExercise?.visibility = View.INVISIBLE
        }
//        Show in RestView for upcoming exercise
        binding?.tvUpcomingLabel?.visibility = View.VISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.VISIBLE
        if (restTimer!=null){
            //            cancel CountDownTimer and reset progress
            restTimer?.cancel()
            restProgress = 0
        }

        binding?.tvUpcomingExerciseName?.text =
            exerciseList!![currentExerciseposition+1]
                .getName()
        setRestProgressBar()
    }
    private fun setRestProgressBar(){
        binding?.progressBar?.progress = restProgress
        binding?.tvTimer?.text = 10.toString()

        restTimer = object : CountDownTimer(restTimerDuration*1000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBar?.progress = 10-restProgress
                binding?.tvTimer?.text = (10-restProgress).toString()

                when{
                    (10-restProgress)==0->speakOut("Let's go")
                    (10-restProgress)<=3->{
                        speakOut((10-restProgress).toString())
                    }
                }
            }

            override fun onFinish() {
                currentExerciseposition++
                exerciseList!![currentExerciseposition].setIsSelected(true)
                exerciseAdapter!!.notifyItemChanged(currentExerciseposition)

                setupExerciseView()
            }

        }.start()

    }

    private fun setupExerciseView(){
        binding?.flRestView?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.tvExerciseName?.visibility = View.VISIBLE
        binding?.ivExercise?.visibility = View.VISIBLE
        binding?.flExerciseView?.visibility = View.VISIBLE
        binding?.tvUpcomingLabel?.visibility = View.INVISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.INVISIBLE

        if (exerciseTimer!=null){
//            cancel CountDownTimer and reset progress
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        speakOut(exerciseList!![currentExerciseposition].getName())

        binding?.ivExercise?.setImageResource(
            exerciseList!![currentExerciseposition].getImage()
        )
        binding?.tvExerciseName?.text = exerciseList!![currentExerciseposition]
            .getName()

        setExerciseProgressBar()
    }
    private fun setExerciseProgressBar(){
        binding?.progressBarExercise?.progress = exerciseProgress
        binding?.tvTimerExercise?.text = 30.toString()

        exerciseTimer = object : CountDownTimer(execiseTimerDuration*1000, 1000){
            override fun onTick(millisUntilFinished: Long) {
//                CountDown is here
                player = MediaPlayer.create(this@ExerciseActivity, Uri.parse("android.resource://br.queiroz.a7minutesworkout/" +
                        R.raw.clock_exercise))
                exerciseProgress++
                binding?.progressBarExercise?.progress = 30-exerciseProgress
                binding?.tvTimerExercise?.text = (30-exerciseProgress).toString()
                when{
                    (30-exerciseProgress)==0->speakOut("Take a rest")
                    (30-exerciseProgress)<=3->{
                        speakOut((30-exerciseProgress).toString())
                    }
                }
            }

            override fun onFinish() {
//                When finish method onTick
                if (currentExerciseposition < exerciseList?.size!!-1){
                    exerciseList!![currentExerciseposition].setIsSelected(false)
                    exerciseList!![currentExerciseposition].setIsCompleted(true)
                    exerciseAdapter!!.notifyItemChanged(currentExerciseposition)
                    setupRestView()
                    Toast.makeText(this@ExerciseActivity,
                        "You got it, keep going",
                        Toast.LENGTH_SHORT).show()
                }else{
                    val intentFinish = Intent(
                        this@ExerciseActivity,
                        FinishActivity::class.java
                    )
                    startActivity(intentFinish)
                    finish()
                    Toast.makeText(this@ExerciseActivity,
                        "Congratulations, You have completed the 7 minutes workout.",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (restTimer!=null){
            //            cancel CountDownTimer and reset progress
            restTimer?.cancel()
            restProgress = 0
        }
        if (exerciseTimer!=null){
            //            cancel CountDownTimer and reset progress
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
        if (tts !=null){
            tts?.stop()
            tts?.shutdown()
        }
        if (player !=null){
            player?.stop()
        }

        binding = null
    }

//    Init obrigatory for implement TextToSpeech
    override fun onInit(status: Int) {
        if (status==TextToSpeech.SUCCESS){
            val result = tts?.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA
                || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS", "The Language specified is not supported")
            }
        }
        else{
            Log.e("TTS", "Initialization Failed!")
        }
    }
//    Speak the text. Operational System speak the text
    private fun speakOut(text:String){
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }
}