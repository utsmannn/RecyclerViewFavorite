package utsman.kucingapes.recyclerviewfavorite;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Detail extends AppCompatActivity {

    public static final String PREFS_NAME = "PRODUCT_APP";
    public static final String FAVORITES = "Product_Favorite";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final String img = getIntent().getStringExtra("img");
        final String title = getIntent().getStringExtra("title");
        final FloatingActionButton actionButton = findViewById(R.id.favorite_button);

        if (img != null) {

            ImageView imageDetail = findViewById(R.id.img_detail);
            TextView tvTitle = findViewById(R.id.title_detail);

            tvTitle.setText(title);
            Picasso.get().load(img).into(imageDetail);

        }

        SharedPreferences preferences = getApplicationContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        final String json = preferences.getString(FAVORITES, null);

        if (json != null && json.contains(title)) {
            actionButton.setImageResource(R.drawable.ic_favorite);
        } else actionButton.setImageResource(R.drawable.ic_favorite_border);


        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFavo(json, actionButton, img, title);
            }
        });
    }

    private void addFavo(String json, FloatingActionButton actionButton, String img, String title) {
        Item item = new Item(img, title);

        if (json != null && json.contains(title)) {
            actionButton.setImageResource(R.drawable.ic_favorite_border);
            Toast.makeText(getApplicationContext(), "dihapus", Toast.LENGTH_SHORT).show();
            SharedPref sharedPref = new SharedPref();
            int position = sharedPref.setIndex(getApplicationContext(), title);
            sharedPref.removeFavorite(getApplicationContext(), position);
            sharedPref.removeIndex(getApplicationContext(), title);

        } else {
            actionButton.setImageResource(R.drawable.ic_favorite);
            SharedPref sharedPref = new SharedPref();
            sharedPref.addFavorite(getApplicationContext(), item);
            sharedPref.addIndex(getApplicationContext(), title);

            Toast.makeText(getApplicationContext(), "ditambahkan", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("refresh", "true");
        setResult(RESULT_OK, intent);
        finish();
    }
}
