package com.example.coinflip;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private TextView win;
    private TextView vesztes;
    private boolean isHeads = true;
    private ImageView coinImage;
    private Button fejGomb;
    private Button irasGomb;
    private TextView dobas;
    private int DobasCount = 0;
    private int gyozelemCount = 0;
    private int vesztesCount = 0;
    private final int maxDobások = 5;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coinImage = findViewById(R.id.coinImageView);
        fejGomb = findViewById(R.id.fejID);
        irasGomb = findViewById(R.id.irasID);
        dobas = findViewById(R.id.dobasokID);
        win = findViewById(R.id.gyozelemID);
        vesztes = findViewById(R.id.veresegID);

        fejGomb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playGame("Fej");
            }
        });
        irasGomb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playGame("írsás");
            }
        });
    }
    private void endGameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (gyozelemCount > vesztesCount) {
            builder.setTitle("Győzelem");
            builder.setMessage("Gratulálok, nyertél!");
        } else {
            builder.setTitle("Vereség");
            builder.setMessage("Szeretnél új játékot játszani?");
        }
        builder.setPositiveButton("IGEN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resetGame();
            }
        });
        builder.setNegativeButton("NEM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.show();
    }
    private void playGame(final String choice) {
        Random random = new Random();
        int eredmeny = random.nextInt(2);
        if (eredmeny == 0) {
            coinImage.setImageResource(isHeads ? R.drawable.tails : R.drawable.heads);
            isHeads = !isHeads;
        } else {
            coinImage.setImageResource(isHeads ? R.drawable.heads : R.drawable.tails);
            isHeads = !isHeads;
        }
        DobasCount++;
        dobas.setText("Dobások: " + DobasCount);

        if ((eredmeny == 0 && choice.equals("head")) || (eredmeny == 1 && choice.equals("tails"))) {
            gyozelemCount++;
            win.setText("Győzelem: " + gyozelemCount);
        } else {
            vesztesCount++;
            vesztes.setText("Vereség: " + vesztesCount);
        }
        String toastMessage = (eredmeny == 0) ? "Eredmény: Fej" : "Eredmény: Írás";
        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
        if (DobasCount >= maxDobások) {
            endGameDialog();
        }
    }
    private void resetGame() {
        dobas.setText("Dobások: 0");
        win.setText("Győzelem: 0");
        vesztes.setText("Vereség: 0");
        DobasCount = 0;
        gyozelemCount = 0;
        vesztesCount = 0;
        isHeads = true;
        coinImage.setImageResource(R.drawable.heads);
    }
}