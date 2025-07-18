package CEPE;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CepController {

    @GetMapping("/inicio")
    public String index() {
        return "componentes/inicio";
    }

}
