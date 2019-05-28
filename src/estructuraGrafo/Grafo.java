package estructuraGrafo;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.jpl7.Query;
import org.jpl7.Term;

import datos.Consultas;
/**
 * Clase que define los metodos principales para crear un
 *  objeto grafo.
 *  Incluye las variables: 
 *  			- nodos : Lista.
 *  			- consultas : objeto de la clase consultas
 * Incluye los metodos :
 * 				-addNode
 * 				- getNodos
 * 				- buscaNodo
 * 				- existeNodo 
 * 				- crearGrafo
 */
public class Grafo {
    private List<Nodo> nodos;
    private Consultas consulta = new Consultas();
    /**
     * Anade un nodo a la lista para formar un grafo.
     * @param nodo : Nodo 
     */
    public void addNode(Nodo nodo) {
        if (nodos == null) {
            nodos = new ArrayList<>();
        }
        nodos.add(nodo);
    }
    /**
     * Obtiene la lista de nodos que froman el grafo.
     * @return list<Nodo> 
     */
    public List<Nodo> getNodos() {
        return nodos;
    }
    /**
     * Busca un nodo con el nombre del lugar
     * En caso de que exista de devuelve sino lo agrega y devuelve.
     * @param nombre : String con el nombre 
     * @return Nodo que conside con el nombre
     */
    public Nodo buscaNodo(String nombre) {
    	if(nodos != null) {
	    	for(Integer i = 0; i < nodos.size(); i++) {
	    		if(nombre.equals(nodos.get(i).getLugar())) {
	    			return nodos.get(i);
	    		}
	    	} 
    	}
    	Nodo nodoNuevo = new Nodo(nombre);
    	nodos.add(nodoNuevo);
    	return nodoNuevo;
    }
    /**
     * Comprueba si en la lista de nodos existe un nodo con 
     *     el nombre ingresado
     * @param nombre : String con el nombre.
     * @return true en caso de encontrarlo, false en caso contrario.
     */
    public Boolean existeNodo(String nombre) {
    	if(nodos != null) {
	    	for(Integer i = 0; i < nodos.size(); i++) {
	    		if(nombre.equals(this.nodos.get(i).getLugar())) {
	    			return true;
	    		}
	    	} 
    	}
    	return false;
    }
    /**
     * Crea el grafo agregando los nodos por medio de consultas a porlog
     * Agrega el nombre al nodo, la posicion X, Y y la arista con otros nodos.
     */
    public void crearGrafo() {

    	//Crea los nodos
    	Map<String, Term>[] todosVertices = consulta.solicitarVertices();
		for(Integer i = 0; i < todosVertices.length; i++) {
			if(existeNodo(todosVertices[i].get("X").toString())){
				continue;	
			}
			else {
				this.addNode(new Nodo(todosVertices[i].get("X").toString()));
			}
		}
	
		for(Integer i = 0; i < todosVertices.length; i++) {
			if(existeNodo(todosVertices[i].get("Y").toString())) {
				continue;
			}
			else {
				this.addNode(new Nodo(todosVertices[i].get("Y").toString()));
			}
		}
		
		//Agrega las aristas y las posiciones de los nodos
		for(Integer i = 0; i < nodos.size(); i++) {
			Map<String, Term>[] todasAristas = consulta.solicitarAristas(nodos.get(i).getLugar());
			Map<String, Term>[] todasPosiciones = consulta.solicitarPosiciones(nodos.get(i).getLugar());
			for(Integer j = 0; j < todasAristas.length; j++) {
				
				nodos.get(i).addArista(new Arista(nodos.get(i), buscaNodo(todasAristas[j].get("Y").toString()), 
						todasAristas[j].get("Z").intValue()));
			}
			nodos.get(i).setPosX(todasPosiciones[0].get("Y").intValue());
			nodos.get(i).setPosY(todasPosiciones[0].get("Z").intValue());
			
		}
    }
}
