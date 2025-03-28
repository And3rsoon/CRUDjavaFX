package View;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import Model.EntradaProdutoModel;
import Model.cliente;
import Model.Funcionarios;
import Model.pagamento;
import Model.parcelas;
import Model.produto;
import Model.produtoSaida;
import Model.produtosEntrada;
import Model.saidaProdutoModel;
import Repository.EntradaProdRepository;
import Repository.FornecedorRepository;
import Repository.FuncionarioRepository;
import Repository.cadClienteRepository;
import Repository.pagamentoRepository;
import Repository.parcelasRepository;
import Repository.produtoEntradaRepository;
import Repository.produtoRepository;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class saidaProduto extends Application {
	private TableView<produtosEntrada> tableView;
	private TableView<produtoSaida> tableViewSaida;
    private ObservableList<produtoSaida> saidaObs;
    private DatePicker dataEntrega,dataValidade,dataVencimento;
    private LocalDate dataSaida,dataValidadeValue,dataVencimentoValue;
    private ObservableList<produtosEntrada> resultado;
    private List<produtoSaida> prodSaida;
    
    @Override
    public void start(Stage stage) {
	
        // Cabeçalho
        Text header = new Text("Saída Produtos");
        header.setFont(Font.font("Arial", 36));
        header.setFill(Color.BLACK);
        

        HBox headerBox = new HBox(10,header);
        HBox.setMargin(header, new Insets(50));
        headerBox.setAlignment(Pos.CENTER_LEFT);
        headerBox.setPrefHeight(80);
        headerBox.setBackground(new Background(new BackgroundFill(
                Color.web("#69C285"), CornerRadii.EMPTY, Insets.EMPTY)));

        
        // Layout para os campos de formulário
        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.setPadding(new Insets(10));

        // Campos do formulário
        
        Label operaçao=new Label("Tipo Operação:");
        operaçao.setFont(Font.font("Arial", 20));
        formGrid.add(operaçao, 0, 0);
        
        
        Label tipoOperacao=new Label("Venda");
        tipoOperacao.setFont(Font.font("Arial", 20));
        formGrid.add(tipoOperacao, 1, 0);
        
        formGrid.add(new Label("Cliente:"), 0, 1);
        
        //Busca Todos Os fornecedores registrado para popular o choiceBox
        ArrayList fornecedores=new ArrayList();
        List<cliente> cliente=new cadClienteRepository().ListarTodos();
        cliente.forEach(nome->fornecedores.add(nome.getNumDoc()));
        ObservableList<String> observableItems = FXCollections.observableArrayList(fornecedores);
        observableItems.add("Não Informado");
        ChoiceBox<String> fornecedoresBox = new ChoiceBox<>(observableItems);
        fornecedoresBox.setValue("Não Informado");
        formGrid.add(fornecedoresBox, 1, 1);
        fornecedoresBox.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	int index=fornecedoresBox.getSelectionModel().getSelectedIndex();
            	
            	
            }
            });
        
        
        
        formGrid.add(new Label("Data:"), 2, 1);
        dataEntrega=new DatePicker();
        dataEntrega.setPromptText("Escolha uma data");
        dataEntrega.setValue(LocalDate.now());
        dataSaida=dataEntrega.getValue();
        formGrid.add(dataEntrega, 3, 1);
        
       
        formGrid.add(new Label("Forma Pagamento:"), 0, 4);
        // Criar um ChoiceBox e adicionar opções
        ChoiceBox<String> pagamentoBox = new ChoiceBox<>();
        pagamentoBox.getItems().addAll("Débito", "Crédito", "Boleto","Boleto Parcelado","PIX","Dinheiro");
        pagamentoBox.setValue("Dinheiro");
        formGrid.add(pagamentoBox, 1, 4);

        
        formGrid.add(new Label("Parcelas:"), 2, 4);
        // Criar um ChoiceBox e adicionar opções
        ChoiceBox<String> parcelaBox = new ChoiceBox<>();
        parcelaBox.getItems().addAll("1", "2", "3","4","5","6","7","8","9","10","11","12");
        parcelaBox.setValue("1");
        formGrid.add(parcelaBox, 3, 4, 1, 1);
        
        // Criar um ChoiceBox e adicionar opções
        formGrid.add(new Label("Status:"), 4, 4);
        ChoiceBox<String> statusBox = new ChoiceBox<>();
        statusBox.getItems().addAll("Finalizado", "Pendente");
        statusBox.setValue("Finalizado");
        formGrid.add(statusBox, 5, 4, 1, 1);
        
        
        formGrid.add(new Label("Data Pagamento:"), 0, 5);
        dataVencimento=new DatePicker();
        dataVencimento.setPromptText("Data do Pagamento:");
        dataVencimentoValue=dataVencimento.getValue();
        formGrid.add(dataVencimento, 1, 5);
        
        
     // Criando a linha horizontal
        Line linha = new Line();
        linha.setStartX(0);  // Posição inicial no eixo X (esquerda)
        linha.setEndX(900);  // Posição final no eixo X (direita)
        linha.setStartY(100); // Posição inicial no eixo Y
        linha.setEndY(100);   // Mantendo a posição Y constante para ser horizontal
        
        // Definindo a cor e espessura da linha
        linha.setStroke(Color.BLACK);
        linha.setStrokeWidth(2);
        formGrid.add(linha, 0, 6,7,1);

        
        Button pesquisarButton = new Button("Adicionar Produto");
        formGrid.add(pesquisarButton, 0, 7,7,1);
        pesquisarButton.setOnAction(event -> {
        	prodSaida=new ArrayList<>();		
        	mostrarPaginaPesquisa(prodSaida);
        	
        	
            }    
       );
        saidaObs= FXCollections.observableArrayList();
        
        tableViewSaida = new TableView<>();
        tableViewSaida.setItems(saidaObs);
        tableViewSaida.setMinHeight(300);

        // Colunas da TableView
        TableColumn<produtoSaida, String> nomeColuna = new TableColumn<>("Nome");
        nomeColuna.setPrefWidth(300);
        nomeColuna.setCellFactory(column -> new TableCell<produtoSaida,String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                
                if (empty) {
                    setText(null);
                } else {
                    // Obtém o item no índice atual da TableView
                    produtoSaida registro = getTableView().getItems().get(getIndex());
                    if (registro != null) {
                        setText(registro.getProdEntrada().getProduto().getNomeProduto());
                    } else {
                        setText(null);
                    }
                }
            }
        });
        
     // Colunas da TableView
        TableColumn<produtoSaida, String> loteColuna = new TableColumn<>("Lote");
        loteColuna.setPrefWidth(300);
        loteColuna.setCellFactory(column -> new TableCell<produtoSaida, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                
                if (empty) {
                    setText(null);
                } else {
                    // Obtém o item no índice atual da TableView
                    produtoSaida registro = getTableView().getItems().get(getIndex());
                    if (registro != null) {
                        setText(String.valueOf(registro.getProdEntrada().getProdutosId()));
                    } else {
                        setText(null);
                    }
                }
            }
        });
            	
                
     // Colunas da TableView
        TableColumn<produtoSaida, String> quantidadeColuna = new TableColumn<>("Quantidade");
        quantidadeColuna.setPrefWidth(300);
        quantidadeColuna.setCellFactory(column -> new TableCell<produtoSaida, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                
                if (empty) {
                    setText(null);
                } else {
                    // Obtém o item no índice atual da TableView
                    produtoSaida registro = getTableView().getItems().get(getIndex());
                    if (registro != null) {
                        setText(String.valueOf(registro.getQuantidade()));
                    } else {
                        setText(null);
                    }
                }
            }
        });       
       
        TableColumn<produtoSaida, String> validade = new TableColumn<>("Validade");
        validade.setCellFactory(column -> {
            return new TableCell<produtoSaida, String>() {
                // Usar DateTimeFormatter em vez de SimpleDateFormat
                private DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    
                    // Verifica se a célula está vazia ou se o valor é nulo
                    if (empty) {
                        setText(null);
                    } else {
                    	produtoSaida registro = getTableView().getItems().get(getIndex());
                        // Formata e exibe a data como string no formato "dd/MM/yyyy"
                        setText(registro.getProdEntrada().getDataVal().format(format));
                    }
                }
            };
        });
        validade.setPrefWidth(100);
        
        
        TableColumn<produtoSaida, Void> apagarColuna = new TableColumn<>("Apagar");
        apagarColuna.setCellFactory(param -> criarBotaoApagar());

        // Adicionando colunas na TableView
        tableViewSaida.getColumns().addAll(nomeColuna,loteColuna,validade,quantidadeColuna, apagarColuna);
        //fim do tableview
        
        
        
        // Botões de Ação
        Button cancelarButton = new Button("Cancelar");
        cancelarButton.setStyle("-fx-background-color: #E4E05D; -fx-text-fill: black;");
        Button cadastrarButton = new Button("Cadastrar");
        cadastrarButton.setStyle("-fx-background-color: #69C285; -fx-text-fill: black;");
        cadastrarButton.setOnAction(event -> {
        					int index=fornecedoresBox.getSelectionModel().getSelectedIndex();
        					cliente cl=cliente.get(index);
        					
        					FuncionarioRepository fnrp=new FuncionarioRepository();
        					Funcionarios fn=new Funcionarios();
        					fn=fnrp.consultar(0);
        	
        					dataSaida=dataEntrega.getValue();
        					saidaProdutoModel spp=new saidaProdutoModel();
        					spp.setCliente(cl);
        					spp.setData(dataSaida);
        					spp.setFuncionario(fn);
        					spp.setTipoOperacao("venda");
        					spp.setStatus(statusBox.getValue());
        					
        					
        					produtosSaidaRepository psrpp=new produtosSaidaRepository();
        					saidaObs.forEach(produto->{
        	        			produto.setSaidaprod(spp);
        	        			psrpp.gavar(produto);
        	        			produtoEntradaRepository rp=new produtoEntradaRepository();
        	        			rp.subtrairQuantidadeEstoque(produto.getProdEntrada().getProdutosId(), produto.getQuantidade());
        	        			
        	        });
        					Alert alert = new Alert(AlertType.INFORMATION);
                    	    alert.setTitle("Sucesso");
                    	    alert.setHeaderText(null); 
                    	    alert.setContentText("Saida adicionada!");
                    	    
        					
        				    
        	
        			
            }    
       );
        
        HBox actionButtons = new HBox(10, cancelarButton, cadastrarButton);
        actionButtons.setAlignment(Pos.CENTER);
        actionButtons.setPadding(new Insets(10));
        
        
        HBox CenterBox = new HBox(10,formGrid);
        CenterBox.setAlignment(Pos.CENTER);
        
        
        // Layout principal
        VBox mainLayout = new VBox(10, headerBox, CenterBox,tableViewSaida, actionButtons);
        mainLayout.setAlignment(Pos.TOP_CENTER);
        mainLayout.setStyle("-fx-background-color: #F0F0F0;"); // Fundo cinza claro
        mainLayout.setPadding(new Insets(10));

        // Adicionar o VBox em um ScrollPane
        ScrollPane scrollPane = new ScrollPane(mainLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        Scene scene = new Scene(scrollPane, 900, 600);
        stage.setScene(scene);
        stage.setTitle("Entrada de Produtos");
        stage.show();
    }
    

    // Método para criar o botão de apagar em cada linha
    private TableCell<produtoSaida, Void> criarBotaoApagar() {
        return new TableCell<>() {
            private final Button apagarButton = new Button("Apagar");

            {
                apagarButton.setOnAction(e -> {
                	produtoSaida registro = getTableView().getItems().get(getIndex());
                	saidaObs.remove(getIndex());
                	tableViewSaida.setItems(saidaObs);
                    
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
 
        private void mostrarPaginaPesquisa(List<produtoSaida> prodSaida) {
            Stage editStage = new Stage();
            editStage.setTitle("Pesquisar Produto");

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
            buscarButton.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                	if(listarTodos.isSelected()) {
                		produtoEntradaRepository prrp=new produtoEntradaRepository();
                		List<produtosEntrada> produtos=prrp.ListarTodos();
                		resultado.clear();
                		resultado.addAll(produtos);
                		tableView.setItems(resultado);
                		
                	}
                	
                	else if(buscarPorNome.isSelected() && nomeField.getText()!=null) {
                		produtoEntradaRepository prrp=new produtoEntradaRepository();
                		List<produtosEntrada> produtos=prrp.getNome(nomeField.getText());
                		resultado.clear();
                		resultado.addAll(produtos);
                		tableView.setItems(resultado);
                		
                	}
                	else if(buscarPorCodigoBarra.isSelected() && nomeField.getText()!=null) {
                		produtoEntradaRepository prrp=new produtoEntradaRepository();
                		List<produtosEntrada> produtos=prrp.getNome(nomeField.getText());
                		resultado.clear();
                		resultado.addAll(produtos);
                		tableView.setItems(resultado);
                		
                	}	 
             }});
            
            
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
            
            TableColumn<produtosEntrada, String> colPrecVenda = new TableColumn<>("Preço Venda");
            colPrecVenda.setCellValueFactory(new PropertyValueFactory<>("precoVenda"));
            
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

            TableColumn<produtosEntrada, Void> apagarColuna = new TableColumn<>("Adicionar");
            apagarColuna.setCellFactory(param -> criarBotaoAdicionar());
            
            tableView.getColumns().addAll(colNome, colLote,colPrecVenda,colQuantDisponivel,colDataValidade,apagarColuna);

            

            // Configurando layout dos RadioButtons e campos de busca
            HBox buscarBox = new HBox(10, listarTodos, buscarPorNome, buscarPorCodigoBarra);
            HBox buscarArea = new HBox(10, nomeLabel, nomeField, buscarButton);
            buscarBox.setPadding(new Insets(10));
            buscarArea.setPadding(new Insets(10));
            
         // Layout principal
            BorderPane root = new BorderPane();
            root.setTop(new VBox(buscarBox, gridpane, buscarArea));
            root.setCenter(tableView);

            Scene scene = new Scene(root, 600, 400);
            editStage.setScene(scene);
            editStage.show();
             
        }
     // Método para criar o botão de apagar em cada linha
        private TableCell<produtosEntrada, Void> criarBotaoAdicionar() {
            return new TableCell<>() {
                private final Button apagarButton = new Button("Adicionar");

                {
                    apagarButton.setOnAction(e -> {
                    	produtoSaida pr=new produtoSaida();
                    	mostrarPaginaVisualizar(pr);
                    	produtosEntrada registro = getTableView().getItems().get(getIndex());
                    	pr.setFuncionario(null);
                    	pr.setProdEntrada(registro);
                    	saidaObs.clear();
                    	prodSaida.add(pr);
                    	saidaObs.addAll(prodSaida);
                    	tableViewSaida.setItems(saidaObs);
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
        
        private void mostrarPaginaVisualizar(produtoSaida registro) {
            Stage editStage = new Stage();
            editStage.setTitle("Quantidade");

            VBox layout = new VBox(10);
            layout.setPadding(new Insets(10));
            
            
            TextField quantidadeField = new TextField();
            Button saveButton = new Button("Salvar");
            saveButton.setOnAction(event -> {
            	registro.setQuantidade(Integer.parseInt(quantidadeField.getText()));
            	editStage.close();
        		
            });

            Button cancelButton = new Button("Cancelar");
            cancelButton.setOnAction(event -> editStage.close());

            layout.getChildren().addAll(
                    new Label("Quantidade:"), quantidadeField, 
                    saveButton,
                    cancelButton
                    
                );

            ScrollPane sc=new ScrollPane(layout);
            Scene scene = new Scene(sc);
            editStage.setScene(scene);
            editStage.initModality(Modality.APPLICATION_MODAL);
            editStage.showAndWait();
        }
        

    public static void main(String[] args) {
        launch(args);
    }
}
