package utsman.kucingapes.recyclerviewfavorite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.thekhaeng.recyclerviewmargin.LinearLayoutMargin;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Item> itemList = new ArrayList<>();
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EmptyRecyclerView recyclerView = findViewById(R.id.recycler_view);
        adapter = new Adapter(itemList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        LinearLayoutMargin layoutMargin = new LinearLayoutMargin(20);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(layoutMargin);
        recyclerView.setAdapter(adapter);
        addItem();
    }

    private void addItem() {
        String img1 = "https://source.unsplash.com/0y92si4ZLyA";
        String img2 = "https://source.unsplash.com/L16dgFLf8tU";
        String img3 = "https://source.unsplash.com/Uh7B6L8ZIeg";
        String img4 = "https://source.unsplash.com/X0lsXXexWR8";
        String img5 = "https://source.unsplash.com/BlIhVfXbi9s";
        String img6 = "https://source.unsplash.com/jHujswdj2V4";

        itemList.add(new Item(img1, "title 1"));
        itemList.add(new Item(img2, "title 2"));
        itemList.add(new Item(img3, "title 3"));
        itemList.add(new Item(img4, "title 4"));
        itemList.add(new Item(img5, "title 5"));
        itemList.add(new Item(img6, "title 6"));
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorit_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.favorite) {
            startActivity(new Intent(getApplicationContext(), FavoriteList.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
