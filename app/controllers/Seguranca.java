package controllers;

import enums.TipoUsuario;
import play.mvc.Before;
import play.mvc.Controller;

public class Seguranca extends Controller{

	@Before(unless= {"dados_pessoais"})
	public static void verificar() {

		if(session.get("tipo_user").equals("aluno")) {
			flash.error("Para acessar essa página é necessário estar conectado como administrador");
			Sistema.permissaoNegada();
		}
	}
	
	public static void senhaCerta() {
		
		if(Sistema.certo == false) {
			flash.error("Informe a senha para acessar essa página!");
			Sistema.segurancaSistema();
		}
		
		
	}
}