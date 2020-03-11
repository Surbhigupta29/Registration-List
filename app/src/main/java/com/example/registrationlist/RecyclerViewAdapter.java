package com.example.registrationlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    Context context;
    List<ResidentDetails> MainImageUploadInfoList;

    public RecyclerViewAdapter(Context context, List<ResidentDetails> TempList) {
        this.MainImageUploadInfoList = TempList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_items, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ResidentDetails residentDetails = MainImageUploadInfoList.get(position);
        holder.ResidentNameTextView.setText(residentDetails.getResidentName());
        holder.ResidentDOBTextView.setText(residentDetails.getResidentDateOfBirth());
    }

    @Override
    public int getItemCount() {
        return MainImageUploadInfoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView ResidentNameTextView;
        public TextView ResidentDOBTextView;
        public ViewHolder(View itemView) {

            super(itemView);

            ResidentNameTextView = (TextView) itemView.findViewById(R.id.ShowResidentNameTextView);
            ResidentDOBTextView = (TextView) itemView.findViewById(R.id.ShowResidentDOBTextView);
        }
    }
}