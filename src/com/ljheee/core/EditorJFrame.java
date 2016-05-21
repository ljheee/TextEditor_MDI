package com.ljheee.core;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.undo.UndoManager;

/**
 * MDI[Multiple Document Interface] TextEditor
 * @author ljheee
 *
 */
public class EditorJFrame extends JFrame {
	
	TextFrame textFrame = null;
	private static final long serialVersionUID = 1L;
	public static final int  DefaultWidth=900;
	public static final int DefaultHeight=700;
	
    private JComboBox fontName;//ѡ����
    private JComboBox fontSize;//�ֺ�
    private JCheckBox fontStyle[];//���ζ�ѡ��
    private JCheckBoxMenuItem zxCheckboxMenuItem[]; //����  ��ѡ��˵����ӵ������Ρ������˵���
   
	private JMenuBar mainMenuBar;
	private  JTextArea contentArea;
	
	private JMenu myFile=new JMenu("�ļ�"); //���ļ����˵�
	private JMenuItem newFile=new JMenuItem("�½�");
	private JMenuItem open=new JMenuItem("��");
	private JMenuItem save=new JMenuItem("����");
	private JMenuItem printFile=new JMenuItem("��ӡ");
	private JMenuItem quitItem=new JMenuItem("�˳�");
	
	private JMenu Edition=new JMenu("�༭");//���༭���˵�
	CutAction cutAction=new CutAction();
	private JMenuItem cuts=new JMenuItem(cutAction);
	CopyAction copyAction=new CopyAction();
	private JMenuItem copy=new JMenuItem(copyAction);//Actionʵ�ֹ���,���ݲ˵��еĹ���һ��
	PasteAction pasteAction=new PasteAction();
	private JMenuItem paste=new JMenuItem(pasteAction);
	private JMenuItem undoEdit=new JMenuItem("����");
	private JMenuItem delete=new JMenuItem("ɾ��");
	private JMenuItem findReplace=new JMenuItem("����/�滻");
	
	private JMenu style=new JMenu("��ʽ");     //����ʽ���˵�
	private JMenuItem font=new JMenuItem("����");
	private JMenu ziXing=new JMenu("����");     //���Ρ������˵�
	private JMenuItem color=new JMenuItem("��ɫ");
	
	private JMenu myWindow=new JMenu("����");     //�����ڡ��˵�
	
	private JMenu help=new JMenu("����");//���������˵�
	private JMenuItem helps=new JMenuItem("����");
	private JMenuItem about=new JMenuItem("����");
   
	private JMenuItem cut2=new JMenuItem(cutAction);//��ݲ˵�
	private JMenuItem copy2=new JMenuItem(copyAction);//Actionʵ�ֹ���
	private JMenuItem paste2=new JMenuItem(pasteAction);
	
	private JFileChooser fileChooser = new JFileChooser(new File("123",""));//�ļ�ѡ����
	
	JPopupMenu popMenu=new JPopupMenu();//��ݲ˵�;
	UndoManager undoManager = new UndoManager();
	
	private JLabel fileInfo = new JLabel("״̬��:");
	private JLabel pathInfo = new JLabel("                              ");
	private JLabel timeInfo = new JLabel("           ");
	
	public  JTextArea getTextArea(){
		return contentArea;
	}
	
	private JDesktopPane desktop; //................................
	private ButtonGroup buttonGroup;
	private File file;
	HamdleAction handle = new HamdleAction();
	public EditorJFrame(File file){
		super("MDI TextEditor");
		this.setSize(DefaultWidth, DefaultHeight);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		fileChooser.setFileFilter(new FileNameExtensionFilter("General", new String[]{"txt","java","html","class","xls","ppt","doc","docx","xml","exe"}));
		contentArea=new JTextArea("Welcome....");
		
		mainMenuBar=new JMenuBar();
		JToolBar toolBar=new JToolBar();
		this.setJMenuBar(mainMenuBar);//ΪJFrame��Ӳ˵���
		this.getContentPane().add(toolBar, BorderLayout.NORTH);//��ToolBar�ӵ�����
		
		FontClass fontset=new FontClass();
		GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
		String fontNames[]=ge.getAvailableFontFamilyNames();//���ϵͳ����
		fontName=new JComboBox(fontNames);
		fontName.setSize(new Dimension(0, 15));
		toolBar.add(fontName);
		
		Integer sizes[]={20,30,40,50,60,70};//�ֺ�����
		fontSize=new JComboBox(sizes);//�ֺ�
		fontSize.addActionListener(fontset);
		toolBar.add(fontSize);//���������  �ֺ���Ͽ�
		
		String stylestr[]={"����","б��"};
		fontStyle=new JCheckBox[stylestr.length];    //new ���飨������ռ�
		zxCheckboxMenuItem=new JCheckBoxMenuItem[stylestr.length];
		
		for(int i=0;i<stylestr.length;++i){
			fontStyle[i]=new JCheckBox(stylestr[i]);
			zxCheckboxMenuItem[i]=new JCheckBoxMenuItem(stylestr[i]);			
		    toolBar.add(fontStyle[i]);
		    fontStyle[i].addActionListener(fontset);
		    
		    ziXing.add(zxCheckboxMenuItem[i]);//���� �����˵�  ��ӡ���ѡ��˵��
		    zxCheckboxMenuItem[i].addActionListener(fontset);
		}
		
		
		
		mainMenuBar.add(myFile);  //���ļ����˵�
		myFile.add(newFile);
		myFile.add(open);
		myFile.add(save);
		myFile.add(printFile);
		myFile.addSeparator();//��� �ָ���
		myFile.add(quitItem);
		
		mainMenuBar.add(Edition);//���༭���˵�
		Edition.add(cuts);
		Edition.add(copy);
		Edition.add(paste);
		Edition.addSeparator();//��� �ָ���
		Edition.add(undoEdit);
		Edition.add(delete);
		Edition.add(findReplace);
		
		mainMenuBar.add(style);//����ʽ���˵�
		style.add(font);
		style.add(ziXing);
		style.add(color);
		
		mainMenuBar.add(myWindow);//�����ڡ��˵�
				
		mainMenuBar.add(help);//���������˵�
		help.add(helps);
		help.add(about);
		
		
		contentArea.setComponentPopupMenu(popMenu);//�� ��ӡ���ݲ˵��������new��ǰ
		popMenu.add(cut2);
		popMenu.add(copy2);
		popMenu.add(paste2);
  //���� ��ݼ�............................................................
		quitItem.setAccelerator(KeyStroke.getKeyStroke("control  Q"));
		newFile.setAccelerator(KeyStroke.getKeyStroke("control  N"));
		open.setAccelerator(KeyStroke.getKeyStroke("control  O"));
		save.setAccelerator(KeyStroke.getKeyStroke("control  S"));
		
		cuts.setAccelerator(KeyStroke.getKeyStroke("control  X"));
		copy.setAccelerator(KeyStroke.getKeyStroke("control  C"));
		paste.setAccelerator(KeyStroke.getKeyStroke("control  V"));
		undoEdit.setAccelerator(KeyStroke.getKeyStroke("control  Z"));
		findReplace.setAccelerator(KeyStroke.getKeyStroke("control  F"));
		
		//.��Ӧ����............................................................	
		findReplace.addActionListener(handle);
		undoEdit.addActionListener(handle);
		
		about.addActionListener(new ActionListener() {   //ʵ�֡�ճ��������
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(about, "My TextEditor\n@Author:ljheee\n2000-00-00");				
			}
		});
		
		
		color.addActionListener(new ActionListener() {  //ѡ��ԭɫDialog
			public void actionPerformed(ActionEvent e) {
				Color c=JColorChooser.showDialog(contentArea, "ѡ����ɫ", Color.blue);
				contentArea.setCaretColor(c);//���ƹ�� ��ɫ
				contentArea.setForeground(c);//�ı� ������ɫ
				contentArea.repaint();
			}
		});
		
		quitItem.addActionListener(new ActionListener() {//ʵ�֡��˳�������
			@Override
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showConfirmDialog(quitItem, "��ֹ��ǰ����","ȷ��",JOptionPane.YES_NO_CANCEL_OPTION)==0)
				    System.exit(0);
			}
		});
		
	//....���ļ�---�½�     ��     ���桱....................................................................	
		newFile.addActionListener(handle);
		open.addActionListener(handle);
		
		save.addActionListener(new ActionListener() {
			BufferedWriter bw = null;
			@Override
			public void actionPerformed(ActionEvent e) {
				int select = fileChooser.showSaveDialog(EditorJFrame.this);
//				fileChooser.setSelectedFile(new File("�½�.txt")); 
				File file = null;
				
				String fileName = null;
				if(select==JFileChooser.APPROVE_OPTION){
					file =fileChooser.getSelectedFile();//������ﲢû��ѡȡ���κε��ļ��������fileChooser.getName(file)���᷵����������ļ��� 
				}
				fileName = fileChooser.getName(file);
				if(fileName==null|| fileName.trim().length()==0){
					JOptionPane.showMessageDialog(EditorJFrame.this, "�ļ���Ϊ�գ�");
					return;
				}
				
				if(file!=null&&file.isFile()){
					fileName = file.getName();
				}
				//�����Ǹ��ļ���
				file = fileChooser.getCurrentDirectory();//��õ�ǰĿ¼
				
				String path = file.getPath()+java.io.File.separator+fileName;
				file =new File(path);
			
				 if(file.exists()) {  //��ѡ�������ļ�----ѯ���Ƿ�Ҫ����   
					 int i = JOptionPane.showConfirmDialog(EditorJFrame.this, "���ļ��Ѿ����ڣ�ȷ��Ҫ������");     
					 if(i == JOptionPane.YES_OPTION)   ;     
					 else   return ;    
					 } 
				
				
				try {
					bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
					bw.write(contentArea.getText());
					bw.flush();
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(EditorJFrame.this, "�ļ��������"+e1.getMessage());
				} catch (IOException e1) {
					e1.printStackTrace();
				}finally{
					try {
						if(bw!=null) bw.close();
					} catch (IOException e1) {
					}
				}
			}
		});
//		getContentPane().add(new JScrollPane(contentArea));
		
		this.file = file;
		if (file==null) 
			this.file = new File("�½��ļ�");
		this.setTitle("MDI TextEditor"+"    "+this.file.getName());
	
		
		desktop  =new JDesktopPane();
		this.getContentPane().add(desktop);
		buttonGroup = new ButtonGroup();
		new TextFrame(file);
		
		
		//״̬��---ʹ��JToolBar����Ϊ�����϶�
				JToolBar  bottomToolBar = new JToolBar();
				bottomToolBar.setFloatable(false);//����JToolBar�����϶�
				
				bottomToolBar.setPreferredSize(new Dimension(this.getWidth(), 18));
				bottomToolBar.add(fileInfo);
				
//				bottomToolBar.addSeparator(); //�˷�����ӷָ���  ��Ч
				JSeparator  jsSeparator = new JSeparator(SwingConstants.VERTICAL);
				bottomToolBar.add(jsSeparator);//��ӷָ���
				
				fileInfo.setPreferredSize(new Dimension(150, 18));
				fileInfo.setHorizontalTextPosition(SwingConstants.LEFT);
				
				bottomToolBar.add(pathInfo);
				pathInfo.setHorizontalTextPosition(SwingConstants.LEFT);
				bottomToolBar.add(new JSeparator(SwingConstants.VERTICAL));//��ӷָ���
				
				bottomToolBar.add(timeInfo);
				timeInfo.setPreferredSize(new Dimension(70, 18));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				timeInfo.setText(sdf.format(new Date()));
				
				
				this.getContentPane().add(bottomToolBar, BorderLayout.SOUTH);
		
		this.setVisible(true);
		
		
		
		
	}
	
	private class CopyAction extends AbstractAction{
		public CopyAction(){
			super("����");
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			contentArea.copy();
		}
	}
	
	/**
	 * ˽���ڲ�Action�࣬Ϊ�˵���� ��ݲ˵�ͳһʵ�ֹ���
	 * @author ljheee
	 *
	 */
	private class PasteAction extends AbstractAction{
		public PasteAction(){
			super("ճ��");
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			contentArea.paste();
		}
	}
	private class CutAction extends AbstractAction{
		public CutAction(){
			super("����");
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			contentArea.cut();
		}
	}
	
	//���塢�ֺš�����  ����
	class FontClass implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			int size=0;
			String fontname0=(String)fontName.getSelectedItem();  //���������
			Object obj= fontSize.getSelectedItem();             //��� �ֺ�
			size=((Integer)obj).intValue();
			
			Font curFont=contentArea.getFont(); //��ȡ��ǰ����
			int curStyle=curFont.getStyle();   //��ȡ��ǰ������
			if(e.getActionCommand().equals("����"))      curStyle=curStyle^1;
			if(e.getActionCommand().equals("б��"))      curStyle=curStyle^2;
			contentArea.setFont(new Font(fontname0,curStyle,size));
		}
		
	}
	
	/**
	 * EditorJFrame���ڲ���,EditorJFrame������һ��JInternalFrame�ڲ����
	 * @author ljheee
	 *Ҳ����һ�����ı���
	 */
	class TextFrame extends JInternalFrame  {
		
		private static final long serialVersionUID = 1L;
		File file;
		JTextArea text;
		JRadioButtonMenuItem rbMenuItem;
		
		public TextFrame(File file) {
			super("",true,true,true,true);
			this.setSize(640, 480);
			this.addInternalFrameListener(new MyInternalFrameListener());
			desktop.add(this);
			JInternalFrame inner = desktop.getSelectedFrame();
			if(inner!=null)
				this.setLocation(inner.getX()+50, inner.getY()+50);
			this.text = new JTextArea();
			this.text.setFont(new Font("����",1,30));
			this.text.getDocument().addUndoableEditListener(undoManager);
			this.text.setComponentPopupMenu(EditorJFrame.this.popMenu);
			this.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getButton()==3) {  //����һ�����ʾ��ݲ˵�
						popMenu.show(text, e.getX(), e.getY());
					}
				}	
			});
			this.getContentPane().add(new JScrollPane(this.text));
			this.rbMenuItem = new JRadioButtonMenuItem(this.getTitle(),true);
			
			this.rbMenuItem.addActionListener(handle);
			EditorJFrame.this.buttonGroup.add(this.rbMenuItem);
			EditorJFrame.this.myWindow.add(this.rbMenuItem);
			this.file = file;
			if(file==null){
				this.file = new File("");
			}
			rbMenuItem.setText(this.file.getName());//���ڲ˵�--��ӱ���
			this.setTitle(this.file.getName());//С����--�ı�����ӱ���
			ReadWriteFile.readFrom(file, text);
			
			this.setVisible(true);
			textFrame = this;
		}
		
		/**
		 * TextFrame���ڲ���---�ڲ����ڼ���
		 * @author ljheee
		 *
		 */
		class MyInternalFrameListener implements InternalFrameListener{
			
			@Override
			public void internalFrameActivated(InternalFrameEvent e) { //����ʱ
				EditorJFrame.this.contentArea = TextFrame.this.text;//�ı��ⲿ��textarea��ʹ�����������ڵ�ǰtext[�ڲ��ı�]
				TextFrame.this.rbMenuItem.setSelected(true);
			}
			@Override
			public void internalFrameClosed(InternalFrameEvent e) {}
			@Override
			public void internalFrameClosing(InternalFrameEvent e) {
				EditorJFrame.this.buttonGroup.remove(TextFrame.this.rbMenuItem);
				EditorJFrame.this.myWindow.remove(TextFrame.this.rbMenuItem);
			}
			@Override
			public void internalFrameDeactivated(InternalFrameEvent e) {}//
			@Override
			public void internalFrameDeiconified(InternalFrameEvent e) {}//�ָ�
			@Override
			public void internalFrameIconified(InternalFrameEvent e) {} //��С��
			@Override
			public void internalFrameOpened(InternalFrameEvent e) {}
		}
		
		public boolean setSelected(File file)  {//����file�Ƿ��Ѵ�
			
			JInternalFrame[] inners = desktop.getAllFrames();
			int i = 0;
			for (i = 0; i < inners.length; i++) {
				File f = ((TextFrame)inners[i]).file;
				if(file.getName().equals(f.getName())){
					try {
						inners[i].setSelected(true);//ѡ���ڲ��ı����ڣ������ظ���
						return true;  //�ѱ��򿪣�����true
					} catch (PropertyVetoException e) {
						e.printStackTrace();
					}
				}
				return false;
			}
			return false;
		}
	}
	
	/**
	 * EditorJFrame���ڲ���---��Ӧaction�¼�
	 * @author ljheee
	 */
	class HamdleAction implements ActionListener{

		private int i = 1;
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==newFile){   //����---�½��ļ�
				new TextFrame(null);
				textFrame.rbMenuItem.setText("�½��ı�"+ i);//���ڲ˵�--��ӱ���
				textFrame.setTitle("�½��ı�"+ i);
				fileInfo.setText("�½��ı�");
				pathInfo.setText("");
				i++;
				return;
			}
			//����---���ļ�
			if(e.getSource()==open&&fileChooser.showOpenDialog(EditorJFrame.this)==0){//��ʾ���ļ��Ի����ҵ�����򿪡�   
				File file = fileChooser.getSelectedFile();
	//			System.out.println(file.getName());
				if(!textFrame.setSelected(file)){ //�ж��Ƿ��Ѵ�
					if(!contentArea.getText().equals("")){//û�п��ı�����ʱ����new�µ�
						new TextFrame(file);
					}
					pathInfo.setText(file.getPath());//״̬��--��ʾ�ļ�·��
					
					ReadWriteFile.readFrom(file, contentArea);
					textFrame.rbMenuItem.setText(fileChooser.getSelectedFile().getName());//���ڲ˵�--��ӱ���
					textFrame.setTitle(fileChooser.getSelectedFile().getName());//С����--����ı�����
				}
				return;
			}
			if(e.getSource() instanceof JRadioButtonMenuItem){
				textFrame.setSelected(new File(e.getActionCommand()));//e.getActionCommand()�����ڴ��ڲ˵�������� �˵���[�ļ���]
//				System.out.println(e.getActionCommand());
				
				JInternalFrame[] inners = desktop.getAllFrames();
				int i = 0;
				for (i = 0; i < inners.length; i++) {
					File f = ((TextFrame)inners[i]).file;
					if(f.getName().equals(e.getActionCommand())){
						new ActivateInternalFrame().activateInternalFrame(inners[i]);
					}
				}
			}
			if(e.getSource()==findReplace){//�����滻
				new FindReplaceFrame(contentArea);
			}
			if(e.getSource()==undoEdit){//�����༭
				if(undoManager.canUndo()) undoManager.undo();
			}
			
		}
		
		
	}
	
	
	public static void main(String[] args) {
		new EditorJFrame(new File(""));
	}
	
}
