package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Bolsa  extends Model{
	public String nome;
	public String setor;
	public String turno;
	public String detalhes;
	
	@OneToMany(mappedBy="bolsa")
	public List<Aluno> alunos;
	
	@ManyToOne
	@JoinColumn(name="processo_id")
	public ProcessoSeletivo processo; 
	
	public Bolsa() {
		
		
		
	}
}
