package com.example.sadic.travelerapp.ui.seatmap.setup;

public class ReservedItem extends AbstractItem {

    public ReservedItem(String label, int seatNo) {
        super(label, seatNo);
    }

    @Override
    public int getType() {
        return 3;
    }
}
