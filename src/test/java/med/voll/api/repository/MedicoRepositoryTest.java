package med.voll.api.repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import med.voll.api.dto.DadosEndereco;
import med.voll.api.dto.medico.RegistroMedicoDTO;
import med.voll.api.dto.paciente.RequestCadastroPaciente;
import med.voll.api.model.Especialidade;
import med.voll.api.model.consulta.Consulta;
import med.voll.api.model.medico.Medico;
import med.voll.api.model.paciente.Paciente;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    void testBuscarDisponiveis() {

    }

    @Test
    @DisplayName("Deveria devolver empty quando unico medico cadastrado nao esta disponivel na data")
    void testBuscarMedicoAleatorioCenario1() {

        var proximaSegundaAsDez = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,0);

        var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);

        var paciente = cadastrarPaciente("Paciente", "paciente@email.com", "00000000000");

        cadastrarConsulta(medico, paciente, proximaSegundaAsDez);
        
        var medicoLivre = medicoRepository.buscarMedicoAleatorio(proximaSegundaAsDez, Especialidade.CARDIOLOGIA);

        Assertions.assertThat(medicoLivre).isEmpty();
    }

    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
        Consulta consulta = new Consulta(null, medico, paciente, data, null);
        em.persist(consulta);
    }
    
    private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
        //TO-DO: adicionar o endereço especificando o CEP
        var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
        medico.getEndereco().setCep("29254349");
        em.persist(medico);
        return medico;
    }
    
    private Paciente cadastrarPaciente(String nome, String email, String cpf) {
        var paciente = new Paciente(dadosPaciente(nome, email, cpf));
        em.persist(paciente);
        return paciente;
    }
    
    private RegistroMedicoDTO dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
        return new RegistroMedicoDTO(
                nome,
                email,
                "61999999999",
                crm,
                especialidade,
                dadosEndereco()
        );
    }
    
    private RequestCadastroPaciente dadosPaciente(String nome, String email, String cpf) {
        return new RequestCadastroPaciente(
                nome,
                email,
                "61999999999",
                cpf,
                dadosEndereco()
        );
    }
    
    private DadosEndereco dadosEndereco() {
        return new DadosEndereco(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                "MG",
                "29254349"
        );
    }
}
