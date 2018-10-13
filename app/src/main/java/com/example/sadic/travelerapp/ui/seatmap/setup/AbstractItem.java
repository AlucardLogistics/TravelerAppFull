package com.example.sadic.travelerapp.ui.seatmap.setup;

public abstract class AbstractItem {

    public static final int TYPE_CENTER = 0;
    public static final int TYPE_EDGE = 1;
    public static final int TYPE_EMPTY = 2;
    public static final int TYPE_RESERVED = 3;

    private String label, reserved;
    private int seatNo;

    public int getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(int seatNo) {
        this.seatNo = seatNo;
    }

    public AbstractItem(String label, int seatNum) {
        this.label = label;
        this.seatNo = seatNum;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    abstract public int getType();
}
