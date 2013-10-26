
import javax.sound.midi.*;
import java.net.*;
import java.io.*;
 
public class SoundPlayer implements MetaEventListener
{
	public Sequencer sequencer = null;
	public Synthesizer synthesizer = null;
	public Sequence sequence = null;
 
	public int loopcount = 0;
 
	long start = 0;
	boolean allways = true;
	int x=0;
	int y=0;
 
	URL url;
 
	public void load(String filepath)//Opens a midi when given a file path
	{
		try
		{
			FileInputStream fis = new FileInputStream(filepath);
			sequence = MidiSystem.getSequence(fis);
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequencer.setSequence(sequence);
			System.out.println("Sound Load: " + x);
			x++;
			
		}
		catch(IOException ioe)
		{
			System.out.println("Error Reading: "+new File(filepath).getName() + " (" + ioe + ")");
		}
		catch(InvalidMidiDataException imde)
		{
			System.out.println("\n|         Not a MIDI File         |\n");
		}
		catch(MidiUnavailableException mue)
		{
			System.out.println("\n| MIDI Device is currently in use |\n");
		}
	}
 
	public void close()
	{
		try
		{
			sequencer.stop();
			sequencer.close();
			sequencer.removeMetaEventListener(this);
		}
		catch(NullPointerException npe){}
		catch(IllegalStateException ise){}
 
		sequencer = null;
		synthesizer = null;//gets rid of the sequencer,sequence,and synthesizer
		sequence = null;
		start = 0;
		loopcount = 0;
	}
 
	public void play()
	{
		try
		{
			sequencer.start();//plays midi
		    System.out.println("Sound Play: " + y);
			y++;
		}
		catch(NullPointerException npe){}
		catch(IllegalStateException ise){}
	}
 
	public void stop()
	{
		try
		{
			sequencer.stop();
			sequencer.setMicrosecondPosition(0);//resets the sequencer
			sequencer.removeMetaEventListener(this);
			start = 0;
			loopcount = 0;
			allways = true;
		}
		catch(NullPointerException npe){}
		catch(IllegalStateException ise){}
	}
 
	public void pause()
	{
		try
		{
			sequencer.stop();//paused if you play again it will start from the point it left off
		}
		catch(NullPointerException npe){}
		catch(IllegalStateException ise){}
	}
 
	public void loop()
	{
		try
		{
			sequencer.start();
			sequencer.addMetaEventListener(this);//sends a message when midi is done playing
		}
		catch(NullPointerException npe){}
	}
 
	public void meta(MetaMessage e){}
}
