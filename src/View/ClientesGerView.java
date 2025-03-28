package View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import Model.cliente;
import Model.enderecoCliente;
import Model.produtoSaida;
import Model.produtosEntrada;
import Model.saidaProdutoModel;
import Model.telefonesClientes;
import Repository.cadClienteRepository;
import Repository.cadEndClienteRepository;
import Repository.produtoEntradaRepository;
import Repository.produtosSaidaRepository;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ClientesGerView extends Application {
    private TableView<cliente> tableView;
    private ObservableList<cliente> resultado;


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Estoque");

        GridPane gridpane = new GridPane();
    	gridpane.setPadding(new Insets(20));
        
        // Criando RadioButtons para opções
        RadioButton listarTodos = new RadioButton("Listar Todos");
        RadioButton buscarPorNome = new RadioButton("Buscar Por Nome");
              ToggleGroup buscarGroup = new ToggleGroup();
        listarTodos.setToggleGroup(buscarGroup);
        buscarPorNome.setToggleGroup(buscarGroup);
        gridpane.add(listarTodos, 0, 0);
        gridpane.add(buscarPorNome, 1, 0);
       
        // Criar um ChoiceBox e adicionar opções
        Label statusLabel=new Label("Status");
        ChoiceBox<String> status = new ChoiceBox<>();
        status.getItems().addAll("Ativo", "Inativo","Ambos");
        status.setValue("Ativo");
        gridpane.add(statusLabel, 0, 1);
        gridpane.add(status, 1, 1);
       
        
        Label nomeLabel=new Label();
        TextField nomeField = new TextField();
        nomeField.setVisible(false);
        
        listarTodos.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	nomeField.setVisible(false);
            	nomeLabel.setVisible(false);
            		 
         }});
        buscarPorNome.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	nomeField.setVisible(true);
            	nomeLabel.setText("Nome: ");
            	nomeLabel.setVisible(true);
            		 
         }});
        
        Button buscarButton = new Button("Buscar");

        
        resultado= FXCollections.observableArrayList();
        //resultado.setAll(null);
        
        // Criando a TableView
        tableView = new TableView<>();
        tableView.setItems(resultado);
        
        
        TableColumn<cliente, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
     
        buscarButton.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if(listarTodos.isSelected()) {
            		cadClienteRepository prrp=new cadClienteRepository();
            		List<cliente> produtos=prrp.ListarTodos();
            		resultado.clear();
            		resultado.addAll(produtos);
            		tableView.setItems(resultado);
            		
            	}
            	
            	else{
            		cadClienteRepository prrp=new cadClienteRepository();
            		List<cliente> produtos=prrp.buscarPorPartNome(nomeField.getText());
            		resultado.clear();
            		resultado.addAll(produtos);
            		tableView.setItems(resultado);
            	}
            		 
         }});
        
        
        TableColumn<cliente, String> colLote = new TableColumn<>("Cod Cliente");
        colLote.setCellValueFactory(new PropertyValueFactory<>("Clienteid"));
        
        TableColumn<cliente, String> colPrecCompra = new TableColumn<>("Tipo Doc");
        colPrecCompra.setCellValueFactory(new PropertyValueFactory<>("tipoDoc"));
        
        TableColumn<cliente, String> colPrecVenda = new TableColumn<>("Numero Doc");
        colPrecVenda.setCellValueFactory(new PropertyValueFactory<>("numDoc"));
        
        TableColumn<cliente, String> colQuantEntrada = new TableColumn<>("Possui Credito");
        colQuantEntrada.setCellValueFactory(new PropertyValueFactory<>("credito"));
        
        TableColumn<cliente, String> colQuantDisponivel = new TableColumn<>("Valor Do Credito");
        colQuantDisponivel.setCellValueFactory(new PropertyValueFactory<>("valorCredito"));

        TableColumn<cliente, Void> visualizarColuna = new TableColumn<>("");
        visualizarColuna.setCellFactory(param -> criarBotaoVisualizar());
        
        TableColumn<cliente, Void> editarColuna = new TableColumn<>("");
        editarColuna.setCellFactory(param -> criarBotaoEditar());

        TableColumn<cliente, Void> apagarColuna = new TableColumn<>("");
        apagarColuna.setCellFactory(param -> criarBotaoApagar());
        
        tableView.getColumns().addAll(colNome, colLote,colPrecCompra,colPrecVenda,colQuantEntrada,colQuantDisponivel,visualizarColuna,editarColuna,apagarColuna);

        

        // Configurando layout dos RadioButtons e campos de busca
        HBox buscarBox = new HBox(10, listarTodos, buscarPorNome);
        HBox buscarArea = new HBox(10, nomeLabel, nomeField, buscarButton);
        buscarBox.setPadding(new Insets(10));
        buscarArea.setPadding(new Insets(10));


        Button voltarButton = new Button("Voltar");
        voltarButton.setOnAction(e -> primaryStage.close());
        
        // Layout principal
        BorderPane root = new BorderPane();
        root.setTop(new VBox(buscarBox, gridpane, buscarArea));
        root.setCenter(tableView);
        

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    
    
    
    private TableCell<cliente, Void> criarBotaoEditar() {
        return new TableCell<>() {
            private final Button editarButton = new Button("Editar");

            {
                editarButton.setOnAction(event -> {
                	cliente registro = getTableView().getItems().get(getIndex());
                    mostrarPaginaEdicao(registro);
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
    
    private void mostrarPaginaEdicao(cliente registro) {
        Stage editStage = new Stage();
        editStage.setTitle("Editar Produto");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
    
        TextField nomeField = new TextField(String.valueOf(registro.getNome())); 
        
        RadioButton opcCPF = new RadioButton("CPF");
        opcCPF.setFont(Font.font("Arial", 16));
        RadioButton opcCNPJ = new RadioButton("CNPJ");
        opcCNPJ.setFont(Font.font("Arial", 16));
        ToggleGroup grupo01 = new ToggleGroup();
        HBox opcBox=new HBox(opcCPF,opcCNPJ);
        
        if(registro.getTipoDoc().equalsIgnoreCase("cp")) {
        	opcCPF.setSelected(true);

        }
        else {
        	opcCNPJ.setSelected(true);
        }
       
        TextField numDocField = new TextField(String.valueOf(registro.getNumDoc()));
        
        RadioButton simcredito = new RadioButton("Sim");
        simcredito.setFont(Font.font("Arial", 16));
        RadioButton naoCredito = new RadioButton("Não");
        naoCredito.setFont(Font.font("Arial", 16));
        ToggleGroup grupo02 = new ToggleGroup();
        HBox opcBox02=new HBox(simcredito,naoCredito);
        
        if(registro.isCredito()==true) {
        	simcredito.setSelected(true);

        }
        else {
        	naoCredito.setSelected(true);
        }    
        TextField valorField = new TextField(String.valueOf(registro.getValorCredito()));
        
        Button saveButton = new Button("Salvar");
        saveButton.setOnAction(event -> {
        	
        	boolean credito;
     	    String tipo;
     	    if(!simcredito.getText().isEmpty()) {
     	    	credito=true;
     	    }
     	    else {
     	    	credito=false;            	    
     	    	}
     	    //

        	
    	    if(!opcCPF.getText().isEmpty()) {
    	    	tipo=opcCPF.getText();
    	    }
    	    else {
    	    	tipo=opcCNPJ.getText();          	    
    	    }
    		cadClienteRepository prrp=new cadClienteRepository();
    		registro.setNome(nomeField.getText());
            registro.setCredito(credito);
            registro.setNumDoc(numDocField.getText());
            registro.setTipoDoc(tipo);
            registro.setValorCredito(Double.parseDouble(valorField.getText()));
    		
    		
            String n=prrp.update(registro); // método para atualizar no repositório
            System.out.println(n);
            System.out.println("okkkk");
            tableView.refresh(); // Atualiza a TableView
            editStage.close();   // Fecha a janela de edição
        });

        Button cancelButton = new Button("Cancelar");
        cancelButton.setOnAction(event -> editStage.close());
        
        layout.getChildren().addAll(
            	new Label("Nome:"), nomeField,
                new Label("Tipo Doc"), opcBox,
                new Label("Numero Doc"), numDocField,
                new Label("Possui Credito?"), opcBox02,
                new Label("Valor:"), valorField,
                saveButton,
                cancelButton
                
            );

        Scene scene = new Scene(layout);
        editStage.setScene(scene);
        editStage.initModality(Modality.APPLICATION_MODAL);
        editStage.showAndWait();
    }

    
    // Método para criar o botão de apagar em cada linha
    private TableCell<cliente, Void> criarBotaoApagar() {
        return new TableCell<>() {
            private final Button apagarButton = new Button("Apagar");

            {
                apagarButton.setOnAction(e -> {
                	cliente registro = getTableView().getItems().get(getIndex());
                	cadClienteRepository prrp=new cadClienteRepository();
                	 Alert alert = new Alert(AlertType.CONFIRMATION);
                	    alert.setTitle("Confirmação");
                	    alert.setHeaderText(null); 
                	    alert.setContentText("Deseja apagar o registro?");
                	    
                	    // Exibe o alerta e espera o usuário fechar
                	    alert.showAndWait().ifPresent(response -> {
                	        if (response == ButtonType.OK) {
                	        	prrp.deletar(registro);
                            	resultado.remove(registro);
                	        } else {
                	        	alert.close();
                	        }
                	    });
                	
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

 // Método para criar o botão de apagar em cada linha
    private TableCell<cliente, Void> criarBotaoVisualizar() {
        return new TableCell<>() {
            private final Button apagarButton = new Button("Visualizar");

            {
                apagarButton.setOnAction(e -> {
                	cliente registro = getTableView().getItems().get(getIndex());
                	mostrarPaginaVisualizar(registro);
                	
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
    
    
    private void mostrarPaginaVisualizar(cliente registro) {
        Stage editStage = new Stage();
        editStage.setTitle("Editar Produto");

        BorderPane bdp=new BorderPane();
        
        TextField nomeField = new TextField(String.valueOf(registro.getNome()));
        nomeField.setDisable(true);
        TextField codigoField = new TextField(String.valueOf(registro.getClienteid()));
        codigoField.setDisable(true);
        TextField tipoDocField = new TextField(String.valueOf(registro.getTipoDoc()));
        tipoDocField.setDisable(true);
        TextField numeroDocField = new TextField(String.valueOf(registro.getNumDoc()));
        numeroDocField.setDisable(true);
        TextField creditoField = new TextField(String.valueOf(registro.isCredito()));
        creditoField.setDisable(true);
        TextField valorField = new TextField(String.valueOf(registro.getValorCredito()));
        valorField.setDisable(true);
        
        cadEndClienteRepository rp=new cadEndClienteRepository();
        List<enderecoCliente> cli=rp.buscarPorId(registro.getClienteid());
        ListView<String> listViewTel = new ListView<>();
        ObservableList<String> telStrings = FXCollections.observableArrayList();
        for (enderecoCliente tel : cli) {
        	telStrings.add("Cep:"+tel.getCep()+"| Bairro:"+tel.getBairro()+"| Cidade: "+tel.getCidade()+ "| Estado:"+tel.getEstado()+ "| Rua:"+tel.getRua());
        }
        listViewTel.setItems(telStrings);
        
        Button cancelButton = new Button("Voltar");
        cancelButton.setOnAction(event -> editStage.close());

        
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10));
        layout.add(new Label("Nome:"), 0, 0);
        layout.add(nomeField, 1, 0);
        layout.add(new Label("Tipo Documento:"), 0, 2);
        layout.add(tipoDocField, 1, 2);
        layout.add(new Label("Cod Cliente:"), 0, 1);
        layout.add(codigoField, 1, 1);
        layout.add(new Label("Numero Doc:"), 0, 3);
        layout.add(numeroDocField, 1, 3);
        layout.add(new Label("Possui Credito:"), 0, 4);
        layout.add(creditoField, 1, 4);
        layout.add(new Label("valor Credito:"), 0, 5);
        layout.add(valorField, 1, 5);
        
        
        VBox topLayout = new VBox(10);
        topLayout.setPadding(new Insets(10));
        topLayout.getChildren().addAll(listViewTel);
        topLayout.setAlignment(Pos.CENTER_LEFT);
        
        HBox bottonLayout = new HBox(10);
        bottonLayout.setPadding(new Insets(10));
        bottonLayout.getChildren().addAll(cancelButton);
        bottonLayout.setAlignment(Pos.CENTER_LEFT);

        bdp.setTop(layout);
        bdp.setCenter(topLayout);
        bdp.setBottom(bottonLayout);

        
        ScrollPane sc=new ScrollPane(bdp);
        Scene scene = new Scene(sc);
        editStage.setScene(scene);
        editStage.initModality(Modality.APPLICATION_MODAL);
        editStage.showAndWait();
    }
   
}


