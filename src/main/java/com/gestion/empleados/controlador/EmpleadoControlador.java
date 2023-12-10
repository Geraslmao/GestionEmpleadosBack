package com.gestion.empleados.controlador;

import com.gestion.empleados.excepciones.ResourceNotFoundException;
import com.gestion.empleados.modelo.Empleado;
import com.gestion.empleados.repositorio.EmpleadoRepositorio;
import jakarta.annotation.Resource;
import jakarta.persistence.Id;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200")
public class EmpleadoControlador {

    @Autowired
    private EmpleadoRepositorio repositorio;

    //sirve para hacer un get a todo
    @GetMapping("/empleados")
    public List<Empleado> listarTodosLosEmpleados() {
        return repositorio.findAll();
    }
    //sirve para crear
    @PostMapping("/empleados")
    public Empleado guardarEmpleado(@RequestBody Empleado empleado){
        return repositorio.save(empleado);
    }
    //obtiene empleado por id
    @GetMapping("/empleado/{id}")
    public ResponseEntity<Empleado> obtenerEmpleadoPorId(@PathVariable Long id){
        Empleado empleado = repositorio.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No existe el empleado con el ID:" + id));
        return ResponseEntity.ok(empleado);
    }
    @PutMapping("/empleado/{id}")
    public ResponseEntity<Empleado> actualizarEmpleadoPor(@PathVariable Long id, @RequestBody Empleado detallesEmpleado){
        Empleado empleado = repositorio.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No existe el empleado con el ID:" + id));
        empleado.setNombre((detallesEmpleado.getNombre()));
        empleado.setApellido(detallesEmpleado.getApellido());
        empleado.setEmail(detallesEmpleado.getEmail());

        Empleado empleadoActualizado = repositorio.save(empleado);
        return ResponseEntity.ok(empleado);
    }
    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<Map<String,Boolean >> eliminarEMpleado(@PathVariable Long id){
        Empleado empleado = repositorio.findById(id)
                .orElseThrow(()->new ResourceNotFoundException(("No existe el id del empleado")));
        repositorio.delete(empleado);
        Map<String,Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminar", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }
}
