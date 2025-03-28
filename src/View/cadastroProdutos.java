package View;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import Model.Fornecedor;
import Model.categoria;
import Model.produto;
import Model.unidadeMedida;
import Repository.categoriaRepository;
import Repository.produtoRepository;
import Repository.unidadeMedRepository;
import Repository.FornecedorRepository;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class cadastroProdutos extends Application {
	  private TextField codigoBarrasField;
	  private ImageView imageView;
	  private String pathImagem;
	  private File arquivoSelecionado;
	  private Fornecedor fornecedorFinal;
	  private categoria categoriaFinal;
	  private unidadeMedida unFinal;
	
    @Override
    public void start(Stage primaryStage) {
    	
    	 //Cabeçalho
        Text header = new Text("Cadastro Produtos");
        header.setFont(Font.font("Arial", 36));
        header.setFill(Color.BLACK);
        
        HBox headerBox = new HBox(10,header);
        HBox.setMargin(header, new Insets(50));
        headerBox.setAlignment(Pos.CENTER_LEFT);
        headerBox.setPrefHeight(80);
        headerBox.setBackground(new Background(new BackgroundFill(
                Color.web("#69C285"), CornerRadii.EMPTY, Insets.EMPTY)));
        

    	GridPane gridpane = new GridPane(10,10);
    	gridpane.setPadding(new Insets(20));
        
    	Label cdBarraLabel=new Label("Código de Barras:");
    	cdBarraLabel.setFont(new Font("arial",20));
    	gridpane.add(cdBarraLabel, 0, 0);
    	
        // Campos do formulário
        codigoBarrasField = new TextField();
        codigoBarrasField.setPromptText("Código de Barras");
        gridpane.add(codigoBarrasField, 1, 0,6,1);
        
        Label cdNomeLabel=new Label("Nome:");
        cdNomeLabel.setFont(new Font("arial",20));
    	gridpane.add(cdNomeLabel, 0, 1);
        
        TextField nomeField = new TextField();
        nomeField.setPromptText("Nome");
        gridpane.add(nomeField, 1, 1,6,1);
        
        
        Label cdfornecedorLabel=new Label("Fornecedor: ");
        cdfornecedorLabel.setFont(new Font("arial",20));
    	gridpane.add(cdfornecedorLabel, 0, 2);
    	
    	 //Busca Todas as categorias registrada para popular o choiceBox
        ArrayList<String> fornecedores=new ArrayList<>();
        List<Fornecedor> fornecedoresList=new FornecedorRepository().ListarTodos();
        	if(fornecedoresList!=null) {
        		fornecedoresList.forEach(nome->fornecedores.add(nome.getNumDoc()+"|"+nome.getNome()));
        		}
        ObservableList<String> fornecedorItems = FXCollections.observableArrayList(fornecedores);
        // Criar um ChoiceBox e adicionar opções
        ChoiceBox<String> fornecedorBox = new ChoiceBox<>(fornecedorItems);
        fornecedorBox.setValue(" ");
        fornecedorBox.setPrefWidth(300);
        gridpane.add(fornecedorBox, 1,2,6,1);
        fornecedorBox.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	int index=fornecedorBox.getSelectionModel().getSelectedIndex();
            	fornecedorFinal=fornecedoresList.get(index);
            }
            });
       
        Label unMedidaLabel=new Label("Un Medida: ");
        unMedidaLabel.setFont(new Font("arial",20));
    	gridpane.add(unMedidaLabel, 0, 3);
        
    	//Busca Todas as categorias registrada para popular o choiceBox
        ArrayList<String> unmedidaArray=new ArrayList<>();
        List<unidadeMedida> unmedidaList=new unidadeMedRepository().ListarTodos();
        	if(unmedidaList!=null) {
        		unmedidaList.forEach(nome->unmedidaArray.add(nome.getUnMedida()));
        	}
        ObservableList<String> unMedItems = FXCollections.observableArrayList(unmedidaArray);
        ChoiceBox<String> unMedBox = new ChoiceBox<>(unMedItems);
        gridpane.add(unMedBox, 1, 3);
        unMedBox.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	int index=unMedBox.getSelectionModel().getSelectedIndex();
            	unFinal=unmedidaList.get(index);
            }
            });
        
        
        TextField unidadeMedidaField = new TextField();
        unidadeMedidaField.setPromptText("Unid Medida");
        

        Label categoriaLabel=new Label("Categoria: ");
        categoriaLabel.setFont(new Font("arial",20));
    	gridpane.add(categoriaLabel, 2, 3);
    	
    	 //Busca Todas as categorias registrada para popular o choiceBox
        ArrayList<String> categorias=new ArrayList<>();
        List<categoria> categoriaList=new categoriaRepository().ListarTodos();
        	if(categoriaList!=null) {
        		categoriaList.forEach(nome->categorias.add(nome.getCategoria()));
        		}
        ObservableList<String> categoriaItems = FXCollections.observableArrayList(categorias);
        // Criar um ChoiceBox e adicionar opções
        ChoiceBox<String> CategoriaBox = new ChoiceBox<>(categoriaItems);
        CategoriaBox.setValue(" ");
        gridpane.add(CategoriaBox, 3,3);
        CategoriaBox.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	int index=CategoriaBox.getSelectionModel().getSelectedIndex();
            	categoriaFinal=categoriaList.get(index);
            }
            });
    	
        Label quantMinLabel=new Label("Quantidade Minima: ");
        quantMinLabel.setFont(new Font("arial",20));
    	gridpane.add(quantMinLabel, 0, 5);
    	
        TextField quantMinField = new TextField();
        quantMinField.setPromptText("Quantidade Mínima");
        gridpane.add(quantMinField, 1, 5);
        
        Label quantMaxLabel=new Label("Quantidade Máxima: ");
        quantMaxLabel.setFont(new Font("arial",20));
    	gridpane.add(quantMaxLabel, 2, 5);
    	
        TextField quantMaxField = new TextField();
        quantMaxField.setPromptText("Quantidade Máxima");
        gridpane.add(quantMaxField, 3, 5);

        
        Label urlLabel=new Label("Url Label: ");
        urlLabel.setFont(new Font("arial",20));
    	gridpane.add(urlLabel, 0, 6);
    	
        TextField urlImagemField = new TextField();
        urlImagemField.setPromptText("Url Imagem");
        gridpane.add(urlImagemField, 1, 6,4,1);
        

        // ImageView para mostrar a imagem escolhida
        imageView = new ImageView();
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);
        imageView.setPreserveRatio(true);
        gridpane.add(imageView, 5, 0,10,10);

        Button procurarImagemButton = new Button("Procurar Imagem");
        procurarImagemButton.setOnAction(e -> abrirSeletorDeArquivos(urlImagemField));
        gridpane.add(procurarImagemButton, 4, 6);

        // Botões de ação
        Button cadastrarButton = new Button("Cadastrar");
        cadastrarButton.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            
            	String cd=codigoBarrasField.getText();
            	String nome=nomeField.getText();
            	int quantMin=Integer.parseInt(quantMinField.getText());
            	int quantMax=Integer.parseInt(quantMaxField.getText());
            	if(fornecedorFinal!=null && unFinal!=null && categoriaFinal!=null && cd!=null && nome!=null) {
            		//codigo para fazer copia de um arquivo em outro 
                    Path origem = Paths.get(arquivoSelecionado.getAbsolutePath());
                    // Obtendo o nome do arquivo
                    String nomeArquivo = origem.getFileName().toString();
                    Path destino = Paths.get("imagensProdutos/" + nomeArquivo.replace(" ", "")).toAbsolutePath();
                    pathImagem=destino.toString();
            		produto pr=new produto();
            		pr.setCategoria(categoriaFinal);
            		pr.setCodBarra(cd);
            		pr.setFornecedor(fornecedorFinal);
            		pr.setUnmedida(unFinal);
            		pr.setUrlImagem(pathImagem);
            		pr.setNomeProduto(nome);
            		pr.setQuantMax(quantMax);
            		pr.setQuantMinima(quantMin);
            		   
                    try {
                    	
	                    if(origem!=null) {	
	                        // Copiando a imagem da origem para o destino
	                        Files.copy(origem, destino, StandardCopyOption.REPLACE_EXISTING);
	                    }
                        produtoRepository prrp=new produtoRepository();
                		prrp.gavar(pr);
                		
                		Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Produto Cadastrado");
                        alert.setHeaderText(null);
                        alert.setContentText("Produto Cadastrado com sucesso!");
                        
                        //limpar os textfield apos cadastro
                        quantMinField.clear();
                        quantMaxField.clear();
                        codigoBarrasField.clear();
                        nomeField.clear();
                        fornecedorBox.setValue("");
                        unMedBox.setValue("");
                        CategoriaBox.setValue("");
                        urlImagemField.clear();
                        alert.show();
                        
                    } catch (IOException e) {
                    	Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Erro");
                        alert.setHeaderText(null);
                        alert.setContentText("Verifique todos os campos!");
                    	
                    }  
            	}
            	else {
            		    Alert alert = new Alert(Alert.AlertType.INFORMATION);
            	        alert.setTitle("Erro");
            	        alert.setHeaderText(null);
            	        alert.setContentText("preencha todos os Campos");
            	        alert.show();
            		
            	}
            	
            }
        });
        
        Button cancelarButton = new Button("Cancelar");

        HBox botoes = new HBox(10, cadastrarButton, cancelarButton);
        botoes.setPadding(new Insets(10, 0, 0, 0));
        botoes.setAlignment(Pos.CENTER);

        HBox CenterBox = new HBox(10,gridpane);
        CenterBox.setAlignment(Pos.CENTER);
        
        VBox mainLayout = new VBox(10, headerBox, CenterBox,botoes);
        mainLayout.setAlignment(Pos.TOP_CENTER);
        mainLayout.setStyle("-fx-background-color: #F0F0F0;"); // Fundo cinza claro
        mainLayout.setPadding(new Insets(10));
        
        Scene scene = new Scene(mainLayout, 600, 400);
        primaryStage.setTitle("Cadastrar Produto");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
 // Método que abre um seletor de arquivos e carrega a imagem selecionada no ImageView
    private void abrirSeletorDeArquivos(TextField urlImagemField) {
    	
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg")
        );
        arquivoSelecionado = fileChooser.showOpenDialog(stage);
        if (arquivoSelecionado != null) {
            Image imagem = new Image(arquivoSelecionado.toURI().toString());
            urlImagemField.setText(arquivoSelecionado.getAbsolutePath());
            imageView.setImage(imagem);
             
        }	
        else {
        	urlImagemField.setText("Nenhum arquivo selecionado");
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}