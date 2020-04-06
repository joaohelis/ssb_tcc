package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {
        render();
    }
    
    public static void inicio() {
    	render();
    }
    
    public static void inicio_aluno(){
    	render();
    }
    
    public static void verificarLogin(String user, String senha) {
    	if(senha.equals("admin") && user.equals("admin")) {
    		inicio();
    	}
    	else {
    		render("/Mensagens/mensagem-login-erro.html");
    	}
    }

}