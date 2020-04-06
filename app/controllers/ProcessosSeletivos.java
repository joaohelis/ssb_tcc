package controllers;

import models.Administrador;
import models.Bolsa;
import models.ProcessoSeletivo;
import play.mvc.Controller;
import java.util.Arrays;
import java.util.List;

public class ProcessosSeletivos extends Controller{

	public static void abrirProcesso() {
		List<Administrador> responsaveis = Administrador.findAll();
		render(responsaveis);
	}
	
	public static void salvar(ProcessoSeletivo processo) {
		processo.save();
		render("/Application/inicio.html");
	}
	
	public static void listar() {
	//	render("/Mensagens/error404.html");
	List<ProcessoSeletivo> processos = ProcessoSeletivo.findAll();
		render(processos);
	}
	
	public static void detalhar(Long id) {
		ProcessoSeletivo processo = ProcessoSeletivo.findById(id);
		List<Bolsa> bolsas = processo.bolsas;
		render(processo, bolsas);
	}
}
