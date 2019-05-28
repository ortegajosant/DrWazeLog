package interfaz;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

/**
 * Clase encargada de producir diferentes elementos gráficos
 * solicitados por el programador.
 */
public class GraficosFactoria {
	
	//Atributos
	private ArrayList<Color> listaColores = new ArrayList<Color>();
	String texto = "";
	Random rand = new Random();			
    
	/**
	 * Función que crea un ovalo para ser utilizado sobre la interfaz.
	 * @param posX posición sobre el eje X donde se desea colocar.
	 * @param posY posición sobre el eje Y donde se desea colocar.
	 * @param medida medida del ancho y largo que se desea.
	 * @return un objeto de tipo Rectangle con los parámetros requeridos.
	 */
    public Rectangle crearOvalo(Integer posX, Integer posY, Integer medida) {
    	final Rectangle ovalo = new Rectangle(posX, posY, medida + 40, medida+5);
    
    	ovalo.setArcHeight(medida+10);
    	ovalo.setArcWidth(medida+40);
    	ovalo.setFill(Color.WHITE);
    	ovalo.setStrokeWidth(0);
    	ovalo.setStroke(Color.BLACK);
    	
    	Image fondoNodo;
		try {
			fondoNodo = new Image(new FileInputStream("img/inicio.png"));
	    	ovalo.setFill(new ImagePattern(fondoNodo));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

    	return ovalo;
    }
    
    /**
     * 
     * Función que crea un Texto para ser utilizado sobre la interfaz.
     * @param texto lo que se desea que diga.
     * @param posX posición sobre el eje X donde se desea colocar.
     * @param posY posición sobre el eje Y donde se desea colocar.
     * @param tamano tamaño de la letra
     * @return un objeto de tipo Text con los parámetros requeridos.
     */
    public Text crearTexto(String texto, Integer posX, Integer posY, Integer tamano) {
    	if(texto.charAt(0) == '\'') {
    		texto = texto.substring(1, texto.length()-1);
    	}
		Text text = new Text(texto.toUpperCase().charAt(0) + texto.substring(1, texto.length()));
		text.setLayoutX(posX + 17);
		text.setLayoutY(posY-5);
		text.setFont(new Font(tamano));
		text.setFill(Color.BLACK);
		text.setBoundsType(TextBoundsType.VISUAL); 
		
		return text;
    }
    
    /**
     * Función que crea una línea para ser utilizada en la interfaz.
     * @param inicioX posición inicial sobre el eje X.
     * @param inicioY posición inicial sobre el eje Y.
     * @param finalX posición final sobre el eje X.
     * @param finalY posición final sobre el eje Y.
     * @return un objeto de tipo Line con los parámetros requeridos.
     */
    public Line dibujaArista(Integer inicioX, Integer inicioY, Integer finalX, Integer finalY) {
    	Line lineaAux = new Line(inicioX, inicioY, finalX, finalY);
    	lineaAux.setStroke(Color.GREEN);
    	lineaAux.setStrokeWidth(2);
    	lineaAux.setStrokeLineCap(StrokeLineCap.SQUARE);
    	
    	return lineaAux;
    }
    
    /**
     * Función que crea una etiqueta para ser utilizada en la interfaz.
     * Esta función realiza un formato de ruta con la lista brindada.
     * @param posX posición sobre el eje X donde se desea colocar.
     * @param posY posición sobre el eje Y donde se desea colocar.
     * @param ruta lista con la ruta a mostrar,
     * @return Una etiqueta con formato de ruta
     */
    public Label dibujaLabel(Integer posX, Integer posY, List<String> ruta) {
    	texto = "";
    	for(String i : ruta) {
    		texto += " -> " + i;
    	}
    	Label etiqueta = new Label(texto);
    	etiqueta.setFont(new Font("Calibri", 15));
    	etiqueta.setStyle("-fx-background-color:POWDERBLUE");
    	etiqueta.setLayoutX(posX);
    	etiqueta.setLayoutY(posY);
    	etiqueta.setScaleX(1);
    	etiqueta.setScaleY(1);
    	
    	return etiqueta;
    }
    
    /**
     * Función que crea una etiqueta para ser utilizada en la interfaz.
     * @param posX posición sobre el eje X donde se desea colocar.
     * @param posY posición sobre el eje Y donde se desea colocar.
     * @param texto texto a mostrar en la etiqueta.
     * @return un objeto de tipo Label con los parámetros requeridos.
     */
    public Label dibujaLabel(Integer posX, Integer posY, String texto) {
    	Label etiqueta = new Label(texto);
    	etiqueta.setFont(new Font("Comic Sans MS", 15));
    	etiqueta.setLayoutX(posX);
    	etiqueta.setLayoutY(posY);
    	etiqueta.setScaleX(1);
    	etiqueta.setScaleY(1);
    	etiqueta.setTextFill(Color.WHITE);
		return etiqueta;
    }
    
    /**
     * Función que crea una caja de texto para ser utilizada en la interfaz.
     * @param posX posición sobre el eje X donde se desea colocar.
     * @param posY posición sobre el eje Y donde se desea colocar.
     * @return un objeto de tipo TextField en la posición dada.
     */
    public TextField crearCajaTexto(Integer posX, Integer posY) {
    	
        TextField textField = new TextField();
        
        textField.setLayoutX(posX);
        textField.setLayoutY(posY);
        
        return textField;
    }
    
}
