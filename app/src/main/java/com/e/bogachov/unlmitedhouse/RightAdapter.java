package com.e.bogachov.unlmitedhouse;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by livingroomadmin on 31.08.16.
 */
public class RightAdapter extends RecyclerView.Adapter<RightAdapter.RightViewHolder> {

    public class RightViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        CardView cv;
        TextView productName;
        TextView productCount;
        Button btn;

        Context context = itemView.getContext();

        RightViewHolder(View itemView){
            super(itemView);

            cv = (CardView)itemView.findViewById(R.id.cv);
            productName = (TextView)itemView.findViewById(R.id.productName);
            productCount = (TextView)itemView.findViewById(R.id.productCount);
            btn = (Button)itemView.findViewById(R.id.btn);
            btn.setOnClickListener(this);


        }


        @Override
        public void onClick(View view) {

        }
    }

    List<Product> product;

    public RightAdapter(List<Product> product){this.product = product;}

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public RightViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_right, viewGroup, false);
        RightViewHolder pvhh = new RightViewHolder(v);
        return pvhh;

    }

    @Override
    public void onBindViewHolder(RightViewHolder rightViewHolder, int i) {

       rightViewHolder.productName.setText(product.get(i).name);
        rightViewHolder.productCount.setText(product.get(i).count);

    }

    @Override
    public int getItemCount() {

        return product.size();
    }
}
