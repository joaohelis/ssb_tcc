package controllers;

import play.mvc.Before;
import play.mvc.Controller;

public class Security extends Controller{

@Before
public static void senhaCerta() {
		
		if(Sistema.certo != true) {
			TrocarUser.form_adm();
		} else {
			flash.error("*Informe a senha para acessar essa página!");
			Sistema.segurancaSistema();
			
		}
		
		
	}
}
