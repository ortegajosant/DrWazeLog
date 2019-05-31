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
import datos.ManejoPL;

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

/**
 * Clase Principal encargada de administrar todos los gráficos y 
 * funciones principales del programa.
 *
 */
public class Principal extends Application implements EventHandler<ActionEvent>{
	
	//Atributos Estáticos
	private static final int ANCHO = 1000;
	private static final int LARGO = 600;
	private static final int NODOX = 40;
	private static final int NODOY = 20;
		
	//Atributos de la clase
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
	private ManejoPL editor;

	/**
	 * Main del Programa, se encarga de correr la ventana principal
	 * @param args
	 */
	public static void main(String[] args) {
		grafoCiudades.crearGrafo();
		Principal ventana = new Principal();
		launch(args);
	}

	/**
	 * Sobrecarga de la clase EventHandler, inicializa los escenarios
	 * y las funciones a utilizar
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		primary = primaryStage;
		principalScene = new Scene(layoutPrincipal, ANCHO, LARGO);
		secondScene = new Scene(layoutAgregar, 600, 400);
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
	
	
	/**
	 * Función encargada de dibujar el grafo en pantalla.
	 * Realiza la consulta de los nodos y aristas y obtiene las
	 * posiciones para graficarlos.
	 */
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
			listaLineas.clear();
			listaDistancias.clear();
			listaFlechas.clear();
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
	
	/**
	 * Función encargada de agregar la ruta cada vez que el usuario da 
	 * click a un nodo en pantalla.
	 */
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
			} 
	      };  
		
	      for(Entry<Rectangle, String> i:setKey) {
	      	  i.getKey().addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
	      }    
    }
	
	/**
	 * Función encargada de agregar a la lista de ruta los lugares 
	 * que el usuario desea.
	 * @param lugar lugar seleccionado por el usuario.
	 */
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
	
	/**
	 * Función encargada de mostrar en pantalla una etiqueta con la
	 * ruta seleccionada para el usuario, también posee los efectos 
	 * del texto al pasar el ratón y realizar la consulta al darle
	 * click
	 */
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
		//________________________
		
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
	
	/**
	 * Crea la escena donde se muestran las opciones para agregar un nuevo
	 * lugar o una nueva arista.
	 * @throws FileNotFoundException error al encontrar la ruta de las imágenes.
	 */
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

	    btnAgregar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				primary.setScene(secondScene);
				primary.show();
			}
	    });
	    anadirLugar();
	}
	
	/**
	 * Función encargada de agregar un nuevo lugar cuando el usuario
	 * lo desee, posee las validaciones necesarias para agregar lugares 
	 * que ya existen entre otros.
	 * @throws FileNotFoundException
	 */
	public void anadirLugar() throws FileNotFoundException {
		Image logo;
		Image fondo;
		
		//Gráficos_____________________________

		fondo = new Image(new FileInputStream("img/fondoAgregar.jpg"), 600, 800, false, false);
		logo = new Image(new FileInputStream("img/DrWazeLog_Logo.png"), 100, 100, false, false);
		Label lblAgregaNuevoLugar = factoria.dibujaLabel(10, 5, "Agregar Nuevo Lugar");
		ImageView fondoImg = new ImageView(fondo);
		ImageView logoImg = new ImageView(logo);
		logoImg.setX(500);
		logoImg.setY(325);
		TextField txtNodo = factoria.crearCajaTexto(200, 30);
		Label lblNodo = factoria.dibujaLabel(10, 30, "Nombre del Nuevo Lugar: ");
		TextField txtArista = factoria.crearCajaTexto(200, 75);
		Label lblArista = factoria.dibujaLabel(10, 75, "Destino(s): ");
		Button btnAgregar = new Button("Agregar");
		Label lblAgregados = factoria.dibujaLabel(10, 250, "");
		Label lblDistancia = factoria.dibujaLabel(10, 125, "Distancia: ");
		TextField txtDistancia = factoria.crearCajaTexto(200, 125);
		Label lblPosX = factoria.dibujaLabel(10, 175, "Posición en X: ");
		TextField txtPosX = factoria.crearCajaTexto(200, 175);
		Label lblPosY = factoria.dibujaLabel(10, 225, "Posición en Y: ");
		TextField txtPosY = factoria.crearCajaTexto(200, 225);
		lblAgregaNuevoLugar.setTextFill(Color.RED);
	    btnAgregar.setMaxSize(100, 40);  
	    btnAgregar.setLayoutX(370);
	    btnAgregar.setLayoutY(225);

	    //______________________________
	    
	    //Agregar nuevas aristas
		List<String> listaAristas = new ArrayList<String>();
		List<String> listaDistancias = new ArrayList<String>();
		btnAgregar.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	layoutAgregar.getChildren().remove(lblAgregados);
		        String aristaNueva = txtArista.getText().toLowerCase();
		        String distanciaNueva = txtDistancia.getText().toLowerCase();
		    	if(txtArista.getText().toLowerCase().length() != 0 && txtDistancia.getText().toLowerCase().length() != 0) {
			    	txtArista.setText("");
			    	txtDistancia.setText("");
			        for(String i : nodosMap.values()) {
			        	if(aristaNueva.equals(i)) {
			        		if(!listaAristas.isEmpty()) {
			            		for(Integer j = 0; j <listaAristas.size(); j++) {
			            			if(listaAristas.get(j).equals(aristaNueva)) {
			            				break;
			            			}else if(j == listaAristas.size()-1) {
			            				listaAristas.add(aristaNueva);
			            				listaDistancias.add(distanciaNueva);
			            			}
			            		}
			        		}else {
			        			listaAristas.add(aristaNueva);
			        			listaDistancias.add(distanciaNueva);		        		}
			        	}
			        }
			        lblAgregados.setText(listaAristas.toString());
			        layoutAgregar.getChildren().add(lblAgregados);
		    	}else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("¡Error!");
					alert.setHeaderText("Campos en Blanco");
					alert.setContentText("Debe añadir vertice y distancia");
					alert.showAndWait();
		    	}
		    }		    
		});
		
		layoutAgregar.getChildren().addAll(fondoImg, txtNodo, lblNodo, logoImg, txtArista, lblArista, 
				btnAgregar, lblDistancia, txtDistancia,lblPosX,txtPosX,lblPosY,txtPosY, lblAgregaNuevoLugar);
		
		//Agregar el nuevo Lugar
		
		Button btnAceptar = new Button("Aceptar"); 
		Button btnCancelar = new Button("Cancelar");
		btnAceptar.setMaxSize(100, 40);  
	    btnAceptar.setLayoutX(460);
	    btnAceptar.setLayoutY(225);
	    
	    btnCancelar.setMaxSize(100, 40);  
	    btnCancelar.setLayoutX(525);
	    btnCancelar.setLayoutY(225);
	   
	    btnCancelar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				dibujaGrafo();
				agregarRutaEtiqueta();
				crearRutaMouse();
				primary.setScene(principalScene);
				primary.show();
			}
	    });
	    
	    btnAceptar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if(txtNodo.getText().toLowerCase().length() != 0 && txtPosX.getText().toLowerCase().length() != 0 && txtPosY.getText().toLowerCase().length() != 0) {
					if(revisaNodos(txtNodo.getText().toLowerCase())) {
						String nuevaLinea = "arista(";
						String nuevoVertice = "vertice(" + txtNodo.getText().toLowerCase() + ", "+ txtPosX.getText().toLowerCase()+ ", "
						+ txtPosY.getText().toLowerCase()+").";
						System.out.println(nuevoVertice);
						editor.ModificarFichero(nuevoVertice, "%AgregaVertice");
						for(Integer i = 0; i < listaAristas.size(); i++) {
							nuevaLinea += txtNodo.getText().toLowerCase()+", "+listaAristas.get(i)+", "+listaDistancias.get(i)+").";
							editor.ModificarFichero(nuevaLinea, "%AgregaArista");
							nuevaLinea = "arista(";
						}
						
						txtNodo.setText("");
						txtDistancia.setText("");
						txtPosX.setText("");
						txtPosY.setText("");
						txtArista.setText("");
						listaAristas.clear();
					}else {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("¡Error!");
						alert.setHeaderText("Imposible añadir nodo");
						alert.setContentText("El nodo agregado ya existe");
						alert.showAndWait();
					}
				
					dibujaGrafo();
					agregarRutaEtiqueta();
					crearRutaMouse();
					primary.setScene(principalScene);
					primary.show();
				}
			}
	    });
	    agregarArista();
	    layoutAgregar.getChildren().addAll(btnAceptar, btnCancelar);
	}
	
	/**
	 * Función encargada de revisar si el nodo ya existe.
	 * @param nombre nombre del lugar a revisar.
	 * @return true si no es encuentra, false si ya existe.
	 */
	public Boolean revisaNodos(String nombre) {
		for(String i : nodosMap.values()) {
			if(nombre.equals(i)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Función encargada de agregar una nueva arista cuando el usuario
	 * lo desee, posee las validaciones necesarias para agregar aristas
	 * entre lugares existentes.
	 */
	public void agregarArista() {
		Label lblAgregaArista = factoria.dibujaLabel(10, 275, "Agregar Nuevo Camino");
		TextField txtInicio = factoria.crearCajaTexto(100, 300);
		Label lblInicio = factoria.dibujaLabel(10, 300, "Inicio: ");
		TextField txtDestino = factoria.crearCajaTexto(100, 360);
		Label lblDestino = factoria.dibujaLabel(10, 360, "Destino: ");
		TextField txtDistancia = factoria.crearCajaTexto(100, 330);
		Label lblDistancia = factoria.dibujaLabel(10, 330, "Distancia: ");
		Label lblIndicacion1 = factoria.dibujaLabel(255, 300, "Si el nombre del lugar es de más de una palabra");
		Label lblIndicacion2 = factoria.dibujaLabel(255, 325, "utilizar comillas simples al inicio y al final.");
		Label lblIndicacion3 = factoria.dibujaLabel(375, 350, "Ejemplo: 'san jose', cartago");
		
		lblAgregaArista.setTextFill(Color.RED);
		
		Button btnAgregar = new Button("Agregar Camino");
		
	    btnAgregar.setMaxSize(250, 250);  
	    btnAgregar.setLayoutX(255);
	    btnAgregar.setLayoutY(356);
	    
	    btnAgregar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				Alert alert = new Alert(AlertType.INFORMATION);
				String inicial = txtInicio.getText().toLowerCase();
				String destino = txtDestino.getText().toLowerCase();
				String pesoNuevo = txtDistancia.getText().toLowerCase();
				String pesoViejo = "";
				String aristaNueva = "arista(" + inicial + ", " + destino + ", " 
						+ pesoNuevo + ").";

				if(!revisaNodos(inicial) && !revisaNodos(destino)
						&& txtDistancia.getText().toLowerCase().length() != 0) {
					if(revisaAristas(inicial, destino)) {
						alert.setTitle("¡Alerta!");
						alert.setHeaderText("Ya existe un camino");
						alert.showAndWait();
					}else {
						txtDistancia.setText("");
						txtInicio.setText("");
						txtDestino.setText("");
						
						if(revisaAristas(destino, inicial)) {
							Map<String, Term>[] aristasDestino = consulta.solicitarAristas(destino);
							for(Integer i = 0; i < aristasDestino.length; i++) {
								if(aristasDestino[i].get("Y").toString().equals(inicial)) {
									pesoViejo = aristasDestino[i].get("Z").toString();
									break;
								}
							}
							alert.setTitle("¡Ya existe una distancia!");
							alert.setHeaderText("Se añadió el camino con la distancia predefinida");
							alert.showAndWait();
							aristaNueva = "arista(" + inicial + ", " + destino + ", " 
									+ pesoViejo + ").";
						editor.ModificarFichero(aristaNueva, "%AgregaArista");	
						}else {
							alert.setTitle("¡Éxito!");
							alert.setHeaderText("Se añadió el nuevo camino");
							alert.showAndWait();
							aristaNueva = "arista(" + inicial + ", " + destino + ", " 
										+ pesoNuevo + ").";
							editor.ModificarFichero(aristaNueva, "%AgregaArista");	
						}
					}
				}else {
					alert.setTitle("¡Error!");
					alert.setHeaderText("Imposible añadir camino");
					alert.setContentText("El lugar de inicio o destino no existe o la distancia es inválida");
					alert.showAndWait();
				}
			}
	    });
		
		layoutAgregar.getChildren().addAll(txtInicio, lblInicio, txtDestino, lblDestino, txtDistancia, 
				lblDistancia, lblAgregaArista, btnAgregar, lblIndicacion1, lblIndicacion2, lblIndicacion3);
	}
	
	/**
	 * Función que revisa si la arista ya existe entre dos lugares.
	 * @param inicio lugar de inicio.
	 * @param destino lugar de destino.
	 * @return true si existe, false si no.
	 */
	public Boolean revisaAristas(String inicio, String destino) {
		Map<String, Term>[] aristasInicio = consulta.solicitarAristas(inicio);
		
		for(Integer i = 0; i < aristasInicio.length; i++) {
			if(aristasInicio[i].get("Y").toString().equals(destino)) {
				return true;
			}
		}
		return false;
	}
}