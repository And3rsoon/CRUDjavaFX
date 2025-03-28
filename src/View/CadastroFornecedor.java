package View;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.shape.Line;
import Model.cepModel;
import Service.CepAPI;

import java.util.List;

import Model.Fornecedor;
import Repository.FornecedorRepository;

public class CadastroFornecedor extends Application {

	private TextField nomeField;
    private TextField cpfField;

    
    @Override
    public void start(Stage stage) {
        // Cabeçalho
        Text header = new Text("Cadastro Fornecedor");
        header.setFont(Font.font("Arial", 36));
        header.setFill(Color.BLACK);
        

        HBox headerBox = new HBox(10,header);
        HBox.setMargin(header, new Insets(50));
        headerBox.setAlignment(Pos.CENTER_LEFT);
        headerBox.setPrefHeight(100);
        headerBox.setBackground(new Background(new BackgroundFill(
                Color.web("#69C285"), CornerRadii.EMPTY, Insets.EMPTY)));

        
        // Layout para os campos de formulário
        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.setPadding(new Insets(10));

        // Campos do formulário
        
        Label operaçao=new Label("Tipo Operação: Cadastro");
        operaçao.setFont(Font.font("Arial", 20));
        formGrid.add(operaçao, 0, 0);

     // Criando a linha horizontal
        Line linha = new Line();
        linha.setStartX(0);  // Posição inicial no eixo X (esquerda)
        linha.setEndX(750);  // Posição final no eixo X (direita)
        linha.setStartY(100); // Posição inicial no eixo Y
        linha.setEndY(100);   // Mantendo a posição Y constante para ser horizontal
        
        // Definindo a cor e espessura da linha
        linha.setStroke(Color.BLACK);
        linha.setStrokeWidth(2);
        formGrid.add(linha, 0, 2,6,1);
        
        
        Label tipoDocumentoLabel=new Label("Tipo Documento:");
        tipoDocumentoLabel.setFont(Font.font("Arial", 20));
        operaçao.setFont(Font.font("Arial", 20));
        formGrid.add(tipoDocumentoLabel, 0, 3);
        
        RadioButton opcCPF = new RadioButton("CPF");
        opcCPF.setFont(Font.font("Arial", 16));
        RadioButton opcCNPJ = new RadioButton("CNPJ");
        opcCNPJ.setFont(Font.font("Arial", 16));
        ToggleGroup grupo01 = new ToggleGroup();
        opcCPF.setToggleGroup(grupo01);
        opcCNPJ.setToggleGroup(grupo01);
        formGrid.add(opcCPF,1, 3);
        formGrid.add(opcCNPJ, 2, 3);
        
        Label NumeroDocLabel=new Label("Numero:");
        NumeroDocLabel.setFont(Font.font("Arial", 20));
        formGrid.add(NumeroDocLabel, 3, 3);
        TextField NumeroDocField = new TextField();
        formGrid.add(NumeroDocField, 4, 3);
        
        
        Label nomeFornecedorLabel=new Label("Fornecedor:");
        nomeFornecedorLabel.setFont(Font.font("Arial", 20));
        formGrid.add(nomeFornecedorLabel, 0, 4);
        TextField fornecedorField = new TextField();
        formGrid.add(fornecedorField, 1, 4,4,1);

        
        Label cepLabel=new Label("Cep:");
        cepLabel.setFont(Font.font("Arial", 20));
        formGrid.add(cepLabel, 0, 5);
        TextField cepField = new TextField();
        formGrid.add(cepField, 1, 5,3,1);
        
        Button procurarButton = new Button("Procurar");
        formGrid.add(procurarButton, 4, 5,2,1);
        
        
        
        Label nomeRuaLabel=new Label("Nome Rua:");
        nomeRuaLabel.setFont(Font.font("Arial", 20));
        formGrid.add(nomeRuaLabel, 0,6);
        TextField nomeRuaField = new TextField();
        nomeRuaField.setDisable(true);
        formGrid.add(nomeRuaField, 1,6, 3, 1);

       
        Label nomeBairroLabel=new Label("Bairro:");
        nomeBairroLabel.setFont(Font.font("Arial", 20));
        formGrid.add(nomeBairroLabel, 0, 7);
        TextField bairroField = new TextField();
        bairroField.setDisable(true);
        formGrid.add(bairroField, 1,7);

        
        Label cidadeLabel=new Label("Cidade:");
        cidadeLabel.setFont(Font.font("Arial", 20));
        formGrid.add(cidadeLabel, 2, 7);
        TextField cidade = new TextField();
        cidade.setDisable(true);
        formGrid.add(cidade, 3, 7);
        
        
        Label estadoLabel=new Label("Estado:");
        estadoLabel.setFont(Font.font("Arial", 20));
        formGrid.add(estadoLabel, 0, 8);
        TextField estado = new TextField();
        estado.setDisable(true);
        formGrid.add(estado, 1, 8);

        
        Label numeroLabel=new Label("Numero:");
        numeroLabel.setFont(Font.font("Arial", 20));
        formGrid.add(numeroLabel, 2, 8);
        TextField numero = new TextField();
        formGrid.add(numero, 3, 8, 1, 1);
        
        Label telefoneLabel=new Label("Telefone:");
        telefoneLabel.setFont(Font.font("Arial", 20));
        formGrid.add(telefoneLabel, 0, 9);
        TextField telField = new TextField();
        formGrid.add(telField, 1, 9, 2, 1);
    
   
        Line linhaFinal = new Line();
        linhaFinal.setStartX(0);  // Posição inicial no eixo X (esquerda)
        linhaFinal.setEndX(750);  // Posição final no eixo X (direita)
        linhaFinal.setStartY(100); // Posição inicial no eixo Y
        linhaFinal.setEndY(100);   // Mantendo a posição Y constante para ser horizontal
        
        // Definindo a cor e espessura da linha
        linhaFinal.setStroke(Color.BLACK);
        linhaFinal.setStrokeWidth(2);
        formGrid.add(linhaFinal, 0, 10,6,1);
        
     
        // Botões de Ação
        Button cancelarButton = new Button("Cancelar");
        cancelarButton.setStyle("-fx-background-radius: 15;"
        		+               "-fx-border-radius: 10;"
        		+                "-fx-pref-width: 150px;"
        		+                "-fx-pref-height: 40px;"
        		+                "-fx-font-size: 16px;"
        		+                "-fx-text-wrap: wrap;");
        
        
        cancelarButton.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                	stage.close();
                	new FornecedorMenu().start(stage);
            	
            }
        });
        
        Button cadastrarButton = new Button("Cadastrar");
        cadastrarButton.setStyle("-fx-background-radius: 15;"
        		+               "-fx-border-radius: 10;"
        		+                "-fx-pref-width: 150px;"
        		+                "-fx-pref-height: 40px;"
        		+                "-fx-font-size: 16px;"
        		+                "-fx-text-wrap: wrap;");
        
        cadastrarButton.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	FornecedorRepository fnrp=new FornecedorRepository();
            	Fornecedor fn=new Fornecedor();
            	if(opcCPF.isSelected()) {
            		fn.setTipoDoc("CPF");
            	}
            	else {
            		fn.setTipoDoc("CNPJ");
            	}
                fn.setBairro(bairroField.getText());
                fn.setCep(cepField.getText());
                fn.setCidade(cidade.getText());
                fn.setEstado(estado.getText());
                fn.setNome(fornecedorField.getText());
                fn.setNumDoc(NumeroDocField.getText());
                fn.setNumEndereco(numero.getText());
                fn.setRua(nomeRuaField.getText());
                fn.setTelefone(telField.getText());
                
                List<Fornecedor> fnn=fnrp.buscarPorNumDoc(NumeroDocField.getText());
        		if(fnn.isEmpty()) {
        			String resultado=fnrp.gavar(fn);
        			if(resultado.equalsIgnoreCase("ok")) {
        				Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Informação");
                        alert.setHeaderText("Sucesso");
                        alert.setContentText("Fornecedor Cadastrado!");
                        // Exibe o alerta e espera o usuário fechar
                        alert.showAndWait();
        			}
        			else {
        				Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Informação");
                        alert.setHeaderText("Erro");
                        alert.setContentText("Verifique os dados e tente Novamente!");
                        // Exibe o alerta e espera o usuário fechar
                        alert.showAndWait();
        			}
        			
        		}
        		else{
        			Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Informação");
                    alert.setHeaderText("Erro");
                    alert.setContentText("Fornecedor já Cadastrado!");
                    // Exibe o alerta e espera o usuário fechar
                    alert.showAndWait();
        		}
                
               
            }
        });

        HBox actionButtons = new HBox(10, cancelarButton, cadastrarButton);
        actionButtons.setAlignment(Pos.CENTER);
        actionButtons.setPadding(new Insets(10));
        
        procurarButton.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	cepModel cepModel=CepAPI.getDataFromURL(cepField.getText());
            	if(cepModel!=null) {
            		 nomeRuaField.setText(cepModel.getLogradouro());
            		 bairroField.setText(cepModel.getBairro());
            		 cidade.setText(cepModel.getLocalidade());
            		 estado.setText(cepModel.getEstado());
            		 
            	}
            	else {
            		Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Informação");
                    alert.setHeaderText("Erro");
                    alert.setContentText("Verifique os dados novamente");
                    // Exibe o alerta e espera o usuário fechar
                    alert.showAndWait();
            	}
            }
        });
        
        
        HBox CenterBox = new HBox(10,formGrid);
        CenterBox.setAlignment(Pos.CENTER);
        
        
        // Layout principal
        VBox mainLayout = new VBox(10, headerBox, CenterBox, actionButtons);
        mainLayout.setAlignment(Pos.TOP_CENTER);
        mainLayout.setStyle("-fx-background-color: #F0F0F0;"); // Fundo cinza claro
        mainLayout.setPadding(new Insets(10));

        // Configuração da Cena
        Scene scene = new Scene(mainLayout, 600, 400);
        stage.setTitle("Entrada de Produtos");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }
    

    public static void main(String[] args) {
        launch(args);
    }
}
