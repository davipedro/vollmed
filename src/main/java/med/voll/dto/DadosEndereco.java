package med.voll.dto;

public record DadosEndereco(String logradouro, 
                            String numero, 
                            String complemento, 
                            String bairro, 
                            String cidade, 
                            String UF, 
                            String CEP) {
    
}