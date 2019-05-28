package interfaz;

import java.awt.SecondaryLoop;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jpl7.Term;

import datos.Consultas;

import java.util.Set; 
import estructuraGrafo.Grafo;
import estructuraGrafo.Nodo;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;


public class Principal extends Application implements EventHandler<ActionEvent>{
	
	private static final int ANCHO = 1000;
	private static final int LARGO = 600;
	private static final int NODOX = 40;
	private static final int NODOY = 20;
	
	private Pane layoutPrincipal= new Pane();
	private Pane layoutAgregar= new Pane();
	private Stage primary;
	private Scene principalScene, secondScene;
	private static Grafo grafoCiudades = new Grafo();
	private GraficosFactoria factoria = new GraficosFactoria();
	private Map<Rectangle, String> nodosMap = new HashMap<Rectangle, String>();
	private List<Flecha> listaFlechas = new ArrayList<Flecha>();
	private List<Line> listaLineas = new ArrayList<Line>();
	private List<Text> listaDistancias = new ArrayList<Text>();
	private List<String> ruta = new ArrayList<String>();
	private Label rutaSeleccionada;
	private Consultas consulta = new Consultas();

	
	public static void main(String[] args) {
		grafoCiudades.crearGrafo();
		Principal ventana = new Principal();
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primary = primaryStage;
		principalScene = new Scene(layoutPrincipal, ANCHO, LARGO);
		secondScene = new Scene(layoutAgregar, 600, 250);
		primaryStage.setTitle("DrWazeLog");	
		
		
		//Fondo del Grafo
		Image fondo = new Image(new FileInputStream("img/fondo3.png"));
		ImageView fondoView = new ImageView(fondo); 
		
		fondoView.setFitWidth(1000);
		fondoView.setFitHeight(600);
		
		
		layoutPrincipal.getChildren().addAll(fondoView);
		dibujaGrafo();
		crearMenu();
		agregarRutaEtiqueta();
		crearRutaMouse();
		primary.setScene(principalScene);
		primary.show();

	}

	@Override
	public void handle(ActionEvent event) {		
	}
	
	
	public void dibujaGrafo() {
		grafoCiudades.crearGrafo();
		List<Nodo> listaNodosGrafo = grafoCiudades.getNodos();

		Integer posXactual = 0;
		Integer posYactual = 0;
		Integer posXdestino = 0;
		Integer posYdestino = 0;
		
		//Agregar las aristas
		for(Integer i = 0; i < listaNodosGrafo.size(); i++) {
			posXactual = listaNodosGrafo.get(i).getPosX(); 
			posYactual = listaNodosGrafo.get(i).getPosY();
		
			//Agrega los pesos de cada arista
			if(listaNodosGrafo.get(i).getAristas() != null) {				
				for(Integer j = 0; j < listaNodosGrafo.get(i).getAristas().size(); j++) {		
					posXdestino = listaNodosGrafo.get(i).getAristas().get(j).getDestino().getPosX();
					posYdestino =  listaNodosGrafo.get(i).getAristas().get(j).getDestino().getPosY();
	
					Line arista = factoria.dibujaArista(posXactual+NODOX, posYactual+NODOY, posXdestino+NODOX, posYdestino+NODOY);
					Text distancia = factoria.crearTexto(listaNodosGrafo.get(i).getAristas().get(j).getDistancia().toString(), 
							(posXdestino+posXactual)/2+5, (posYdestino + posYactual)/2+25, 11);
					Flecha flecha = new Flecha(posXactual+NODOX, posYactual+NODOY,
							(posXdestino+posXactual+NODOX*2)/2, (posYdestino+posYactual+NODOY*2)/2);
					
					//Agregar los objetos a las listas correpondientes.
					listaLineas.add(arista);
					listaDistancias.add(distancia);
					listaFlechas.add(flecha);
					
					layoutPrincipal.getChildren().addAll(flecha, arista, distancia);				
				}
			}
		}

		//Agregar los nodos
		for(Integer i = 0; i < listaNodosGrafo.size(); i++) {
			posXactual = listaNodosGrafo.get(i).getPosX(); 
			posYactual = listaNodosGrafo.get(i).getPosY();
			
			Rectangle ovaloTemp = factoria.crearOvalo(posXactual, posYactual, 40);
			nodosMap.put(ovaloTemp, listaNodosGrafo.get(i).getLugar());
			Text nombreNodo = factoria.crearTexto(listaNodosGrafo.get(i).getLugar(), posXactual, posYactual, 14);
			layoutPrincipal.getChildren().addAll(ovaloTemp, nombreNodo);
		}	
	}
	
	public void crearRutaMouse() {
		
	      Set<Map.Entry<Rectangle, String>> setKey = nodosMap.entrySet(); 
		 EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() { 
			@Override
			public void handle(MouseEvent event) {

				//Revisa en que posicion se dió el click y agrega la posicion
				for(Entry<Rectangle, String> i: setKey) {
					if(event.getSceneX() >= i.getKey().getX() && event.getSceneX() <= i.getKey().getX()+NODOX*2
							&& event.getSceneY() >= i.getKey().getY() && event.getSceneY() <= i.getKey().getY()+NODOY*2) {
						agregarRutas(i.getValue());					}
				}
				System.out.println(ruta);
			} 
	      };  
		
	      for(Entry<Rectangle, String> i:setKey) {
	      	  i.getKey().addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
	      }    
    }
	
	public void agregarRutas(String lugar) {
		if(ruta.isEmpty()) {
			ruta.add(0, lugar);
			agregarRutaEtiqueta();
		}
		else {	
			for(Integer j = 0; j < ruta.size(); j++) {
				if(ruta.get(j).equals(lugar)) {
					break;
				}
				else if(j == ruta.size()-1) {
					ruta.add(lugar);
					agregarRutaEtiqueta();
				}
			}
		}
	}
	
	public void agregarRutaEtiqueta() {
		layoutPrincipal.getChildren().remove(rutaSeleccionada);
		rutaSeleccionada = factoria.dibujaLabel(50, 560, ruta);
		rutaSeleccionada.setTextFill(Color.BLACK);
		layoutPrincipal.getChildren().add(rutaSeleccionada);
	
		//Efecto de zoom en el texto
	  	rutaSeleccionada.setOnMouseEntered(new EventHandler<MouseEvent>() {
		    @Override public void handle(MouseEvent e) {
		    	rutaSeleccionada.setScaleX(1.1);
		    	rutaSeleccionada.setScaleY(1.1);
		    }
		});

		rutaSeleccionada.setOnMouseExited(new EventHandler<MouseEvent>() {
		    @Override public void handle(MouseEvent e) {
		    	rutaSeleccionada.setScaleX(1);
		    	rutaSeleccionada.setScaleY(1);
		    }
		});
		//______________________________________________________________________
		
		//Evento al clickear la ruta.
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>(){ 
			@Override
			public void handle(MouseEvent event) {
				layoutPrincipal.getChildren().remove(rutaSeleccionada);
				ArrayList<String> mejorRuta = consulta.solicitarRuta(ruta);
				String rutaFinal = "";
				for(Integer i = 1; i < mejorRuta.size()-1; i++) {
					rutaFinal += mejorRuta.get(i) + ", ";
				}
				System.out.println(rutaFinal);
				
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("¡Ruta Encontrada!");
				alert.setHeaderText("La mejor ruta y su distancia es:");
				alert.setContentText(mejorRuta.toString().substring(1, (mejorRuta.toString().length()-1)) + "km");
				alert.showAndWait();
				
				ruta.clear();
				
			} 
	      };  
	    rutaSeleccionada.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
	}
	
	public void crearMenu() throws FileNotFoundException {
		Image image = new Image(new FileInputStream("img/agregarLugar.png"));
		ImageView imageView = new ImageView(image); 
	    
		Button btnAgregar = new Button("Agregar", imageView);
	    btnAgregar.setGraphic(imageView);
	    
		imageView.setFitWidth(30);
		imageView.setFitHeight(30);
		
	    btnAgregar.setMaxSize(100, 40);
	    
	    btnAgregar.setLayoutX(900);
	    btnAgregar.setLayoutY(555);
	    
	    layoutPrincipal.getChildren().addAll(btnAgregar);

	    btnAgregar.setOnAction(e -> primary.setScene(secondScene));
	    anadirLugar();
	}
	
	public void anadirLugar() {
		HBox txtNodo = factoria.crearCajaTexto(200, 10);
		Label lblNodo = factoria.dibujaLabel(5, 10, "Nombre del Nuevo Lugar");
		layoutAgregar.getChildren().addAll(txtNodo, lblNodo);
	
	}
}

