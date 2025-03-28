package Control;

import Model.Funcionarios;
import View.MenuInicial;
import View.menuLoginView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainController extends Application {
	
	private static Funcionarios funcionario;
	
	public static Funcionarios getFuncionario() {
		return funcionario;
	}




	public static void setFuncionario(Funcionarios funcionario) {
		MainController.funcionario = funcionario;
	}




	public void menuLogin(Funcionarios funcionario, MainController mn) {
		menuLoginView login=new menuLoginView();
		login.setFuncionario(funcionario,mn);
		login.start(new Stage());
	}
	
	
	
	
	public void menuInicial(Funcionarios funcionario,MainController mn) {
		MenuInicial mni=new MenuInicial();
		mni.setFuncionario(funcionario);
		mni.start(new Stage());	
	}
	
	
	public static void main(String [] args) {
		
		launch(args);	
	}
	
	
	 @Override
	    public void start(Stage primaryStage) {
		 
		 	MainController mn=new MainController();
		 	if(funcionario==null) {
		 		mn.menuLogin(funcionario,mn);
			}
			else {
				mn.menuInicial(funcionario,mn);
			}
	    }
	 
}
