package Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Model.Socity_model;
import gogrocer.tcc.R;


public class Socity_adapter extends RecyclerView.Adapter<Socity_adapter.MyViewHolder>
        implements Filterable {

    private List<Socity_model> modelList;
    private List<Socity_model> mFilteredList;

    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_socity_name);
        }
    }

    public Socity_adapter(List<Socity_model> modelList) {
        this.modelList = modelList;
        this.mFilteredList = modelList;
    }

    @Override
    public Socity_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_socity_rv, parent, false);

        context = parent.getContext();

        return new Socity_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Socity_adapter.MyViewHolder holder, int position) {
        Socity_model mList = mFilteredList.get(position);

        holder.title.setText(mList.getSocity_name()+" - "+mList.getPincode());
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
               // String charString = charSequence.toString();
                ArrayList<Socity_model> filteredList = new ArrayList<>();

                if (charSequence==null || charSequence.length()==0) {
                   // Toast.makeText(context, "empty", Toast.LENGTH_SHORT).show();
                    mFilteredList.addAll(modelList);
                } else {
                    String filterPattern=charSequence.toString().toLowerCase().trim();
                  //  Toast.makeText(context, String.valueOf(modelList.size()), Toast.LENGTH_SHORT).show();
                    for (Socity_model androidVersion : modelList) {
                        if (androidVersion.getSocity_name().toLowerCase().contains(filterPattern) || androidVersion.getSocity_name().contains(charSequence)) {
                                filteredList.add(androidVersion);
                        }
                    }


                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList.clear();
                mFilteredList.addAll((ArrayList<Socity_model>)filterResults.values);
              //  mFilteredList = (ArrayList<Socity_model>) filterResults.values;
                notifyDataSetChanged();

            }
        };
    }


}
