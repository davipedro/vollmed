package med.voll.api.infra.exception.medico;

public class MedicosIndisponiveisException extends RuntimeException{
    
    public MedicosIndisponiveisException(){}

    public MedicosIndisponiveisException(String mensagem){
        super(mensagem);
    }
}
