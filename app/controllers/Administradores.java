package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import enums.Entrevista;
import enums.SituacaoAluno;
import models.Administrador;
import models.Aluno;
import models.Bolsa;
import play.mvc.Controller;
import play.mvc.With;

@With(Seguranca.class)
public class Administradores extends Controller{
	
	public static void form() {
		render();
	}
	
	public static void salvar(Administrador adm) {
		adm.save();
		//render("Mensagens/cadastro-sucesso.html");
		render("/Sistema/form_adm.html");
	}
	
	public static void login () {
		render();
	}
	
	public static void verificarLogin(Administrador adm) {
		List<Administrador> adms = Administrador.findAll();
		
		for( int i = 0; i < adms.size(); i++) {
			String senha = adm.senha;
			String mat = adm.matricula;
			if(senha.equals(adms.get(i).senha) && mat.equals(adms.get(i).matricula)) {
				render("Administradores/pagina-inicial.html", adm);
			} else {
				render("Mensagens/login_adm-falha.html");
			}
		}
	}
	
	public static void segurancaSistema() {
		render("Mensagens/senha-sistema.html");
	}
	
	public static void senhaSistema(String senhaSys) {
		if(senhaSys.equals("tcc2019")) {

			render("/Sistema/form_adm.html");
		} else {
			render("Mensagens/senha-sistema-falha.html");
		}
	}
	
	public static void listarAlunos() {
		List<Aluno> alunos = Aluno.findAll();
		render("Alunos/listar.html", alunos);
	}
	
	public static void listar() {
		List<Administrador> administradores = Administrador.findAll();
		render(administradores);
	}
	
	public static void buscar(String buscarNome) {
		List<Administrador> administradores = Administrador.find(" nome like ?", "%" + buscarNome + "%").fetch();
		render("/Administradores/listar.html", administradores);
	}
	
	public static void excluir(Long id) {
		Administrador administrador = Administrador.findById(id);
		administrador.delete();
		listar();
	}
	
	public static void editar(Long id) {
		Administrador administrador = Administrador.findById(id);
		render("Administradores/form.html", administrador);
	}
	
	
	// METODOS PARA EXECUÇÃO DE ALUNOS
	
	
	public static void ranking() {
		List<Aluno> alunos = Aluno.find("order by pontuacao desc").fetch();
		
		render("/Alunos/ranking.html", alunos);
	}
	
	public static void deferir(Long id) {
		Aluno aluno = Aluno.findById(id);
		aluno.situacao = SituacaoAluno.DEFERIDO;
		aluno.save();
		flash.success("* Aluno " + aluno.nome + " deferido com sucesso!");
		ranking();
	}
	public static void indeferir(Long id) {
		Aluno aluno = Aluno.findById(id);
		aluno.situacao = SituacaoAluno.INDEFERIDO;
		aluno.save();
		flash.success("* Aluno " + aluno.nome + " indeferido com sucesso!");
		ranking();
	}
	
	public static void buscarAluno(String buscarNome) {
		List<Aluno> alunos = Aluno.find(" nome like ?", "%" + buscarNome + "%").fetch();
		render("/Alunos/listar.html", alunos);
	}
	
	public static void excluirAluno(Long id) {
		Aluno aluno = Aluno.findById(id);
		aluno.delete();
		listar();
	}
	
	public static void editarAluno(Long id) {
		List<Bolsa> bolsas = Bolsa.findAll();
		List<SituacaoAluno> situacoes = Arrays.asList(SituacaoAluno.values());
		Aluno aluno = Aluno.findById(id);
		render("Alunos/editar.html", aluno, bolsas, situacoes);
	}
	
	public static void dadosAluno(Long id) {
		List<Entrevista> entrevista = Arrays.asList(Entrevista.values());
		Aluno aluno = Aluno.findById(id);
		
		render("/Alunos/dados_completos.html", aluno, entrevista);
	}
	public static void confEntrevista(Long id) {
		Aluno aluno = Aluno.findById(id);
		aluno.entrevista = Entrevista.CONFIRMADA;
		aluno.save();
		flash.success("* Entrevista do aluno " + aluno.nome + " confirmada com sucesso!");
		dadosAluno(aluno.id);
	}
	public static void indefEntrevista(Long id) {
		Aluno aluno = Aluno.findById(id);
		aluno.entrevista = Entrevista.INDEFERIDA;
		aluno.save();
		flash.success("* Entrevista do aluno " + aluno.nome + " indeferida com sucesso!");
		dadosAluno(aluno.id);
	}
	
	public static void RGAluno(Long id) {
		Aluno aluno = Aluno.findById(id);
		renderBinary(aluno.doc_rg.get());
	}
	public static void CPFAluno(Long id) {
		Aluno aluno = Aluno.findById(id);
		renderBinary(aluno.doc_cpf.get());
	}
	public static void CompResidAluno(Long id) {
		Aluno aluno = Aluno.findById(id);
		renderBinary(aluno.doc_comp_resid.get());
	}
	public static void CompRendaAluno(Long id) {
		Aluno aluno = Aluno.findById(id);
		renderBinary(aluno.doc_comp_renda.get());
	}
	
	
}
