package manuel.de.kuehlschrankinventar.InterfacesAndStatics;

public class Interfaces {
    public interface scannedBarcodeDialogOnClickListener {
        void onClicked(int selectedButton, String name, String barcode, resultObserver resultObserver);
        void abbruch();
    }

    public interface resultObserver {
        void result(int result);
    }

    public interface information {
        void inform(int information);
    }
}