package exemplo.autenticacao.mngbeans;

import static com.sun.activation.registries.LogSupport.log;
import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utfpr.faces.support.RequestBean;

@ManagedBean
@RequestScoped
public class LoginBean extends RequestBean {
    
    private String login;
    private String senha;
    private String result;
    
    public LoginBean() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String logoutAction() {
        String action = null;
        HttpServletRequest request = getRequest();
        try {
            request.logout();
            action = "/index.xhtml?faces-redirect=true";
        } catch (ServletException e) {
            log("logoutAction", e);
        }
        return action;
    }
    
    public String loginAction() {
        String action = null;
        HttpServletRequest request = getRequest();
        try {
            request.login(login, senha);
            if (request.isUserInRole("administrador"))
                action = "/admin/admin";
            else if (request.isUserInRole("professor"))
                action = "prof/professor";
            else if (request.isUserInRole("aluno"))
                action = "aluno/aluno";
            else
                error("Perfil sem permissão de acesso");
        } catch (ServletException e) {
            error("Autenticação falhou");
        }
        return action;
    }
}
