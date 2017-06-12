package application.components;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.Scanner;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class AboutFileMenuItem extends JMenuItem {

	private static final long serialVersionUID = 1L;

	public AboutFileMenuItem() {
		super("About");
		setToolTipText("Learn about the editor.");
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				JEditorPane textPane = new JEditorPane(); // creates an empty text pane
		        textPane.setContentType("text/html"); // lets Java know it will be HTML      
		        textPane.addHyperlinkListener(new HyperlinkListener() {

					@Override
					public void hyperlinkUpdate(HyperlinkEvent hle) {
						if (HyperlinkEvent.EventType.ACTIVATED.equals(hle.getEventType())) {
                        	System.out.println(hle.getURL());
                            Desktop desktop = Desktop.getDesktop();
                            try {
                            	desktop.browse(hle.getURL().toURI());
                            } catch (Exception ex) {
                            	ex.printStackTrace();
                            }
                        }	
					}
		        });
		        
		        String text = "File not found!";

		        InputStream in = this.getClass().getClassLoader().getResourceAsStream("application/aboutPageContents.html");
				Scanner s = new Scanner(in);//.useDelimiter("\\A");
				s.useDelimiter("\\A");
		        text = s.hasNext() ? s.next() : "";
		        s.close();

		        textPane.setText(text); // sets its text
		        textPane.setEditable(false);
		        textPane.setCaretPosition(0);
		        textPane.setPreferredSize(new Dimension(480, 450));
		        JScrollPane scroll = new JScrollPane(textPane);
		        JFrame frame = new JFrame(); // makes a window to put it in
		        frame.getContentPane().add(scroll); // adds the text pane to the window
		        frame.pack(); // adjusts the window to the right size
		        frame.setVisible(true); // makes it show up}
			}
		});
	}
}
