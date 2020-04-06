package models;

import javax.persistence.Entity;

import enums.TipoUsuario;
import play.db.jpa.Model;

@Entity
public class Usuario extends Model {
	public String tipo_user;
	
}
