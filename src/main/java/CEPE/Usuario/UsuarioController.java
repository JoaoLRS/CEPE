package CEPE.Usuario;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/cadastrar-usuario")
    public String cadastrarUsuario(Model model) {
        model.addAttribute("usuario", new UsuarioRecordDto("", "", ""));
        return "usuario/cadastrarUsuario";
    }


    @PostMapping("/cadastrar-usuario/salvar")
    public String salvarUsuario(@ModelAttribute UsuarioRecordDto usuarioRecordDto, Model model) {
        try {
            usuarioService.saveUsuario(usuarioRecordDto);
            model.addAttribute("sucesso", "Usuário cadastrado com sucesso!");
            return "redirect:/consultar-usuario";
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao cadastrar usuário: " + e.getMessage());
            model.addAttribute("usuario", usuarioRecordDto);
            return "usuario/cadastrarUsuario";
        }
    }


    @GetMapping("/consultar-usuario")
    public String consultarUsuario(Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "usuario/consultarUsuario";
    }


}
