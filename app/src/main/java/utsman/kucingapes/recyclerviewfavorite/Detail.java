package utsman.kucingapes.recyclerviewfavorite;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class Detail extends AppCompatActivity {

    private static final String PREFS_NAME = "FILE_PREFERENCES";
    private static final String FAVORITES = "ITEM_FAVORITE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final String img = getIntent().getStringExtra("img");
        final String title = getIntent().getStringExtra("title");
        final FloatingActionButton actionButton = findViewById(R.id.favorite_button);
        final NestedScrollView scrollView = findViewById(R.id.nested);

        if (img != null) {

            ImageView imageDetail = findViewById(R.id.img_detail);
            TextView tvTitle = findViewById(R.id.title_detail);

            tvTitle.setText(title);
            Glide.with(this).load(img).into(imageDetail);
        }

        /* pasang dulu sharedpreferencesnya */
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        final String json = preferences.getString(FAVORITES, null); // panggil string json nye

        if (json != null && json.contains(title)) {
            actionButton.setImageResource(R.drawable.ic_favorite);
        } else actionButton.setImageResource(R.drawable.ic_favorite_border);


        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFavo(json, actionButton, img, title);
            }
        });

        setupPosition(scrollView, actionButton);
    }

    private void setupPosition(NestedScrollView scrollView, FloatingActionButton actionButton) {
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {

            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                    if (i1 > i3) {
                        Toast.makeText(getApplicationContext(), "down", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void addFavo(String json, FloatingActionButton actionButton, String img, String title) {
        Item item = new Item(img, title); // pasang objek yang mau ditambahin ke recyclerview

        /* ngecek apakah itemnya udeh ada di favorit ape belum */
        if (json != null && json.contains(title)) { // andaikata udah ada
            actionButton.setImageResource(R.drawable.ic_favorite_border);
            Toast.makeText(getApplicationContext(), "dihapus", Toast.LENGTH_SHORT).show();
            SharedPref sharedPref = new SharedPref();
            int position = sharedPref.setIndex(getApplicationContext(), title);
            sharedPref.removeFavorite(getApplicationContext(), position); // maka metodenya adalah remove item
            sharedPref.removeIndex(getApplicationContext(), title); // nah ini remove string single nya buat acuan posisi

        } else { // nah kalau belom
            actionButton.setImageResource(R.drawable.ic_favorite);
            SharedPref sharedPref = new SharedPref();
            sharedPref.addFavorite(getApplicationContext(), item); // ya tambahin deh
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