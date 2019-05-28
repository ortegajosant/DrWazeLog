package reader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class ManejoPL {
	
	static File Ffichero;
	private static final String NOMBRE = "GRAFO.pl";
	
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
