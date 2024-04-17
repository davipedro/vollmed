package med.voll.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import med.voll.api.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    /**
     * faz o acesso ao banco de dados e é responsável por trazer as informações do usuário
     * @param login
     * @return Informações do usuário pelo UserDetails
     */
    UserDetails findByLogin(String login);
    
}
