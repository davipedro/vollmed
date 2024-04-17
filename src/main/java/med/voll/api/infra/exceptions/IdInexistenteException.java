package med.voll.api.infra.exceptions;

public class IdInexistenteException extends RuntimeException{

    public IdInexistenteException(){}

    public IdInexistenteException(String mensagem){
        super(mensagem);
    }
}
