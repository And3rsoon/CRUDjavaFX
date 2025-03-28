package View;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.shape.Line;
import Model.cepModel;
import Model.cliente;
import Model.enderecoCliente;
import Model.produto;
import Model.produtosEntrada;
import Model.telefonesClientes;
import Repository.cadClienteRepository;
import Repository.cadEndClienteRepository;
import Repository.cadTelClienteRepository;
import Service.CepAPI;
import jakarta.persistence.Column;

import java.time.LocalDate;



public class CadastroClientes extends Application {

	private TextField nomeField;
    private TextField cpfField;
    private DatePicker dataEntrada,DataDeligamento;
    private LocalDate dataVencimentoValue;
    private boolean credito;
    private double valorCredito;
    private TableView<enderecoCliente> tableViewEndereco;
    private ObservableList<enderecoCliente> enderecosObs;
    private TableView<telefonesClientes> tableViewtelefones;
    private ObservableList<telefonesClientes> telefoneObs;
    
    
    @Override
    public void start(Stage stage) {
        // Cabeçalho
        Text header = new Text("Cadastro Clientes");
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
        
        
        Label nomeClienteLabel=new Label("Nome:");
        nomeClienteLabel.setFont(Font.font("Arial", 20));
        formGrid.add(nomeClienteLabel, 0, 4);
        TextField nomeClienteField = new TextField();
        formGrid.add(nomeClienteField, 1, 4,4,1);

        
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
        
        Label unidadeLabel=new Label("Nome Endereço:");
        unidadeLabel.setFont(Font.font("Arial", 20));
        formGrid.add(unidadeLabel, 4, 8);
        TextField nomeEndField = new TextField();
        formGrid.add(nomeEndField, 5, 8, 1, 1);
        
        
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
        
        
        enderecosObs = FXCollections.observableArrayList();
        // Botão "Adicionar Novo"
        Button adicionarNovoButton =  new Button("Adicionar Endereço");
        formGrid.add(adicionarNovoButton, 6, 8);
        adicionarNovoButton.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
	            	enderecoCliente pre=new enderecoCliente();
	            	pre.setBairro(bairroField.getText());
	            	pre.setCep(cepField.getText());
	            	pre.setCidade(cidade.getText());
	            	pre.setNumero(numero.getText());
	            	pre.setRua(nomeRuaField.getText());
	            	pre.setEstado(estado.getText());
	            	pre.setNome(nomeEndField.getText());
	            	enderecosObs.add(pre);
	            	tableViewEndereco.setItems(enderecosObs);
	            	bairroField.clear();
	            	cepField.clear();
	            	cidade.clear();
	            	numero.clear();
	            	nomeRuaField.clear();
	            	estado.clear();
	            	nomeEndField.clear();
	            	
                    // Atualiza a exibição na TableView
                    //tableView.setItems(registros);
         }});
        //Criação e adição do tableView para listar Endereços Clientes
        
        
        tableViewEndereco = new TableView<>();
        tableViewEndereco.setItems(enderecosObs);
        tableViewEndereco.setMinHeight(300);

        // Colunas da TableView
        TableColumn<enderecoCliente, String> nomeColuna = new TableColumn<>("Nome");
        nomeColuna.setCellValueFactory(new PropertyValueFactory<>("nome"));
        nomeColuna.setPrefWidth(300);
        
        TableColumn<enderecoCliente, String> est = new TableColumn<>("Estado");
        est.setCellValueFactory(new PropertyValueFactory<>("estado"));
        est.setPrefWidth(200);
        
        
        TableColumn<enderecoCliente, String> cid = new TableColumn<>("Cidade");
        cid.setCellValueFactory(new PropertyValueFactory<>("cidade"));
        cid.setPrefWidth(200);
        
        TableColumn<enderecoCliente, String> Bairro = new TableColumn<>("Bairro");
        Bairro.setCellValueFactory(new PropertyValueFactory<>("bairro"));
        Bairro.setPrefWidth(200);
        
        TableColumn<enderecoCliente, String> Rua = new TableColumn<>("Rua");
        Rua.setCellValueFactory(new PropertyValueFactory<>("rua"));
        Rua.setPrefWidth(200);
        
        TableColumn<enderecoCliente, String> Cep = new TableColumn<>("Cep");
        Cep.setCellValueFactory(new PropertyValueFactory<>("cep"));
        Cep.setPrefWidth(200);
        
        TableColumn<enderecoCliente, String> Numero = new TableColumn<>("Numero");
        Numero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        Numero.setPrefWidth(200);
        
        
        TableColumn<enderecoCliente, Void> editarColuna = new TableColumn<>("Editar");
        editarColuna.setCellFactory(param -> criarBotaoEditar());

        TableColumn<enderecoCliente, Void> apagarColuna = new TableColumn<>("Apagar");
        apagarColuna.setPrefWidth(200);
        apagarColuna.setCellFactory(param -> criarBotaoApagar());

        // Adicionando colunas na TableView
        tableViewEndereco.getColumns().addAll(nomeColuna,cid,Bairro,Rua,Cep, Numero, apagarColuna);
        //fim do tableview
        
        
        Label telefoneLabel=new Label("Telefone:");
        telefoneLabel.setFont(Font.font("Arial", 20));
        formGrid.add(telefoneLabel, 0, 9);
        TextField telField = new TextField();
        formGrid.add(telField, 1, 9);
        
        Label nomeTelLabel=new Label("Nome Resp:");
        nomeTelLabel.setFont(Font.font("Arial", 20));
        formGrid.add(nomeTelLabel, 2, 9);
        TextField nometelField = new TextField();
        formGrid.add(nometelField, 3, 9);
        
        Label cargoLabel=new Label("Cargo Resp:");
        cargoLabel.setFont(Font.font("Arial", 20));
        formGrid.add(cargoLabel, 4, 9);
        TextField cargoField = new TextField();
        formGrid.add(cargoField, 5, 9);
        
        
        
        telefoneObs = FXCollections.observableArrayList();
        tableViewtelefones = new TableView<>();
        tableViewtelefones.setItems(telefoneObs);
        tableViewtelefones.setMinHeight(300);

       
        // Colunas da TableView
        TableColumn<telefonesClientes, String> numColuna = new TableColumn<>("Numero");
        numColuna.setCellValueFactory(new PropertyValueFactory<>("numero"));
        numColuna.setPrefWidth(300);
        
        TableColumn<telefonesClientes, String> nomREsp = new TableColumn<>("Nome Responsável");
        nomREsp.setCellValueFactory(new PropertyValueFactory<>("nomeResponsavel"));
        nomREsp.setPrefWidth(200);
        
        
        TableColumn<telefonesClientes, String> cargo = new TableColumn<>("Cargo");
        cargo.setCellValueFactory(new PropertyValueFactory<>("cargoResponsavel"));
        cargo.setPrefWidth(200);
        
        TableColumn<telefonesClientes, Void> apagarColunaTel = new TableColumn<>("Apagar");
        apagarColunaTel.setPrefWidth(200);
        apagarColunaTel.setCellFactory(param -> criarBotaoApagarTel());

        // Adicionando colunas na TableView
        tableViewtelefones.getColumns().addAll(nomREsp,cargo,numColuna,apagarColunaTel);
        //fim do tableview
        
        
        Button adicionarTel =  new Button("Adicionar Telefone");
        formGrid.add(adicionarTel, 6, 9);
        adicionarTel.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
	            	telefonesClientes tel=new telefonesClientes();
	            	tel.setNumero(telField.getText());
	            	tel.setCargoResponsavel(cargoField.getText());
	            	tel.setNomeResponsavel(nometelField.getText());
	            	telefoneObs.add(tel);
	            	tableViewtelefones.setItems(telefoneObs);	
	            	telField.clear();
	            	cargoField.clear();
	            	nometelField.clear();
	            	
            }
        });
        
        
        Label creditoLabel=new Label("Credito:");
        creditoLabel.setFont(Font.font("Arial", 20));
        formGrid.add(creditoLabel, 0, 10);
        // Criar um ChoiceBox e adicionar opções
        ChoiceBox<String> creditoBox = new ChoiceBox<>();
        creditoBox.getItems().addAll("Sim", "Não");
        creditoBox.setValue("");
        formGrid.add(creditoBox, 1, 10);
        
        Label valorCredLabel=new Label("Valor Credito:");
        valorCredLabel.setFont(Font.font("Arial", 20));
        formGrid.add(valorCredLabel, 2, 10);
        TextField valorCredField = new TextField();
        formGrid.add(valorCredField, 3, 10);
        
        
        Label dataEntradaLabel=new Label("Data Registro:");
        dataEntradaLabel.setFont(Font.font("Arial", 20));
        formGrid.add(dataEntradaLabel, 4, 10);
        dataEntrada=new DatePicker();
        dataEntrada.setPromptText("Data Registro:");
        formGrid.add(dataEntrada, 5, 10);
        
        
        Label statusLabel=new Label("Status:");
        statusLabel.setFont(Font.font("Arial", 20));
        formGrid.add(statusLabel, 6, 10);
        // Criar um ChoiceBox e adicionar opções
        ChoiceBox<String> statusBox = new ChoiceBox<>();
        statusBox.getItems().addAll("Ativo", "Encerrado");
        statusBox.setValue("");
        formGrid.add(statusBox, 7, 10);
        
   
        Line linhaFinal = new Line();
        linhaFinal.setStartX(0);  // Posição inicial no eixo X (esquerda)
        linhaFinal.setEndX(750);  // Posição final no eixo X (direita)
        linhaFinal.setStartY(100); // Posição inicial no eixo Y
        linhaFinal.setEndY(100);   // Mantendo a posição Y constante para ser horizontal
        
        // Definindo a cor e espessura da linha
        linhaFinal.setStroke(Color.BLACK);
        linhaFinal.setStrokeWidth(2);
        formGrid.add(linhaFinal, 0, 11,6,1);
        
     
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
            	    cadClienteRepository  clrp=new cadClienteRepository();  
            	    cadEndClienteRepository edrp=new cadEndClienteRepository();
            	    cadTelClienteRepository tlrp=new cadTelClienteRepository();
            	    String valor;
            	    if(!opcCPF.getText().isEmpty()) {
            	    	valor=opcCPF.getText();
            	    }
            	    else {
            	    	valor=opcCNPJ.getText();            	    
            	    	}
            	    boolean credito;
            	    double valorCred;
            	    if(creditoBox.getValue().equalsIgnoreCase("sim")) {
            	    	credito=true;
            	    	valorCred=Double.parseDouble(valorCredField.getText());
            	    }
            	    else {
            	    	credito=false;
            	    	valorCred=0;
            	    }
            	    //
            	    
            	    if( valor!=null &&  NumeroDocField.getText() !=null && nomeClienteField!=null 
            	    		&& statusBox.getValue()!=null	) {
		            	    cliente cl=new cliente();
		            	    cl.setCredito(credito);
		            	    cl.setNome(nomeClienteField.getText());
		            	    cl.setNumDoc(NumeroDocField.getText());
		            	    cl.setTipoDoc(valor);
		            	    cl.setValorCredito(valorCredito);
		            	    clrp.gavar(cl);
		            	    
		            	    if(!telefoneObs.isEmpty()) {
		            	    	telefoneObs.forEach(obj->{obj.setCliente(cl);
		            	    						tlrp.gavar(obj);}

		            	    			);
		            	    }
		            	    if(!enderecosObs.isEmpty()) {
		            	    	enderecosObs.forEach(obj->{obj.setCliente(cl);
	    						edrp.gavar(obj);}
		            	    	);
		            	    	
		            	    }
		            	    telefoneObs.clear();
		            	    enderecosObs.clear();
		            	    nomeClienteField.clear();
		            	    NumeroDocField.clear();
		            	    tableViewtelefones.setItems(telefoneObs);
		            	    tableViewEndereco.setItems(enderecosObs);
		            	    Alert alert = new Alert(AlertType.INFORMATION);
		                    alert.setTitle("Informação");
		                    alert.setHeaderText("Cadastrado");
		                    alert.setContentText("Cliente Cadastrado!");
		                    // Exibe o alerta e espera o usuário fechar
		                    alert.showAndWait();
            	    }
            	    else {
		            		Alert alert = new Alert(AlertType.INFORMATION);
		                    alert.setTitle("Informação");
		                    alert.setHeaderText("Erro");
		                    alert.setContentText("Os campos Tipo Doc,numero e nome são obrigatórios!");
		                    // Exibe o alerta e espera o usuário fechar
		                    alert.showAndWait();
            	    }
            }
        });

        HBox actionButtons = new HBox(10, cancelarButton, cadastrarButton);
        actionButtons.setAlignment(Pos.CENTER);
        actionButtons.setPadding(new Insets(10));
        
        
        HBox CenterBox = new HBox(10,formGrid);
        CenterBox.setAlignment(Pos.CENTER);
        
        
        // Layout principal
        VBox mainLayout = new VBox(10, headerBox, CenterBox,tableViewEndereco,tableViewtelefones, actionButtons);
        mainLayout.setAlignment(Pos.TOP_CENTER);
        mainLayout.setStyle("-fx-background-color: #F0F0F0;"); // Fundo cinza claro
        mainLayout.setPadding(new Insets(10));

        // Adicionar o VBox em um ScrollPane
        ScrollPane scrollPane = new ScrollPane(mainLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        // Configuração da Cena
        Scene scene = new Scene(scrollPane, 600, 400);
        stage.setTitle("Entrada de Produtos");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }
    
    
    // Método para criar o botão de editar em cada linha
    private TableCell<enderecoCliente, Void> criarBotaoEditar() {
        return new TableCell<>() {
            private final Button editarButton = new Button("Editar");

            {
                editarButton.setOnAction(e -> {
                	enderecoCliente registro = getTableView().getItems().get(getIndex());
     
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(editarButton);
                }
            }
        };
    }

    // Método para criar o botão de apagar em cada linha
    private TableCell<enderecoCliente, Void> criarBotaoApagar() {
        return new TableCell<>() {
            private final Button apagarButton = new Button("Apagar");

            {
                apagarButton.setOnAction(e -> {
                	enderecoCliente registro = getTableView().getItems().get(getIndex());
                	enderecosObs.remove(registro);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(apagarButton);
                }
            }
        };
    }
    
 // Método para criar o botão de editar em cada linha
    private TableCell<telefonesClientes, Void> criarBotaoEditarTel() {
        return new TableCell<>() {
            private final Button editarButton = new Button("Editar");

            {
                editarButton.setOnAction(e -> {
                	telefonesClientes registro = getTableView().getItems().get(getIndex());
                    
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(editarButton);
                }
            }
        };
    }

    // Método para criar o botão de apagar em cada linha
    private TableCell<telefonesClientes, Void> criarBotaoApagarTel() {
        return new TableCell<>() {
            private final Button apagarButton = new Button("Apagar");

            {
                apagarButton.setOnAction(e -> {
                	telefonesClientes registro = getTableView().getItems().get(getIndex());
                    
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(apagarButton);
                }
            }
        };
    }
    

    public static void main(String[] args) {
        launch(args);
    }
}