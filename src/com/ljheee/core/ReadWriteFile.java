package com.ljheee.core;

import java.io.*;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
/**
 * ��д�ļ�
 * @author ljheee
 *
 */
public class ReadWriteFile {

	/**
	 * ��file �ж�ȡ��׷�ӵ�text��
	 * @param file
	 * @param text
	 */
	public static void readFrom(File file, JTextArea text){
		FileReader fr =null;
		BufferedReader br =  null;
		if(file==null) return;
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			text.setText("");
			String str ;
			while((str=br.readLine())!=null){
				text.append(str+"\r\n");
			}
		} catch (FileNotFoundException e) {
//			JOptionPane.showMessageDialog(null, "�ļ��Ҳ���");
//			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "IO�쳣,��ȡ�ļ�����");
			e.printStackTrace();
		}finally{
			
			try {
				if(fr!=null) fr.close();
				if(br!=null) br.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "IO�쳣��д���ļ�����");
				e.printStackTrace();
			}
		}
		
	}
	/**
	 * ��text�ı��������ݣ�д��file��
	 * @param file
	 * @param text
	 */
	public static void writeTo(File file, JTextArea text){
		FileWriter fw = null;
		if(file==null) return;
		try {
			fw = new FileWriter(file);
			fw.write(text.getText());
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				if(fw!=null) fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
