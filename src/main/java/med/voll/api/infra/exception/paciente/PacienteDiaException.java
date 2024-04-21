package med.voll.api.infra.exception.paciente;

public class PacienteDiaException extends RuntimeException{
    
    public PacienteDiaException(){}

    public PacienteDiaException(String mensagem){
        super(mensagem);
    }
}
