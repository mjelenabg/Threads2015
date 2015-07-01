package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;



import music.Performance;
import music.Singer;
import music.Song;
import music.Synchronizer;
import music.Voice;

public class Test {
	public static final Scanner IN= new Scanner(System.in);
	private List<String> lyrics;
	private Song love;
	private Performance performance;
	
	private Singer bbk;
	private Singer bono;
	private Singer edge;
	
	private Synchronizer synch;
	
	private void initialize(){
		//od 1.6 u <> ne mora da se pise koji je tip nego compiler sam nadje
		lyrics= new ArrayList<String>();
		lyrics.add("When love comes to town I'm gonna jump that train");
		lyrics.add("When love comes to town I'm gonna catch that flame.");
		lyrics.add("Maybe I was wrong to ever let you down");
		lyrics.add("But I did what I did before love came to town.");
		love = new Song("When love comes to town", lyrics);
		//dopuna, kasnjenje izrazavamo u milisekundama pa nam je zato bio potreban long, mogli smo int pa bi ga on prebacio ako bude prevelik, ali bolje za threadove long
		performance = new Performance(love, 1000);
		//dodatno
		synch=new Synchronizer(Voice.LEAD);
		//bbk=new Singer("B.B. King", Voice.LEAD, performance);
		//bono=new Singer("Bono", Voice.BACKING, performance);
		//synch mora da bude fizicki ISTI OBJEKAT U OBA THREADA
		
	}
	public void testGetAllLyrics(){
		initialize();
		System.out.println(love.getAllLyrics());
	}
	
	public void testPickLine(){
		initialize();
		//4i stih, jer get ide od nula
		System.out.println(love.pickLine(Voice.LEAD, 3));
		System.out.println(love.pickLine(Voice.BACKING, 3));
	}
	
	public void testSingWithRepetitions(){
		initialize();
		bbk.sing(love, 8);
		System.out.println();
		bono.sing(love, 8);
	}
	//samo ovako nece da radi, ako kaze IllegalMonitorStateException znaci da radimo threadove
	public void testSingWithDelay(){
		initialize();
		bbk.singWithDelay(love, 8);
		System.out.println();
		bono.singWithDelay(love, 8);
	}
	//kako rade timeri-stoperica, postoji i timertask, ono iz taska se odradi kad timer odbroji
	public void testSingWithTimer(){
		initialize();
		//timer iz utila
		Timer timer=new Timer();
		ShoutTask shout=new ShoutTask(timer);
		//treci thread, odradio svoje posle 2.5s i to je to
		timer.schedule(shout, 2500);
		//moramo da dodamo da thread prekine, inace ce i pored main metode ovaj da nastavi da se izvrsava, dodati u shout task cancel
		
		bbk.singWithDelay(love, 8);
		System.out.println();
		bono.singWithDelay(love, 8);
	}
	
	public void testSingWithThreads(){
		initialize();
		//threadovi imaju metodu start koja pokrece njihovu run metodu
		bbk.start();
		bono.start();
		//main ce da stane i cekace da lupimo enter i kad mi kliknemo enter tek ce onda da se zaustavi
		IN.nextLine();
		bbk.setStop(true);
		bono.setStop(true);
	}
	
	public void testStartAllThreads(){
		initialize();
		testStopAllThreads();
		bbk=new Singer("B.B. King", Voice.LEAD, performance, false, synch, true);
		bono=new Singer("Bono", Voice.BACKING, performance, false, synch, true);
		edge=new Singer("David Howell Evans", Voice.GUITAR_SOLO, performance, false, synch, true);
		bbk.start();
		bono.start();
		edge.start();
	}
	public void testStartBBK(){
		initialize();
		synch.setVoice(Voice.LEAD);
		bbk=new Singer("B.B. King", Voice.LEAD, performance, false, synch, false);
		bbk.start();
	}
	public void testStartBono(){
		initialize();
		synch.setVoice(Voice.BACKING);
		bono=new Singer("Bono", Voice.BACKING, performance, false, synch, false);
		bono.start();
	}
	public void testStartEdge(){
		initialize();
		synch.setVoice(Voice.GUITAR_SOLO);
		edge=new Singer("David Howell Evans", Voice.GUITAR_SOLO, performance, false, synch, false);
		edge.start();
	}
	public void testStopAllThreads(){
		if(bbk!=null) bbk.setStop(true);
		bbk=null;
		if(bono!=null) bono.setStop(true);
		bono=null;
		if(edge!=null) edge.setStop(true);
		edge=null;
	}
}
