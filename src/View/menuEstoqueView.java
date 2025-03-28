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

public class menuEstoqueView extends Application{

	private Stage stagemenu;
	private Stage st;
	
	
	public void setStage(Stage st) {
		stagemenu= st;
	}
	
    @Override
    public void start(Stage stage) {
        // Cabeçalho
        Text header = new Text("Entrada Produtos");
        header.setFont(Font.font("Arial", 30));
        header.setFill(Color.BLACK);
        
        HBox headerBox = new HBox(header);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.setPrefHeight(100);
        headerBox.setBackground(new Background(new BackgroundFill(
                Color.web("#69C285"), new CornerRadii(0), null)));

        // Botões
        Button btnEstoque = new Button("Estoque");
        
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
        
        Button btnCadastrar = new Button("Cadastrar Produtos");

        // Estilo dos botões
        btnCadastrar.setPrefSize(150, 40);
        btnCadastrar.setPrefSize(150, 40);
        btnCadastrar.setStyle(
                "-fx-background-radius: 15; " +
                "-fx-border-radius: 10;"+
                "-fx-pref-width: 300px;" +
                "-fx-pref-height: 70px;"+
                "-fx-font-size: 16px;"+
                "-fx-text-wrap: wrap;"
        );
        Button btnCategoria = new Button("Cadastrar Categorias");

        // Estilo dos botões
        btnCategoria.setPrefSize(150, 40);
        btnCategoria.setPrefSize(150, 40);
        btnCategoria.setStyle(
                "-fx-background-radius: 15; " +
                "-fx-border-radius: 10;"+
                "-fx-pref-width: 300px;" +
                "-fx-pref-height: 70px;"+
                "-fx-font-size: 16px;"+
                "-fx-text-wrap: wrap;"
        );
        
        Button btnUn = new Button("Cadastrar Unidade");

        // Estilo dos botões
        btnUn.setPrefSize(150, 40);
        btnUn.setPrefSize(150, 40);
        btnUn.setStyle(
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
        VBox vbox = new VBox(20, btnEstoque,btnCadastrar,btnCategoria, btnUn,btnVoltar);
        vbox.setAlignment(Pos.CENTER);

        
        // Layout principal
        BorderPane root = new BorderPane();
        root.setTop(headerBox);
        root.setCenter(vbox);
        root.setStyle("-fx-background-color: #F0F0F0;"); // Fundo da tela

        
        btnCategoria.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            		
                	 CadastroCategorias et=new CadastroCategorias();
                	 et.start(new Stage());
                	 
            }
        });
        
        btnUn.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            		
                	 cadastroUnMedida et=new cadastroUnMedida();
                	 et.start(new Stage());
                	 
            }
        });
        
        
        btnCadastrar.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            		
                	 cadastroProdutos et=new cadastroProdutos();
                	 et.start(new Stage());
                	 
            }
        });
        
        
        btnEstoque.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            		
                	 estoqueView et=new estoqueView();
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

