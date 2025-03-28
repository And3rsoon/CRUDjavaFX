package View;

import java.util.List;
import Model.categoria;
import Repository.categoriaRepository;
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
import javafx.scene.control.ScrollPane;
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

public class CadastroCategorias extends Application {


private TableView<categoria> tableView;
private ObservableList<categoria> listaCategoria;


@Override
public void start(Stage stage) {
    // Cabeçalho
    Text header = new Text("Categorias");
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
    
    Label nomeCatLabel=new Label("Nome Categoria:");
    nomeCatLabel.setFont(new Font("Arial",16));
    formGrid.add(nomeCatLabel, 0, 1);
    
    TextField nomeCatField = new TextField();
    nomeCatField.setPrefHeight(30);
    formGrid.add(nomeCatField, 1, 1,4,1);
    
    
    Button Adicionar = new Button("Adicionar");
    formGrid.add(Adicionar, 5, 1);
    Adicionar.setStyle("-fx-background-radius: 15;"
    		+               "-fx-border-radius: 10;"
    		+                "-fx-pref-width: 150px;"
    		+                "-fx-pref-height: 10px;"
    		+                "-fx-font-size: 16px;"
    		+                "-fx-text-wrap: wrap;");
    
    Button Retornar = new Button("Retornar");
    Retornar.setStyle("-fx-background-radius: 15;"
    		+               "-fx-border-radius: 10;"
    		+                "-fx-pref-width: 150px;"
    		+                "-fx-pref-height: 40px;"
    		+                "-fx-font-size: 16px;"
    		+                "-fx-text-wrap: wrap;");
    
    Adicionar.setOnAction(event -> {
        categoriaRepository fnrp = new categoriaRepository();
        categoria ct=new categoria();
        if(!nomeCatField.getText().isEmpty()) {
              ct.setCategoria(nomeCatField.getText());
              fnrp.gavar(ct);
        }
        else {
        	
        	Alert alert = new Alert(Alert.AlertType.INFORMATION, "Insira uma categoria válida!");
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
    formGrid.add(linha, 0, 2,6,1);

    
    // Botão "Adicionar Novo"
    Button listarTodosButton = new Button("Categorias Cadastradas");
    listarTodosButton.setStyle("-fx-background-radius: 15;"
    		+               "-fx-border-radius: 10;"
    		+                "-fx-pref-width: 200px;"
    		+                "-fx-pref-height: 10px;"
    		+                "-fx-font-size: 16px;"
    		+                "-fx-text-wrap: wrap;");
    HBox adicionarBox = new HBox(listarTodosButton);
    adicionarBox.setAlignment(Pos.CENTER_RIGHT);
    formGrid.add(adicionarBox, 5, 3);

    listaCategoria = FXCollections.observableArrayList();
    
    listarTodosButton.setOnAction(event -> {
    	categoriaRepository fnrp = new categoriaRepository();
        List<categoria> fornList = fnrp.ListarTodos();
        
      if (fornList != null && !fornList.isEmpty()) {
            this.listaCategoria.clear(); // Limpa a lista atual
            this.listaCategoria.addAll(fornList); // Adiciona todos os fornecedores
            // Atualiza a exibição na TableView
            tableView.setItems(listaCategoria);
        } else {
            // Pode exibir um alerta se a lista estiver vazia
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informação");
            alert.setHeaderText(null);
            alert.setContentText("Nenhuma Categoria encontrado.");
            alert.showAndWait();
        }
    });
    

    // Configurando a TableView
    tableView = new TableView<>();
    tableView.setItems(listaCategoria);
    tableView.setPrefWidth(900);
    tableView.setMaxWidth(900);
    formGrid.add(tableView, 0, 4,6,1);
    
    TableColumn<categoria, String> tipoDoc = new TableColumn<>("Id Categoria");
    tipoDoc.setCellValueFactory(new PropertyValueFactory<>("categoraId"));
    tipoDoc.setPrefWidth(100);
    // Coluna Nome
    TableColumn<categoria, String> nomeColuna = new TableColumn<>("Nome");
    nomeColuna.setCellValueFactory(new PropertyValueFactory<>("categoria"));
    nomeColuna.setPrefWidth(500);

    
    // Coluna Ações: Atualizar, Apagar, Visualizar
    TableColumn<categoria, Void> acoesColuna = new TableColumn<>("Ações");
    acoesColuna.setPrefWidth(300);
    acoesColuna.setCellFactory(param -> criarBotoesAcoes());

    // Adicionando as colunas à TableView
    tableView.getColumns().addAll(tipoDoc,nomeColuna, acoesColuna);

    
    
    HBox CenterBox = new HBox(10,formGrid);
    CenterBox.setAlignment(Pos.CENTER);
    
    
    // Layout principal
    VBox mainLayout = new VBox(10, headerBox, CenterBox,Retornar);
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

// Método para criar os botões de ações
private TableCell<categoria, Void> criarBotoesAcoes() {
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
private void abrirJanelaAtualizar(categoria categoria) {
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
    nomeCategoria.setText(categoria.getCategoria());
    
    
    // Botão "Salvar"
    Button salvarButton = new Button("Salvar");
    gridPane.add(salvarButton, 1, 10);
    // Configuração da cena e exibição
    Scene scene = new Scene(gridPane, 500, 500);
    
    salvarButton.setOnAction(event -> {
         categoria ct=new categoria();
         ct.setCategoraId(categoria.getCategoraId());
         ct.setCategoria(nomeCategoria.getText());
         categoriaRepository ctrp=new categoriaRepository();
         ctrp.update(ct);
        // Atualiza a lista na TableView
        List<categoria> fornList = ctrp.ListarTodos();
        this.listaCategoria.clear();
        this.listaCategoria.addAll(fornList);
        tableView.setItems(listaCategoria);

        // Fecha a janela de atualização
        janelaAtualizar.close();
    });
    
    
    janelaAtualizar.setScene(scene);
    janelaAtualizar.show();
}

private void remover(categoria categoria) {
	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	alert.setTitle("Deletar");
	alert.setHeaderText("Deseja mesmo deletar do Banco de Dados?");

	// Exibe o alerta e espera pela resposta do usuário
	alert.showAndWait();

	// Captura o resultado da escolha do usuário
	if (alert.getResult() == ButtonType.OK) {
		categoriaRepository fnrp = new categoriaRepository();
    	fnrp.deletar(categoria);
    	List<categoria> fornList = fnrp.ListarTodos();
    	this.listaCategoria.clear();
        this.listaCategoria.addAll(fornList);
        tableView.setItems(listaCategoria);
	} 

}

public static void main(String[] args) {
    launch(args);
}
}
