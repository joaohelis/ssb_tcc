package controllers;

import java.util.HashMap;
import java.util.Map;

import models.Aluno;

public class Pontuacao {

	public static void calcular(Aluno aluno) {
		if(aluno.renda_per.equals("Renda per capita de até 1/2 salário mínimo")) {
			aluno.pontuacao = 30;
		}
		if(aluno.renda_per.equals("Renda per capita de até 1 salário mínimo")) {
			aluno.pontuacao = 15;
		}
		if(aluno.renda_per.equals("Renda per capita de até 1 salário mínimo e meio")) {
			aluno.pontuacao = 10;	
		}
		if(aluno.renda_per.equals("Renda Per capita acima de 1 salário mínimo e meio")) {
			aluno.pontuacao = 0;
		}
		if(aluno.sit_moradia.equals("Próprio")) {
			aluno.pontuacao = aluno.pontuacao + 4;
		}
		if(aluno.sit_moradia.equals("Alugado")) {
			aluno.pontuacao = aluno.pontuacao + 3;
		}
		if(aluno.sit_moradia.equals("Cedido")) {
			aluno.pontuacao = aluno.pontuacao + 4;
		}
		if(aluno.moradia.equals("Sim")) {
			aluno.pontuacao = aluno.pontuacao + 10;
		}
		
		Map<String, Integer> mapaPontuacao = new HashMap<>();
		mapaPontuacao.put("Membros menores de 18 anos", 5);
		mapaPontuacao.put("Pessoas do núcleo familiar com 60 anos ou mais", 5 );
		mapaPontuacao.put("Pessoas do núcleo familiar com doenças crônicas", 5 );
		mapaPontuacao.put("Pessoas do núcleo familiar com deficiência", 5 );
		mapaPontuacao.put("Família monoparental", 5 );
		
		for(String comp: aluno.comp_familiar) {
			aluno.pontuacao += mapaPontuacao.get(comp);
			System.out.println(comp + "  " + mapaPontuacao.get(comp));
			System.out.println(aluno.pontuacao);
		}
		
		Map<String, Integer> mapaInfo = new HashMap();
		mapaInfo.put("Desempregado", 5);
		mapaInfo.put("Beneficiário de programas sociais", 3);
		mapaInfo.put("Possui gastos mensais com medicações", 3);
		mapaInfo.put("Utiliza SUS como serviço de saúde", 3);
		
		for(String info: aluno.outras_info) {
			aluno.pontuacao += mapaInfo.get(info);
			System.out.println(info + "  " + mapaInfo.get(info));
			System.out.println(aluno.pontuacao);
		}
	}
	
}
