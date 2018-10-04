package technology.nine.test;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import technology.nine.test.model.Items;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Items> items;

    RecyclerAdapter(Context context, ArrayList<Items> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int i) {
        holder.titles.setText(items.get(i).getTitles());
        holder.titles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (items.get(i).getTitles().equals("Scan")) {
                   context.startActivity(new Intent(context, ScannerActiivity.class));

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView titles;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titles = itemView.findViewById(R.id.titles);
        }
    }

    public void addAll(List<Items> items) {
        for (Items result : items) {
            add(result);
        }

    }

    public void add(Items r) {
        items.add(r);
        notifyItemInserted(items.size() - 1);
    }

}
