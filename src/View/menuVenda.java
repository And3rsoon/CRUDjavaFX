package View;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class menuVenda  extends Application{

	private Stage stagemenu;
	private Stage st;
	
	
	public void setStage(Stage st) {
		stagemenu= st;
	}
	
    @Override
    public void start(Stage stage) {
        // Cabeçalho
        Text header = new Text("Venda");
        header.setFont(Font.font("Arial", 30));
        header.setFill(Color.BLACK);
        
        HBox headerBox = new HBox(header);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.setPrefHeight(100);
        headerBox.setBackground(new Background(new BackgroundFill(
                Color.web("#69C285"), new CornerRadii(0), null)));

        // Botões
        Button btnEstoque = new Button("Venda");
        
        // Estilo dos botões
        btnEstoque.setPrefSize(150, 40);
       
        btnEstoque.setStyle(
                "-fx-background-radius: 15; " +
                "-fx-border-radius: 10;"+
                "-fx-pref-width: 300px;" +
                "-fx-pref-height: 70px;"+
                "-fx-font-size: 16px;"+
                "-fx-text-wrap: wrap;"
        );
        
       
        
        Button btnVoltar = new Button("Voltar");

        // Estilo dos botões
        btnVoltar.setPrefSize(150, 40);
        btnVoltar.setPrefSize(150, 40);
        btnVoltar.setStyle(
                "-fx-background-radius: 15; " +
                "-fx-border-radius: 10;"+
                "-fx-pref-width: 300px;" +
                "-fx-pref-height: 70px;"+
                "-fx-font-size: 16px;"+
                "-fx-text-wrap: wrap;"
        );

        // Layout central
        VBox vbox = new VBox(20, btnEstoque,btnVoltar);
        vbox.setAlignment(Pos.CENTER);

        
        // Layout principal
        BorderPane root = new BorderPane();
        root.setTop(headerBox);
        root.setCenter(vbox);
        root.setStyle("-fx-background-color: #F0F0F0;"); // Fundo da tela

        
        
        btnEstoque.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            		
                	 saidaProduto et=new saidaProduto();
                	 et.start(new Stage());
                	 
            }
        });
        
        
        btnVoltar.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                	stage.close();
                	stagemenu.show();
            		
            }
        });
        
        
        // Configuração da Cena
        Scene scene = new Scene(root, 700, 500);
        stage.setTitle("Entrada de Produtos");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

