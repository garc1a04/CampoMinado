package br.com.cod3r.cm.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import br.com.cod3r.cm.excecao.ExplosaoException;

public class Tabuleiro {
	
	private int Linha,Coluna,Minas;
	public List<Campo> campo = new ArrayList<>();
	
	public Tabuleiro(int Linha, int Coluna, int Minas){
		this.Linha = Linha;
		this.Coluna = Coluna;
		this.Minas = Minas;
		
		gerarCampos();
		associasVizinhos();
		sortearMinas();
	}


	private void gerarCampos() {
		for(int i = 1; i <= Linha;i++) { 
			for(int j = 1; j <= Coluna;j++) {
				campo.add(new Campo(i, j));
			}
		}
		
	}
	
	private void associasVizinhos() {
		for(Campo getCampo: campo) {
			for(Campo getCampo2: campo) {
				getCampo.adicionarVizinhos(getCampo2);
			}
		}
	}
	
	private void sortearMinas() {
		
		for(int i = 0; i <= Minas;i++) {
			int aleatorio = (int) (Math.random() * campo.size());
			campo.get(aleatorio).Minar();
		}
		
	}
	
	
	
	public void Abrir(int Linha, int Coluna) {
		try {
			campo.stream()
			.filter(c-> c.getLinha() == Linha && c.getColuna() == Coluna)
			.forEach(c->c.abrir());
		} catch(ExplosaoException e) {
			campo.forEach(c-> c.setAberto(true));
			throw new ExplosaoException();
		}

			
	}
	
	public void MarcarBandeirinha(int Linha, int Coluna) {
		campo.stream()
		.filter(c-> c.getLinha() == Linha && c.getColuna() == Coluna)
		.forEach(c-> c.marcador());
	}
	
	public boolean ObjetivoAlcancadoGlobal() {
		return campo.stream().allMatch(c -> c.ObjetivoAlcancado());
}
	public void reiniciar() {
		campo.stream().forEach(c -> c.reiniciar());
		sortearMinas();
	}
	
	public String toString() {
		
		StringBuilder String = new StringBuilder();
		String.append("  ------------------\n");
		int Campos = 0;
		for(int i = 1;i<=Linha;i++) {
			String.append(i+"|");	
			for(int j =1;j<=Coluna;j++) {
				String.append(" ");
				String.append(campo.get(Campos));
				String.append(" ");
				Campos++;
			}
			String.append("\n");
		}
		String.append("  ------------------\n");
		String.append("  ");
		for(int i=1;i<=Coluna;i++) {
			String.append(" ");
			String.append(i);
			String.append(" ");
		}
		return String.toString() ;
		
	}
}
