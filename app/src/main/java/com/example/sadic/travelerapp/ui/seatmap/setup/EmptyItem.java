package com.example.sadic.travelerapp.ui.seatmap.setup;

public class EmptyItem extends AbstractItem {

    public EmptyItem(String label, int seatNo) {
        super(label, seatNo);
    }


    @Override
    public int getType() {
        return TYPE_EMPTY;
    }

}
