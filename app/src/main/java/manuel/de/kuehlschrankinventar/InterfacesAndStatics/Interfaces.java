package manuel.de.kuehlschrankinventar.InterfacesAndStatics;

public class Interfaces {
    public interface scannedBarcodeDialogOnClickListener {
        void onClicked(int selectedButton, String name, String barcode, resultObserver resultObserver);
        void aborted();
    }

    public interface resultObserver {
        void result(int result);
    }
}