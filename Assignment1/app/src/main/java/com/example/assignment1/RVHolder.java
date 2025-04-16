package com.example.assignment1;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

public class RVHolder extends RecyclerView.ViewHolder {
    public ImageView imageView;
    TextView tv;


    public RVHolder(View itemView) {
        super(itemView);
        tv = itemView.findViewById(R.id.c_name);
        imageView = itemView.findViewById(R.id.imageView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(itemView.getContext(),
                        tv.getText() + " Clicked!",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}