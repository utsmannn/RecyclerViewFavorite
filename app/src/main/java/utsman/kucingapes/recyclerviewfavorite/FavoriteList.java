package utsman.kucingapes.recyclerviewfavorite;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.thekhaeng.recyclerviewmargin.LinearLayoutMargin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FavoriteList extends AppCompatActivity {

    private List<Item> itemList;
    private Adapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Item Favorite");

        EmptyRecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        LinearLayoutMargin layoutMargin = new LinearLayoutMargin(20);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(layoutMargin);
        recyclerView.setEmptyView(findViewById(R.id.empty_layout));

        setupEmpty();

        setItem(recyclerView);
    }

    private void setupEmpty() {
        String url = "https://media.giphy.com/media/qPvUs7Ij5JGX6/giphy.gif";
        Glide.with(this).load(url).apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into((ImageView) findViewById(R.id.img_empty));

        TextView textView = findViewById(R.id.textempty);
        textView.setText("kosong");
    }

    private void setItem(RecyclerView recyclerView) {
        SharedPref sharedPref = new SharedPref();
        itemList = sharedPref.getFavorites(this);

        if (itemList == null) {
            itemList = new ArrayList<>();
        }

        adapter = new Adapter(itemList, this);
        Collections.reverse(itemList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String output = data.getStringExtra("refresh");
                if (output.equals("true")) {
                    recreate();
                }
            }
        }
    }
}
