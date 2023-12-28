package br.com.cod3r.cm.modelo;

import java.util.ArrayList;
import java.util.List;

import br.com.cod3r.cm.excecao.ExplosaoException;

public class Campo {
	
	private int Linha,Coluna;
	private boolean Minado = false,Marcado = false,Aberto = false;
	
	private List<Campo> vizinhos = new ArrayList<Campo>();
	
	public Campo(int linha, int coluna){
		this.Linha = linha;
		this.Coluna = coluna;
	}
	
	public boolean adicionarVizinhos(Campo campo) {
		boolean LinhaDiferente = this.Linha != campo.Linha;
		boolean ColunaDiferente = this.Coluna != campo.Coluna;
		boolean diagonal = LinhaDiferente && ColunaDiferente;
		
		int DiferencaLinha = Math.abs(Linha-campo.Linha);
		int DiferencaColuna = Math.abs(Coluna-campo.Coluna);
		int SomaVizinhos = DiferencaLinha + DiferencaColuna;
		
		if(SomaVizinhos == 1 & !diagonal) {
			vizinhos.add(campo);
			return true;
		}else if(SomaVizinhos == 2 & diagonal) {
			vizinhos.add(campo);
			return true;
		}else {
			return false;
		}
	}
	
	public void marcador() {
		if(!Aberto){
			Marcado = !Marcado;
		}
	}
	public boolean abrir() {
		
		if(!Marcado && !Aberto) {
			Aberto = true;
			
			if(Minado) {
				throw new ExplosaoException();
			}
			if(VizinhosSeguros()) {
				vizinhos.forEach(v -> v.abrir());
			}
			return true;
		} else {
			return false;
		}
	}
	public boolean VizinhosSeguros() {
		return vizinhos.stream().noneMatch(v -> v.Minado); 
	}
	
	public boolean isMarcado() {
		return Marcado;
	}
	
	public void Minar() {
	 Minado = true;
	}
	
	public boolean isMinado() {
		return Minado;
	}
	
	public void setAberto(boolean aberto) {
		this.Aberto = aberto;
	}
	
	public boolean isAberto() {
		return Aberto;
	}
	public int getLinha(){
		return Linha;
	}
	public int getColuna(){
		return Coluna;
	}
	
	public boolean ObjetivoAlcancado() {
		boolean Desvendado = !Minado && Aberto;
		boolean Protegido = Minado && Marcado;
		
		return	Desvendado || Protegido;
	}
	public long minasNaVizinhaca() {
		return vizinhos.stream().filter(v -> v.Minado).count();
	}
	public void reiniciar() {
		Aberto = false;
		Minado = false;
		Marcado = false;
	}
	public String toString(){
		if(Marcado) {
			return "x";
		}else if(Aberto && Minado) {
			return "*";
			
		}else if(Aberto && minasNaVizinhaca() > 0) {
			return Long.toString(minasNaVizinhaca());
		}else if(Aberto) {
			return " ";
		}else {
			return "?";
		}
		
	}
}
