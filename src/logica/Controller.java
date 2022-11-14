
package logica;

import persistencia.ControladoraPersistencia;

/**
 *
 * @author Julio
 */
public class Controller {
    
    ControladoraPersistencia contro = new ControladoraPersistencia();
    
    public void agregar(int nroCliente, String nombre, String raza, String color, 
            String alergico, String atencionEspecial, String nombreDuenio, 
            String celularDuenio, String observaciones)
    {
        Perros perro = new Perros();
        perro.setNroCliente(nroCliente);
        perro.setNombre(nombre);
        perro.setRaza(raza);
        perro.setColor(color);
        perro.setAlergico(alergico);
        perro.setAtencionEspecial(atencionEspecial);
        perro.setNombreDuenio(nombreDuenio);
        perro.setCelularDuenio(celularDuenio);
        perro.setObservaciones(observaciones);
        
        contro.crearPerro(perro);
    }
}
