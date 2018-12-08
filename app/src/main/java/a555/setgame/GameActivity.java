package a555.setgame;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.json.JSONException;

import java.util.HashSet;

public class GameActivity extends AppCompatActivity {
    public String[] cols = new String[]{"#ff3355", "#66CC22", "#22BBFF"};
    public HashSet<String> Cards = new HashSet();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        this.createCards();
    }

    public void createCards() {
        CardView card;
        int id = R.id.card0;
        int col, quantity, sh, bg;
        for (int i = 0; i < 15; i++) {
            card = findViewById(id + i);
            if (i != 0)
                while (Cards.size() != i + 1) {
                    col = (int) (Math.random() * 3);
                    quantity = (int) (Math.random() * 3);
                    sh = (int) (Math.random() * 3);
                    bg = (int) (Math.random() * 3);
                    try {
                        card.attrs(cols[col], quantity, sh, bg);
                        card.attr.put("clicked", false);
                        card.setBackgroundColor(Color.WHITE);
                        Cards.add("" + col + quantity + sh + bg);
                        card.invalidate();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            else {
                col = (int) (Math.random() * 3);
                quantity = (int) (Math.random() * 3);
                sh = (int) (Math.random() * 3);
                bg = (int) (Math.random() * 3);
                try {
                    card.attrs(cols[col], quantity, sh, bg);
                    card.attr.put("clicked", false);
                    card.setBackgroundColor(Color.WHITE);
                    Cards.add("" + col + quantity + sh + bg);
                    card.invalidate();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void nextCard(CardView card) throws JSONException {
        int cardsQuan = Cards.size();
        if (cardsQuan == 81) {
            card.attrs(null, -1, -1, -1);
            return;
        }
        int col, quantity, sh, bg;
        while (Cards.size() == cardsQuan) {
            col = (int) (Math.random() * 3);
            quantity = (int) (Math.random() * 3);
            sh = (int) (Math.random() * 3);
            bg = (int) (Math.random() * 3);
            card.attrs(cols[col], quantity, sh, bg);
            Cards.add("" + col + quantity + sh + bg);
        }
        card.invalidate();
    }

    public void add3Cards(View view) {

    }

    public void newGame(View view) {
        CardView.clickedCards.clear();
        Cards.clear();
        this.createCards();
    }
}