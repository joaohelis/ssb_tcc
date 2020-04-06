package controllers;

import play.mvc.Controller;
import play.mvc.With;

@With(Security.class)
public class TrocarUser extends Controller{

	public static void form() {
		render();
	}

	public static void form_adm() {
		render("/Sistema/form_adm.html");
	}
	
	public static void segurancaSistema() {
		render("Mensagens/senha-sistema.html");
	}
	public static Boolean certo = false;
	public static void senhaSistema(String senhaSys) {
		if(senhaSys.equals("tcc2019")) {
			certo = true;
			render("/Sistema/form_adm.html");
		} else {
			flash.error("*Senha incorreta!");
			Sistema.segurancaSistema();
		}
	}
	
	public static void permissaoNegada() {
		render();
	}
	
}
