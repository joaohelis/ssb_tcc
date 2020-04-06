package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class ProcessoSeletivo extends Model{
	
	public String nome;
	public String data;
	
	@OneToOne
	@JoinColumn(name="adm_id")
	public Administrador adm;
	
	
	@OneToMany(mappedBy="processo")
	public List<Bolsa> bolsas;

}
