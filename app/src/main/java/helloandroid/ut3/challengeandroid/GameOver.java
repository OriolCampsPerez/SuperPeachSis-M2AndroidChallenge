package helloandroid.ut3.challengeandroid;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import helloandroid.ut3.challengeandroid.activities.MainActivity;

public class GameOver extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        // Get score by key "score" from intent
        int score = getIntent().getIntExtra("score", 0);
        // Set score to text view
        TextView scoreTextView = findViewById(R.id.scoreTextView);
        scoreTextView.setText("Score: " + score);

        Button restartButton = findViewById(R.id.restartGameButton);
        restartButton.setOnClickListener(v -> {
            Intent intent = new Intent(GameOver.this, MainActivity.class);
            startActivity(intent);
        });
    }
}
