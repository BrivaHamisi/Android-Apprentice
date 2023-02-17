package com.autouserinc.timefighter

import android.content.IntentSender.OnFinished
import android.os.Bundle
import android.os.CountDownTimer
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog

private var score = 0
private var gameStarted = false
private lateinit var countDownTimer: CountDownTimer
private val  initialCountDown: Long = 60000
private val countDownInterval:Long = 10000
private var timeLeft = 60

class MainActivity : AppCompatActivity() {
    private lateinit var gameScoreTextView: TextView
    private lateinit var  timeLeftTextView: TextView
    private lateinit var tapMeButton: Button
    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate called: Score is:$score", )

        gameScoreTextView = findViewById(R.id.game_score_text_view)
        timeLeftTextView = findViewById(R.id.time_left_text_view)
        tapMeButton = findViewById(R.id.tap_me_button)

        tapMeButton.setOnClickListener{ view ->
            val bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce)
            view.startAnimation(bounceAnimation)
            incrementScore() }

        //connect views to Logic
        if(savedInstanceState != null){
            score = savedInstanceState.getInt(SCORE_KEY)
            timeLeft = savedInstanceState.getInt(TIME_LEFT_KEY)
            restoreGame()
        }else{
            resetGame()
        }
    }

    private fun restoreGame() {
        val restoredScore = getString(R.string.your_score, score)
        gameScoreTextView.text = restoredScore

        val restordTime = getString(R.string.time_left, timeLeft)
        timeLeftTextView.text = restordTime

        countDownTimer = object : CountDownTimer((timeLeft * 1000).toLong(), countDownInterval){
            override fun onTick(millisUntilFinished: Long){
                timeLeft = millisUntilFinished.toInt()/1000
                val timeleftString= getString(R.string.time_left, timeLeft)
                timeLeftTextView.text = timeleftString
            }

            override fun onFinish() {
                endGame()
            }
        }
        countDownTimer.start()
        gameStarted = true
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)

        outState.putInt(SCORE_KEY, score)
        outState.putInt(TIME_LEFT_KEY, timeLeft)
        countDownTimer.cancel()

        Log.d(TAG, "onSaveInstanceState: Saving score: $score & Time Left: $timeLeft")
        countDownTimer.cancel()

    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d(TAG, "OnDestroy called.")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //If the menu; this adds to the action bar if it is present
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.about_item){
            showInfo()
        }
        return true
    }

    private fun showInfo() {
        val dialogTitle = getString(R.string.about_title,
            BuildConfig.VERSION_NAME)
        val dialogMessage = getString(R.string.about_message)

        val builder = AlertDialog.Builder(this)
        builder.setTitle(dialogTitle)
        builder.setMessage(dialogMessage)
        builder.create().show()
    }


    private fun incrementScore(){
        //Increment the score logic
        if (!gameStarted){
            startGame()
        }

        score++
        val newScore = getString(R.string.your_score, score)
        gameScoreTextView.text = newScore
    }
    private fun resetGame(){
        //reset game logic
        score = 0

        val initialScore = getString(R.string.your_score, score)
        gameScoreTextView.text = initialScore

        val initialTimeLeft = getString(R.string.time_left, 60)
        timeLeftTextView.text = initialTimeLeft

        countDownTimer = object : CountDownTimer(initialCountDown, countDownInterval){
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished.toInt()/1000
                val timeLeftString = getString(R.string.time_left, timeLeft)
                timeLeftTextView.text= timeLeftString

            }

            override fun onFinish() {
                endGame()
            }
        }
        gameStarted = false
    }
    private fun startGame(){
        //start game logic
        countDownTimer.start()
        gameStarted = true
    }
    private fun endGame(){
        //end game logic
        Toast.makeText(this, getString(R.string.game_over_message, score), Toast.LENGTH_LONG).show()
        resetGame()
    }
    companion object {
        private const val SCORE_KEY = "SCORE_KEY"
        private const val TIME_LEFT_KEY = "TIME"
    }
}