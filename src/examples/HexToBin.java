package examples;

import java.awt.GridLayout;
import java.math.BigInteger;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class HexToBin extends JFrame 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4255853698649328899L;

	public static void main(String[]args)
	{
		JFrame as = new HexToBin();
		as.setSize(300,300);
		as.setTitle("HexToBin");
		as.setVisible(true);
		as.setDefaultCloseOperation(3);
	}
	
	public static String hexToBin(long x)
	{
		BigInteger ine = new BigInteger(""+x,16);
		return ine.toString(2);	
	}
	
	public HexToBin()
	{
		setLayout(new GridLayout(4,1));
		final JTextArea jta = new JTextArea();
		jta.setText("Hexadecimal");
		final JTextArea ans  = new JTextArea("Binary");
		ans.setEditable(false);
		add(new JLabel("Hex:"));
		add(jta);
		add(new JLabel("Bin:"));
		add(ans);
		jta.getDocument().addDocumentListener(new DocumentListener()
		{
			@Override
			public void changedUpdate(DocumentEvent de) 
			{
				if(jta.getText().equals(""))
					return;
				if(jta.getText().matches("^[0-9a-fA-F]+$"))
					System.out.println("SUPDAWG");
				else
					return;
				BigInteger ine = new BigInteger(jta.getText(),16);
				ans.setText(ine.toString(2));
			}
			@Override
			public void insertUpdate(DocumentEvent de) 
			{
				changedUpdate(de);
			}
			@Override
			public void removeUpdate(DocumentEvent de) 
			{
				changedUpdate(de);
			}
		});
	}
}
