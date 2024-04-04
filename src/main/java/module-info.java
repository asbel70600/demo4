module com.example.demo4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires org.apache.lucene.core;
    requires org.apache.lucene.queryparser;
    requires com.ibm.icu;

    opens com.example.demo4 to javafx.fxml;
    exports com.example.demo4;
}