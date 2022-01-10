import java.util.ArrayList;

public class Mese{
	
	private Integer valoreNumerico;

	public String toString(){
		switch(valoreNumerico) {
		case 1: return "Gennaio"; 
		case 2: return "Febbraio";
		case 3: return "Marzo";
		case 4: return "Aprile";
		case 5: return "Maggio";
		case 6: return "Giugno";
		case 7: return "Luglio";
		case 8: return "Agosto";
		case 9: return "Settembre";
		case 10: return "Ottobre";
		case 11: return "Novembre";
		case 12: return"Dicembre";
		default: return null;
		}
	}

	public Integer getValoreNumerico() {
		return valoreNumerico;
	}

	public void setValoreNumerico(Integer valoreNumerico) {
		this.valoreNumerico = valoreNumerico;
	}
	
	public void setDaStringa(String meseStringa) throws MeseErratoException {
		
		String meseStringaFormattato = meseStringa.toUpperCase();
		MeseErratoException ecc = new MeseErratoException();
		
		switch(meseStringaFormattato) {
		case "GENNAIO": this.valoreNumerico = 1;
			break;
		case "FEBBRAIO": this.valoreNumerico = 2;
			break;
		case "MARZO": this.valoreNumerico = 3;
			break;
		case "APRILE": this.valoreNumerico = 4;
			break;
		case "MAGGIO": this.valoreNumerico = 5;
			break;
		case "GIUGNO": this.valoreNumerico = 6;
			break;
		case "LUGLIO": this.valoreNumerico = 7;
			break;
		case "AGOSTO": this.valoreNumerico = 8;
			break;
		case "SETTEMBRE": this.valoreNumerico = 9;
			break;
		case "OTTOBRE": this.valoreNumerico = 10;
			break;
		case "NOVEMBRE": this.valoreNumerico = 11;
			break;
		case "DICEMBRE": this.valoreNumerico = 12;
			break;
		default: throw ecc;
		}
	}
}
