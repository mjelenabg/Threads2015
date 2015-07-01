package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.TextArea;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import test.Test;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class ThreadsGUI extends JFrame {

	private JPanel contentPane;
	private JPanel panel;
	private JButton btnStartAll;
	private JButton btnStop;
	private JScrollPane scrollPane;
	private static JTextArea textArea;
	private JButton btnStartBbk;
	private JButton btnStartBono;
	private JButton btnStartEdge;
	private Test t;
	private JButton btnClear;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ThreadsGUI frame = new ThreadsGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ThreadsGUI() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ThreadsGUI.class.getResource("/icons/images.png")));
		setTitle("Threads");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getPanel(), BorderLayout.EAST);
		contentPane.add(getScrollPane(), BorderLayout.CENTER);
		t=new Test();
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setPreferredSize(new Dimension(120, 10));
			panel.add(getBtnStartBbk());
			panel.add(getBtnStartBono());
			panel.add(getBtnStartEdge());
			panel.add(getBtnStartAll());
			panel.add(getBtnStop());
			panel.add(getBtnClear());
		}
		return panel;
	}
	private JButton getBtnStartAll() {
		if (btnStartAll == null) {
			btnStartAll = new JButton("Start All");
			btnStartAll.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					t.testStartAllThreads();
					btnStartAll.setEnabled(false);
					btnStartBbk.setEnabled(false);
					btnStartBono.setEnabled(false);
					btnStartEdge.setEnabled(false);
					btnStop.setEnabled(true);
				}
			});
			btnStartAll.setPreferredSize(new Dimension(100, 30));
		}
		return btnStartAll;
	}
	private JButton getBtnStop() {
		if (btnStop == null) {
			btnStop = new JButton("Stop");
			btnStop.setEnabled(false);
			btnStop.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				t.testStopAllThreads();
				btnStartAll.setEnabled(true);
				btnStartBbk.setEnabled(true);
				btnStartBono.setEnabled(true);
				btnStartEdge.setEnabled(true);
				}
			});
			btnStop.setPreferredSize(new Dimension(100, 30));
		}
		return btnStop;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTextArea());
		}
		return scrollPane;
	}
	private JTextArea getTextArea() {
		if (textArea == null) {
			textArea = new JTextArea();
		}
		return textArea;
	}
	public static void addToTextArea(String s){
		textArea.append(s+"\n");
	}
	private JButton getBtnStartBbk() {
		if (btnStartBbk == null) {
			btnStartBbk = new JButton("Start BBK");
			btnStartBbk.setPreferredSize(new Dimension(100, 30));
			btnStartBbk.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					t.testStartBBK();
					btnStartBbk.setEnabled(false);
					btnStop.setEnabled(true);
					btnStartAll.setEnabled(false);
				}
			});
		}
		return btnStartBbk;
	}
	private JButton getBtnStartBono() {
		if (btnStartBono == null) {
			btnStartBono = new JButton("Start Bono");
			btnStartBono.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					t.testStartBono();
					btnStartBono.setEnabled(false);
					btnStop.setEnabled(true);
					btnStartAll.setEnabled(false);
				}
			});
			btnStartBono.setPreferredSize(new Dimension(100, 30));
		}
		return btnStartBono;
	}
	private JButton getBtnStartEdge() {
		if (btnStartEdge == null) {
			btnStartEdge = new JButton("Start Edge");
			btnStartEdge.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				t.testStartEdge();
				btnStartEdge.setEnabled(false);
				btnStop.setEnabled(true);
				btnStartAll.setEnabled(false);
				}
			});
			btnStartEdge.setPreferredSize(new Dimension(100, 30));
		}
		return btnStartEdge;
	}
	private JButton getBtnClear() {
		if (btnClear == null) {
			btnClear = new JButton("Clear");
			btnClear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					textArea.setText("");
				}
			});
			btnClear.setPreferredSize(new Dimension(100, 30));
		}
		return btnClear;
	}
}
