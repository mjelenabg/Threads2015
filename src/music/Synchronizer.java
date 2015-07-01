package music;

import main.ThreadsGUI;

public class Synchronizer {
	private Voice voice;
	private int counter=0;
	
	public Synchronizer(Voice voice) {
		super();
		this.voice=voice;
	}
	
	
	public Voice getVoice() {
		return voice;
	}


	public void setVoice(Voice voice) {
		this.voice = voice;
	}


	//dve metode, jedna za pevanje prvog glasa, druga drugog
	public synchronized void singLeadLine(String line, long delay){
		//prvo pita da li metoda ima dozvolu da "peva"
		while(voice!=Voice.LEAD){
			//nemoj da radis nista dok ti ne kazu
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		singOneLine(line,delay);
		
	}
	public synchronized void singBackingLine(String line, long delay){
		//dok prvi glas peva ja cekam, dok je to tacno, preko flaga se dogovaraju, potrebno da budu sinhronizovane (!leadVoiceFlag,leadVoiceFlag)
		while(voice!=Voice.BACKING){
			
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		singOneLine(line,delay);
		
	}
	public synchronized void playGuitar(String line, long delay){
		
		while(voice!=Voice.GUITAR_SOLO){
			
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		singOneLine(line,delay);
		
	}
	private void singOneLine(String line, long delay) {
		//kazemo mu da ceka malo, da se ne zalece
		try {
			wait(delay);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ThreadsGUI.addToTextArea(line);
		if(voice==Voice.LEAD){
			voice=Voice.BACKING;
			counter++;
		}
		else if(voice==Voice.BACKING){
			counter++;
			if(counter==8) {	
				voice=Voice.GUITAR_SOLO;
			}
			else
				voice=Voice.LEAD;
		}
		else {
			counter=0;
			voice=Voice.LEAD;
		}
		
		//iz klase object, budi sve druge uspavane threadove, notify samo jedan budi
		notifyAll();
	}
	
	public synchronized void singOneLineUnsynchronized(String line, long delay){
		try {
			wait(delay);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ThreadsGUI.addToTextArea(line);
	}

}
