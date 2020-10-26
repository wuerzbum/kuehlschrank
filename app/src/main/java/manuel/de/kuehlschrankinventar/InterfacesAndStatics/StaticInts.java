package manuel.de.kuehlschrankinventar.InterfacesAndStatics;

public class StaticInts {
    //Static Integer
    public final static int
            SELECTED_BUTTON_SAVE = 0,
            SELECTED_BUTTON_CANCEL = 1,
            SELECTED_MAIN_SCREEN = 2,
            SELECTED_SCAN_SCREEN = 3,
            REQUEST_CAMERA_PERMISSION = 201;

    //Binäre Integer für Flaggensetzung
    public final static int
            OK = 0b0,
            NAME_IST_LEER = 0b1,
            NAME_IST_BEREITS_VORHANDEN = 0b10,
            BARCODE_IST_BEREITS_VORHANDEN = 0b100,
            CAMERA_RELEASE = 0b1000;
}