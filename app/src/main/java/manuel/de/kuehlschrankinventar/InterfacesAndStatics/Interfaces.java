package manuel.de.kuehlschrankinventar.InterfacesAndStatics;

import java.util.Date;

import manuel.de.kuehlschrankinventar.holder.Produkt;

public class Interfaces {
    public interface scanDialogListener {
        void onClicked(int selectedButton, String name, String barcode, resultObserver resultObserver);
        void abbruch();
    }

    public interface resultObserver {
        void result(int result);
    }

    public interface information {
        void inform(int information);
    }

    public interface produktDialogListener {
        void produktSpeichern(Produkt neuesProdukt, Produkt altesProdukt);
        void getBarcodeScan(getStringListener listener);
    }

    public interface benutzerDialogListener {
        //TODO Methoden für benutzerDialog einfügen
    }

    public interface mengenAnpassungsListener {
        //TODO Methoden für mengenAnpassung einfügen
    }

    public interface listViewDialogListener {
        //TODO Methoden für listViewDialog einfügen
    }

    public interface changeListenerDatePicker {
        void changed(Date newDate);
    }

    public interface getStringListener {
        void getString(String string);
    }
}