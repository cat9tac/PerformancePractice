package com.example.performancepractice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.performancepractice.anr.ANRActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static Class[] sActivityArray = {ANRActivity.class};
    private final static ArrayList<Class<? extends AppCompatActivity>> sActivityList = new ArrayList<>();
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recycler_main);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(new ListAdapter());
        sActivityList.clear();
        for (Class c : sActivityArray) {
            sActivityList.add(c);
        }
    }


    class ListAdapter extends RecyclerView.Adapter<ListViewHolder> {
        private View.OnClickListener mItemOnClickListener = (view) -> {
            if (view.getTag() != null) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, sActivityList.get((Integer) view.getTag()));
                MainActivity.this.startActivity(intent);
            }

        };

        @NonNull
        @Override
        public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_recycler_item, parent, false);
            return new ListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
            holder.mTv.setTag(position);
            holder.mTv.setText(sActivityList.get(position).getSimpleName());
            holder.mTv.setOnClickListener(mItemOnClickListener);
        }

        @Override
        public int getItemCount() {
            return sActivityList.size();
        }
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView mTv;


        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            mTv = itemView.findViewById(R.id.tv_main_recycler_item);
        }
    }

}