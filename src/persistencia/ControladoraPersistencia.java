
package persistencia;

import logica.Perros;

/**
 *
 * @author Julio
 */
public class ControladoraPersistencia {
    
    PerrosJpaController perrosJpa = new PerrosJpaController();
    
    public void crearPerro(Perros perro)
    {
        try 
        {
            perrosJpa.create(perro);
        } 
        catch (Exception e) 
        {
        }
        
    }
    
}
