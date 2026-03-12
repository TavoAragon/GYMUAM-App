package mx.edu.poo.repository;

import mx.edu.poo.entity.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Integer> {
    List<Prestamo> findByAlumnoMatricula(Integer matricula);
    List<Prestamo> findByFechaDevolucionIsNull();
}
