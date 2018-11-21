import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MyPanel extends JPanel {

	private String str;
	private JLabel label;
	private JTextArea textA;
	
	public MyPanel(String str) {
		this.str=str;
		this.label=new JLabel(this.str,10);
		this.add(label);
		this.textA=new JTextArea(2,35);
		this.textA.setEditable(false);
		JScrollPane scr=new JScrollPane(textA);
		scr.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scr.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(scr);
		
		
		
		
	}
	
	public String getTimeP() {
		return this.str;
	}
	
	public void setEvents(String events) {
		//There should be a for loop to loop over all events schedule on the same timePeriod
		this.textA.setText(events+"\n");
	}
	
	public JTextArea getTextA() {
		return this.textA;
	}
	
	public void clearText() {
		this.textA.setText("");
	}
	
	
}
