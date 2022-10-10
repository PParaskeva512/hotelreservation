package model;

import java.util.Comparator;

public class SortbyCheckOutDate implements Comparator<Reservation> {
    // Used for sorting in descending order of
    public int compare(Reservation a, Reservation b) {
        if (a.checkOutDate.before(b.checkOutDate)) {
            return 1;
        } else {
            return -1;
        }
    }
}
