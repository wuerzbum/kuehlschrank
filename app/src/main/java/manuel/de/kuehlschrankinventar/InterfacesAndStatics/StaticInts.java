package manuel.de.kuehlschrankinventar.InterfacesAndStatics;

public class StaticInts {
    //Static Integer
    public final static int
            DEFAULT = -1,
            AUSGEWAEHLT_TASTE_SPEICHERN = 0,
            AUSGEWAEHLT_TASTE_ABBRECHEN = 1,
            AUSGEWAEHLT_INVENTAR_ANSICHT = 2,
            AUSGEWAEHLT_SCAN_ANSICHT = 3,
            AUSGEWAEHLT_BENUTZER_ANSICHT = 4,
            AUSGEWAEHLT_BARCODE_DATENBANK_ANSICHT = 5,
            AUSGEWAEHLT_EINKAUSLISTEN_ANSICHT = 6,
            AUSGEWAEHLT_EINSTELLUNGS_ANSICHT = 7,
            AUSGEWAEHLT_JA = 8,
            AUSGEWAEHLT_NEIN = 9,
            RESULT_OK = 10,
            ANFRAGE_KAMERA_BERECHTIGUNG = 201;

    //Binäre Integer für Flaggensetzung
    public final static int
            OK = 0b0,
            NAME_IST_LEER = 0b1,
            NAME_IST_BEREITS_VORHANDEN = 0b10,
            BARCODE_IST_BEREITS_VORHANDEN = 0b100,
            KAMERA_FREIGABE = 0b1000;
}