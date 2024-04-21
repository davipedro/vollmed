package med.voll.api.infra.exception.medico;

public class EspecialidadeNaoEncontrada extends RuntimeException{
    
    public EspecialidadeNaoEncontrada(){}

    public EspecialidadeNaoEncontrada(String mensagem){
        super(mensagem);
    }
}
