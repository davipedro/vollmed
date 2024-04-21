package med.voll.api.infra.exception.consulta;

public class ConsultaHorarioException extends RuntimeException{
    
    public ConsultaHorarioException(){}

    public ConsultaHorarioException(String mensagem){
        super(mensagem);
    }
}
