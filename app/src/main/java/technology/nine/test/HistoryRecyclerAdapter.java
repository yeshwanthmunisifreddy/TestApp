package technology.nine.test;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import technology.nine.test.model.Items;

public class HistoryRecyclerAdapter extends RecyclerView.Adapter<HistoryRecyclerAdapter.MyViewHolder> {
    private Context context;
    private List<Items> items ;

    public HistoryRecyclerAdapter(Context context) {
        this.context = context;
        items= new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        holder.valueView.setText(items.get(i).getTitles());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

     class MyViewHolder extends RecyclerView.ViewHolder {
        TextView valueView;
        MyViewHolder(View itemView) {
            super(itemView);
            valueView = itemView.findViewById(R.id.value);
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
