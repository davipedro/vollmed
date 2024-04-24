package med.voll.api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import med.voll.api.dto.consulta.RequestAgendamento;
import med.voll.api.dto.consulta.ResponseDadosConsulta;
import med.voll.api.dto.consulta.ResponseDadosMedicoResumo;
import med.voll.api.dto.consulta.ResponseDadosPacienteResumo;
import med.voll.api.model.Especialidade;
import med.voll.api.service.ConsultaService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class ConsultaControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<RequestAgendamento> jsonRequestAgendamento;

    @Autowired
    private JacksonTester<ResponseDadosConsulta> jsonResponseDadosConsulta;

    @MockBean
    private ConsultaService consultaService;

    @Test
    @WithMockUser
    @DisplayName("Deveria devolver codigo HTTP 400 quado informar dados invalidos para agendamento de consulta")
    void agendar_cenario1() throws Exception {

        var response = mockMvc.perform(post("/consultas/agendar")).andReturn().getResponse();
        
        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @WithMockUser
    @DisplayName("Deveria devolver codigo HTTP 200 quando informar dados validos para agendamento de consulta")
    void agendar_cenario2() throws Exception {
        
        var data = LocalDateTime.now().plusDays(1);
        var especialidade = Especialidade.CARDIOLOGIA;
        
        var resumoMedico = new ResponseDadosMedicoResumo(
            1L,
            "Dr. Fulano",
            especialidade,
            "123456"
        );

        var resumoPaciente = new ResponseDadosPacienteResumo(
            1L,
            "Fulano",
            "12345678901",
            "44441111"
        );

        var respostaConsulta = new ResponseDadosConsulta(
            null,
            resumoMedico,
            resumoPaciente,
            data);

        when(consultaService.agendar(eq(false), any())).thenReturn(respostaConsulta);
            

        var response = mockMvc.perform(
            post("/consultas/agendar?aleatorio=false")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonRequestAgendamento.write(new RequestAgendamento(
                1L,
                especialidade,
                1L,
                data))
                .getJson())
            )
            .andReturn().getResponse();
        
        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());


        var jsonEsperado = jsonResponseDadosConsulta.write(respostaConsulta).getJson();

        Assertions.assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    @Test
    void testCancelar() {

    }
}
