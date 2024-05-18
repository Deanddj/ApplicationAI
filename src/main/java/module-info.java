module org.app.applicationai {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.json;

    opens org.app.applicationai to javafx.fxml;
    exports org.app.applicationai;
}