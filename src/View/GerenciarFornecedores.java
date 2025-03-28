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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.shape.Line;
import Repository.FornecedorRepository;
import java.util.List;
import Model.Fornecedor;


public class GerenciarFornecedores extends Application {

	private TextField nomeField;
    private TextField cpfField;
    private TableView<Fornecedor> tableView;
    private ObservableList<Fornecedor> listaPessoas;
    
    
    @Override
    public void start(Stage stage) {
        // Cabeçalho
        Text header = new Text("Fornecedores");
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
        
        
        Label tipoPesquisaLabel=new Label("Procurar por:");
        tipoPesquisaLabel.setFont(Font.font("Arial", 20));
        formGrid.add(tipoPesquisaLabel, 0, 2);
        
        RadioButton opcNumero = new RadioButton("Numero Documento");
        opcNumero.setFont(Font.font("Arial", 16));
        RadioButton opcNome = new RadioButton("Nome Fornecedor");
        opcNome.setFont(Font.font("Arial", 16));
        ToggleGroup grupo = new ToggleGroup();
        opcNumero.setToggleGroup(grupo);
        opcNome.setToggleGroup(grupo);
        formGrid.add(opcNumero,1, 2);
        formGrid.add(opcNome, 2, 2);
        
        
        Label tipoDocumentoLabel=new Label("Tipo Documento:");
        tipoDocumentoLabel.setFont(Font.font("Arial", 20));
        formGrid.add(tipoDocumentoLabel, 0, 3);
        tipoDocumentoLabel.setVisible(false);
        RadioButton opcCPF = new RadioButton("CPF");
        opcCPF.setFont(Font.font("Arial", 16));
        opcCPF.setVisible(false);
        RadioButton opcCNPJ = new RadioButton("CNPJ");
        opcCNPJ.setFont(Font.font("Arial", 16));
        opcCNPJ.setVisible(false);
        ToggleGroup grupo01 = new ToggleGroup();
        opcCPF.setToggleGroup(grupo01);
        opcCNPJ.setToggleGroup(grupo01);
        formGrid.add(opcCPF,1, 3);
        formGrid.add(opcCNPJ, 2, 3);
        
        
        
        Label numeDocLabel=new Label("Numero Documento:");
        numeDocLabel.setVisible(false);
        formGrid.add(numeDocLabel, 0, 4);
        
        TextField numDocField = new TextField();
        numDocField.setVisible(false);
        formGrid.add(numDocField, 1, 4,2,1);
        
        
        Label nomeLabel = new Label("Nome:");
        nomeLabel.setVisible(false);
        formGrid.add(nomeLabel, 0, 4);
        TextField nomeField = new TextField();
        nomeField.setVisible(false);
        formGrid.add(nomeField, 1,4, 3, 1);
        
        opcNumero.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	
            	if(opcNumero.isSelected()) {
            		tipoDocumentoLabel.setVisible(true);
            		opcCPF.setVisible(true);
            		opcCNPJ.setVisible(true);
            		numeDocLabel.setVisible(true);
            		numDocField.setVisible(true);
            		nomeLabel.setVisible(false);
            		nomeField.setVisible(false);
            		nomeField.clear();
               }
            	
            }
        });
        
        opcNome.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	
            	if(opcNome.isSelected()) {
            		tipoDocumentoLabel.setVisible(false);
            		opcCPF.setVisible(false);
            		opcCNPJ.setVisible(false);
            		numeDocLabel.setVisible(false);
            		numDocField.setVisible(false);
            		nomeLabel.setVisible(true);
            		nomeField.setVisible(true);
            		numDocField.clear();
            		
               }
            	
            }
        });
        
        
     // Criando a linha horizontal
        Line linha = new Line();
        linha.setStartX(0);  // Posição inicial no eixo X (esquerda)
        linha.setEndX(700);  // Posição final no eixo X (direita)
        linha.setStartY(100); // Posição inicial no eixo Y
        linha.setEndY(100);   // Mantendo a posição Y constante para ser horizontal
        
        // Definindo a cor e espessura da linha
        linha.setStroke(Color.BLACK);
        linha.setStrokeWidth(2);
        formGrid.add(linha, 0, 6,6,1);

        

       
        Button procurarButton = new Button("Procurar Fornecedor");
        formGrid.add(procurarButton, 2, 7);

        // Botão "Adicionar Novo"
        Button listarTodosButton = new Button("Listar Todos");
        HBox adicionarBox = new HBox(listarTodosButton);
        adicionarBox.setAlignment(Pos.CENTER_RIGHT);
        formGrid.add(adicionarBox, 3, 7);

        listaPessoas = FXCollections.observableArrayList();
        
        
        listarTodosButton.setOnAction(event -> {
            FornecedorRepository fnrp = new FornecedorRepository();
            List<Fornecedor> fornList = fnrp.ListarTodos();
            
            if (fornList != null && !fornList.isEmpty()) {
                this.listaPessoas.clear(); // Limpa a lista atual
                this.listaPessoas.addAll(fornList); // Adiciona todos os fornecedores
                // Atualiza a exibição na TableView
                tableView.setItems(listaPessoas);
            } else {
                // Pode exibir um alerta se a lista estiver vazia
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Informação");
                alert.setHeaderText(null);
                alert.setContentText("Nenhum fornecedor encontrado.");
                alert.showAndWait();
            }
        });
        
        procurarButton.setOnAction(event -> {
            FornecedorRepository fnrp = new FornecedorRepository();
           
            
            if (!numDocField.getText().isEmpty()) {
            	List<Fornecedor> fornList = fnrp.buscarPorNumDoc(numDocField.getText());
                this.listaPessoas.clear(); // Limpa a lista atual
                this.listaPessoas.addAll(fornList); // Adiciona todos os fornecedores
                // Atualiza a exibição na TableView
                tableView.setItems(listaPessoas);
            } else if(!nomeField.getText().isEmpty()){
            	List<Fornecedor> fornList = fnrp.buscarPorNome(nomeField.getText());
            	this.listaPessoas.clear(); // Limpa a lista atual
                this.listaPessoas.addAll(fornList); // Adiciona todos os fornecedores
                // Atualiza a exibição na TableView
                tableView.setItems(listaPessoas);     
            }
            else {
            	Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Informação");
                alert.setHeaderText("Erro!");
                alert.setContentText("Escolha uma das Opções!!");
                alert.showAndWait();
            	
            }
        }
       );
        
        
        
        // Botões de Ação
        Button cancelarButton = new Button("Cancelar");
        cancelarButton.setStyle("-fx-background-color: #E4E05D; -fx-text-fill: black;");
        Button cadastrarButton = new Button("Cadastrar");
        cadastrarButton.setStyle("-fx-background-color: #69C285; -fx-text-fill: black;");

        HBox actionButtons = new HBox(10, cancelarButton, cadastrarButton);
        actionButtons.setAlignment(Pos.CENTER);
        actionButtons.setPadding(new Insets(10));
        
        
     // Inicializando a lista de pessoas
        

        // Configurando a TableView
        tableView = new TableView<>();
        tableView.setItems(listaPessoas);

        // Coluna Nome
        TableColumn<Fornecedor, String> nomeColuna = new TableColumn<>("Nome");
        nomeColuna.setCellValueFactory(new PropertyValueFactory<>("nome"));
        nomeColuna.setPrefWidth(500);

        // Coluna CPF
        TableColumn<Fornecedor, String> tipoDoc = new TableColumn<>("Tipo Documento");
        tipoDoc.setCellValueFactory(new PropertyValueFactory<>("tipoDoc"));
        tipoDoc.setPrefWidth(100);
        
        // Coluna CPF
        TableColumn<Fornecedor, String> numDoc = new TableColumn<>("Numero Documento");
        numDoc.setCellValueFactory(new PropertyValueFactory<>("numDoc"));
        numDoc.setPrefWidth(300);

        // Coluna Ações: Atualizar, Apagar, Visualizar
        TableColumn<Fornecedor, Void> acoesColuna = new TableColumn<>("Ações");
        acoesColuna.setPrefWidth(300);
        acoesColuna.setCellFactory(param -> criarBotoesAcoes());

        // Adicionando as colunas à TableView
        tableView.getColumns().addAll(nomeColuna, tipoDoc,numDoc, acoesColuna);

        
        
        HBox CenterBox = new HBox(10,formGrid);
        CenterBox.setAlignment(Pos.CENTER);
        
        
        // Layout principal
        VBox mainLayout = new VBox(10, headerBox, CenterBox,tableView, actionButtons);
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
    
 // Método para criar os botões de ações
    private TableCell<Fornecedor, Void> criarBotoesAcoes() {
        return new TableCell<>() {
            private final Button atualizarButton = new Button("Atualizar");
            private final Button apagarButton = new Button("Apagar");
            private final Button visualizarButton = new Button("Visualizar");

            {
                atualizarButton.setOnAction(e -> abrirJanelaAtualizar(getTableView().getItems().get(getIndex())));
                apagarButton.setOnAction(e -> remover(getTableView().getItems().get(getIndex())));
                visualizarButton.setOnAction(e -> visualizarPessoa(getTableView().getItems().get(getIndex())));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox botoes = new HBox(10, atualizarButton, apagarButton, visualizarButton);
                    botoes.setAlignment(Pos.CENTER);
                    setGraphic(botoes);
                }
            }
        };
    }

    // Método para abrir a janela de atualização
    private void abrirJanelaAtualizar(Fornecedor fornecedor) {
        Stage janelaAtualizar = new Stage();
        janelaAtualizar.setTitle("Atualizar Pessoa");
     // GridPane layout com padding e gaps
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);

        // Label "Tipo de Documento" e RadioButtons (CPF, CNPJ)
        Label tipoDocLabel = new Label("Tipo de Documento:");
        RadioButton cpfRadioButton = new RadioButton("CPF");
        RadioButton cnpjRadioButton = new RadioButton("CNPJ");
        ToggleGroup tipoDocGroup = new ToggleGroup();
        cpfRadioButton.setToggleGroup(tipoDocGroup);
        cnpjRadioButton.setToggleGroup(tipoDocGroup);
        if ("CPF".equals(fornecedor.getTipoDoc())) {
            cpfRadioButton.setSelected(true);
        } else if ("CNPJ".equals(fornecedor.getTipoDoc())) {
            cnpjRadioButton.setSelected(true);
        }


        // Adicionando ao GridPane
        gridPane.add(tipoDocLabel, 0, 0);
        gridPane.add(cpfRadioButton, 1, 0);
        gridPane.add(cnpjRadioButton, 2, 0);

        // Label e TextField "Número do Documento"
        Label numDocLabel = new Label("Número do Documento:");
        TextField numDocField = new TextField();
        gridPane.add(numDocLabel, 0, 1);
        gridPane.add(numDocField, 1, 1, 2, 1);
        numDocField.setText(fornecedor.getNumDoc());
        
        // Label e TextField "Nome"
        Label nomeLabel = new Label("Nome:");
        TextField nomeField = new TextField();
        gridPane.add(nomeLabel, 0, 2);
        gridPane.add(nomeField, 1, 2, 2, 1);
        nomeField.setText(fornecedor.getNome());
        // Label e TextField "CEP"
        Label cepLabel = new Label("CEP:");
        TextField cepField = new TextField();
        gridPane.add(cepLabel, 0, 3);
        gridPane.add(cepField, 1, 3, 2, 1);
        cepField.setText(fornecedor.getCep());
        // Label e TextField "Rua"
        Label ruaLabel = new Label("Rua:");
        TextField ruaField = new TextField();
        gridPane.add(ruaLabel, 0, 4);
        gridPane.add(ruaField, 1, 4, 2, 1);
        ruaField.setText(fornecedor.getRua());
        // Label e TextField "Bairro"
        Label bairroLabel = new Label("Bairro:");
        TextField bairroField = new TextField();
        gridPane.add(bairroLabel, 0, 5);
        gridPane.add(bairroField, 1, 5, 2, 1);
        bairroField.setText(fornecedor.getBairro());
        // Label e TextField "Cidade"
        Label cidadeLabel = new Label("Cidade:");
        TextField cidadeField = new TextField();
        gridPane.add(cidadeLabel, 0, 6);
        gridPane.add(cidadeField, 1, 6, 2, 1);
        cidadeField.setText(fornecedor.getCidade());
        // Label e TextField "Estado"
        Label estadoLabel = new Label("Estado:");
        TextField estadoField = new TextField();
        gridPane.add(estadoLabel, 0, 7);
        gridPane.add(estadoField, 1, 7, 2, 1);
        estadoField.setText(fornecedor.getEstado());
        // Label e TextField "Número Endereço"
        Label numEnderecoLabel = new Label("Número Endereço:");
        TextField numEnderecoField = new TextField();
        gridPane.add(numEnderecoLabel, 0, 8);
        gridPane.add(numEnderecoField, 1, 8, 2, 1);
        numEnderecoField.setText(fornecedor.getNumEndereco());
        // Label e TextField "Telefone"
        Label telefoneLabel = new Label("Telefone:");
        TextField telefoneField = new TextField();
        gridPane.add(telefoneLabel, 0, 9);
        gridPane.add(telefoneField, 1, 9, 2, 1);
        telefoneField.setText(fornecedor.getTelefone());
        // Botão "Salvar"
        Button salvarButton = new Button("Salvar");
        gridPane.add(salvarButton, 1, 10);
        // Configuração da cena e exibição
        Scene scene = new Scene(gridPane, 500, 500);
        
        salvarButton.setOnAction(event -> {
            // Atualiza o objeto fornecedor com os novos valores
            fornecedor.setBairro(bairroField.getText());
            fornecedor.setCep(cepField.getText());
            fornecedor.setCidade(cidadeField.getText());
            fornecedor.setEstado(estadoField.getText());
            fornecedor.setNome(nomeField.getText());
            fornecedor.setNumDoc(numDocField.getText());
            fornecedor.setNumEndereco(numEnderecoField.getText());
            fornecedor.setRua(ruaField.getText());
            fornecedor.setTelefone(telefoneField.getText());

            String tipoDoc;
            if (cpfRadioButton.isSelected()) {
                tipoDoc = "CPF";
            } else {
                tipoDoc = "CNPJ";
            }
            fornecedor.setTipoDoc(tipoDoc);

            // Atualiza o fornecedor no banco de dados
            FornecedorRepository fnrp = new FornecedorRepository();
            fnrp.update(fornecedor);

            // Atualiza a lista na TableView
            List<Fornecedor> fornList = fnrp.ListarTodos();
            this.listaPessoas.clear();
            this.listaPessoas.addAll(fornList);
            tableView.setItems(listaPessoas);

            // Fecha a janela de atualização
            janelaAtualizar.close();
        });
        
        
        janelaAtualizar.setScene(scene);
        janelaAtualizar.show();
    }

    // Método para visualizar os detalhes de uma pessoa
    private void visualizarPessoa(Fornecedor fornecedor) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Detalhes da Pessoa");
        alert.setHeaderText(null);
        alert.setContentText("Nome: " + fornecedor.getNome() + "\nCPF: ");
        alert.showAndWait();
    }
    
    private void remover(Fornecedor fornecedor) {
    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    	alert.setTitle("Deletar");
    	alert.setHeaderText("Deseja mesmo deletar do Banco de Dados?");

    	// Exibe o alerta e espera pela resposta do usuário
    	alert.showAndWait();

    	// Captura o resultado da escolha do usuário
    	if (alert.getResult() == ButtonType.OK) {
    		FornecedorRepository fnrp = new FornecedorRepository();
        	fnrp.deletar(fornecedor.getFornecedor_id());
        	List<Fornecedor> fornList = fnrp.ListarTodos();
        	this.listaPessoas.clear();
            this.listaPessoas.addAll(fornList);
            tableView.setItems(listaPessoas);

    	} 

    }

    public static void main(String[] args) {
        launch(args);
    }
}
