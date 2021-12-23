import java.util.ArrayList;

public interface CameriereDAO 
{
	public ArrayList<Cameriere> EstraiCamerieriInServizio(Ristorante ristorante);
	
	public ArrayList<Cameriere> EstraiCamerieriLicenziati(Ristorante ristorante);
}
