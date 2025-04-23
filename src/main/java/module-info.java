module com.ijse.gdse72.therapycenter {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires com.jfoenix;
    requires java.prefs;

    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;
    requires jbcrypt;
    requires java.management;

    opens com.ijse.gdse72.therapycenter.config to jakarta.persistence;
    opens com.ijse.gdse72.therapycenter.entity to org.hibernate.orm.core;
    opens com.ijse.gdse72.therapycenter.dto.tm to javafx.base;

    opens com.ijse.gdse72.therapycenter.controller to javafx.fxml;
    opens com.ijse.gdse72.therapycenter to javafx.fxml;
    exports com.ijse.gdse72.therapycenter;
}