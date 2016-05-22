package com.laxen.auxiliaire.tabs.ListTab;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.laxen.auxiliaire.JobFragment;
import com.laxen.auxiliaire.MainActivity;
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
    protected Context context;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView vTitle;
        protected TextView vDate;
        protected TextView vDescription;
        protected TextView vPrice;
        protected CardView cardView;
        protected View cardBackground;

        // each data item is just a string in this case
        public View view;
        public ViewHolder(View v) {
            super(v);
            view = v;
            cardView = (CardView) view.findViewById(R.id.card_view);
            vTitle = (TextView) view.findViewById(R.id.tvTitle);
            vDescription = (TextView) view.findViewById(R.id.tvDescription);
            vPrice = (TextView) view.findViewById(R.id.tvPrice);
            vDate = (TextView) view.findViewById(R.id.tvDate);
            cardBackground = view.findViewById(R.id.cardBackground);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ListAdapter(List<Job> myDataset, Context context) {
        this.context = context;
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
        final Job job = mJobset.get(position);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.popFragment();

                mainActivity.setCurrentJob(job);
                mainActivity.jobFragment = new JobFragment();

                FragmentTransaction transaction;
                transaction = mainActivity.getSupportFragmentManager().beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.add(R.id.fragmentcontainer, mainActivity.jobFragment).addToBackStack("jobFrag");
                transaction.commit();
            }
        });

        holder.vTitle.setText(job.getTitle());

        String vDateString = "";
        Date createDate = job.getCreated_at();
        Log.d("CreatedAt", createDate.toString());
        if(DateUtils.isToday(createDate.getTime())){
            android.text.format.DateFormat df = new android.text.format.DateFormat();
            vDateString = df.format("HH:mm", createDate).toString();
        } else if(isYesterday(createDate)){
            vDateString = "yesterday";
        } else {
            android.text.format.DateFormat df = new android.text.format.DateFormat();
            vDateString = df.format("dd MMM", createDate).toString();
        }
        holder.vDate.setText(vDateString);

        holder.vDescription.setText(job.getDescription());
        holder.vPrice.setText(job.getPrice().toString());

        int color = ((MainActivity)context).getJobsModel().getCatToColor().get(job.getKind());

        holder.cardBackground.setBackgroundColor(context.getResources().getColor(color));
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
