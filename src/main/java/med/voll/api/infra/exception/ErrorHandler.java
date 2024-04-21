package med.voll.api.infra.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import med.voll.api.infra.exception.consulta.AntecedenciaException;
import med.voll.api.infra.exception.consulta.ConsultaException;
import med.voll.api.infra.exception.consulta.ConsultaHorarioException;
import med.voll.api.infra.exception.consulta.HorarioOcupadoException;
import med.voll.api.infra.exception.medico.EspecialidadeErradaException;
import med.voll.api.infra.exception.medico.EspecialidadeNaoEncontrada;
import med.voll.api.infra.exception.medico.MedicoInativoException;
import med.voll.api.infra.exception.medico.MedicoNaoEncontradoException;
import med.voll.api.infra.exception.medico.MedicosIndisponiveisException;
import med.voll.api.infra.exception.paciente.PacienteDiaException;
import med.voll.api.infra.exception.paciente.PacienteNaoEncontradoException;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(PacienteDiaException.class)
    public ResponseEntity<Object> pacienteConsultasDia(){
        return ResponseEntity.badRequest().body("Paciente já possui uma consulta agendada nesse dia");
    }

    @ExceptionHandler(MedicoInativoException.class)
    public ResponseEntity<Object> medicoInativo(){
        return ResponseEntity.badRequest().body("Consulta não pode ser agendada com médico excluído");
    }

    @ExceptionHandler(HorarioOcupadoException.class)
    public ResponseEntity<Object> horarioOcupado(){
        return ResponseEntity.badRequest().body("O horario já está ocupado por outro paciente!");
    }

    @ExceptionHandler(AntecedenciaException.class)
    public ResponseEntity<Object> antecedenciaInvalida(){
        return ResponseEntity.badRequest().body("A consulta de ser agendada com antecedencia minima de 30 minutos!");
    }

    @ExceptionHandler(ConsultaHorarioException.class)
    public ResponseEntity<Object> horarioInvalido(){
        return ResponseEntity.badRequest().body("O horário fornecido está fora do horário de atendimento!");
    }

    @ExceptionHandler(EspecialidadeErradaException.class)
    public ResponseEntity<Object> especilidadeErrada(){
        return ResponseEntity.badRequest().body("O médico não condiz com a especialidade fornecida!");
    }

    @ExceptionHandler(EspecialidadeNaoEncontrada.class)
    public ResponseEntity<Object> especilidadeInexistente(){
        return ResponseEntity.badRequest().body("Especialidade não encontrada!");
    }

    @ExceptionHandler(MedicosIndisponiveisException.class)
    public ResponseEntity<Object> medicosIndisponiveis(){
        return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("Não foi possível criar a consulta!");
    }

    @ExceptionHandler(ConsultaException.class)
    public ResponseEntity<Object> consultaInvalida(){
        return ResponseEntity.badRequest().body("Não foi possível criar a consulta!");
    }
    
    @ExceptionHandler(IdInexistenteException.class)
    public ResponseEntity<Object> idInexistente(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(PacienteNaoEncontradoException.class)
    public ResponseEntity<Object> pacienteNaoEncontrado(){
        return ResponseEntity.badRequest().body("Paciente não encontrado!");
    }

    @ExceptionHandler(MedicoNaoEncontradoException.class)
    public ResponseEntity<Object> MedicoNaoEncontrado(){
        return ResponseEntity.badRequest().body("Médico não encontrado!");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> dadosErrados(MethodArgumentNotValidException exception){
        var erros = exception.getFieldErrors();
        
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValicao::new).toList());
    }

    private record DadosErroValicao(String campo, String mensagem){

        public DadosErroValicao(FieldError erro){
            this(erro.getField(), erro.getDefaultMessage());
        }
    }
}
