package med.voll.api.infra.exception.consulta;

public class HorarioOcupadoException extends RuntimeException{
    
    public HorarioOcupadoException(){}

    public HorarioOcupadoException(String mensagem){
        super(mensagem);
    }
}
