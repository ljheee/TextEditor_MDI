package com.ljheee.core;

import java.io.*;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
/**
 * 读写文件
 * @author ljheee
 *
 */
public class ReadWriteFile {

	/**
	 * 从file 中读取，追加到text中
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
//			JOptionPane.showMessageDialog(null, "文件找不到");
//			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "IO异常,读取文件出错");
			e.printStackTrace();
		}finally{
			
			try {
				if(fr!=null) fr.close();
				if(br!=null) br.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "IO异常，写入文件出错");
				e.printStackTrace();
			}
		}
		
	}
	/**
	 * 将text文本区的内容，写到file中
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
