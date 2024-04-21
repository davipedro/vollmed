package med.voll.api.infra.exception.consulta;

public class ConsultaException extends RuntimeException{
    
    public ConsultaException(){}
    
    public ConsultaException(String mensagem){
        super(mensagem);
    }
}
