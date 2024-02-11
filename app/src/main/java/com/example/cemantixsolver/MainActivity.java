package com.example.cemantixsolver;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private TextView scoreTextView;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreTextView = findViewById(R.id.scoreTextView);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setIndeterminate(true);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            CemantixSolver cemantixSolver = new CemantixSolver();
            String result = cemantixSolver.solve();
            handler.post(() -> {
                scoreTextView.setText(result);
                progressBar.setVisibility(View.INVISIBLE);
            });
        });

    }

}
