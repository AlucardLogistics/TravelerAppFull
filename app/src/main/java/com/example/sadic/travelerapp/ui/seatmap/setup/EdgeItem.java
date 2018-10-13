package com.example.sadic.travelerapp.ui.seatmap.setup;

public class EdgeItem extends AbstractItem {

    public EdgeItem(String label, int seatNo) {
        super(label, seatNo);
    }



    @Override
    public int getType() {
        return TYPE_EDGE;
    }

}
