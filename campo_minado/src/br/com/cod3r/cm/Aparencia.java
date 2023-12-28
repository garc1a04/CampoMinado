package br.com.cod3r.cm;

import br.com.cod3r.cm.modelo.Tabuleiro;
import br.com.cod3r.cm.visao.TabuleiroConsole;

public class Aparencia {
	public static void main(String[] args) {
		Tabuleiro tabuleiro = new Tabuleiro(6, 6, 36);
		new TabuleiroConsole(tabuleiro);
	}
}
