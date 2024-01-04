package pt.iade.guilhermeabrantes.blackjack;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import pt.iade.guilhermeabrantes.blackjack.models.Card;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.List;

public class BlackJack extends AppCompatActivity {
    @SuppressLint({"MissingInflatedId", "SetTextI18n"})

    private SeekBar creditsBarBJ;
    private TextView creditsResultBJ, totalCreditsBJ;
    private int playerBetBJ, maxBetBJ;


    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black_jack);

        creditsBarBJ = (SeekBar) findViewById(R.id.seekBarCreditsBJ);
        creditsResultBJ = (TextView) findViewById(R.id.playerBetBJ);
        totalCreditsBJ = (TextView) findViewById(R.id.totalCreditsBJ);
        maxBetBJ = creditsBarBJ.getMax();

        List<Card> pHand = new ArrayList<>();
        List<Card> dHand = new ArrayList<>();

        Button start = findViewById(R.id.btnStartBJ);
        Button stand = findViewById(R.id.btnStand);
        Button hit = findViewById(R.id.btnHit);
        Button ok = findViewById(R.id.btnOk);
        Button leave = findViewById(R.id.btnExit);
        Button hit2 = findViewById(R.id.btnHit2);
        Button hit3 = findViewById(R.id.btnHitAgain);
        Button split = findViewById(R.id.btnSplit);

        TextView playerPoints = findViewById(R.id.playerPoints);
        TextView dealerPoints = findViewById(R.id.dealerPoints);

        ImageView card1 = findViewById(R.id.playerCard);
        ImageView card2 = findViewById(R.id.playerCard2);
        ImageView card3 = findViewById(R.id.playerCard3);
        ImageView card4 = findViewById(R.id.playerCard4);
        ImageView card5 = findViewById(R.id.playerCard5);

        ImageView dCard1 = findViewById(R.id.dealerCard);
        ImageView dCard2 = findViewById(R.id.dealerCard2);
        ImageView dCard3 = findViewById(R.id.dealerCard3);
        ImageView dCard4 = findViewById(R.id.dealerCard4);
        ImageView dCard5 = findViewById(R.id.dealerCard5);

        start.setVisibility(View.VISIBLE);
        stand.setVisibility(View.GONE);
        hit.setVisibility(View.GONE);
        hit2.setVisibility(View.GONE);
        hit3.setVisibility(View.GONE);
        ok.setVisibility(View.GONE);
        card1.setVisibility(View.GONE);
        card2.setVisibility(View.GONE);
        card3.setVisibility(View.GONE);
        card4.setVisibility(View.GONE);
        card5.setVisibility(View.GONE);
        dCard1.setVisibility(View.GONE);
        dCard2.setVisibility(View.GONE);
        dCard3.setVisibility(View.GONE);
        dCard4.setVisibility(View.GONE);
        dCard5.setVisibility(View.GONE);
        split.setVisibility(View.GONE);

        creditsBarBJ.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int credits, boolean b) {
                creditsResultBJ.setText("Apostar: " + credits);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                creditsResultBJ.setText("Apostar: " + seekBar.getProgress());
                playerBetBJ = seekBar.getProgress();
                seekBar.setMax(maxBetBJ);
            }
        });

        leave.setOnClickListener(v -> {
            startActivity(new Intent(BlackJack.this, FrontPage.class));
        });


        start.setOnClickListener(v -> {
            if (playerBetBJ == 0) {
                Toast.makeText(BlackJack.this, "Invalid Bet!", Toast.LENGTH_SHORT).show();
                return;
            } else if (playerBetBJ > maxBetBJ) {
                Toast.makeText(BlackJack.this, "Invalid Bet!", Toast.LENGTH_SHORT).show();
                return;
            }
            split.setVisibility(View.VISIBLE);
            stand.setVisibility(View.VISIBLE);
            hit.setVisibility(View.VISIBLE);
            hit2.setVisibility(View.GONE);
            hit3.setVisibility(View.GONE);
            start.setVisibility(View.GONE);
            leave.setVisibility(View.GONE);
            creditsResultBJ.setVisibility(View.GONE);
            creditsBarBJ.setVisibility(View.GONE);

            maxBetBJ -= playerBetBJ;
            totalCreditsBJ.setText("Créditos: " + String.valueOf(maxBetBJ));

            Card first = new Card();
            Card second = new Card();

            pHand.add(first);
            pHand.add(second);

            card1.setImageResource(getResources().getIdentifier(first.getName(), "drawable", getPackageName()));
            card2.setImageResource(getResources().getIdentifier(second.getName(), "drawable", getPackageName()));

            card1.setVisibility(View.VISIBLE);
            card2.setVisibility(View.VISIBLE);

            Card dFirst = new Card();
            Card dSecond = new Card();

            dHand.add(dFirst);
            dHand.add(dSecond);

            dCard1.setImageResource(getResources().getIdentifier(dFirst.getName(), "drawable", getPackageName()));
            dCard2.setImageResource(getResources().getIdentifier("cardback", "drawable", getPackageName()));

            dCard1.setVisibility(View.VISIBLE);
            dCard2.setVisibility(View.VISIBLE);
        });

        stand.setOnClickListener(v -> {
            stand.setVisibility(View.GONE);
            hit.setVisibility(View.GONE);
            hit2.setVisibility(View.GONE);
            hit3.setVisibility(View.GONE);
            split.setVisibility(View.GONE);

            boolean canHit = true;
            int dSum = 0;
            int dAces = 0;

            dCard2.setImageResource(getResources().getIdentifier(dHand.get(1).getName(), "drawable", getPackageName()));
            for (int i = 0; i < 2; i++) {
                dSum += dHand.get(i).getRank();
                if (dHand.get(i).getRank() == 11) {
                    dAces++;
                }
            }
            while (canHit && (dSum < 17 || (dSum == 17 && dAces > 0))) {
                Card toAdd = new Card();
                dHand.add(toAdd);

                int cardIndex = dHand.size() - 1;
                int resourceId = getResources().getIdentifier(dHand.get(cardIndex).getName(), "drawable", getPackageName());
                switch (cardIndex) {
                    case 2:
                        dCard3.setImageResource(resourceId);
                        dCard3.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        dCard4.setImageResource(resourceId);
                        dCard4.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        dCard5.setImageResource(resourceId);
                        dCard5.setVisibility(View.VISIBLE);
                        break;
                }

                dSum += toAdd.getRank();

                if (toAdd.getRank() == 11) {
                    dAces++;
                }
                if (dSum > 21 && dAces > 0) {
                    dSum -= dAces * 10;
                }
            }
            int pSum = 0;
            int pAces = 0;
            for (int i = 0; i < pHand.size(); i++) {
                pSum += pHand.get(i).getRank();
                if (pHand.get(i).getRank() == 11) {
                    pAces++;
                }
            }
            if (pSum > 21 && pAces > 0) {
                pSum -= pAces * 10;
            }
            playerPoints.setText("Player: " + pSum);
            playerPoints.setVisibility(View.VISIBLE);


            dealerPoints.setText("Dealer: " + dSum);
            dealerPoints.setVisibility(View.VISIBLE);

            if (dSum > 21) {
                Toast.makeText(BlackJack.this, "Dealer busted. You win!", Toast.LENGTH_SHORT).show();
                totalCreditsBJ.setText("Créditos: " + String.valueOf(maxBetBJ + (2 * playerBetBJ)));
                maxBetBJ += 2 * playerBetBJ;
            } else if (pSum > dSum) {
                Toast.makeText(BlackJack.this, "You win!", Toast.LENGTH_SHORT).show();
                totalCreditsBJ.setText("Créditos: " + String.valueOf(maxBetBJ + (2 * playerBetBJ)));
                maxBetBJ += 2 * playerBetBJ;
            } else if (pSum == dSum) {
                Toast.makeText(BlackJack.this, "Dead end!", Toast.LENGTH_SHORT).show();
                totalCreditsBJ.setText("Créditos: " + String.valueOf(maxBetBJ + playerBetBJ));
                maxBetBJ += playerBetBJ;
            } else {
                Toast.makeText(BlackJack.this, "Dealer Wins!", Toast.LENGTH_SHORT).show();
            }
            ok.setVisibility(View.VISIBLE);
        });

        hit.setOnClickListener(v -> {
            Card added = new Card();
            pHand.add(added);

            card3.setImageResource(getResources().getIdentifier(pHand.get(pHand.size() - 1).getName(), "drawable", getPackageName()));
            card2.setImageResource(getResources().getIdentifier(pHand.get(pHand.size() - 2).getName(), "drawable", getPackageName()));
            card1.setImageResource(getResources().getIdentifier(pHand.get(pHand.size() - 3).getName(), "drawable", getPackageName()));

            card3.setVisibility(View.VISIBLE);
            hit.setVisibility(View.GONE);
            hit2.setVisibility(View.VISIBLE);
            hit3.setVisibility(View.GONE);
            split.setVisibility(View.GONE);

            int sum = 0;
            int aceCtr = 0;
            for (int i = 0; i < pHand.size(); i++) {
                sum += pHand.get(i).getRank();
                if (pHand.get(i).getRank() == 11) {
                    aceCtr++;
                }
            }
            if (sum > 21 && aceCtr > 0) {
                sum -= aceCtr * 10;
            }

            if (sum > 21) {

                Toast.makeText(BlackJack.this, "Bad luck. Dealer Wins!", Toast.LENGTH_SHORT).show();

                ok.setVisibility(View.VISIBLE);
                stand.setVisibility(View.GONE);
                hit.setVisibility(View.GONE);
                hit2.setVisibility(View.GONE);
                hit3.setVisibility(View.GONE);
                split.setVisibility(View.GONE);
            }
        });

        hit2.setOnClickListener(v -> {
            Card added = new Card();
            pHand.add(added);

            card4.setImageResource(getResources().getIdentifier(pHand.get(pHand.size() - 1).getName(), "drawable", getPackageName()));
            card3.setImageResource(getResources().getIdentifier(pHand.get(pHand.size() - 2).getName(), "drawable", getPackageName()));
            card2.setImageResource(getResources().getIdentifier(pHand.get(pHand.size() - 3).getName(), "drawable", getPackageName()));
            card1.setImageResource(getResources().getIdentifier(pHand.get(pHand.size() - 4).getName(), "drawable", getPackageName()));

            card4.setVisibility(View.VISIBLE);
            hit.setVisibility(View.GONE);
            hit2.setVisibility(View.GONE);
            hit3.setVisibility(View.VISIBLE);

            int sum = 0;
            int aceCtr = 0;
            for (int i = 0; i < pHand.size(); i++) {
                sum += pHand.get(i).getRank();
                if (pHand.get(i).getRank() == 11) {
                    aceCtr++;
                }
            }
            if (sum > 21 && aceCtr > 0) {
                sum -= aceCtr * 10;
            }

            if (sum > 21) {

                Toast.makeText(BlackJack.this, "Bad luck. Dealer Wins!", Toast.LENGTH_SHORT).show();

                ok.setVisibility(View.VISIBLE);
                stand.setVisibility(View.GONE);
                hit.setVisibility(View.GONE);
                hit2.setVisibility(View.GONE);
                hit3.setVisibility(View.GONE);
                split.setVisibility(View.GONE);
            }
        });

        hit3.setOnClickListener(v -> {
            Card added = new Card();
            pHand.add(added);

            card5.setImageResource(getResources().getIdentifier(pHand.get(pHand.size() - 1).getName(), "drawable", getPackageName()));
            card4.setImageResource(getResources().getIdentifier(pHand.get(pHand.size() - 2).getName(), "drawable", getPackageName()));
            card3.setImageResource(getResources().getIdentifier(pHand.get(pHand.size() - 3).getName(), "drawable", getPackageName()));
            card2.setImageResource(getResources().getIdentifier(pHand.get(pHand.size() - 4).getName(), "drawable", getPackageName()));
            card1.setImageResource(getResources().getIdentifier(pHand.get(pHand.size() - 5).getName(), "drawable", getPackageName()));

            card5.setVisibility(View.VISIBLE);
            hit.setVisibility(View.GONE);
            hit2.setVisibility(View.GONE);
            hit3.setVisibility(View.GONE);

            int sum = 0;
            int aceCtr = 0;
            for (int i = 0; i < pHand.size(); i++) {
                sum += pHand.get(i).getRank();
                if (pHand.get(i).getRank() == 11) {
                    aceCtr++;
                }
            }
            if (sum > 21 && aceCtr > 0) {
                sum -= aceCtr * 10;
            }

            if (sum > 21) {

                Toast.makeText(this, "Bad luck. Dealer Wins!", Toast.LENGTH_SHORT).show();

                ok.setVisibility(View.VISIBLE);
                stand.setVisibility(View.GONE);
                hit.setVisibility(View.GONE);
                hit2.setVisibility(View.GONE);
                hit3.setVisibility(View.GONE);
            }
        });

        ok.setOnClickListener(v -> {

            Toast.makeText(this, "BlackJack", Toast.LENGTH_SHORT).show();
            pHand.clear();
            dHand.clear();

            creditsResultBJ.setVisibility(View.VISIBLE);
            creditsBarBJ.setVisibility(View.VISIBLE);
            start.setVisibility(View.VISIBLE);
            leave.setVisibility(View.VISIBLE);
            ok.setVisibility(View.GONE);
            card1.setVisibility(View.GONE);
            card2.setVisibility(View.GONE);
            card3.setVisibility(View.GONE);
            card4.setVisibility(View.GONE);
            card5.setVisibility(View.GONE);
            dCard1.setVisibility(View.GONE);
            dCard2.setVisibility(View.GONE);
            dCard3.setVisibility(View.GONE);
            dCard4.setVisibility(View.GONE);
            dCard5.setVisibility(View.GONE);
            dealerPoints.setVisibility(View.GONE);
            playerPoints.setVisibility(View.GONE);
        });
    }
}