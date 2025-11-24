package app;

import core.MegaferiaFrame;
import repository.*;
import controller.*;

public class Main {
    public static void main(String[] args) {

        // Repositorios (memoria simulada)
        StandRepository standRepo = new StandRepository();
        PersonRepository personRepo = new PersonRepository();
        EditorialRepository editorialRepo = new EditorialRepository();
        BookRepository bookRepo = new BookRepository();

        // Controladores
        StandController standCtrl = new StandController(standRepo);
        PersonController personCtrl = new PersonController(personRepo);
        EditorialController editorialCtrl = new EditorialController(editorialRepo, personRepo);
        BookController bookCtrl = new BookController(bookRepo, editorialRepo, personRepo);
        PurchaseController purchaseCtrl = new PurchaseController(standRepo, editorialRepo);

        // Iniciar GUI
        java.awt.EventQueue.invokeLater(() -> {
            MegaferiaFrame ventana = new MegaferiaFrame();

            ventana.setStandController(standCtrl);
            ventana.setPersonController(personCtrl);
            ventana.setEditorialController(editorialCtrl);
            ventana.setBookController(bookCtrl);
            ventana.setPurchaseController(purchaseCtrl);

            ventana.setVisible(true);
            ventana.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);  // OPCIONAL: pantalla completa
        });
    }
}
