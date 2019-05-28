package interfaz;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

/**
 * Clase para la creacion una linea que une  cada nodo para mostrar 
 *  visualmente la arista.
 *  Se compone de dos constructores para insertar las caracteristicas a las lineas.
 *
 */
public class Flecha extends Path{
    private static final double tamanoPredetereminado = 7.0;
    
    /**
     * Constructor que inserta parametros a la flecha.
     * @param inicioX : Double donde inicia la linea en el eje x 
     * @param inicioY : Double donde inicia la linea en el eje Y
     * @param finalX : Double donde termina la linea en el eje X
     * @param finalY : Double donde termina la linea en el eje Y
     * @param tamanoFlecha : Double con el grosor de la punta de la flecha.
     */
    public Flecha(double inicioX, double inicioY, double finalX, double finalY, double tamanoFlecha){
        super();
        strokeProperty().bind(fillProperty());
        setFill(Color.GREEN);
        
        //Linea
        getElements().add(new MoveTo(inicioX, inicioY));
        getElements().add(new LineTo(finalX, finalY));
        
        //Flecha
        double angulo = Math.atan2((finalY - inicioY), (finalX - inicioX)) - Math.PI / 2.0;
        double sin = Math.sin(angulo);
        double cos = Math.cos(angulo);
        //Punto 1
        double x1 = (- 1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * tamanoFlecha + finalX;
        double y1 = (- 1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * tamanoFlecha + finalY;
        //Punto 2
        double x2 = (1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * tamanoFlecha + finalX;
        double y2 = (1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * tamanoFlecha + finalY;
        
        getElements().add(new LineTo(x1, y1));
        getElements().add(new LineTo(x2, y2));
        getElements().add(new LineTo(finalX, finalY));
    }
    /**
     * Realiza la misma funcion que el primer constrcutor, solo que en este caso usa el tamano de flecha
     * preterminado.
     * @param inicioX : Double donde inicia la linea en el eje x 
     * @param inicioY : Double donde inicia la linea en el eje Y
     * @param finalX : Double donde termina la linea en el eje X
     * @param finalY : Double donde termina la linea en el eje Y
     */
    public Flecha(double inicioX, double inicioY, double finalX, double finalY){
        this(inicioX, inicioY, finalX, finalY, tamanoPredetereminado);
    }
}