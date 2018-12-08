package a555.setgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private EditText nickField;
    public int token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nickField = findViewById(R.id.nickfield);
    }

    public void register(View view) throws IOException, InterruptedException, JSONException {
        String nick = nickField.getText().toString();
        JSONObject res = new JSONObject();
        RegThread t = new RegThread("{\"action\": \"register\", \"nickname\": \"" + nick + "\"}", res);
        t.start();
        t.join();
        token = t.result.getInt("token");
        //name1 6347452
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}