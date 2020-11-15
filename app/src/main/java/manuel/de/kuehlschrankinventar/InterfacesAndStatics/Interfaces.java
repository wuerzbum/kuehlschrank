package manuel.de.kuehlschrankinventar.InterfacesAndStatics;

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
        //TODO Methoden für produktDialog einfügen
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
}