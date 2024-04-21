package med.voll.api.infra.exception.medico;

public class MedicoNaoEncontradoException extends RuntimeException{
    
    public MedicoNaoEncontradoException(){}

    public MedicoNaoEncontradoException(String mensagem){
        super(mensagem);
    }
}
