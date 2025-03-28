package Control;
	import javafx.application.Application;
	import javafx.scene.Scene;
	import javafx.scene.chart.*;
	import javafx.scene.layout.GridPane;
	import javafx.stage.Stage;

	public class teste extends Application {

	    @Override
	    public void start(Stage primaryStage) {
	        // Dados do array
	        int[] valores = {10, 20, 15, 30, 25, 35, 40};

	        // Gráfico de Linha
	        LineChart<Number, Number> graficoLinha = criarGraficoLinha(valores);

	        // Gráfico de Barras
	        BarChart<String, Number> graficoBarras = criarGraficoBarras(valores);

	        // Gráfico de Pizza
	        PieChart graficoPizza = criarGraficoPizza(valores);

	        // Gráfico de Dispersão
	        ScatterChart<Number, Number> graficoDispersao = criarGraficoDispersao(valores);

	        // Layout
	        GridPane layout = new GridPane();
	        layout.setHgap(10);
	        layout.setVgap(10);

	        // Adicionando os gráficos ao layout
	        layout.add(graficoLinha, 0, 0);
	        layout.add(graficoBarras, 1, 0);
	        layout.add(graficoPizza, 0, 1);
	        layout.add(graficoDispersao, 1, 1);

	        // Cena
	        Scene scene = new Scene(layout, 1000, 800);

	        // Configuração do palco
	        primaryStage.setTitle("Múltiplos Gráficos com JavaFX");
	        primaryStage.setScene(scene);
	        primaryStage.show();
	    }

	    // Método para criar o Gráfico de Linha
	    private LineChart<Number, Number> criarGraficoLinha(int[] valores) {
	        NumberAxis eixoX = new NumberAxis();
	        eixoX.setLabel("Índice");
	        NumberAxis eixoY = new NumberAxis();
	        eixoY.setLabel("Valor");
	        LineChart<Number, Number> graficoLinha = new LineChart<>(eixoX, eixoY);
	        graficoLinha.setTitle("Gráfico de Linha");

	        XYChart.Series<Number, Number> serie = new XYChart.Series<>();
	        serie.setName("Valores");
	        for (int i = 0; i < valores.length; i++) {
	            serie.getData().add(new XYChart.Data<>(i, valores[i]));
	        }
	        graficoLinha.getData().add(serie);

	        return graficoLinha;
	    }

	    // Método para criar o Gráfico de Barras
	    private BarChart<String, Number> criarGraficoBarras(int[] valores) {
	        CategoryAxis eixoX = new CategoryAxis();
	        eixoX.setLabel("Índice");
	        NumberAxis eixoY = new NumberAxis();
	        eixoY.setLabel("Valor");
	        BarChart<String, Number> graficoBarras = new BarChart<>(eixoX, eixoY);
	        graficoBarras.setTitle("Gráfico de Barras");

	        XYChart.Series<String, Number> serie = new XYChart.Series<>();
	        serie.setName("Valores");
	        for (int i = 0; i < valores.length; i++) {
	            serie.getData().add(new XYChart.Data<>(String.valueOf(i), valores[i]));
	        }
	        graficoBarras.getData().add(serie);

	        return graficoBarras;
	    }

	    // Método para criar o Gráfico de Pizza
	    private PieChart criarGraficoPizza(int[] valores) {
	        PieChart graficoPizza = new PieChart();
	        graficoPizza.setTitle("Gráfico de Pizza");

	        for (int i = 0; i < valores.length; i++) {
	            graficoPizza.getData().add(new PieChart.Data("Índice " + i, valores[i]));
	        }

	        return graficoPizza;
	    }

	    // Método para criar o Gráfico de Dispersão
	    private ScatterChart<Number, Number> criarGraficoDispersao(int[] valores) {
	        NumberAxis eixoX = new NumberAxis();
	        eixoX.setLabel("Índice");
	        NumberAxis eixoY = new NumberAxis();
	        eixoY.setLabel("Valor");
	        ScatterChart<Number, Number> graficoDispersao = new ScatterChart<>(eixoX, eixoY);
	        graficoDispersao.setTitle("Gráfico de Dispersão");

	        XYChart.Series<Number, Number> serie = new XYChart.Series<>();
	        serie.setName("Valores");
	        for (int i = 0; i < valores.length; i++) {
	            serie.getData().add(new XYChart.Data<>(i, valores[i]));
	        }
	        graficoDispersao.getData().add(serie);

	        return graficoDispersao;
	    }

	    public static void main(String[] args) {
	        launch(args);
	    }
	}
