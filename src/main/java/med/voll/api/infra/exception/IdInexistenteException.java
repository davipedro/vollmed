package med.voll.api.infra.exception;

public class IdInexistenteException extends RuntimeException{

    public IdInexistenteException(){}

    public IdInexistenteException(String mensagem){
        super(mensagem);
    }
}
