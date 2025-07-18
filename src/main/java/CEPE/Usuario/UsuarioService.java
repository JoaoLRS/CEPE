package CEPE.Usuario;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService{
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @Transactional
    public Usuario saveUsuario(UsuarioRecordDto usuarioRecordDto) {
        Usuario usuario = new Usuario();
        usuario.setCpf(usuarioRecordDto.cpf());
        usuario.setNome(usuarioRecordDto.nome());
        usuario.setEmail(usuarioRecordDto.email());

        return usuarioRepository.save(usuario);
    }


    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }
}
