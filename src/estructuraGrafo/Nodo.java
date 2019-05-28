package estructuraGrafo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * Clase que define las caracteristicas para crear un nodo.
 * Incluye las variables:
 * 				- lugar : String
 *				- posX : Integer
 *				- posY : Integer
 *				- aristas : lits<Arista>
 */
public class Nodo {
    private String lugar;
    private Integer posX = 0;
    private Integer posY = 0;
    private List<Arista> aristas;
 
    /**
     * Contructor para nodo,
     * @param lugar : String con el nombre del nodo.
     */
    public Nodo(String lugar) {
        this.lugar = lugar;
    }
 
    /**
     * Obtiene la poscion en X del nodo
     * @return Integer con el valor de la posicion.
     */
    public Integer getPosX() {
		return posX;
	}
    /**
     * Inserta un valor a X.
     * @param posX : Integer con el valor.
     */
	public void setPosX(Integer posX) {
		this.posX = posX;
	}
	
	/**
	 * 	Obtiene la posicion en Y del nodo.
	 * @return Integer con el valor de la posicion.
	 */
	public Integer getPosY() {
		return posY;
	}

	/**
	 * Inserta un valor a la variable posY.
	 * @param posY : Integer con el valor.
	 */
	public void setPosY(Integer posY) {
		this.posY = posY;
	}

	/**
	 * Inserta un valor a la variable arista.
	 * @param aristas : list<Arista> Lista con el valor de las aristas.
	 */
	public void setAristas(List<Arista> aristas) {
		this.aristas = aristas;
	}

	/**
	 * Obtiene el valor de la variable lugar.
	 * @return String con el nombre del valor.
	 */
	public String getLugar() {
        return lugar;
    }
 
	/**
	 * Inserta un nombre a ala variable lugar.
	 * @param lugar : String el nombre del lugar.
	 */
    public void setLugar(String lugar) {
        this.lugar = lugar;
    }
 
    /**
     * Obtiene la lista que contiene las aristas del nodo.
     * @return list<Aristas> con el valor de las aristas del nodo.
     */
    public List<Arista> getAristas() {
        return aristas;
    }
 
    /**
     * Anade las nuevas aristas a la lista.
     * @param arista Objeto de la clase Arista.
     */
    public void addArista(Arista arista) {
        if (aristas == null) {
            aristas = new ArrayList<>();
        }
        aristas.add(arista);
    }
    /**
     * Sobreescritura al metodo toString para formar un string con el valor de
     * el nombre del lugar y los nombres de los nodos de las aristas.
     */
    @Override
    public String toString() {
        return "\n[" + lugar + ", " + aristas + "]";
    }
}
