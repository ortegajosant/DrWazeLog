package reader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
/**
 * Clase que define los metodos para el manejo de archivo prolog
 * Incluye los metodos 
 * 				-LeerFichero
 * 				-EscribirFichero
 *				-BorrarFichero
 *				-Modificar fichero.
 *- Incluye las variables.
 *			-Ffichero.
 *			-NOMBRE
 */
public class ManejoPL {
	
	private static final String NOMBRE = "GRAFO.pl";
	static File Ffichero = new File(NOMBRE);
	/**
	 * Abre el fichero y lee lo que hay en su interior.
	 * @param nombreFichero : String con el nombre del fichero a abrir.
	 */
	public static  void LeerFichero(String nombreFichero){
	   try {
		   Ffichero = new File(nombreFichero);
	       if(Ffichero.exists()){
	    	   BufferedReader Flee= new BufferedReader(new FileReader(Ffichero));
	           String Slinea;
	           System.out.println("**********Leyendo Fichero***********");
	           while((Slinea=Flee.readLine())!=null) {   
	           System.out.println(Slinea);              
	           }
	           System.out.println("*********Fin Leer Fichero**********");
	           Flee.close();
	         }else{
	           System.out.println("Fichero No Existe");
	         }
	   } catch (Exception ex) {
	        System.out.println(ex.getMessage());
	   }
	 }
	
	/**
	 * Crea o utiliza un fichero existente para descargar una cadena de entrada.
	 * @param Ffichero1 : Fichero en el que se descargara la un string de salida.
	 * @param SCadena : String con la informacion que se quiere guardar en el fichero.
	 */
	public static void EscribirFichero(File Ffichero1,String SCadena){
		  try {
		           if(!Ffichero1.exists()){
		               Ffichero1.createNewFile();
		           }
		          BufferedWriter Fescribe = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Ffichero1,true), "utf-8"));
		          Fescribe.write("\r\n"+SCadena);
		          Fescribe.close();
		       } catch (Exception ex) {
		          System.out.println(ex.getMessage());
		       } 
		}
	
	/**
	 * Borra un fichero, recibe el fichero ya abierto.
	 * @param Ffichero : fichero a borrar.
	 */
	public static  void BorrarFichero(File Ffichero){
	     try {
	         if(Ffichero.exists()){
	           Ffichero.delete(); 
	           System.out.println("Fichero Borrado con Exito");
	         }
	     } catch (Exception ex) {
	          System.out.println(ex.getMessage());
	     }
	}
	
	/**
	 * Modifica un fichero, crea un fichero auxiliar para agregar la nueva informacion 
	 *  y la existente en el fichero modificando la linea indicada.
	 * @param Snuevalinea : String con la nueva informacion de la linea.
	 * @param numLinea : Integer con el numero de linea a modificar.
	 */
    public static  void ModificarFichero(String Snuevalinea, Integer numLinea){    
    	
        String SnombFichNuev = "auxiliar"+numLinea+".pl";
        File FficheroNuevo=new File(SnombFichNuev);
        try {
			FficheroNuevo.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
        try {
            if(Ffichero.exists()){
            	FficheroNuevo.createNewFile();
                BufferedReader Flee= new BufferedReader(new FileReader(Ffichero));
                String Slinea;
                Integer cont = 0;
                while((Slinea=Flee.readLine())!=null) {
                	if(cont != numLinea) {
                		EscribirFichero(FficheroNuevo, Slinea);    
                	}
                	else {
                		EscribirFichero(FficheroNuevo, Snuevalinea);
                	}
                	cont++;
                }
                Flee.close();
                String SnomAntiguo=Ffichero.getName();
                BorrarFichero(Ffichero);
                FficheroNuevo.renameTo(Ffichero);
            }else{
                System.out.println("Fichero No Existe");
            }
        } catch (Exception ex) {
             System.out.println(ex.getMessage());
        }
    }

}
