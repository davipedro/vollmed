package med.voll.api.infra.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> dadoJsonInvalido(HttpMessageNotReadableException ex){
        return ResponseEntity.badRequest().body("Dados inválidos ou insuficientes");
    }

    @ExceptionHandler(PacienteDiaException.class)
    public ResponseEntity<Object> pacienteConsultasDia(PacienteDiaException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(MedicoInativoException.class)
    public ResponseEntity<Object> medicoInativo(MedicoInativoException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(HorarioOcupadoException.class)
    public ResponseEntity<Object> horarioOcupado(HorarioOcupadoException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(AntecedenciaException.class)
    public ResponseEntity<Object> antecedenciaInvalida(AntecedenciaException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(ConsultaHorarioException.class)
    public ResponseEntity<Object> horarioInvalido(ConsultaHorarioException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(EspecialidadeErradaException.class)
    public ResponseEntity<Object> especilidadeErrada(EspecialidadeErradaException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(EspecialidadeNaoEncontrada.class)
    public ResponseEntity<Object> especilidadeInexistente(EspecialidadeNaoEncontrada ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(MedicosIndisponiveisException.class)
    public ResponseEntity<Object> medicosIndisponiveis(MedicosIndisponiveisException ex){
        return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(ex.getMessage());
    }

    @ExceptionHandler(ConsultaException.class)
    public ResponseEntity<Object> consultaInvalida(ConsultaException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    
    @ExceptionHandler(IdInexistenteException.class)
    public ResponseEntity<Object> idInexistente(IdInexistenteException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(PacienteNaoEncontradoException.class)
    public ResponseEntity<Object> pacienteNaoEncontrado(PacienteNaoEncontradoException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(MedicoNaoEncontradoException.class)
    public ResponseEntity<Object> MedicoNaoEncontrado(MedicoNaoEncontradoException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
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
