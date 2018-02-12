package com.demo.admin.onlinemysqldbdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

/**
 * Created by Admin on 12-02-2018.
 */

public class GithubAdapter extends RecyclerView.Adapter<GithubAdapter.GithubViewHolder> {

    private Context context;
    private User[] data;

    public GithubAdapter(Context context, User[] data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public GithubViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_user_layout, parent, false);
        return new GithubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GithubViewHolder holder, int position) {

        final User user = data[position];
        holder.textUser.setText(user.getLogin());
        Glide.with(holder.imageUser.getContext()).load(user.getAvatarUrl()).into(holder.imageUser);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code to run on click event of holder images
                Toast.makeText(context, user.getLogin() + " was Clicked ", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class GithubViewHolder extends RecyclerView.ViewHolder {
        ImageView imageUser;
        TextView textUser;

        public GithubViewHolder(View itemView) {
            super(itemView);
            imageUser = itemView.findViewById(R.id.imgUser);
            textUser = itemView.findViewById(R.id.txtUser);

        }
    }
}
