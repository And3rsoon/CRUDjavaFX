module ProjetoCRUD {
	 requires javafx.base;
	    requires javafx.controls;
	    requires javafx.fxml;
	    requires javafx.graphics;
	    requires java.sql;
	    requires com.h2database;
	    requires jakarta.persistence;
	    requires net.bytebuddy;
	    requires org.hibernate.orm.core;
		requires java.net.http;
		requires com.fasterxml.jackson.databind;
		requires com.fasterxml.jackson.annotation;
		requires org.apache.poi.ooxml;
	    exports View;
	    exports Repository;  
	    exports Model;
	    exports Control;
	    opens Model;

}




