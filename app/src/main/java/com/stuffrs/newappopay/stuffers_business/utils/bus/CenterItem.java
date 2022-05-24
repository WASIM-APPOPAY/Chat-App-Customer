package com.stuffrs.newappopay.stuffers_business.utils.bus;

public class CenterItem extends AbstractItem {

    public CenterItem(String label) {
        super(label);
    }


    @Override
    public int getType() {
        return TYPE_CENTER;
    }

}
