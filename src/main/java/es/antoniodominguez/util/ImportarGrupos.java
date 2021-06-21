
package es.antoniodominguez.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ImportarGrupos {

 
    public static void main(String[] args) {
        
        try {
            Connection conexionBD = DriverManager.getConnection(
                    "jdbc:derby:BDdisennadores", 
                    "APP", "");
            
            Statement sentenciaSQL = conexionBD.createStatement();
            
            String nombreFichero = "Grupos.csv";
            
            BufferedReader br = new BufferedReader(new FileReader(nombreFichero));
            
            String texto = br.readLine();
            
            texto = br.readLine();
            
            while(texto != null) {
                String[] lineaGrupo = texto.split(",");
                //String idGrupo = lineaGrupo[0];
                String estudioGrupo = lineaGrupo[0].replace("\"", "");
                
                String select = "SELECT ESTUDIO FROM GRUPODISENNO WHERE ESTUDIO = '"+estudioGrupo+"'";
                System.out.println(select);
                ResultSet resultados = sentenciaSQL.executeQuery(select);
                if(!resultados.next()) {
                    String insert = "INSERT INTO GRUPODISENNO(ESTUDIO) "
                            + "VALUES ('"+ estudioGrupo +"')";
                        sentenciaSQL.executeUpdate(insert);
                       
                }
                texto = br.readLine();
                
            }
            conexionBD.close();
            br.close();
        } catch(SQLException e) {
            System.out.println("Se ha producido un error al conectar con la BD");
            e.printStackTrace();
        } catch(FileNotFoundException e) {
            System.out.println("Error: Fichero no encontrado");
            e.printStackTrace();
        } catch(IOException e) {
            System.out.println("Error en la lectura del fichero");
            e.printStackTrace();
        }
    }
    
}
