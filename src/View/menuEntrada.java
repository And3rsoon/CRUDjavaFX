package View;
import java.util.List;

import Model.Fornecedor;
import Model.Funcionarios;
import Model.categoria;
import Model.produto;
import Model.remetenteNota;
import Model.unidadeMedida;
import Repository.FuncionarioRepository;
import Repository.cadRemetenteRepository;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;

public class menuEntrada extends Application {

	private Stage stagemenu;
	private List<unidadeMedida> unMedida;
	private List<Fornecedor> fornecedor;
	private List<produto> produto;
	private List<categoria> categoria;
	private Funcionarios funcionario;
	private List<remetenteNota> remetente;
	
	public void setStage(Stage st, List<unidadeMedida> unMedida,List<categoria> categoria,List<Fornecedor> fornecedor,List<produto> produto,List<remetenteNota> remetente,Funcionarios fn) {
		cadRemetenteRepository rem=new cadRemetenteRepository();
		stagemenu= st;
		this.unMedida=unMedida;
		this.fornecedor=fornecedor;
		this.categoria=categoria;
		this.produto=produto;
		this.funcionario=fn;
		this.remetente=rem.ListarTodos();
	}
	
	
	public void atualizarRmetente() {
		cadRemetenteRepository rem=new cadRemetenteRepository();
		this.remetente=rem.ListarTodos();
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
        Button btnCompra = new Button("Compra");
        Button btnDevolucao = new Button("Devolução");
        Button btnCadRemetente = new Button("Cadastrar Remetente");

        // Estilo dos botões
        btnCompra.setPrefSize(150, 40);
        btnDevolucao.setPrefSize(150, 40);
        btnCompra.setStyle(
                "-fx-background-radius: 15; " +
                "-fx-border-radius: 10;"+
                "-fx-pref-width: 300px;" +
                "-fx-pref-height: 70px;"+
                "-fx-font-size: 16px;"+
                "-fx-text-wrap: wrap;"
        );
        
        btnDevolucao.setStyle(
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
        
        btnCadRemetente.setStyle(
                "-fx-background-radius: 15; " +
                "-fx-border-radius: 10;"+
                "-fx-pref-width: 300px;" +
                "-fx-pref-height: 70px;"+
                "-fx-font-size: 16px;"+
                "-fx-text-wrap: wrap;"
        );

        // Layout central
        VBox vbox = new VBox(20, btnCompra, btnDevolucao,btnCadRemetente,btnVoltar);
        vbox.setAlignment(Pos.CENTER);

        // Layout principal
        BorderPane root = new BorderPane();
        root.setTop(headerBox);
        root.setCenter(vbox);
        root.setStyle("-fx-background-color: #F0F0F0;"); // Fundo da tela

        btnCompra.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            		atualizarRmetente();
            		EntradaProduto et=new EntradaProduto();
                	et.setList(unMedida,categoria,fornecedor,produto,remetente,funcionario);
                	et.start(new Stage());
            }
        });
        
        
        btnCadRemetente.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            		
            		cadastroRemetente et=new cadastroRemetente();
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
