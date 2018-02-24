package com.laxen.auxiliaire.tabs.ListTab;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.laxen.auxiliaire.models.Job;
import com.laxen.auxiliaire.R;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by rawa on 2016-05-14.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    public void setListener(ListAdapterListener listener) {
        this.listener = listener;
    }

    public interface ListAdapterListener {
        void onJobClicked(Job job);
    }

    private ListAdapterListener listener;

    private List<Job> mJobset;
    protected Context context;
    static HashMap<String, Integer[]> catToColor = new HashMap(); // maps a category to a color
    static HashMap<String, Integer> catToIcon = new HashMap(); // maps a category to a color

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
        protected ImageView cardIcon;

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
            cardIcon = (ImageView) view.findViewById(R.id.card_icon);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ListAdapter(List<Job> myDataset, Context context) {
        this.context = context;
        mJobset = myDataset;

        Integer[] hw = {R.color.colorHandiworkStart, R.color.colorHandiworkEnd};
        Integer[] cp = {R.color.colorComputerStart, R.color.colorComputerEnd};
        Integer[] st = {R.color.colorStudiesStart, R.color.colorStudiesEnd};
        Integer[] ck = {R.color.colorCookingStart, R.color.colorCookingEnd};
        Integer[] ot = {R.color.colorOtherStart, R.color.colorOtherEnd};

        // maps all job categories to their color
        catToColor.put("Handiwork", hw);
        catToColor.put("Computers/IT", cp);
        catToColor.put("Studies", st);
        catToColor.put("Cooking", ck);
        catToColor.put("Other", ot);


        // maps all job categories to their color
        catToIcon.put("Handiwork", R.drawable.ic_build_white_24dp);
        catToIcon.put("Computers/IT", R.drawable.ic_computer_white_24dp);
        catToIcon.put("Studies", R.drawable.ic_school_white_24dp);
        catToIcon.put("Cooking", R.drawable.ic_cake_white_24dp);
        catToIcon.put("Other", R.drawable.ic_filter_vintage_white_24dp);

    }

    // Create new views (invoked by the layout manager)
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new views
        View v = LayoutInflater.from(parent.getContext())
                               .inflate(R.layout.list_card_new, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Job job = mJobset.get(position);

        holder.cardIcon.setImageDrawable(context.getResources().getDrawable(catToIcon.get(job.getKind())));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onJobClicked(job);
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

        int c0 = catToColor.get(job.getKind())[0];
        int c1 = catToColor.get(job.getKind())[1];

        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[] {context.getResources().getColor(c0), context.getResources().getColor(c1)});

        holder.cardBackground.setBackgroundDrawable(gd);

        //holder.cardBackground.setBackgroundColor(context.getResources().getColor(color));
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
