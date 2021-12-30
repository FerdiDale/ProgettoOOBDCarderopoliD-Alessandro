
public class NumeroAvventoriMese {
	
	private Integer numAvventori;
	private Mese meseCorrente = new Mese();
	private Integer anno;
	
	NumeroAvventoriMese(Integer numeroAvventoriCorrente, Integer meseIntero, Integer annoCorrente){
		
		this.numAvventori = numeroAvventoriCorrente;
		this.meseCorrente.setValoreNumerico(meseIntero);	
		this.anno = annoCorrente;
		
	}
	
	public Integer getNumAvventori() {
		return numAvventori;
	}

	public void setNumAvventori(Integer numAvventori) {
		this.numAvventori = numAvventori;
	}

	public Mese getMese() {
		return meseCorrente;
	}

	public void setMese(Mese mese) {
		this.meseCorrente = mese;
	}

	public Integer getAnno() {
		return anno;
	}

	public void setAnno(Integer anno) {
		this.anno = anno;
	}
}
