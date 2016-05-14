package com.laxen.auxiliaire.tabs.ListTab;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.laxen.auxiliaire.models.Job;
import com.laxen.auxiliaire.R;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by rawa on 2016-05-14.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<Job> mJobset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView vTitle;
        protected TextView vDate;
        protected CardView cardView;

        // each data item is just a string in this case
        public View view;
        public ViewHolder(View v) {
            super(v);
            view = v;
            cardView = (CardView) view.findViewById(R.id.card_view);
            vTitle = (TextView) view.findViewById(R.id.tvTitle);
            vDate = (TextView) view.findViewById(R.id.tvDate);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ListAdapter(List<Job> myDataset) {
        mJobset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new views
        View v = LayoutInflater.from(parent.getContext())
                               .inflate(R.layout.list_card, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Job job = mJobset.get(position);
        holder.vTitle.setText(job.getTitle());

        String vDateString = "";
        Date createDate = job.getDate();
        if(DateUtils.isToday(createDate.getTime())){
            android.text.format.DateFormat df = new android.text.format.DateFormat();
            vDateString = df.format("HH:mm", new java.util.Date()).toString();
        } else if(isYesterday(createDate)){
            vDateString = "yesterday";
        } else {
            android.text.format.DateFormat df = new android.text.format.DateFormat();
            vDateString = df.format("dd MMM", new java.util.Date()).toString();
        }
        holder.vDate.setText(vDateString);

        // - get element from your dataset at this position
        // - replace the contents of the view with that element

    }
    private boolean isYesterday(Date date){
        long unixtime = date.getTime();
        // Add one day

        unixtime+=1000*60*60*24;
        return DateUtils.isToday(unixtime);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mJobset.size();
    }
}
