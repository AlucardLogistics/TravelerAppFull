package com.example.sadic.travelerapp.ui.seatmap.setup;

public class CenterItem extends AbstractItem {

    public CenterItem(String label, int seatNo) {
        super(label, seatNo);
    }


    @Override
    public int getType() {
        return TYPE_CENTER;
    }

}
