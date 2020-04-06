package controllers;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.mail.Session;

import enums.SituacaoAluno;
import models.Aluno;
import models.Bolsa;
import play.data.validation.Valid;
import play.db.jpa.Blob;
import play.db.jpa.JPABase;
import play.exceptions.UnexpectedException;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;


public class Alunos extends Controller {
	
	

	public static void form() {
		List<Bolsa> bolsas = Bolsa.findAll();
		List<SituacaoAluno> situacaoAluno = Arrays.asList(SituacaoAluno.values());
		render(bolsas, situacaoAluno);
	}
	
	public static void salvar(Aluno aluno) {
		aluno.save();
		listar();
	}
	
	public static void form2() {
		List<Bolsa> bolsas = Bolsa.findAll();
		List<SituacaoAluno> situacoes = Arrays.asList(SituacaoAluno.values());
		Aluno aluno = Aluno.find("matricula = ?1", session.get("aluno.matricula")).first();
		render(bolsas, situacoes, aluno);
		
		if(aluno.bolsa != null) {
			flash.success("Você já preencheu esse formulário! Preencha o formulário de análise.");
			Application.inicio_aluno();
		} else {
			render(bolsas, situacoes, aluno);
		}
	}
	
	public static void formAnalise() {
		List<Bolsa> bolsas = Bolsa.findAll();
		List<SituacaoAluno> situacoes = Arrays.asList(SituacaoAluno.values());
		Aluno aluno = Aluno.find("matricula = ?1", session.get("aluno.matricula")).first();
		
		if(aluno.bolsa == null) {
			flash.error("Para acessar essa página, primeiro faça a sua inscrição no processo seletivo! ");
			Application.inicio_aluno();
		}
		
		if(aluno.outras_info != null) {
			flash.success("Você já preencheu o formulário!!");
			Application.inicio_aluno();
			
		} else { 
			render(bolsas, situacoes, aluno);
	
		}
	}
	
	public static void salvarAnaliseSessao(@Valid Aluno aluno, File doc_rg, File doc_cpf, File doc_comp_resid, File doc_comp_renda) {
	/*	if(validation.hasErrors()) {
			validation.keep();
			formAnalise();
		} */

		
	//	aluno.nomeRG = doc_rg.getName();
	//	aluno.nomeCPF = doc_cpf.getName();
	//	aluno.nomeCompResid = doc_comp_resid.getName();
	//	aluno.nomeCompRenda = doc_comp_renda.getName();
		
		session.put("aluno.dependencia", aluno.dependencia);
		session.put("aluno.comp_familiar", aluno.getComposicaoFamiliarString());
		Pontuacao.calcular(aluno);
		session.put("aluno.pontuacao", aluno.pontuacao);
		flash.success("Formulário enviado à Assistência Social. Aguarde a análise dos seus dados e a data da entrevista!");
		aluno.save();
		
		new File("./public/uploads/" + aluno.id).mkdirs();
	//	doc_rg.renameTo(new File("./public/uploads/" + aluno.id + "/" + doc_rg.getName()));
	//	doc_cpf.renameTo(new File("./public/uploads/" + aluno.id + "/" + doc_cpf.getName()));
	//	doc_comp_resid.renameTo(new File("./public/uploads/" + aluno.id + "/" + doc_comp_resid.getName()));
	//	doc_comp_renda.renameTo(new File("./public/uploads/" + aluno.id + "/" + doc_comp_renda.getName())); 
		render("/Application/inicio_aluno.html");
	}
	
	public static void salvarNaSessao(@Valid Aluno aluno){
		if(aluno.bolsa == null) {
			flash.error("*Adicione uma bolsa!");
			form2();
		} 
	/*	if(validation.hasErrors()) {
			validation.keep();
			form2();
		} */
		session.put("aluno.situacao", aluno.situacao);
		session.put("aluno.bolsa.nome", aluno.bolsa.nome);
		session.put("aluno.turma", aluno.turma);
		
		aluno.save();
		flash.success("*Inscrição no processo realizada com sucesso. Agora preencha o formuláro de análise social, o qual encontra-se na aba de bolsas à esquerda!");
		render("/Application/inicio_aluno.html");
		
	}
	public static void salvarNaSessaoADM(Aluno aluno) {
		verificarAluno(aluno);
		session.put("aluno.situacao", aluno.situacao);
		aluno.save();
		listar();
	}
	
	
	public static void verificarAluno(Aluno aluno) {
		 aluno = Aluno.find("matricula = ?1", session.get("aluno.matricula")).first();
		
	}
	
	public static void login() {
		render();
	}
	
	public static void verificarLogin(Aluno aluno) {
		List<Aluno> alunos = Aluno.findAll();
		
		for( int i = 0; i < alunos.size(); i++) {
			String senha = aluno.senha;
			String mat = aluno.matricula;
			if(senha.equals(alunos.get(i).senha) && mat.equals(alunos.get(i).matricula)) { //mat == alunos.get(i).matricula) {
				render("Alunos/pagina-inicial.html", aluno);
			} else {
				render("Mensagens/login_aluno-falha.html");
			}
		}
		
	}
	
	public static void dados_completos(Long id) {
		Aluno aluno = Aluno.findById(id);
		render(aluno);
	}
	
	public static void situacao() {
		render();
		
	}
	
	public static void listar() {
		List<Aluno> alunos = Aluno.findAll();
		render(alunos);
		
	/*	for(int i= 0; i < alunos.size(); i++) {
			if(alunos.get(i).bolsa.nome == null){
				flash.error("Aluno não inscrito");
				render(alunos);
			} 
		} */
	}

	public static void dados_pessoais() {
		render();
		
	}
	
	public static void perfil() {
		render();
	}
	
	// ALA PARA EXECUÇÃO DE BOLSAS
	
	public static void buscar_para_alunos(String buscarNome) {
		List<Bolsa> bolsas = Bolsa.find(" nome like ?", "%" + buscarNome + "%").fetch();
		render("/Bolsas/listar_para_alunos.html", bolsas);
	}
	
	public static void listar_para_alunos() {
		List<Bolsa> bolsas = Bolsa.findAll();
		render("/Bolsas/listar_para_alunos.html",bolsas);
	}
	
	public static void detalhar(Long id) {
		Bolsa bolsa = Bolsa.findById(id);
		render("/Bolsas/detalhar.html",bolsa);
	}
	
}
