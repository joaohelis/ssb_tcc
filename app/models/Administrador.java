package models;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import enums.TipoUsuario;
import play.db.jpa.Model;

@Entity
public class Administrador extends Usuario{
	
	public String nome;
	public String nome_completo;
	public String matricula;
	public String senha;
	public int nivel = 2;
	public String tipo_vinculo;
	public String email;
	public String url_foto_75x100;
	public String rg;
	public String cpf;
	public String data_nascimento;
	public String curso;
	public String campus;
	public String situacao;

	
	public Administrador() {
	tipo_user = "admin";
	}
	
	
	@OneToOne(mappedBy="adm")
	public ProcessoSeletivo processo;
	
}
