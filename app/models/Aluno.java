package models;

import java.io.File;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import enums.Entrevista;
import enums.SituacaoAluno;
import enums.TipoUsuario;
import play.data.validation.Email;
import play.data.validation.Required;
import play.db.jpa.Blob;
import play.db.jpa.Model;

@Entity
public class Aluno extends Usuario{
	public int pontuacao;
	public Entrevista entrevista;
	
	public String nome;
	public String nome_completo;
	public String curso;
	public String matricula;
	public String[] interesses;
	public String senha;
	public String tipo_vinculo;
	public String url_foto_75x100;
	public String url_foto_150x200;
	public String rg;
	public String cpf;
	public String data_nascimento;
	public String campus;
	public String situacao_matricula;
	public SituacaoAluno situacao;
	@Required
	public String cidade;

	
	@Required
	@Email
	public String email;
	@Required
	public String turma;
	@Required
	public String telefone;
	@Required
	public String idade;
	@Required
	public String moradia;
	@Required
	public String turno;
	@Required
	public String dependencia;
	@Required
	public String nDepen;

	public String situacao_academica;
	@Required
	public String endereco;
	@Required
	public String pont_ref;
	@Required
	public String sit_moradia;
	@Required
	public String[] comp_familiar;
	@Required
	public String[] programas;
	@Required
	public String[] outras_info;
	@Required
	public String renda_bruta;
	@Required
	public String renda_per;
	@Required
	public String renda_familiar;
	@Required
	public String quant_pessoas;
	@Required
	public Blob doc_rg;
	@Required
	public Blob doc_cpf;
	@Required
	public Blob doc_comp_resid;
	@Required
	public Blob doc_comp_renda;
	
	public String nomeRG, nomeCPF, nomeCompResid, nomeCompRenda;
	
	
	public String getComposicaoFamiliarString() {
		String com = "";
		for(String s: comp_familiar)
			com += ", \n" + s;
		return com;
	}
	public String getProgramasString() {
		String com = "";
		for(String s: programas) 
			com =com + ", \n " + s + "";
			
		return com;
	}
	public String getOutrasInfoString() {
		String com = "";
		for(String s: outras_info) 
			com =com + ",  \n " + s + "";
			
		return com;
	}
	

	
	
	@ManyToOne
	@JoinColumn(name="bolsa_id")
	public Bolsa bolsa;
	
	@ManyToOne
	@JoinColumn(name="processo_id")
	public ProcessoSeletivo processo;

	public Aluno() {
		situacao = SituacaoAluno.INDEFERIDO;
		tipo_user = "aluno";
		entrevista = Entrevista.AGUARDANDO;
		
		
	}

}
