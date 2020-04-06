package controllers;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import enums.SituacaoAluno;
import models.Administrador;
import models.Aluno;
import models.Bolsa;
import models.DadosSUAP;
import play.libs.WS;
import play.mvc.Controller;
import play.mvc.With;

//@With(Seguranca.class)
public class Sistema extends Controller {
	
	

	public static void form() {
		render();
	}

	public static void form_adm() {
		render();
	}

	public static void loginAluno(String matricula, String senha) {
		Aluno aluno = Aluno.find("matricula = ?1 and senha = ?2", matricula, senha).first();

		if (aluno == null) {
			flash.error("Matrícula inválida");
			form();
		} else {
			session.put("aluno.matricula", aluno.matricula);
			session.put("aluno.senha", aluno.senha);
			session.put("aluno.nome", aluno.nome);
			render("/Application/inicio.html", aluno);
		}
	}
	
	public static void cadastroAdmin() {
		render("/Administradores/cadastrar.html");
	}
	
	public static void salvarAdm(Administrador adm) {
		adm.save();
		flash.success("Admininstrador cadastrado com sucesso!");
		form_adm();
	}

	public static void loginAdmin(String matricula, String senha) {
		Administrador adm = Administrador.find("matricula = ?1 and senha = ?2", matricula, senha).first();

		if (adm == null) {
			flash.error("Dados incorretos! Verificar matrícula e senha.");
			form_adm();
		} else {
			session.put("adm.senha", adm.senha);
			session.put("adm.nome", adm.nome);
			session.put("adm.matricula", adm.matricula);
			session.put("adm.email", adm.email);
			session.put("adm.nome", adm.nome);
			session.put("tipo_user", adm.tipo_user);
			render("/Application/inicio.html");
		}

	}

	public static void autenticarSuapAdm(String senha, String matricula) {

		Administrador adm = Administrador.find("matricula = ?1 and senha = ?2", matricula, senha).first();

		if (adm != null) {
			System.out.println("Entrou no suap!");
			session.put("adm.matricula", adm.matricula);
			session.put("adm.email", adm.email);
			session.put("adm.nome", adm.nome);
			session.put("adm.foto", adm.url_foto_75x100);
			session.put("adm.id", adm.id);
			session.put("adm.rg", adm.rg);
			session.put("adm.cpf", adm.cpf);
			session.put("adm.data_nascimento", adm.data_nascimento);
			session.put("adm.campus", adm.campus);
			session.put("adm.situacao", adm.situacao);
			session.put("adm.nome_completo", adm.nome_completo);
			session.put("tipo_user", adm.tipo_user);

			render("/Application/inicio.html");

		} else {

			WS.HttpResponse resposta;

			String urlToken = "https://suap.ifrn.edu.br/api/v2/autenticacao/token/";
			String urlDados = "https://suap.ifrn.edu.br/api/v2/minhas-informacoes/meus-dados/";

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("username", matricula);
			parametros.put("password", senha);

			resposta = WS.url(urlToken).params(parametros).post();

			if (resposta.success()) {

				String token = resposta.getJson().getAsJsonObject().get("token").getAsString();
				Map<String, String> header = new HashMap<String, String>();
				header.put("X-CSRFToken", token);
				header.put("Authorization", "JWT " + token);

				resposta = WS.url(urlDados).headers(header).get();

				DadosSUAP dadosSUAP = new Gson().fromJson(resposta.getString(), DadosSUAP.class);

				adm = Administrador.find("matricula = ?", dadosSUAP.matricula).first();
				if (adm == null) {
					adm = new Administrador();
					adm.nome = dadosSUAP.nome_usual;
					adm.matricula = dadosSUAP.matricula;
					adm.tipo_vinculo = dadosSUAP.tipo_vinculo;
					adm.url_foto_75x100 = "http://suap.ifrn.edu.br" + dadosSUAP.url_foto_75x100;
					adm.email = dadosSUAP.email;
					adm.rg = dadosSUAP.rg;
					adm.cpf = dadosSUAP.cpf;
					adm.data_nascimento = dadosSUAP.data_nascimento;
					if (dadosSUAP.vinculo != null && !dadosSUAP.vinculo.isEmpty()) {
						adm.campus = dadosSUAP.vinculo.get("campus");
				//		adm.situacao = dadosSUAP.situacao;
						adm.nome_completo = dadosSUAP.vinculo.get("nome");
					}

					adm.save();
				}
				session.put("adm.matricula", adm.matricula);
				session.put("adm.email", adm.email);
				session.put("adm.nome", adm.nome);
				session.put("adm.foto", adm.url_foto_75x100);
				session.put("adm.id", adm.id);
				session.put("adm.rg", adm.rg);
				session.put("adm.cpf", adm.cpf);
				session.put("adm.data_nascimento", adm.data_nascimento);
				session.put("adm.campus", adm.campus);
				session.put("adm.situacao", adm.situacao);
				session.put("adm.nome_completo", adm.nome_completo);
				session.put("tipo_user", adm.tipo_user);
				
				render("/Application/inicio.html");

			} else {
				flash.error("Dados incorretos! Verificar matrícula e senha.");
				form_adm();
			}

		}

	}

	public static void autenticarSuapAluno(String senha, String matricula) {

		Aluno aluno = Aluno.find("matricula = ?1 and senha = ?2", matricula, senha).first();

		if (aluno != null) {
			System.out.println("Entrou no suap!");
			session.put("aluno.matricula", aluno.matricula);
			session.put("aluno.email", aluno.email);
			session.put("aluno.nome", aluno.nome);
			session.put("aluno.foto", aluno.url_foto_75x100);
			session.put("aluno.id", aluno.id);
			session.put("aluno.campus", aluno.campus);
			session.put("aluno.cpf", aluno.cpf);
			session.put("aluno.rg", aluno.rg);
			session.put("aluno.curso", aluno.curso);
			session.put("aluno.data_nascimento", aluno.data_nascimento);
			session.put("aluno.situacao_matricula", aluno.situacao_matricula);
			session.put("aluno.nome_completo", aluno.nome_completo);
			session.put("aluno.situacao", aluno.situacao);
			session.put("tipo_user", aluno.tipo_user);
			session.put("aluno.entrevista", aluno.entrevista);
			session.put("aluno.cidade", aluno.cidade);
			session.put("aluno.telefone", aluno.telefone);
			session.put("aluno.endereco", aluno.endereco);
			

			render("/Application/inicio_aluno.html");

		} else {

			WS.HttpResponse resposta;

			String urlToken = "https://suap.ifrn.edu.br/api/v2/autenticacao/token/";
			String urlDados = "https://suap.ifrn.edu.br/api/v2/minhas-informacoes/meus-dados/";

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("username", matricula);
			parametros.put("password", senha);

			resposta = WS.url(urlToken).params(parametros).post();

			if (resposta.success()) {

				String token = resposta.getJson().getAsJsonObject().get("token").getAsString();
				Map<String, String> header = new HashMap<String, String>();
				header.put("X-CSRFToken", token);
				header.put("Authorization", "JWT " + token);

				resposta = WS.url(urlDados).headers(header).get();

				DadosSUAP dadosSUAP = new Gson().fromJson(resposta.getString(), DadosSUAP.class);
				
				aluno = Aluno.find("matricula = ?", dadosSUAP.matricula).first();

				if (aluno == null) {
					aluno = new Aluno();
					aluno.nome = dadosSUAP.nome_usual;
					aluno.matricula = dadosSUAP.matricula;
					aluno.tipo_vinculo = dadosSUAP.tipo_vinculo;
					aluno.url_foto_75x100 = "http://suap.ifrn.edu.br" + dadosSUAP.url_foto_75x100;
					aluno.url_foto_150x200 = "http://suap.ifrn.edu.br" + dadosSUAP.url_foto_150x200;
					aluno.email = dadosSUAP.email;
					aluno.situacao_matricula = dadosSUAP.situacao;
					System.out.println(dadosSUAP.vinculo.size());
					
					if (dadosSUAP.vinculo != null && !dadosSUAP.vinculo.isEmpty()) {
						aluno.campus = dadosSUAP.vinculo.get("campus");
						aluno.curso = dadosSUAP.vinculo.get("curso");
						aluno.nome_completo = dadosSUAP.vinculo.get("nome");
					}
						aluno.cpf = dadosSUAP.cpf;
						aluno.rg = dadosSUAP.rg;
						aluno.data_nascimento = dadosSUAP.data_nascimento;

					
						aluno.save();
				}

				session.put("aluno.matricula", aluno.matricula);
				session.put("aluno.email", aluno.email);
				session.put("aluno.nome", aluno.nome);
				session.put("aluno.foto", aluno.url_foto_75x100);
				session.put("aluno.id", aluno.id);
				session.put("aluno.campus", aluno.campus);
				session.put("aluno.cpf", aluno.cpf);
				session.put("aluno.rg", aluno.rg);
				session.put("aluno.curso", aluno.curso);
				session.put("aluno.data_nascimento", aluno.data_nascimento);
				session.put("aluno.situacao_matricula", aluno.situacao_matricula);
				session.put("aluno.nome_completo", aluno.nome_completo);
				session.put("aluno.situacao", aluno.situacao);
				session.put("tipo_user", aluno.tipo_user);
				session.put("aluno.entrevista", aluno.entrevista);
				session.put("aluno.cidade", aluno.cidade);
				session.put("aluno.telefone", aluno.telefone);
				session.put("aluno.endereco", aluno.endereco);
				

				render("/Application/inicio_aluno.html");

			} else {
				flash.error("Dados incorretos! Verificar matrícula e senha.");
				form();
			}

		}

	}

	public static void salvarNaSessao(Aluno aluno) {
		session.put("aluno.situacao", aluno.situacao);
		session.put("aluno.bolsa.id", aluno.bolsa.id);
		List<Bolsa> bolsas = Bolsa.findAll();
		List<SituacaoAluno> situacaoAluno = Arrays.asList(SituacaoAluno.values());
		render("/Application/inicio_aluno.html");
	}
	
	public static void segurancaSistema() {
		render("Mensagens/senha-sistema.html");
	}
	public static Boolean certo = false;
	public static void senhaSistema(String senhaSys) {
		if(senhaSys.equals("tcc2019")) {
			render("/Sistema/form_adm.html");
			certo = true;
		} else {
			flash.error("*Senha incorreta!");
			segurancaSistema();
		}
	}

	public static void logout() {
		session.clear();
		form();
	}
	
	public static void permissaoNegada() {
		render();
	}

}
