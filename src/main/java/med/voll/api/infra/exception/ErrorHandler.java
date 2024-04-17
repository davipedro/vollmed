package med.voll.api.infra.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {
    
    @ExceptionHandler(IdInexistenteException.class)
    public ResponseEntity<Object> idInexistente(){
        return ResponseEntity.notFound().build();
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
