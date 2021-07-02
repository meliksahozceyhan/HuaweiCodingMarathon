package com.meliksah.huaweicodingmarathonproject.viewadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.meliksah.huaweicodingmarathonproject.R;
import com.meliksah.huaweicodingmarathonproject.model.Fence;

import java.util.List;

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.MyViewHolder> {
    Context context;
    List<Fence> fenceList;

    public MainRecyclerViewAdapter(Context context, List<Fence> fences){
        this.context = context;
        this.fenceList = fences;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.fence_recycler_view,parent,false);
        return new MainRecyclerViewAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Fence item = fenceList.get(position);
        holder.textViewFenceName.setText(item.getName());
        holder.textViewFenceCreatedAt.setText(item.getCreatedAt().toString());
    }

    @Override
    public int getItemCount() {
        return fenceList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewFenceName, textViewFenceCreatedAt;
        ImageView imageView;
        ConstraintLayout recyclerViewConstraintLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            recyclerViewConstraintLayout = itemView.findViewById(R.id.recyclerViewConstraintLayout);
            imageView = itemView.findViewById(R.id.imageView);
            textViewFenceName = itemView.findViewById(R.id.textViewFenceName);
            textViewFenceCreatedAt = itemView.findViewById(R.id.textViewFenceCreatedAt);
        }
    }
}
