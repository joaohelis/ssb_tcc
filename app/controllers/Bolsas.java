package controllers;

import java.util.List;

import models.Administrador;
import models.Aluno;
import models.Bolsa;
import models.ProcessoSeletivo;
import play.db.jpa.JPABase;
import play.mvc.After;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

 @With(Seguranca.class)
public class Bolsas extends Controller {
	
	
	public static void form() {
		List<ProcessoSeletivo> processos = ProcessoSeletivo.findAll();
		render(processos);
	}
	
	public static void salvar(Bolsa bolsa) {
		Bolsa bolsanome = Bolsa.find(" nome like ?", "%" + bolsa.nome + "%").first();
		if(bolsanome == null) {
			bolsa.save();
			flash.success("* Bolsa " + bolsa.nome +" cadastrada com sucesso!");
			render("/Application/inicio.html");
			
		} else {
			flash.error("* Bolsa já cadastrada no sistema!");
			form();
		}
	}
	
	public static void listar() {
		List<Bolsa> bolsas = Bolsa.findAll();
		render(bolsas);
	}
	
	public static void listar_para_alunos() {
		List<Bolsa> bolsas = Bolsa.findAll();
		render(bolsas);
	}
	
	public static void buscar(String buscarNome) {
		List<Bolsa> bolsas = Bolsa.find(" nome like ?", "%" + buscarNome + "%").fetch();
		render("/Bolsas/listar.html", bolsas);
	}
	
	public static void buscar_para_alunos(String buscarNome) {
		List<Bolsa> bolsas = Bolsa.find(" nome like ?", "%" + buscarNome + "%").fetch();
		render("/Bolsas/listar_para_alunos.html", bolsas);
	}
	
	public static void detalhar(Long id) {
		Bolsa bolsa = Bolsa.findById(id);
		render(bolsa);
	}
	
	public static void alunosNaBolsa(Long id) {
		Bolsa bolsa = Bolsa.findById(id);
		List<Aluno> alunos = bolsa.alunos;
		render(bolsa, alunos);
	}
	
	public static void excluir(Long id) {
		Bolsa bolsa = Bolsa.findById(id);
		bolsa.delete();
		listar();
	}
	
	public static void editar(Long id) {
		Bolsa bolsa = Bolsa.findById(id);
		render("Bolsas/form.html", bolsa);
	}
}
