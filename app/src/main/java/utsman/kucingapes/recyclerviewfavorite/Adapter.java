package utsman.kucingapes.recyclerviewfavorite;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {
    List<Item> itemList;
    Context context;
    View view;

    public Adapter(List<Item> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        view = LayoutInflater.from(context)
                    .inflate(R.layout.item_row, viewGroup, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int i) {
        final Item item = itemList.get(i);
        holder.titleCard.setText(item.getTitle());

        Picasso.get().load(item.getImg()).into(holder.imageCard);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Detail.class);
                intent.putExtra("img", item.getImg());
                intent.putExtra("title", item.getTitle());
                ((AppCompatActivity) context).startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView imageCard;
        TextView titleCard;

        public Holder(@NonNull View itemView) {
            super(itemView);
            imageCard = itemView.findViewById(R.id.img_card);
            titleCard = itemView.findViewById(R.id.title_card);
        }
    }
}
