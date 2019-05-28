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

public class GraficosFactoria {
	
	private ArrayList<Color> listaColores = new ArrayList<Color>();
	String texto = "";
	Random rand = new Random();
			
    public Circle crearCirculo(Integer posX, Integer posY, Integer radio) {
        final Circle circle = new Circle(radio);
        
        circle.setStroke(Color.FORESTGREEN);
        circle.setStrokeWidth(5);
        circle.setStrokeType(StrokeType.INSIDE);
        circle.setFill(Color.AZURE);
        circle.relocate(0, 0);
		circle.setLayoutX(posX);
		circle.setLayoutY(posY);

        return circle;
    }
    
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
    
    public Line dibujaArista(Integer inicioX, Integer inicioY, Integer finalX, Integer finalY) {
    	Line lineaAux = new Line(inicioX, inicioY, finalX, finalY);
    	lineaAux.setStroke(Color.GREEN);
    	lineaAux.setStrokeWidth(2);
    	lineaAux.setStrokeLineCap(StrokeLineCap.SQUARE);
    	
    	return lineaAux;
    }
    
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
    
    public Label dibujaLabel(Integer posX, Integer posY, String texto) {
    	Label etiqueta = new Label(texto);
    	etiqueta.setFont(new Font("Comic Sans MS", 15));
    	etiqueta.setLayoutX(posX);
    	etiqueta.setLayoutY(posY);
    	etiqueta.setScaleX(1);
    	etiqueta.setScaleY(1);
		return etiqueta;
    }
    
    public HBox crearCajaTexto(Integer posX, Integer posY) {
    	
        TextField textField = new TextField();
        HBox hbox = new HBox(textField);
        hbox.setLayoutX(posX);
        hbox.setLayoutY(posY);
        
        return hbox;
    }
    
}
