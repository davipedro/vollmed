package med.voll.api.infra.exception.consulta;

public class AntecedenciaException extends RuntimeException{
    
    public AntecedenciaException(){}

    public AntecedenciaException(String mensagem){
        super(mensagem);
    }
}
