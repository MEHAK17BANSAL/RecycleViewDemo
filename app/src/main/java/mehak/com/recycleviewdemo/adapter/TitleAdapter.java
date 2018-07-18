package mehak.com.recycleviewdemo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mehak.com.recycleviewdemo.R;
import mehak.com.recycleviewdemo.model.Title;

import java.util.ArrayList;


public class TitleAdapter extends RecyclerView.Adapter<TitleAdapter.ViewHolder>{

    Context context;
    int resource;
    ArrayList<Title> objects;

    public TitleAdapter(Context context, int resource, ArrayList<Title> objects) {
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(resource,parent,false);

        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Title title = objects.get(position);


        holder.txtName.setText(title.title);
        holder.price.setText(title.sale_price);

    }

    @Override
    public int getItemCount() {
        return objects.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{


        TextView txtName,price;


        public ViewHolder(View itemView) {
            super(itemView);


            txtName = itemView.findViewById(R.id.textViewName);
            price = itemView.findViewById(R.id.textViewName2);
        }
    }

}