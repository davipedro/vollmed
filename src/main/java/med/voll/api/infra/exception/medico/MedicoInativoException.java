package med.voll.api.infra.exception.medico;

public class MedicoInativoException extends RuntimeException{
    
    public MedicoInativoException(){}

    public MedicoInativoException(String mensagem){
        super(mensagem);
    }
}
