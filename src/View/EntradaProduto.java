package View;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import Model.remetenteNota;
import Model.EntradaProdutoModel;
import Model.Fornecedor;
import Model.Funcionarios;
import Model.categoria;
import Model.pagamento;
import Model.parcelas;
import Model.produto;
import Model.produtosEntrada;
import Model.unidadeMedida;
import Repository.EntradaProdRepository;
import Repository.pagamentoRepository;
import Repository.parcelasRepository;
import Repository.produtoEntradaRepository;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.shape.Line;


public class EntradaProduto extends Application {

	
    private TableView<produtosEntrada> tableView;
    private ObservableList<produtosEntrada> registros;
    private DatePicker dataEntrega,dataValidade,dataVencimento;
    private LocalDate dataEntregaValue,dataValidadeValue,dataVencimentoValue;
    private ImageView imageView;
    private Funcionarios funcionarioFinal;
    private List<produtosEntrada> produtosFinal;
    private List<unidadeMedida> unMedida;
    private List<categoria> categoria;
    private  List<Fornecedor> fornecedor;
    private List<produto> produto;
    private List<remetenteNota> remetente;
    private unidadeMedida unidadeFinal;
    private categoria categoriaFinal;
    private Fornecedor fornecedorFinal;
    private produto produtoFinal;
    private Funcionarios funcionario;
    private remetenteNota remetenteFinal;
    String emissao;
    
   public void setList(List<unidadeMedida> unMedida,List<categoria> categoria,List<Fornecedor> fornecedor,List<produto> produto,List<remetenteNota> remetente,Funcionarios fn) {
	   this.categoria=categoria;
	   this.fornecedor=fornecedor;
	   this.produto=produto;
	   this.funcionario=fn;
	   this.remetente=remetente;
	   this.unMedida=unMedida;
	    
   }
    
    
    @Override
    public void start(Stage stage) {
    	produtosFinal=new ArrayList<produtosEntrada>();
    	
        // Cabeçalho
        Text header = new Text("Entrada Produtos");
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
        
        
        Label tipoOperacao=new Label("Compra");
        tipoOperacao.setFont(Font.font("Arial", 20));
        formGrid.add(tipoOperacao, 1, 0);
        
        formGrid.add(new Label("Fornecedor:"), 0, 1);
        
        //Busca Todos Os fornecedores registrado para popular o choiceBox
        ArrayList<String> fornecedores=new ArrayList<>();
        fornecedor.forEach(nome->fornecedores.add(nome.getNome()+" | "+nome.getNumDoc()));
        ObservableList<String> observableItems = FXCollections.observableArrayList(fornecedores);
        ChoiceBox<String> fornecedoresBox = new ChoiceBox<>(observableItems);
        formGrid.add(fornecedoresBox, 1, 1,2,1);
        fornecedoresBox.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	int index=fornecedoresBox.getSelectionModel().getSelectedIndex();
            	fornecedorFinal=fornecedor.get(index);
            }
            });
        
        formGrid.add(new Label("Data da Entrada:"), 3, 1,2,1);
        dataEntrega=new DatePicker();
        dataEntrega.setPromptText("Escolha uma data");
        dataEntregaValue=dataEntrega.getValue();
        formGrid.add(dataEntrega, 4, 1,2,1);
        
        CheckBox checkBox = new CheckBox("Sem Emissão de Nota Fiscal"); 
        formGrid.add(checkBox, 6,1, 3, 1);
        
        
        formGrid.add(new Label("Remetente:"), 0, 2);
        //Busca Todos Os fornecedores registrado para popular o choiceBox
        ArrayList<String> remetenteList=new ArrayList<>();
        remetente.forEach(nome->remetenteList.add(nome.getNome()+" | "+nome.getNumero()));
        ObservableList<String> obsRemItems = FXCollections.observableArrayList(remetenteList);
        ChoiceBox<String> remetenteBox = new ChoiceBox<>(obsRemItems);
        formGrid.add(remetenteBox, 1, 2,2,1);
        fornecedoresBox.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	int index=fornecedoresBox.getSelectionModel().getSelectedIndex();
            	remetenteFinal=remetente.get(index);
            }
            });
        
        formGrid.add(new Label("Nota Fiscal:"), 0, 3);
        TextField notaFiscalField = new TextField();
        formGrid.add(notaFiscalField, 1, 3, 2, 1);
        
        formGrid.add(new Label("Série:"), 3, 3);
        TextField serieFiscalField = new TextField();
        formGrid.add(serieFiscalField, 4, 3, 3, 1);
        
        formGrid.add(new Label("Chave Acesso:"), 0, 4);
        TextField chaveAcessoField = new TextField();
        formGrid.add(chaveAcessoField, 1, 4, 3, 1);
        
        checkBox.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if(checkBox.isSelected()) {
            		notaFiscalField.setDisable(true);
            		serieFiscalField.setDisable(true);
            		chaveAcessoField.setDisable(true);
            		remetenteBox.setDisable(true);
            		notaFiscalField.setText("0");
            		serieFiscalField.setText("0");
            		chaveAcessoField.setText("0");
            		emissao="";
            		
            	}
            	else {
            		notaFiscalField.setDisable(false);
            		serieFiscalField.setDisable(false);
            		chaveAcessoField.setDisable(false);
            		notaFiscalField.setText("");
            		serieFiscalField.setText("");
            		chaveAcessoField.setText("");
            	}
            }
            });
        
        
        formGrid.add(new Label("Valor Total:"), 4, 4);
        TextField valorField = new TextField();
        formGrid.add(valorField, 5, 4, 1, 1);
        
        formGrid.add(new Label("Forma Pagamento:"), 0, 5);
        // Criar um ChoiceBox e adicionar opções
        ChoiceBox<String> pagamentoBox = new ChoiceBox<>();
        pagamentoBox.getItems().addAll("Débito", "Crédito", "Boleto","Boleto Parcelado","PIX","Dinheiro");
        pagamentoBox.setValue("Dinheiro");
        formGrid.add(pagamentoBox, 1, 5);

        
        formGrid.add(new Label("Parcelas:"), 2, 5);
        // Criar um ChoiceBox e adicionar opções
        ChoiceBox<String> parcelaBox = new ChoiceBox<>();
        parcelaBox.getItems().addAll("1", "2", "3","4","5","6","7","8","9","10","11","12");
        parcelaBox.setValue("1");
        formGrid.add(parcelaBox, 3, 5, 1, 1);
        
        // Criar um ChoiceBox e adicionar opções
        formGrid.add(new Label("Status:"), 4, 5);
        ChoiceBox<String> statusBox = new ChoiceBox<>();
        statusBox.getItems().addAll("Finalizado", "Pendente");
        statusBox.setValue("Pendente");
        formGrid.add(statusBox, 5, 5, 1, 1);
        
        
        formGrid.add(new Label("Data Vencimento:"), 0, 6);
        dataVencimento=new DatePicker();
        dataVencimento.setPromptText("Data Do Primeiro Vencimento");
        dataVencimentoValue=dataVencimento.getValue();
        formGrid.add(dataVencimento, 1, 6);
        
        
     // Criando a linha horizontal
        Line linha = new Line();
        linha.setStartX(0);  // Posição inicial no eixo X (esquerda)
        linha.setEndX(900);  // Posição final no eixo X (direita)
        linha.setStartY(100); // Posição inicial no eixo Y
        linha.setEndY(100);   // Mantendo a posição Y constante para ser horizontal
        
        // Definindo a cor e espessura da linha
        linha.setStroke(Color.BLACK);
        linha.setStrokeWidth(2);
        formGrid.add(linha, 0, 7,7,1);

        
        Label produtosName=new Label("Produtos:");
        produtosName.setFont(Font.font("Arial", 20));
        formGrid.add(produtosName, 0, 8);
        
        
     // Criando a linha horizontal 
        GridPane formGrid02 = new GridPane();
        formGrid02.setHgap(10);
        formGrid02.setVgap(10);
        formGrid02.setPadding(new Insets(10));
        
        ArrayList<String> produtos=new ArrayList<>();
        produto.forEach(nome->produtos.add(nome.getNomeProduto()));
        ObservableList<String> produtosItems = FXCollections.observableArrayList(produtos);
        ChoiceBox<String> produtosaBox = new ChoiceBox<>(produtosItems);
        produtosaBox.setMinWidth(400);
        formGrid02.add(new Label("Nome:"), 0, 0);
        formGrid02.add(produtosaBox, 1,0, 4, 1);
         
        
        // ImageView para mostrar a imagem escolhida
        imageView = new ImageView();
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);
        imageView.setPreserveRatio(true);
        formGrid02.add(imageView, 5, 0,6,6);
        
        
        formGrid02.add(new Label("Código Barras:"), 0, 1);
        Label codigoLabel=new Label("");
        formGrid02.add(codigoLabel, 1, 1,4,1);
        
        Label categoriaLabel=new Label();
        categoriaLabel.setText("Categoria: ");
        formGrid02.add(categoriaLabel, 0,2);
        Label categoriaProdLabel=new Label();
        categoriaProdLabel.setText("");
        formGrid02.add(categoriaProdLabel, 1,2);
        
        Label unLabel=new Label();
        unLabel.setText("Un Medida:");
        formGrid02.add(unLabel, 2,2);
        ArrayList<String> unidade=new ArrayList<>();
        Label unProdLabel=new Label();
        unProdLabel.setText("");
        formGrid02.add(unProdLabel, 3,2);
        
        produtosaBox.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	int index=produtosaBox.getSelectionModel().getSelectedIndex();
            	codigoLabel.setText(produto.get(index).getCodBarra());
            	categoriaProdLabel.setText(produto.get(index).getCategoria().getCategoria());
            	unProdLabel.setText(produto.get(index).getUnmedida().getUnMedida());
            }
            });
        
        
        formGrid02.add(new Label("Preço Compra:"), 0, 3);
        TextField precoCompraField = new TextField();
        formGrid02.add(precoCompraField, 1, 3);

        formGrid02.add(new Label("Preço Venda:"), 2, 3);
        TextField precoVendaField = new TextField();
        formGrid02.add(precoVendaField, 3, 3);

        formGrid02.add(new Label("Quantidade:"), 2, 4);
        TextField quantidadeField = new TextField();
        formGrid02.add(quantidadeField, 3, 4);


        formGrid02.add(new Label("Validade:"), 0, 4);
        
        dataValidade=new DatePicker();
        dataValidade.setPromptText("Escolha uma data");
        dataValidadeValue=dataValidade.getValue();
        formGrid02.add(dataValidade, 1, 4);
        
        
        registros = FXCollections.observableArrayList();
        
        // Botão "Adicionar Novo"
        Button adicionarNovoButton = new Button("Adicionar");
        HBox adicionarBox = new HBox(adicionarNovoButton);
        formGrid02.setAlignment(Pos.CENTER_RIGHT);
        formGrid02.add(adicionarBox, 3, 8);
        adicionarNovoButton.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	
            		int index=produtosaBox.getSelectionModel().getSelectedIndex();
	            	produto pr=produto.get(index);
	            	produtosEntrada pre=new produtosEntrada();
	            	pre.setDataVal(dataValidade.getValue());
	            	pre.setPrecoCompra(Double.parseDouble(precoCompraField.getText()));
	            	pre.setPrecoVenda(Double.parseDouble(precoVendaField.getText()));
	            	pre.setQuantEntrada(Integer.parseInt(quantidadeField.getText()));
	            	pre.setQuantEstoque(Integer.parseInt(quantidadeField.getText()));
	            	pre.setProduto(pr);
	            	pre.setStatus("Ativo");
	            	
                   	registros.add(pre);
                   	produtosFinal.add(pre);
                    // Atualiza a exibição na TableView
                    tableView.setItems(registros);
            }
        });
        
        
        
        Line linhaInferior = new Line();
        linhaInferior.setStartX(0);  // Posição inicial no eixo X (esquerda)
        linhaInferior.setEndX(900);  // Posição final no eixo X (direita)
        linhaInferior.setStartY(100); // Posição inicial no eixo Y
        linhaInferior.setEndY(100);   // Mantendo a posição Y constante para ser horizontal
        
        // Definindo a cor e espessura da linha
        linhaInferior.setStroke(Color.BLACK);
        linhaInferior.setStrokeWidth(2);
        formGrid02.add(linhaInferior, 0,9,7,1);
        

        
        //Criação e adição do tableView usado para lista os produtos
        tableView = new TableView<>();
        tableView.setItems(registros);
        tableView.setMinHeight(300);

        // Colunas da TableView
        TableColumn<produtosEntrada, Void> nomeColuna = new TableColumn<>("Nome");
        nomeColuna.setPrefWidth(300);
        
        
        // Formatando a data com SimpleDateFormat
        nomeColuna.setCellFactory(column -> {
            return new TableCell<produtosEntrada, Void>() {
            	int index=produtosaBox.getSelectionModel().getSelectedIndex();
                private String nome=produto.get(index).getNomeProduto();

            	@Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                    	setText(nome);
                    }
                }
             };
        });
        
        

        TableColumn<produtosEntrada, String> precCompra = new TableColumn<>("Preço Compra");
        precCompra.setCellValueFactory(new PropertyValueFactory<>("precoCompra"));
        precCompra.setPrefWidth(100);
        
        TableColumn<produtosEntrada, String> precVenda = new TableColumn<>("Preço Venda");
        precVenda.setCellValueFactory(new PropertyValueFactory<>("precoVenda"));
        precVenda.setPrefWidth(100);
        
        TableColumn<produtosEntrada, String> quantidade = new TableColumn<>("Quantidade");
        quantidade.setCellValueFactory(new PropertyValueFactory<>("quantEntrada"));
        quantidade.setPrefWidth(100);
        
        TableColumn<produtosEntrada, Void> validade = new TableColumn<>("Validade");
        validade.setCellFactory(column -> {
            return new TableCell<produtosEntrada, Void>() {
                // Usar DateTimeFormatter em vez de SimpleDateFormat
                private DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    
                    // Verifica se a célula está vazia ou se o valor é nulo
                    if (empty) {
                        setText(null);
                    } else {
                        // Formata e exibe a data como string no formato "dd/MM/yyyy"
                        setText(dataValidade.getValue().format(format));
                    }
                }
            };
        });
        validade.setPrefWidth(100);
        
        TableColumn<produtosEntrada, Void> editarColuna = new TableColumn<>("Editar");
        editarColuna.setCellFactory(param -> criarBotaoEditar());

        TableColumn<produtosEntrada, Void> apagarColuna = new TableColumn<>("Apagar");
        apagarColuna.setCellFactory(param -> criarBotaoApagar());

        // Adicionando colunas na TableView
        tableView.getColumns().addAll(nomeColuna,precCompra,precVenda,quantidade,validade, editarColuna, apagarColuna);
        //fim do tableview
        
        // Botões de Ação
        Button cancelarButton = new Button("Cancelar");
        cancelarButton.setStyle("-fx-background-color: #E4E05D; -fx-text-fill: black;");
        Button cadastrarButton = new Button("Cadastrar");
        cadastrarButton.setStyle("-fx-background-color: #69C285; -fx-text-fill: black;");
        cadastrarButton.setOnAction(event -> {
        			
        			EntradaProdRepository erp=new EntradaProdRepository();
        			pagamentoRepository pdrp=new pagamentoRepository();
        			parcelasRepository pcrp=new parcelasRepository();
        			produtoEntradaRepository prodrp=new produtoEntradaRepository();
        			
        	
        			EntradaProdutoModel epm=new EntradaProdutoModel();
        			epm.setChaveAcesso(chaveAcessoField.getText());
        			epm.setData(dataEntrega.getValue());
        			epm.setnNotaFiscal(notaFiscalField.getText());
        			epm.setSerieNotaFiscal(serieFiscalField.getText());
        			epm.setTipoOperacao(tipoOperacao.getText());
        			epm.setFornecedor(fornecedorFinal);
        			epm.setFuncionario(funcionario);
        			epm.setRemetentes(remetenteFinal);
        			epm.setEmissaoNota(emissao);
        			erp.gavar(epm);
        			
        	
        	
        			pagamento pg=new pagamento();
        			int nParcelas=Integer.parseInt(parcelaBox.getValue());
        			double valorTotal=Double.parseDouble(valorField.getText());
        			pg.setParcelas(nParcelas);
        			pg.setForma(pagamentoBox.getValue());
        			pg.setValorTotal(valorTotal);
        			pg.setStatus(statusBox.getValue());
        			pg.setEntrada(epm);
        			pdrp.gavar(pg);
        			
        			//pega o numero de vezes e o valor total e cria as parcelas
        	        LocalDate dataInicial = dataVencimento.getValue();
        	        int numeroDeDias = 30;
        	        
        	        // Gera as datas
        	        for (int i = 0; i < nParcelas; i++) {
        	        	parcelas nome = new parcelas(); 
        	        	nome.setData(dataInicial);
        	        	nome.setValorParcela(valorTotal / nParcelas);
        	        	nome.setStatus(statusBox.getValue());
        	        	nome.setPagamento(pg);
        	            pcrp.gavar(nome);
        	            // Atualiza a data para o próximo vencimento
        	            dataInicial = dataInicial.plusDays(numeroDeDias);
        	        }

        	        
        	        registros.forEach(produto->{
        	        			produto.setEntrada(epm);
        	        			prodrp.gavar(produto);
        	        });
        		}    
       );
        
        cancelarButton.setOnAction(event -> {
        	 stage.close();
        	 
        });
        
        
        HBox actionButtons = new HBox(10, cancelarButton, cadastrarButton);
        actionButtons.setAlignment(Pos.CENTER);
        actionButtons.setPadding(new Insets(10));
        
        
        HBox CenterBox = new HBox(10,formGrid);
        CenterBox.setAlignment(Pos.CENTER);
        HBox CenterBox02 = new HBox(10,formGrid02);
        CenterBox02.setAlignment(Pos.CENTER);
        
        
        // Layout principal
        VBox mainLayout = new VBox(10, headerBox, CenterBox,CenterBox02,tableView, actionButtons);
        mainLayout.setAlignment(Pos.TOP_CENTER);
        mainLayout.setStyle("-fx-background-color: #F0F0F0;"); // Fundo cinza claro
        mainLayout.setPadding(new Insets(10));

        // Adicionar o VBox em um ScrollPane
        ScrollPane scrollPane = new ScrollPane(mainLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        Scene scene = new Scene(scrollPane, 900, 600);
        
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("Entrada de Produtos");
        stage.show();
    }
    
    

    // Método para criar o botão de editar em cada linha
    private TableCell<produtosEntrada, Void> criarBotaoEditar() {
        return new TableCell<>() {
            private final Button editarButton = new Button("Editar");

            {
                editarButton.setOnAction(e -> {
                	produtosEntrada registro = getTableView().getItems().get(getIndex());
                    editarRegistro(registro);
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
    private TableCell<produtosEntrada, Void> criarBotaoApagar() {
        return new TableCell<>() {
            private final Button apagarButton = new Button("Apagar");

            {
                apagarButton.setOnAction(e -> {
                	produtosEntrada registro = getTableView().getItems().get(getIndex());
                    registros.remove(registro);
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

    // Método para editar um registro existente
    private void editarRegistro(produtosEntrada registro) {
       
    	
    	
    	
    	
        };

    public static void main(String[] args) {
        launch(args);
    }
}
