import java.applet.Applet;
import java.applet.AudioClip;
import java.net.*;
import java.io.*;
 /*
  FUCK YEAH! Real Music no more MIDI crap!
  And no more Exception Crap.
  Albert Holtermann 10:46PM 10/20/2011
  <3 AudioClip
  */
public class MusicPlayer 
{
	AudioClip click;

	public MusicPlayer(String m){ // m = the wav file that you want to be played.
		URL urlClick = null;
		try {
			urlClick = new File("audio/" + m).toURI().toURL();
			System.out.println(urlClick);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //MusicPlayer.class.getResource(m);
		click = Applet.newAudioClip(urlClick);
	}
	
	public void p() {
		click.play();
	}
	public void s() {
		click.stop();
	}
	public void l() { // use this instead of play to have a song looped
		click.loop();
	}
}

