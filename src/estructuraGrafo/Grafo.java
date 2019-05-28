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

public class Grafo {
    private List<Nodo> nodos;
    private Consultas consulta = new Consultas();
    
    public void addNode(Nodo nodo) {
        if (nodos == null) {
            nodos = new ArrayList<>();
        }
        nodos.add(nodo);
    }
 
    public List<Nodo> getNodos() {
        return nodos;
    }
 
    
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
