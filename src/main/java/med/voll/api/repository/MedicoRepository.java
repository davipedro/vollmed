package med.voll.api.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import med.voll.api.model.Especialidade;
import med.voll.api.model.medico.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long>{

    Page<Medico> findAllByAtivoTrue(Pageable paginacao);

    @Query("SELECT m FROM Medico m WHERE m.ativo = true AND m.especialidade = :especialidade AND m.id NOT IN (select c.medico.id from Consulta c where c.data = :data)")
    List<Medico> buscarDisponiveis(Especialidade especialidade, LocalDateTime data);


    @Query("SELECT m FROM Medico m WHERE m.ativo = true AND m.especialidade = :especialidade AND m.id NOT IN (select c.medico.id from Consulta c where c.data = :data) order by RANDOM() limit 1")
    Optional<Medico> buscarMedicoAleatorio(LocalDateTime data, Especialidade especialidade);

    @Query("""
            SELECT m.ativo
            FROM Medico m
            WHERE
            m.id = :id
            """)
    Boolean findAtivoById(Long id);
    
}
