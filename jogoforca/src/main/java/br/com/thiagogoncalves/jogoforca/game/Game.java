package br.com.thiagogoncalves.jogoforca.game;

import java.util.HashSet;
import java.util.Set;

import br.com.thiagogoncalves.jogoforca.core.Config;
import br.com.thiagogoncalves.jogoforca.core.Dictionary;
import br.com.thiagogoncalves.jogoforca.core.InvalidCharacterException;
import br.com.thiagogoncalves.jogoforca.core.Word;
import br.com.thiagogoncalves.jogoforca.ui.UI;

public class Game {

	public void start(String[] args) {
		UI.print("Bem Vindo ao Jogo da Forca!");
		
		Dictionary dictionary = Dictionary.getInstance();
		UI.print("Dicion�rio usado: " + dictionary.getName());
		Word word = dictionary.nextWord();
		
		UI.print("A palvra tem " + word.size() + " letras");
		
		Set<Character> usedChars = new HashSet<>();
		int errorCount = 0;
		
		if(args.length > 0) {
			Config.setMaxErrors(args[0]);
		}
		
		int maxErrors = Integer.parseInt(Config.get("maxErrors"));
		UI.print("Voc� pode errar no m�ximo " + maxErrors + " vez(es)");
		
		while (true) {
			UI.print(word);
			UI.printNewLine();
			
			char c;
			try {
				
				c = UI.readChar("Digite uma letra:");
				
				if (usedChars.contains(c)) {
					throw new InvalidCharacterException("Esta letra j� foi utilizada");
				}
				
				usedChars.add(c);
				
				if(word.hasChar(c)) {
					UI.print("Voc� acertou uma letra!");
				} else {
					errorCount++;
					
					if(errorCount < maxErrors) {
						UI.print("Voc� errou! Voc� ainda pode errar " + (maxErrors - errorCount) + " vez(es)");
					}
				}
				
				UI.printNewLine();
				
				if(word.discovered()) {
					UI.print("PARAB�NS! Voc� acertou a palavra completa: " + word.getOriginalWord());
					UI.print("Fim do jogo!");
					break;
				}
				if(errorCount == maxErrors) {
					UI.print("Voc� perdeu o jogo! A palavra correta era: " + word.getOriginalWord());
					UI.print("Fim do jogo!");
					break;
				}
			} catch (InvalidCharacterException e) {
				UI.print("Erro: " + e.getMessage());
				UI.printNewLine();
			}
			
		}
	}
}
