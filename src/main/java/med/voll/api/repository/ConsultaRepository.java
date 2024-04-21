package med.voll.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import med.voll.api.model.consulta.Consulta;
import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long>{
    
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END FROM Consulta c WHERE c.data = :data AND c.medico.id = :medicoId")
    Boolean existeConsultaNaData(LocalDateTime data, Long medicoId);

    Boolean existsByPacienteIdAndDataBetween(Long idPaciente, LocalDateTime primeiroHorario, LocalDateTime ultimoHorario);
}
