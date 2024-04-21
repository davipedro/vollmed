package med.voll.api.infra.exception.paciente;

public class PacienteNaoEncontradoException extends RuntimeException{
    
    public PacienteNaoEncontradoException(){};
    
    public PacienteNaoEncontradoException(String mensagem){
        super(mensagem);
    }
}
