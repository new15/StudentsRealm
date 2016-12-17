package com.example.alex.studentsrealm.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.studentsrealm.R;
import com.example.alex.studentsrealm.realmHelper.models.StudentRealmObj;

import java.util.List;

/**
 * Created by Alex on 15.12.2016.
 */

public class RealDataAdapter extends RecyclerView.Adapter<RealDataAdapter.RealHolder> {

    private static final String TAG = "log";
    List<StudentRealmObj> realNamesList;

    public RealDataAdapter(List<StudentRealmObj> realNamesList) {
        this.realNamesList = realNamesList;
    }

    @Override
    public RealHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.data_base_recycler_row,parent,false);
        return new RealHolder(view);
    }

    @Override
    public void onBindViewHolder(final RealHolder holder, final int position) {

        holder.name.setText(realNamesList.get(position).getName());
        holder.lastName.setText(realNamesList.get(position).getLastName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: "+holder.lastName.getText());

                Toast.makeText(view.getContext(),"Details for "
                        +holder.lastName.getText()
                        +" "
                        +holder.name.getText(),Toast.LENGTH_SHORT)
                        .show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return realNamesList == null ? 0 : realNamesList.size();

    }

    class RealHolder extends RecyclerView.ViewHolder {
        TextView name,lastName;
        RealHolder(View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.real_names_recycler_row_name);
            lastName = (TextView)itemView.findViewById(R.id.real_names_recycler_row_last_name);

        }
    }


}