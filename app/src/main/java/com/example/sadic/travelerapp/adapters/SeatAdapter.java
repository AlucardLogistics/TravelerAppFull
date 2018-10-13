package com.example.sadic.travelerapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sadic.travelerapp.R;
import com.example.sadic.travelerapp.adapters.setup.AbstractSelectSeatAdapter;
import com.example.sadic.travelerapp.ui.seatmap.setup.AbstractItem;
import com.example.sadic.travelerapp.ui.seatmap.setup.CenterItem;
import com.example.sadic.travelerapp.ui.seatmap.setup.EdgeItem;
import com.example.sadic.travelerapp.ui.seatmap.setup.OnSeatSelected;
import com.example.sadic.travelerapp.ui.seatmap.setup.ReservedItem;

import java.util.List;

public class SeatAdapter extends AbstractSelectSeatAdapter<RecyclerView.ViewHolder> {
    private OnSeatSelected mOnSeatSelected;
    private static final String TAG = "SeatAdapter";

    private static class ReservedViewHolder extends RecyclerView.ViewHolder {

        ImageView ivReserved, ivSeat;
        TextView tvSeatLabel;

        public ReservedViewHolder(View itemView) {
            super(itemView);
            tvSeatLabel = itemView.findViewById(R.id.tvSeatLabel);
            ivReserved = itemView.findViewById(R.id.ivSeatReserved);
            ivSeat = itemView.findViewById(R.id.ivSeat);
        }
    }

    private static class EdgeViewHolder extends RecyclerView.ViewHolder {

        TextView tvSeatLabel;
        ImageView imgSeat;
        private final ImageView imgSeatSelected;

        public EdgeViewHolder(View itemView) {
            super(itemView);
            tvSeatLabel = itemView.findViewById(R.id.tvSeatLabel);
            imgSeat = (ImageView) itemView.findViewById(R.id.ivSeat);
            imgSeatSelected = (ImageView) itemView.findViewById(R.id.ivSeatSelected);

        }

    }

    private static class CenterViewHolder extends RecyclerView.ViewHolder {

        TextView tvSeatLabel;
        ImageView imgSeat;
        private final ImageView imgSeatSelected;

        public CenterViewHolder(View itemView) {
            super(itemView);
            tvSeatLabel = itemView.findViewById(R.id.tvSeatLabel);
            imgSeat = (ImageView) itemView.findViewById(R.id.ivSeat);
            imgSeatSelected = (ImageView) itemView.findViewById(R.id.ivSeatSelected);


        }

    }

    private static class EmptyViewHolder extends RecyclerView.ViewHolder {

        public EmptyViewHolder(View itemView) {
            super(itemView);
        }

    }

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    private List<AbstractItem> mItems;

    public SeatAdapter(Context context, List<AbstractItem> items) {
        mOnSeatSelected = (OnSeatSelected) context;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mItems = items;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == AbstractItem.TYPE_CENTER) {
            View itemView = mLayoutInflater.inflate(R.layout.item_view_seat, parent, false);
            return new CenterViewHolder(itemView);
        } else if (viewType == AbstractItem.TYPE_EDGE) {
            View itemView = mLayoutInflater.inflate(R.layout.item_view_seat, parent, false);
            return new EdgeViewHolder(itemView);
        } else if(viewType ==  AbstractItem.TYPE_RESERVED) {
            View itemView = mLayoutInflater.inflate(R.layout.item_view_seat, parent, false);
            return new ReservedViewHolder(itemView);
        } else {
            View itemView = new View(mContext);
            return new EmptyViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        int type = mItems.get(position).getType();


        if (type == AbstractItem.TYPE_CENTER) {
            //final CenterItem item = (CenterItem) mItems.get(position);
            CenterViewHolder holder = (CenterViewHolder) viewHolder;
            final int seatNo = mItems.get(position).getSeatNo();
            holder.tvSeatLabel.setText("S" + String.valueOf(seatNo));

            holder.imgSeat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    toggleSelection(position);
                    //Log.d(TAG, "onClick: CenterSeat position: " + position);

                    mOnSeatSelected.onSeatSelected(getSelectedItemCount());
                    mOnSeatSelected.seatPosition(seatNo);
                }
            });

            holder.imgSeatSelected.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);

        } else if (type == AbstractItem.TYPE_EDGE) {
            final EdgeItem item = (EdgeItem) mItems.get(position);
            final int seatNo = mItems.get(position).getSeatNo();
            EdgeViewHolder holder = (EdgeViewHolder) viewHolder;
            holder.tvSeatLabel.setText("S" + String.valueOf(seatNo));

            holder.imgSeat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    toggleSelection(position);
                    mOnSeatSelected.onSeatSelected(getSelectedItemCount());
                    mOnSeatSelected.seatPosition(seatNo);
                    //Log.d(TAG, "onClick: EdgeSeat position: " + position);


                }
            });

            holder.imgSeatSelected.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);

        } else if(type == AbstractItem.TYPE_RESERVED) {
            final ReservedItem item = (ReservedItem) mItems.get(position);
            ReservedViewHolder holder = (ReservedViewHolder) viewHolder;
            int seatNo = mItems.get(position).getSeatNo();
            holder.ivSeat.setVisibility(View.INVISIBLE);
            holder.ivReserved.setVisibility(View.VISIBLE);
            holder.tvSeatLabel.setText("S" + String.valueOf(seatNo));

            holder.ivReserved.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "Seat is Reserved.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}

