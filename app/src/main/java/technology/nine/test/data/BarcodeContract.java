package technology.nine.test.data;

import android.provider.BaseColumns;

public class BarcodeContract {
    public BarcodeContract() {
    }

    static class barcodeEntry implements BaseColumns {
        static final String BARCODE_TABLE = "Barcodes", VALUE = "value";
    }
}
