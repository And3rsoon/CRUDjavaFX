package View;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.poi.xwpf.usermodel.*;
import Model.produtosEntrada;
import Repository.produtoEntradaRepository;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class estoqueView extends Application {
    private TableView<produtosEntrada> tableView;
    private  TableView<Object[]> tableViewUnificado;
    private ObservableList<produtosEntrada> resultado;
    private ObservableList<Object[]> resultadoUnificado;


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Estoque");

        GridPane gridpane = new GridPane();
    	gridpane.setPadding(new Insets(20));
        
        // Criando RadioButtons para opções
        RadioButton listarTodos = new RadioButton("Listar Todos");
        RadioButton buscarPorNome = new RadioButton("Buscar Por Nome");
        RadioButton buscarPorCodigoBarra = new RadioButton("Buscar Por Codigo Barra");
        ToggleGroup buscarGroup = new ToggleGroup();
        listarTodos.setToggleGroup(buscarGroup);
        buscarPorNome.setToggleGroup(buscarGroup);
        buscarPorCodigoBarra.setToggleGroup(buscarGroup);
        gridpane.add(listarTodos, 0, 0);
        gridpane.add(buscarPorNome, 1, 0);
        gridpane.add(buscarPorCodigoBarra, 2, 0);
        
        // Criar um ChoiceBox e adicionar opções
        Label statusLabel=new Label("Status");
        ChoiceBox<String> status = new ChoiceBox<>();
        status.getItems().addAll("Ativo", "Inativo","Ambos");
        status.setValue("Ativo");
        gridpane.add(statusLabel, 0, 1);
        gridpane.add(status, 1, 1);
       
        // Criar um ChoiceBox e adicionar opções
        Label formaLabel=new Label("Forma: ");
        ChoiceBox<String> formaBox = new ChoiceBox<>();
        formaBox.getItems().addAll("Lote", "Unificado");
        formaBox.setValue("");
        gridpane.add(formaLabel, 2, 1);
        gridpane.add(formaBox, 3, 1);
               
        
       

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
        buscarPorCodigoBarra.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	nomeLabel.setText("Cod Barras: ");
            	nomeLabel.setVisible(true);
            		 
         }});
        
        
        
        Button buscarButton = new Button("Buscar");

        resultadoUnificado=FXCollections.observableArrayList();
        resultado= FXCollections.observableArrayList();
        //resultado.setAll(null);
        
        // Criando a TableView
        tableView = new TableView<>();
        tableView.setItems(resultado);
        TableColumn<produtosEntrada, String> colNome = new TableColumn<>("Nome");
        /// Definindo o CellFactory para exibir o nome do produto corretamente
        colNome.setCellFactory(column -> new TableCell<produtosEntrada, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                
                if (empty) {
                    setText(null);
                } else {
                    // Obtém o item no índice atual da TableView
                    produtosEntrada registro = getTableView().getItems().get(getIndex());
                    if (registro != null && registro.getProduto() != null) {
                        setText(registro.getProduto().getNomeProduto());
                    } else {
                        setText(null);
                    }
                }
            }
        });
		TableColumn<produtosEntrada, String> colLote = new TableColumn<>("Lote");
        colLote.setCellValueFactory(new PropertyValueFactory<>("produtosId"));
        
        TableColumn<produtosEntrada, String> colPrecCompra = new TableColumn<>("Preço Compra");
        colPrecCompra.setCellValueFactory(new PropertyValueFactory<>("precoCompra"));
        
        TableColumn<produtosEntrada, String> colPrecVenda = new TableColumn<>("Preço Venda");
        colPrecVenda.setCellValueFactory(new PropertyValueFactory<>("precoVenda"));
        
        TableColumn<produtosEntrada, String> colQuantEntrada = new TableColumn<>("Quantidade Entrada");
        colQuantEntrada.setCellValueFactory(new PropertyValueFactory<>("quantEntrada"));
        
        TableColumn<produtosEntrada, String> colQuantDisponivel = new TableColumn<>("Quantidade Atual");
        colQuantDisponivel.setCellValueFactory(new PropertyValueFactory<>("quantEstoque"));
        
        TableColumn<produtosEntrada, LocalDate> colDataValidade = new TableColumn<>("Validade");
        // Configurando a coluna para pegar o valor da propriedade "dataVal" de produtosEntrada
        colDataValidade.setCellValueFactory(new PropertyValueFactory<>("dataVal"));
        // Definindo o `CellFactory` para formatar e exibir a data
        colDataValidade.setCellFactory(column -> new TableCell<produtosEntrada, LocalDate>() {
         private DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");

         @Override
         protected void updateItem(LocalDate dataValidade, boolean empty) {
             super.updateItem(dataValidade, empty);
             
             // Verifica se a célula está vazia ou se o valor é nulo
             if (empty || dataValidade == null) {
                 setText(null);
             } else {
                 // Formata e exibe a data como string no formato "dd/MM/yyyy"
                 setText(dataValidade.format(format));
             }
         }
     });
        TableColumn<produtosEntrada, Void> visualizarColuna = new TableColumn<>("");
        visualizarColuna.setCellFactory(param -> criarBotaoVisualizar());
        
        TableColumn<produtosEntrada, Void> editarColuna = new TableColumn<>("");
        editarColuna.setCellFactory(param -> criarBotaoEditar());

        TableColumn<produtosEntrada, Void> apagarColuna = new TableColumn<>("");
        apagarColuna.setCellFactory(param -> criarBotaoApagar());
        
        tableView.getColumns().addAll(colNome, colLote,colPrecCompra,colPrecVenda,colQuantEntrada,colQuantDisponivel,colDataValidade,visualizarColuna,editarColuna,apagarColuna);
        
        
        tableViewUnificado=new TableView<>();
        tableViewUnificado.setItems(resultadoUnificado);

		TableColumn<Object[], String> collNome = new TableColumn<>("Nome");
		collNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0].toString()));

		TableColumn<Object[], String> collPrecCompra = new TableColumn<>("Preço Compra");
		collPrecCompra.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1].toString()));

		TableColumn<Object[], String> collPrecVenda = new TableColumn<>("Preço Venda");
		collPrecVenda.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[2].toString()));

		TableColumn<Object[], String> collQuantDisponivel = new TableColumn<>("Quantidade Atual");
		collQuantDisponivel.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[3].toString()));

		tableViewUnificado.getColumns().addAll(collNome, collPrecCompra, collPrecVenda, collQuantDisponivel);
        
        buscarButton.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if(listarTodos.isSelected() && formaBox.getValue().equalsIgnoreCase("Lote")) {
            		produtoEntradaRepository prrp=new produtoEntradaRepository();
            		List<produtosEntrada> produtos=prrp.ListarTodos(status.getValue());
            		resultado.clear();
            		resultado.addAll(produtos);
            		tableView.setItems(resultado);
            		
            	}
            	else if(listarTodos.isSelected() && formaBox.getValue().equalsIgnoreCase("Unificado")) {
            		produtoEntradaRepository prrp=new produtoEntradaRepository();
            		List<Object[]> produtos=prrp.getUnificado(status.getValue());
            		resultadoUnificado.addAll(produtos);
            		tableViewUnificado.setItems(resultadoUnificado);
            	
            	}
            	else if(buscarPorNome.isSelected() && nomeField.getText()!=null && buscarPorNome.isSelected()) {
            		produtoEntradaRepository prrp=new produtoEntradaRepository();
            		List<produtosEntrada> produtos=prrp.getNome(nomeField.getText(),status.getValue());
            		resultado.clear();
            		resultado.addAll(produtos);
            		tableView.setItems(resultado);	
            	}
            	else if(buscarPorCodigoBarra.isSelected() && nomeField.getText()!=null && buscarPorCodigoBarra.isSelected()) {
            		produtoEntradaRepository prrp=new produtoEntradaRepository();
            		List<produtosEntrada> produtos=prrp.getcod(nomeField.getText(),status.getValue());
            		resultado.clear();
            		resultado.addAll(produtos);
            		tableView.setItems(resultado);
            	}
            		 
         }}); 
        
        BorderPane root = new BorderPane();
        formaBox.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	String valor=formaBox.getValue();
            	if(valor.equalsIgnoreCase("lote")) {
            		
                    root.setCenter(tableView);
                                        
            	}
            	else {
            		
            		
            		root.setCenter(tableViewUnificado);
            		
            	}
            	 
         }});
        
        
        // Configurando layout dos RadioButtons e campos de busca
        HBox buscarBox = new HBox(10, listarTodos, buscarPorNome, buscarPorCodigoBarra);
        HBox buscarArea = new HBox(10, nomeLabel, nomeField, buscarButton);
        buscarBox.setPadding(new Insets(10));
        buscarArea.setPadding(new Insets(10));

        // Botão para gerar relatório
        Button gerarRelatorioButton = new Button("Gerar Relatório");
        gerarRelatorioButton.setOnAction(e -> gerarRelatorio());

        Button voltarButton = new Button("Voltar");
        voltarButton.setOnAction(e -> primaryStage.close());

        root.setTop(new VBox(buscarBox, gridpane, buscarArea));
        
        root.setBottom(new HBox(gerarRelatorioButton, voltarButton));
        BorderPane.setMargin(gerarRelatorioButton, new Insets(10));

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private TableCell<produtosEntrada, Void> criarBotaoEditar() {
        return new TableCell<>() {
            private final Button editarButton = new Button("Editar");

            {
                editarButton.setOnAction(event -> {
                	produtosEntrada registro = getTableView().getItems().get(getIndex());
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
    
    private void mostrarPaginaEdicao(produtosEntrada registro) {
        Stage editStage = new Stage();
        editStage.setTitle("Editar Produto");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
    
        TextField nomeField = new TextField(String.valueOf(registro.getProduto().getNomeProduto()));
        nomeField.setDisable(true);
        TextField loteField = new TextField(String.valueOf(registro.getProdutosId()));
        loteField.setDisable(true);
        TextField precoCompraField = new TextField(String.valueOf(registro.getPrecoCompra()));
        TextField precoVendaField = new TextField(String.valueOf(registro.getPrecoVenda()));
        TextField quantEntradaField = new TextField(String.valueOf(registro.getQuantEntrada()));
        TextField quantEstoqueField = new TextField(String.valueOf(registro.getQuantEstoque()));
        DatePicker dataValPicker = new DatePicker(registro.getDataVal());

        Button saveButton = new Button("Salvar");
        saveButton.setOnAction(event -> {

    		produtoEntradaRepository prrp=new produtoEntradaRepository();
    		registro.setPrecoCompra(Double.parseDouble(precoCompraField.getText()));
            registro.setPrecoVenda(Double.parseDouble(precoVendaField.getText()));
            registro.setQuantEntrada(Integer.parseInt(quantEntradaField.getText()));
            registro.setQuantEstoque(Integer.parseInt(quantEstoqueField.getText()));
            registro.setDataVal(dataValPicker.getValue());
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
                new Label("Lote"), loteField,
                new Label("Preço de Compra"), precoCompraField,
                new Label("Preço de Venda"), precoVendaField,
                new Label("Quantidade de Entrada"), quantEntradaField,
                new Label("Quantidade em Estoque"), quantEstoqueField,
                new Label("Data de Validade"), dataValPicker,
                saveButton,
                cancelButton
                
            );

        Scene scene = new Scene(layout);
        editStage.setScene(scene);
        editStage.initModality(Modality.APPLICATION_MODAL);
        editStage.showAndWait();
    }

    
    // Método para criar o botão de apagar em cada linha
    private TableCell<produtosEntrada, Void> criarBotaoApagar() {
        return new TableCell<>() {
            private final Button apagarButton = new Button("Apagar");

            {
                apagarButton.setOnAction(e -> {
                	produtosEntrada registro = getTableView().getItems().get(getIndex());
                	produtoEntradaRepository prrp=new produtoEntradaRepository();
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
    private TableCell<produtosEntrada, Void> criarBotaoVisualizar() {
        return new TableCell<>() {
            private final Button apagarButton = new Button("Visualizar");

            {
                apagarButton.setOnAction(e -> {
                	produtosEntrada registro = getTableView().getItems().get(getIndex());
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
    
    
    private void mostrarPaginaVisualizar(produtosEntrada registro) {
        Stage editStage = new Stage();
        editStage.setTitle("Editar Produto");

        BorderPane bdp=new BorderPane();
        
        ImageView imageView = new ImageView();
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);
        imageView.setPreserveRatio(true);
        
        Path origem = Paths.get(registro.getProduto().getUrlImagem());
        String nomeArquivo = origem.getFileName().toString();
        Image imagem = new Image(nomeArquivo);
        imageView.setImage(imagem);
        
        
        TextField nomeField = new TextField(String.valueOf(registro.getProduto().getNomeProduto()));
        nomeField.setDisable(true);
        TextField codigoField = new TextField(String.valueOf(registro.getProduto().getCodBarra()));
        codigoField.setDisable(true);
        TextField loteField = new TextField(String.valueOf(registro.getProdutosId()));
        loteField.setDisable(true);
        TextField fornecedorField = new TextField(String.valueOf(registro.getEntrada().getFornecedor().getNome()));
        fornecedorField.setDisable(true);
        TextField notaField = new TextField(String.valueOf(registro.getEntrada().getnNotaFiscal()));
        notaField.setDisable(true);
        TextField serieField = new TextField(String.valueOf(registro.getEntrada().getSerieNotaFiscal()));
        serieField.setDisable(true);
        TextField dataField = new TextField(String.valueOf(registro.getEntrada().getData()));
        dataField.setDisable(true);
        //TextField funcionarioField = new TextField(String.valueOf(registro.getEntrada().getFuncionario().getNome()));
       // funcionarioField.setDisable(true);
        TextField precoCompraField = new TextField(String.valueOf(registro.getPrecoCompra()));
        precoCompraField.setDisable(true);
        TextField precoVendaField = new TextField(String.valueOf(registro.getPrecoVenda()));
        precoVendaField.setDisable(true);
        TextField quantEntradaField = new TextField(String.valueOf(registro.getQuantEntrada()));
        quantEntradaField.setDisable(true);
        TextField quantEstoqueField = new TextField(String.valueOf(registro.getQuantEstoque()));
        quantEstoqueField.setDisable(true);
        
        DatePicker dataValPicker = new DatePicker(registro.getDataVal());
        dataValPicker.setDisable(true);


        Button cancelButton = new Button("Voltar");
        cancelButton.setOnAction(event -> editStage.close());

        
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10));
        layout.add(new Label("Nome:"), 0, 0);
        layout.add(nomeField, 1, 0);
        layout.add(new Label("Lote"), 0, 1);
        layout.add(loteField, 1, 1);
        layout.add(new Label("Cod Barra:"), 0, 2);
        layout.add(codigoField, 1, 2);
        layout.add(new Label("Fornecedor:"), 0, 3);
        layout.add(fornecedorField, 1, 3);
        layout.add(new Label("N Nota Fiscal:"), 0, 4);
        layout.add(notaField, 1, 4);
        layout.add(new Label("Série:"), 0, 5);
        layout.add(serieField, 1, 5);
        layout.add(new Label("Preço de Compra"), 0, 6);
        layout.add(precoCompraField, 1, 6);
        layout.add(new Label("Preço de Venda"), 0, 7);
        layout.add(precoVendaField, 1, 7);
        layout.add(new Label("Quantidade de Entrada"), 0, 8);
        layout.add(quantEntradaField, 1, 8);
        layout.add(new Label("Quantidade em Estoque"), 0, 9);
        layout.add(quantEstoqueField, 1, 9);
        layout.add(new Label("Data de Validade"), 0, 10);
        layout.add(dataValPicker, 1, 10);
        

        HBox topLayout = new HBox(10);
        topLayout.setPadding(new Insets(10));
        topLayout.getChildren().add(imageView);
        topLayout.setAlignment(Pos.CENTER);
        
        
        HBox bottonLayout = new HBox(10);
        bottonLayout.setPadding(new Insets(10));
        bottonLayout.getChildren().addAll(cancelButton);
        bottonLayout.setAlignment(Pos.CENTER_LEFT);
        
        bdp.setTop(topLayout);
        bdp.setCenter(layout);
        bdp.setBottom(bottonLayout);

        
        ScrollPane sc=new ScrollPane(bdp);
        Scene scene = new Scene(sc);
        editStage.setScene(scene);
        editStage.initModality(Modality.APPLICATION_MODAL);
        editStage.showAndWait();
    }
    
    private void gerarRelatorio() {
        XWPFDocument document = new XWPFDocument();

        try (FileOutputStream out = new FileOutputStream("Relatorio_Estoque.doc")) {
            // Título do Documento
            XWPFParagraph titleParagraph = document.createParagraph();
            XWPFRun titleRun = titleParagraph.createRun();
            titleRun.setText("Relatório de Estoque");
            titleRun.setBold(true);
            titleRun.setFontSize(20);

            // Tabela
            XWPFTable table = document.createTable();
            XWPFTableRow headerRow = table.getRow(0);
            headerRow.getCell(0).setText("Nome");
            headerRow.addNewTableCell().setText("Status");

            // Adicionando os dados da TableView ao documento
            for (produtosEntrada item : tableView.getItems()) {
                XWPFTableRow row = table.createRow();
                row.getCell(0).setText(item.getProduto().getNomeProduto());
                
                row.getCell(1).setText((item.getDataVal()).toString());
                
            }

            document.write(out);
            out.close();

            // Abrir o arquivo gerado
            File file = new File("Relatorio_Estoque.doc");
            if (file.exists()) {
                new ProcessBuilder("cmd", "/c", "start", file.getAbsolutePath()).start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
       }
    }
public static void main(String [] args) {
		
		launch(args);	
	}
	
}