package mx.edu.poo.repository;

import mx.edu.poo.entity.Registro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface RegistroRepository extends JpaRepository<Registro, Integer> {
    List<Registro> findByAlumnoMatricula(Integer matricula);
    List<Registro> findByFecha(LocalDate fecha);
}