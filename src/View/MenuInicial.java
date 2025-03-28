package View;
	
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import Model.Funcionarios;
import Model.unidadeMedida;
import Model.categoria;
import Model.produto;
import Model.Fornecedor;
import Repository.FornecedorRepository;
import Repository.FuncionarioRepository;
import Repository.categoriaRepository;
import Repository.produtoRepository;
import Repository.unidadeMedRepository;



public class MenuInicial extends Application {
	
	private Funcionarios funcionario;
	private List<unidadeMedida >unMedida;
	private List<categoria >categoria;
	private List<Fornecedor>fornecedor;
	private List<produto>produtos;
	
	
	public void setFuncionario(Funcionarios fn) {
		funcionario=fn;
	}
	
	public void atualizar() {
        unidadeMedRepository unrp= new unidadeMedRepository();
        categoriaRepository ctrp=new categoriaRepository();
        FornecedorRepository fnrp=new FornecedorRepository();
        produtoRepository prrp=new produtoRepository();
        this.unMedida=unrp.ListarTodos();
        this.categoria=ctrp.ListarTodos();
        this.fornecedor=fnrp.ListarTodos();
        this.produtos=prrp.ListarTodos();    
	}
	
	
	@Override
	public void start(Stage stage) {
		Pane pane = new Pane();
		ScrollPane scp=new ScrollPane(pane);
		Scene scene = new Scene(scp,1000, 600);
		
		Image imagemBtn01=new Image("icon08.png");
		ImageView imgView01=new ImageView(imagemBtn01);
        Button button1 = new Button();
        button1.setStyle(
                "-fx-background-radius: 15; " +
                "-fx-border-radius: 10;"+
                "-fx-pref-width: 200px;" +
                "-fx-pref-height: 150px;"+
                "-fx-font-size: 16px;"+
                "-fx-text-wrap: wrap;"
        );
        button1.setGraphic(imgView01);
        button1.setText("Entrada Produtos");
        button1.setLayoutX(20);
        button1.setLayoutY(30);
        
        Image imagemBtn02=new Image("icon02.png");
		ImageView imgView02=new ImageView(imagemBtn02);
        Button button2 = new Button();
        button2.setStyle(
                "-fx-background-radius: 15; " +
                "-fx-border-radius: 10;"+
                "-fx-pref-width: 180px;" +
                "-fx-pref-height: 150px;"+
                "-fx-font-size: 16px;"+
                "-fx-text-wrap: wrap;"
        );
        button2.setGraphic(imgView02);
        button2.setText("Venda");
        button2.setLayoutX(240);
        button2.setLayoutY(30);
        
        
        Image imagemBtn03=new Image("icon03.png");
		ImageView imgView03=new ImageView(imagemBtn03);
        Button button3 = new Button();
        button3.setStyle(
                "-fx-background-radius: 15; " +
                "-fx-border-radius: 10;"+
                "-fx-pref-width: 180px;" +
                "-fx-pref-height: 150px;"+
                "-fx-font-size: 16px;"+
                "-fx-text-wrap: wrap;"
        );
        button3.setGraphic(imgView03);
        button3.setText("Estoque");
        button3.setLayoutX(440);
        button3.setLayoutY(30);
        
        
        Image imagemBtn04=new Image("icon04.png");
		ImageView imgView04=new ImageView(imagemBtn04);
        Button button4 = new Button();
        button4.setStyle(
                "-fx-background-radius: 15; " +
                "-fx-border-radius: 10;"+
                "-fx-pref-width: 180px;" +
                "-fx-pref-height: 150px;"+
                "-fx-font-size: 16px;"+
                "-fx-text-wrap: wrap;"
        );
        button4.setGraphic(imgView04);
        button4.setText("Financeiro");
        button4.setLayoutX(640);
        button4.setLayoutY(30);
        
        
        Image imagemBtn05=new Image("icon05.png");
		ImageView imgView05=new ImageView(imagemBtn05);
        Button button5 = new Button();
        button5.setStyle(
                "-fx-background-radius: 15; " +
                "-fx-border-radius: 10;"+
                "-fx-pref-width: 180px;" +
                "-fx-pref-height: 150px;"+
                "-fx-font-size: 16px;"+
                "-fx-text-wrap: wrap;"
        );
        button5.setGraphic(imgView05);
        button5.setText("Cliente");
        button5.setLayoutX(840);
        button5.setLayoutY(30);
        
        
        Image imagemBtn06=new Image("icon06.png");
		ImageView imgView06=new ImageView(imagemBtn06);
        Button button6 = new Button();
        button6.setStyle(
                "-fx-background-radius: 15; " +
                "-fx-border-radius: 10;"+
                "-fx-pref-width: 180px;" +
                "-fx-pref-height: 150px;"+
                "-fx-font-size: 16px;"+
                "-fx-text-wrap: wrap;"
        );
        button6.setGraphic(imgView06);
        button6.setText("Fornecedores");
        button6.setLayoutX(1040);
        button6.setLayoutY(30);
        
        Image imagemBtn07=new Image("icon07.png");
		ImageView imgView07=new ImageView(imagemBtn07);
        Button button7 = new Button();
        button7.setStyle(
                "-fx-background-radius: 15; " +
                "-fx-border-radius: 10;"+
                "-fx-pref-width: 180px;" +
                "-fx-pref-height: 150px;"+
                "-fx-font-size: 16px;"+
                "-fx-text-wrap: wrap;"
        );
        button7.setGraphic(imgView07);
        button7.setText("Gestão");
        button7.setLayoutX(1240);
        button7.setLayoutY(30);
        
        
        String cargo=funcionario.getCargo();
        if(cargo.equalsIgnoreCase("vendedor")) {
        	button4.setDisable(true);
        	button5.setDisable(true);
        	button6.setDisable(true);
        	button7.setDisable(true);
        }
        
        Line line = new Line();
        line.setStartX(0);
        line.endXProperty().bind(pane.widthProperty()); 
        line.setStartY(200);
        line.setEndY(200);
        line.setStrokeWidth(5);
        line.setStroke(Color.BLACK);
      
        Label bemVindotext=new Label("Bem Vindo, "+funcionario.getNome()+"!");
        bemVindotext.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        bemVindotext.setLayoutX(650);
        bemVindotext.setLayoutY(450);
        
        Label dateTimeLabel = new Label();
        updateDateTime(dateTimeLabel);
        dateTimeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        dateTimeLabel.setLayoutX(1000);
        dateTimeLabel.setLayoutY(700);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateDateTime(dateTimeLabel)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        
        pane.getChildren().addAll(button1,button2,button3,button4,button5,button6,button7,line,bemVindotext,dateTimeLabel);
        
        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            double width = newVal.doubleValue();
            double height = scene.getHeight();
            updateScreenSizeLabel(dateTimeLabel,bemVindotext, width, height);
        });

        // Listener para mudanças na altura da cena
        scene.heightProperty().addListener((obs, oldVal, newVal) -> {
            double width = scene.getWidth();
            double height = newVal.doubleValue();
            updateScreenSizeLabel(dateTimeLabel,bemVindotext, width, height);
        });
        
        button1.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            			stage.close();
            			menuEntrada mn=new menuEntrada();
            			mn.setStage(stage,unMedida,categoria,fornecedor,produtos,null,funcionario);
            			mn.start(new Stage());
            			atualizar();
            }
        });
        
        button2.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {      		
            			stage.hide();
            			menuVenda mn=new menuVenda();
            			mn.setStage(stage);
            			mn.start(new Stage());
            			atualizar();
            }
        });
        
        button3.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                	
            		
            			stage.hide();
            			menuEstoqueView mn=new menuEstoqueView();
            			mn.setStage(stage);
            			mn.start(new Stage());
            			atualizar();
            	
            }
        });
        
        
        button5.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                	
            			stage.hide();
            			MenuClienteView mn=new MenuClienteView();
            			mn.setStage(stage);
            			mn.start(new Stage());
            			atualizar();
            }
        });
        
        button6.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                	
            			stage.hide();
            			FornecedorMenu mn=new FornecedorMenu();
            			mn.setStage(stage);
            			mn.start(new Stage());
            			atualizar();
            }
        });
        
        button7.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            			stage.hide();
            			menuGestao mn=new menuGestao();
            			mn.setStage(stage);
            			mn.start(new Stage());
            			atualizar();
            }
        });
        
        stage.setMinHeight(600);
        stage.setMaximized(true);
        stage.setMinWidth(1100);
        stage.setTitle("Menu");
        stage.setScene(scene);
        stage.show();
        atualizar();	
	}
	
	
	private void updateDateTime(Label dateTimeLabel) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        dateTimeLabel.setText(now.format(formatter));
    }
	
	
	private void updateScreenSizeLabel(Label date,Label bemvindo, double width, double height) {
		bemvindo.setLayoutX(width/2.5);
		bemvindo.setLayoutY(height/2);
        date.setLayoutX(width-200);
        date.setLayoutY(height-50);
    }
	
}

