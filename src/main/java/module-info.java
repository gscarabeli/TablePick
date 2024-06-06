module br.edu.fesa.tablepick {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.zaxxer.hikari;

    opens br.edu.fesa.tablepick to javafx.fxml;
    opens br.edu.fesa.tablepick.controller to javafx.fxml;
    opens br.edu.fesa.tablepick.model to javafx.base;
    exports br.edu.fesa.tablepick;
}
