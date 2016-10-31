/**
 * PMT/PT1 Praktikum Aufgabenblatt 
 * @author Johannes Kruber
 * @author Luis Nickel 
 */
package aufgabe2_3;

import java.util.ArrayList;
import java.util.List;
//import java.time.Clock;
import java.time.LocalTime;

public class Flughafen extends Thread
{
	private int maxAnzahlFlugzeuge;
	private List<Flugzeug> flugzeuge;
	private int index;

	public Flughafen(int maxAnzhalFlugzeuge)
	{
		this.maxAnzahlFlugzeuge = maxAnzhalFlugzeuge;
		flugzeuge = new ArrayList<Flugzeug>();
		index = 0;
	}

	@Override
	public void run() // not finished
	{
		while (!isInterrupted())
		{
			if (flugzeuge.size() < maxAnzahlFlugzeuge)
			{
				Flugzeug flugzeug =erzeugeFlugzeug((int) (10000 * Math.random()),
						"Flugzeug " + index);
				flugzeug.start();
				flugzeuge.add(flugzeug);
			}
			for(int i =0; i< flugzeuge.size(); i++)
			{
				Flugzeug flug = flugzeuge.get(i);
				flug.setZeit((int) (LocalTime.now().toNanoOfDay() / 1000000));
				if(flug.istGelandet())
				{
					flugzeuge.remove(flug);
					System.out.println(flug.toString());
				}
			}
			
			try
			{
				Thread.sleep(500);
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private Flugzeug erzeugeFlugzeug(int flugdauer, String id)
	{
		index++;
		return new Flugzeug(id, flugdauer, this,
				(int) (LocalTime.now().toNanoOfDay() / 1000000));
	}

	public synchronized void landen(Flugzeug flugzeug)
	{
		try
		{
			Thread.sleep(1500);
		}
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		flugzeug.gelandet();
	}
}