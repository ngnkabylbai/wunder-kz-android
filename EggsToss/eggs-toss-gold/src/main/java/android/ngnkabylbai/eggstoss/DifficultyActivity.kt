package android.ngnkabylbai.eggstoss

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_difficulty.*

/**
 * Created by Nurbek Kabylbay on 23.03.2018.
 */
class DifficultyActivity: AppCompatActivity() {

    private lateinit var gameIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_difficulty)

        gameIntent = Intent(this, MainActivity::class.java)

        medium.setOnClickListener {
            gameIntent.putExtra("difficulty", "medium")
            startGame()
        }

        hard.setOnClickListener {
            gameIntent.putExtra("difficulty", "hard")
            startGame()
        }
    }

    private fun startGame() {
        startActivity(gameIntent)
        finish()
    }
}