module es.antoniodominguez.disennadores {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.instrument;
    requires java.persistence;
    requires java.sql;
    requires java.base;
    
    opens es.antoniodominguez.entidades;
    opens es.antoniodominguez.disennadores to javafx.fxml;
    exports es.antoniodominguez.disennadores;
}
