package View;
import java.util.List;
import Model.categoria;
import Model.unidadeMedida;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

public class cadastroUnMedida extends Application {


private TableView<unidadeMedida> tableView;
private ObservableList<unidadeMedida> listaCategoria;


@Override
public void start(Stage stage) {
    // Cabeçalho
    Text header = new Text("Unidade de Medida");
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
    
    Label nomeUnLabel=new Label("Nome Unidade:");
    formGrid.add(nomeUnLabel, 0, 1);
    
    TextField nomeUnField = new TextField();
    formGrid.add(nomeUnField, 1, 1,4,1);
    
    Label abreviacaoLabel=new Label("Abreviação:");
    formGrid.add(abreviacaoLabel, 0, 2);
    
    TextField abreviacaoField = new TextField();
    formGrid.add(abreviacaoField, 1, 2,4,1);
    
    Label quantidadeLabel=new Label("Quantidade:");
    formGrid.add(quantidadeLabel, 0, 3);
    
    TextField quantidaeField = new TextField();
    formGrid.add(quantidaeField, 1, 3,4,1);
    
    Button Adicionar = new Button("Adicionar");
    formGrid.add(Adicionar, 5, 3);
    
    Button Retornar = new Button("Retornar");
    formGrid.add(Retornar, 5, 7);
    
    Retornar.setOnAction(event -> {
     
    }
   );
    
    Adicionar.setOnAction(event -> {
        unidadeMedRepository fnrp = new unidadeMedRepository();
        unidadeMedida un=new unidadeMedida();
        if(!nomeUnField.getText().isEmpty() && !abreviacaoField.getText().isEmpty() && !quantidaeField.getText().isEmpty()) {
              un.setUnMedida(nomeUnField.getText());
              un.setAbreviacao(abreviacaoField.getText());
              un.setQuantidade(Integer.parseInt(quantidaeField.getText()));
              String result=fnrp.gavar(un);
              if(result.equalsIgnoreCase("ok")) {
            	  nomeUnField.clear();
            	  abreviacaoField.clear();
            	  quantidaeField.clear();
            	  Alert alert = new Alert(Alert.AlertType.INFORMATION, "Unidade Cadastrada!");
                  List<unidadeMedida> fornList1 = fnrp.ListarTodos();
                  this.listaCategoria.clear(); // Limpa a lista atual
                  this.listaCategoria.addAll(fornList1);
                  // Atualiza a exibição na TableView
                   tableView.setItems(listaCategoria);
              }
                
        }
        else {
        	
        	Alert alert = new Alert(Alert.AlertType.INFORMATION, "Preencha Todos os Campos!");
            alert.show();
        }
        }    
   );
    
    Retornar.setOnAction(event -> {
    	stage.close();
    	
    });
    
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
    Button listarTodosButton = new Button("Categorias Cadastradas");
    HBox adicionarBox = new HBox(listarTodosButton);
    adicionarBox.setAlignment(Pos.CENTER_RIGHT);
    formGrid.add(adicionarBox, 5, 5);

    listaCategoria = FXCollections.observableArrayList();
    
    listarTodosButton.setOnAction(event -> {
    	unidadeMedRepository fnrp = new unidadeMedRepository();
        List<unidadeMedida> fornList = fnrp.ListarTodos();
        
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
    tableView.setPrefWidth(600);
    tableView.setMaxWidth(900);
    formGrid.add(tableView, 0, 6,6,1);
    
    TableColumn<unidadeMedida, String> tipoDoc = new TableColumn<>("Id Unidade");
    tipoDoc.setCellValueFactory(new PropertyValueFactory<>("idUnMedida"));
    tipoDoc.setPrefWidth(100);
    // Coluna Nome
    TableColumn<unidadeMedida, String> nomeColuna = new TableColumn<>("Nome");
    nomeColuna.setCellValueFactory(new PropertyValueFactory<>("unMedida"));
    nomeColuna.setPrefWidth(150);
    
    TableColumn<unidadeMedida, String> abreviacaoColuna = new TableColumn<>("Abreviação");
    abreviacaoColuna.setCellValueFactory(new PropertyValueFactory<>("abreviacao"));
    abreviacaoColuna.setPrefWidth(150);

    TableColumn<unidadeMedida, String> quantidadeColuna = new TableColumn<>("Quantidade");
    quantidadeColuna.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
    quantidadeColuna.setPrefWidth(150);
    
    // Coluna Ações: Atualizar, Apagar, Visualizar
    TableColumn<unidadeMedida, Void> acoesColuna = new TableColumn<>("Ações");
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
private TableCell<unidadeMedida, Void> criarBotoesAcoes() {
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
private void abrirJanelaAtualizar(unidadeMedida categoria) {
    Stage janelaAtualizar = new Stage();
    janelaAtualizar.setTitle("Atualizar Pessoa");
 // GridPane layout com padding e gaps
    GridPane gridPane = new GridPane();
    gridPane.setPadding(new Insets(20));
    gridPane.setHgap(10);
    gridPane.setVgap(10);
    gridPane.setAlignment(Pos.CENTER);


    // Label e TextField "Número do Documento"
    Label numDocLabel = new Label("Categoria:");
    TextField nomeCategoria = new TextField();
    gridPane.add(numDocLabel, 0, 1);
    gridPane.add(nomeCategoria, 1, 1, 2, 1);
    
    // Botão "Salvar"
    Button salvarButton = new Button("Salvar");
    gridPane.add(salvarButton, 1, 10);
    // Configuração da cena e exibição
    Scene scene = new Scene(gridPane, 500, 500);
    
    salvarButton.setOnAction(event -> {
         categoria ct=new categoria();
         //ct.setId_categora(categoria.getId_categora());
         ct.setCategoria(nomeCategoria.getText());
         categoriaRepository ctrp=new categoriaRepository();
         ctrp.update(ct);
        // Atualiza a lista na TableView
        List<categoria> fornList = ctrp.ListarTodos();
        this.listaCategoria.clear();
       // this.listaCategoria.addAll(fornList);
        tableView.setItems(listaCategoria);

        // Fecha a janela de atualização
        janelaAtualizar.close();
    });
    
    
    janelaAtualizar.setScene(scene);
    janelaAtualizar.show();
}

private void remover(unidadeMedida categoria) {
	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	alert.setTitle("Deletar");
	alert.setHeaderText("Deseja mesmo deletar do Banco de Dados?");

	// Exibe o alerta e espera pela resposta do usuário
	alert.showAndWait();

	// Captura o resultado da escolha do usuário
	if (alert.getResult() == ButtonType.OK) {
		unidadeMedRepository fnrp = new unidadeMedRepository();
    	fnrp.deletar(categoria);
    	List<unidadeMedida> fornList = fnrp.ListarTodos();
    	this.listaCategoria.clear();
        this.listaCategoria.addAll(fornList);
        tableView.setItems(listaCategoria);
	} 

}

public static void main(String[] args) {
    launch(args);
}
}
