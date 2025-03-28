package View;
import java.util.List;
import Model.categoria;
import Model.remetenteNota;
import Model.unidadeMedida;
import Repository.cadRemetenteRepository;
import Repository.categoriaRepository;
import Repository.unidadeMedRepository;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class cadastroRemetente extends Application {


private TableView<remetenteNota> tableView;
private ObservableList<remetenteNota> listaCategoria;


@Override
public void start(Stage stage) {
    // Cabeçalho
    Text header = new Text("Cadastro Remetente");
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
    
    Label tipoDocumentoLabel=new Label("Tipo Documento:");
    tipoDocumentoLabel.setFont(Font.font("Arial", 20));
    formGrid.add(tipoDocumentoLabel, 0, 1);
    
    RadioButton opcCPF = new RadioButton("CPF");
    opcCPF.setFont(Font.font("Arial", 16));
    RadioButton opcCNPJ = new RadioButton("CNPJ");
    opcCNPJ.setFont(Font.font("Arial", 16));
    ToggleGroup grupo01 = new ToggleGroup();
    opcCPF.setToggleGroup(grupo01);
    opcCNPJ.setToggleGroup(grupo01);
    formGrid.add(opcCPF,1, 1);
    formGrid.add(opcCNPJ, 2, 1);
    
    Label numeroLabel=new Label("Numero Doc:");
    formGrid.add(numeroLabel, 0, 2);
    
    TextField numeroField = new TextField();
    formGrid.add(numeroField, 1, 2,4,1);
    
    Label nomeLabel=new Label("Nome:");
    formGrid.add(nomeLabel, 0, 3);
    
    TextField nomeField = new TextField();
    formGrid.add(nomeField, 1, 3,4,1);
    
    Button Adicionar = new Button("Adicionar");
    formGrid.add(Adicionar, 5, 3);
    
    Button Retornar = new Button("Retornar");
    formGrid.add(Retornar, 5, 7);
    
    Retornar.setOnAction(event -> {
    }
   );
    
    Adicionar.setOnAction(event -> {
        cadRemetenteRepository fnrp = new cadRemetenteRepository();
        remetenteNota remetente=new remetenteNota();
        String opcao;
        if(opcCPF.isSelected()) {
        	opcao="CPF";
        }
        else if(opcCNPJ.isSelected()) {
        	opcao="CNPJ";
        }
        else {
        	opcao=null;
        }
        
        String nome=nomeField.getText();
        Long numero =Long.parseLong(numeroField.getText());
        		
        		
        if(opcao!=null && nome!=null && numero!=null) {
        	try {
		        	remetente.setNome(nome);
		        	remetente.setNumero(numero);
		            remetente.setTipoDoc(opcao);
		            fnrp.gavar(remetente);
		            numeroField.clear();
		            nomeField.clear();
		        	Alert alert = new Alert(Alert.AlertType.INFORMATION, "Remetente Cadastrado!");
		            alert.show();
		            
        	}
        	catch(Exception e) {
        		Alert alert = new Alert(Alert.AlertType.INFORMATION, "Verifique os dados!");
	            alert.show();
        		
        	}
        }
        else {
        	
        	Alert alert = new Alert(Alert.AlertType.INFORMATION, "Preencha Todos os Campos!");
            alert.show();
        }
        }    
   );
    
    
    
 // Criando a linha horizontal
    Line linha = new Line();
    linha.setStartX(0);  // Posição inicial no eixo X (esquerda)
    linha.setEndX(900);  // Posição final no eixo X (direita)
    linha.setStartY(100); // Posição inicial no eixo Y
    linha.setEndY(100);   // Mantendo a posição Y constante para ser horizontal
    
    // Definindo a cor e espessura da linha
    linha.setStroke(Color.BLACK);
    linha.setStrokeWidth(2);
    formGrid.add(linha, 0, 4,6,1);

    
    // Botão "Adicionar Novo"
    Button listarTodosButton = new Button("Remetentes Cadastradas");
    HBox adicionarBox = new HBox(listarTodosButton);
    adicionarBox.setAlignment(Pos.CENTER_RIGHT);
    formGrid.add(adicionarBox, 5, 5);

    listaCategoria = FXCollections.observableArrayList();
    
    listarTodosButton.setOnAction(event -> {
    	cadRemetenteRepository fnrp = new cadRemetenteRepository();
        List<remetenteNota> fornList = fnrp.ListarTodos();
        
      if (fornList != null && !fornList.isEmpty()) {
            this.listaCategoria.clear(); // Limpa a lista atual
            this.listaCategoria.addAll(fornList);
            // Atualiza a exibição na TableView
            tableView.setItems(listaCategoria);
        } else {
            // Pode exibir um alerta se a lista estiver vazia
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informação");
            alert.setHeaderText(null);
            alert.setContentText("Nenhuma Unidade encontrada.");
            alert.showAndWait();
        }
    });
    

    // Configurando a TableView
    tableView = new TableView<>();
    tableView.setItems(listaCategoria);
    tableView.setPrefWidth(850);
    tableView.setMaxWidth(900);
    formGrid.add(tableView, 0, 6,6,1);
    
    TableColumn<remetenteNota, String> tipoDoc = new TableColumn<>("Id Remetente");
    tipoDoc.setCellValueFactory(new PropertyValueFactory<>("remetenteId"));
    tipoDoc.setPrefWidth(100);
    // Coluna Nome
    TableColumn<remetenteNota, String> nomeColuna = new TableColumn<>("Nome");
    nomeColuna.setCellValueFactory(new PropertyValueFactory<>("nome"));
    nomeColuna.setPrefWidth(150);
    
    TableColumn<remetenteNota, String> abreviacaoColuna = new TableColumn<>("Tipo Doc");
    abreviacaoColuna.setCellValueFactory(new PropertyValueFactory<>("tipoDoc"));
    abreviacaoColuna.setPrefWidth(150);

    TableColumn<remetenteNota, Integer> quantidadeColuna = new TableColumn<>("Numero");
    quantidadeColuna.setCellValueFactory(new PropertyValueFactory<>("numero"));
    quantidadeColuna.setPrefWidth(150);
    
    // Coluna Ações: Atualizar, Apagar, Visualizar
    TableColumn<remetenteNota, Void> acoesColuna = new TableColumn<>("Ações");
    acoesColuna.setPrefWidth(300);
    acoesColuna.setCellFactory(param -> criarBotoesAcoes());

    // Adicionando as colunas à TableView
    tableView.getColumns().addAll(tipoDoc,nomeColuna,abreviacaoColuna,quantidadeColuna, acoesColuna);

    HBox CenterBox = new HBox(10,formGrid);
    CenterBox.setAlignment(Pos.CENTER);
    
    // Layout principal
    VBox mainLayout = new VBox(10, headerBox, CenterBox);
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
private TableCell<remetenteNota, Void> criarBotoesAcoes() {
    return new TableCell<>() {
        private final Button atualizarButton = new Button("Atualizar");
        private final Button apagarButton = new Button("Apagar");
        {
            atualizarButton.setOnAction(e -> abrirJanelaAtualizar(getTableView().getItems().get(getIndex())));
            apagarButton.setOnAction(e -> remover(getTableView().getItems().get(getIndex())));            
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                HBox botoes = new HBox(10, atualizarButton, apagarButton);
                botoes.setAlignment(Pos.CENTER);
                setGraphic(botoes);
            }
        }
    };
}

// Método para abrir a janela de atualização
private void abrirJanelaAtualizar(remetenteNota categoria) {
    Stage janelaAtualizar = new Stage();
    janelaAtualizar.setTitle("Atualizar Remetente");
 // GridPane layout com padding e gaps
    GridPane gridPane = new GridPane();
    gridPane.setPadding(new Insets(20));
    gridPane.setHgap(10);
    gridPane.setVgap(10);
    gridPane.setAlignment(Pos.CENTER);


    // Label e TextField "Número do Documento"
    Label tipoDocumentoLabel=new Label("Tipo Documento:");
    tipoDocumentoLabel.setFont(Font.font("Arial", 20));
    gridPane.add(tipoDocumentoLabel, 0, 1);
    RadioButton opcCPF = new RadioButton("CPF");
    opcCPF.setFont(Font.font("Arial", 16));
    RadioButton opcCNPJ = new RadioButton("CNPJ");
    opcCNPJ.setFont(Font.font("Arial", 16));
    ToggleGroup grupo01 = new ToggleGroup();
    opcCPF.setToggleGroup(grupo01);
    opcCNPJ.setToggleGroup(grupo01);
    gridPane.add(opcCPF,1, 1);
    gridPane.add(opcCNPJ, 2, 1);
    
    if(categoria.getTipoDoc().equalsIgnoreCase("cpf")) {
    	opcCPF.setSelected(true);
    }
    else {
    	opcCNPJ.setSelected(true);
    }
    
    Label numeroLabel=new Label("Numero Doc:");
    gridPane.add(numeroLabel, 0, 2);
    
    TextField numeroField = new TextField();
    gridPane.add(numeroField, 1, 2,4,1);
    numeroField.setText(categoria.getNumero().toString());
    
    Label nomeLabel=new Label("Nome:");
    gridPane.add(nomeLabel, 0, 3);
    
    TextField nomeField = new TextField();
    gridPane.add(nomeField, 1, 3);
    nomeField.setText(categoria.getNome());
    
    // Botão "Salvar"
    Button salvarButton = new Button("Salvar");
    gridPane.add(salvarButton, 1, 10);
    Button cancelarButton = new Button("Cancelar");
    gridPane.add(cancelarButton, 2, 10);
    // Configuração da cena e exibição
    Scene scene = new Scene(gridPane, 500, 500);
    
    cancelarButton.setOnAction(event -> {
    	
        // Fecha a janela de atualização
        janelaAtualizar.close();
    });
    
    salvarButton.setOnAction(event -> {
    	String opcao;
        if(opcCPF.isSelected()) {
        	opcao="CPF";
        }
        else if(opcCNPJ.isSelected()) {
        	opcao="CNPJ";
        }
        else {
        	opcao=null;
        }
        categoria.setNome(nomeField.getText());
        categoria.setNumero(Long.parseLong(numeroField.getText()));
        categoria.setTipoDoc(opcao);
         cadRemetenteRepository ctrp=new cadRemetenteRepository();
         ctrp.update(categoria);
        // Atualiza a lista na TableView
        List<remetenteNota> fornList = ctrp.ListarTodos();
        this.listaCategoria.clear();
        this.listaCategoria.addAll(fornList);
        tableView.setItems(listaCategoria);

        // Fecha a janela de atualização
        janelaAtualizar.close();
    });
    
    janelaAtualizar.setScene(scene);
    janelaAtualizar.show();
}

private void remover(remetenteNota categoria) {
	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	alert.setTitle("Deletar");
	alert.setHeaderText("Deseja mesmo deletar do Banco de Dados?");

	// Exibe o alerta e espera pela resposta do usuário
	alert.showAndWait();

	// Captura o resultado da escolha do usuário
	if (alert.getResult() == ButtonType.OK) {
		cadRemetenteRepository fnrp = new cadRemetenteRepository();
    	fnrp.deletar(categoria);
    	List<remetenteNota> fornList = fnrp.ListarTodos();
    	this.listaCategoria.clear();
        this.listaCategoria.addAll(fornList);
        tableView.setItems(listaCategoria);
	} 

}

public static void main(String[] args) {
    launch(args);
}
}
