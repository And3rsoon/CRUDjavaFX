package View;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import Control.MainController;
import Model.Funcionarios;
import Repository.FuncionarioRepository;
import Service.CriptografiaService;

public class menuLoginView extends Application {
	private static Funcionarios funcionario;
	private static MainController controller;
	
	
	public static void setFuncionario(Funcionarios func, MainController con) {
        funcionario = func;
        controller=con;
    }
	
	
	@Override
	public void start(Stage stage) {
		Stage editStage = new Stage();
		
		//imagem view da tela incial
		Image image = new Image("imagem_login2.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(600);
        imageView.setFitWidth(1000*0.666);
        imageView.setLayoutX(0);
        imageView.setLayoutY(0);
        imageView.setOpacity(1);
        
        
        PasswordField passwordFieldSenha = new PasswordField();
        passwordFieldSenha.setPrefHeight(10);
        passwordFieldSenha.setPrefWidth(250);
        passwordFieldSenha.setLayoutX(1000*0.81);
        passwordFieldSenha.setLayoutY(40);
        
        
        TextField textFieldUser = new TextField();
        textFieldUser.setPrefHeight(10);
        textFieldUser.setPrefWidth(250);
        textFieldUser.setLayoutX(1000*0.81);
        textFieldUser.setLayoutY(200);
        
        
        Label loginLabel = new Label("LOGIN");
        loginLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        loginLabel.setLayoutX(1000*0.81);
        loginLabel.setLayoutY(20);
        
        Label userLabel = new Label("User: ");
        userLabel.setLayoutX(1000*0.7);
        userLabel.setLayoutY(100);
        
        
        Label senhaLabel = new Label("Senha: ");
        senhaLabel.setLayoutX(1000*0.7);
        senhaLabel.setLayoutY(200);
         
        
        Button button1 = new Button("ENTRAR");
        button1.setStyle("-fx-background-color: green; " +
                "-fx-text-fill: white; " +
                "-fx-background-radius: 15; " +
                "-fx-border-radius: 10;"+
                "-fx-pref-width: 250px;" +
                "-fx-pref-height: 40px;"+
                "-fx-font-size: 16px;"
        		
        		);
        button1.setLayoutX(1000*0.7);
        button1.setLayoutY(300);
        
        
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setMinHeight(0);
        gridPane.setMaxHeight(0);
        gridPane.setLayoutX(1000*0.7);
        gridPane.setLayoutY(600*0.25);

        // Adicionando nós ao GridPane
        gridPane.add(loginLabel, 0, 0);
        gridPane.add(userLabel, 0, 2);
        gridPane.add(textFieldUser, 0, 3);
        gridPane.add(senhaLabel, 0, 4);
        gridPane.add(passwordFieldSenha, 0, 5);
        gridPane.add(button1, 0, 6);
        
        
        Pane pane = new Pane();
        pane.getChildren().addAll(imageView,gridPane);
        
        
        Scene scene = new Scene(pane, 1000, 600);

        
        // Listener para mudanças na largura da cena
        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            double width = newVal.doubleValue();
            double height = scene.getHeight();
            updateScreenSizeLabel(imageView,gridPane, width, height);
        });

        // Listener para mudanças na altura da cena
        scene.heightProperty().addListener((obs, oldVal, newVal) -> {
            double width = scene.getWidth();
            double height = newVal.doubleValue();
            updateScreenSizeLabel(imageView,gridPane, width, height);
        });

        
        
        button1.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            		CriptografiaService cripService=new CriptografiaService();
                	String user=textFieldUser.getText();
            		String senha=passwordFieldSenha.getText();
            		if(user!=null && senha!=null) {
		            		Funcionarios consulta=new FuncionarioRepository().buscarPorNumDoc(user);
		            		if(consulta!=null) {
				            		String senhaCadastrada=cripService.decode(consulta.getSenhas().getSenha()) ;
				            		if(senhaCadastrada.equals(senha) && senhaCadastrada!=null) {
				            			stage.close();
				            			controller.setFuncionario(consulta);
				            			controller.start(stage);
				            			
				            		}
				            		else {
				            			Alert alert = new Alert(AlertType.INFORMATION);
				            			alert.setTitle("Informação");
					                    alert.setHeaderText("erro");
					                    alert.setContentText("Usuário ou Senha Incorretos!");
					                    // Exibe o alerta e espera o usuário fechar
					                    alert.showAndWait();
				            		}
		            		}
		            		else {
		            			Alert alert = new Alert(AlertType.INFORMATION);
		            			alert.setTitle("Informação");
			                    alert.setHeaderText("erro");
			                    alert.setContentText("Usuário ou Senha Incorretos!");
			                    // Exibe o alerta e espera o usuário fechar
			                    alert.showAndWait();
		            		}
            		}
            		else {
            			Alert alert = new Alert(AlertType.INFORMATION);
            			alert.setTitle("Informação");
	                    alert.setContentText("Preencha todos os campos!");
	                    // Exibe o alerta e espera o usuário fechar
	                    alert.showAndWait();
            		}
            }
        });
        stage.setMinHeight(600);
        stage.setMinWidth(1000);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
			
	}
	private void updateScreenSizeLabel(ImageView imageView,GridPane gridPane, double width, double height) {
        imageView.setFitHeight(height);
        imageView.setFitWidth(width*0.66);
        gridPane.setLayoutX(width*0.7);
        gridPane.setLayoutY(height*0.25);
    }
	
}

