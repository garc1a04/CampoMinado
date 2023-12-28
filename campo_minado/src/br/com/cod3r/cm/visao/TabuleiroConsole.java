package br.com.cod3r.cm.visao;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import br.com.cod3r.cm.excecao.ExplosaoException;
import br.com.cod3r.cm.excecao.SairException;
import br.com.cod3r.cm.modelo.*;

public class TabuleiroConsole {
	Tabuleiro tabuleiro;
	
	Scanner input = new Scanner(System.in);
	
	public TabuleiroConsole(Tabuleiro tabuleiro){
		this.tabuleiro = tabuleiro;
		
		executarJogo();
	}

	private void executarJogo() {
		boolean continuar = true;
		
		try {
			while(continuar) {
				CicloDoJogo();
				
				System.out.println("Quer outra partida? (S/n) ");
				String ContinuarJogando = input.nextLine();
				
				if("n".equalsIgnoreCase(ContinuarJogando)) {
					continuar = false;
				} else {
					tabuleiro.reiniciar();
				}
			}
		} catch(SairException e) {
			System.out.println("Tmj!!");
			
		} finally {
			input.close();
		}
	}

	private void CicloDoJogo() {
		try {
			while(!tabuleiro.ObjetivoAlcancadoGlobal()) {
				System.out.println(tabuleiro);
				String SentencaDigitado = CapturarValor();
				
				Iterator<Integer> xy = Arrays.stream(SentencaDigitado.split(","))
					.map(e -> Integer.parseInt(e)).iterator();
				
				System.out.println("1-Abrir e 2-(Des)Marcar");
				SentencaDigitado = input.nextLine();
				
				if("1".equals(SentencaDigitado)) {
					tabuleiro.Abrir(xy.next(), xy.next());
				}else if("2".equals(SentencaDigitado)) {
					tabuleiro.MarcarBandeirinha(xy.next(), xy.next());
				}
			}
			
			System.out.println(tabuleiro);
			System.out.println("Ganhou irmão!!!");
		} catch(ExplosaoException e) {
			System.out.println(tabuleiro);
			System.out.println("Perdeu meu brother...");
		}
	}

	private String CapturarValor() {
		System.out.print("Digite o X,Y: ");
		String Sentenca = input.nextLine();
		
		if("sair".equalsIgnoreCase(Sentenca)) {
			throw new SairException();
		}
		
		return Sentenca;
		
	}
}
