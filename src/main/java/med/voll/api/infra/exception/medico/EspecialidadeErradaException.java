package med.voll.api.infra.exception.medico;

public class EspecialidadeErradaException extends RuntimeException{
    
    public EspecialidadeErradaException(){}

    public EspecialidadeErradaException(String mensagem){
        super(mensagem);
    }
}
