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
	
    private JComboBox fontName;//选字体
    private JComboBox fontSize;//字号
    private JCheckBox fontStyle[];//字形多选框
    private JCheckBoxMenuItem zxCheckboxMenuItem[]; //字形  复选框菜单项，添加到“字形”二级菜单里
   
	private JMenuBar mainMenuBar;
	private  JTextArea contentArea;
	
	private JMenu myFile=new JMenu("文件"); //“文件”菜单
	private JMenuItem newFile=new JMenuItem("新建");
	private JMenuItem open=new JMenuItem("打开");
	private JMenuItem save=new JMenuItem("保存");
	private JMenuItem printFile=new JMenuItem("打印");
	private JMenuItem quitItem=new JMenuItem("退出");
	
	private JMenu Edition=new JMenu("编辑");//“编辑”菜单
	CutAction cutAction=new CutAction();
	private JMenuItem cuts=new JMenuItem(cutAction);
	CopyAction copyAction=new CopyAction();
	private JMenuItem copy=new JMenuItem(copyAction);//Action实现功能,与快捷菜单中的功能一样
	PasteAction pasteAction=new PasteAction();
	private JMenuItem paste=new JMenuItem(pasteAction);
	private JMenuItem undoEdit=new JMenuItem("撤销");
	private JMenuItem delete=new JMenuItem("删除");
	private JMenuItem findReplace=new JMenuItem("查找/替换");
	
	private JMenu style=new JMenu("格式");     //“格式”菜单
	private JMenuItem font=new JMenuItem("字体");
	private JMenu ziXing=new JMenu("字形");     //字形”二级菜单
	private JMenuItem color=new JMenuItem("颜色");
	
	private JMenu myWindow=new JMenu("窗口");     //“窗口”菜单
	
	private JMenu help=new JMenu("帮助");//“帮助”菜单
	private JMenuItem helps=new JMenuItem("帮助");
	private JMenuItem about=new JMenuItem("关于");
   
	private JMenuItem cut2=new JMenuItem(cutAction);//快捷菜单
	private JMenuItem copy2=new JMenuItem(copyAction);//Action实现功能
	private JMenuItem paste2=new JMenuItem(pasteAction);
	
	private JFileChooser fileChooser = new JFileChooser(new File("123",""));//文件选择器
	
	JPopupMenu popMenu=new JPopupMenu();//快捷菜单;
	UndoManager undoManager = new UndoManager();
	
	private JLabel fileInfo = new JLabel("状态栏:");
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
		this.setJMenuBar(mainMenuBar);//为JFrame添加菜单栏
		this.getContentPane().add(toolBar, BorderLayout.NORTH);//把ToolBar加到顶部
		
		FontClass fontset=new FontClass();
		GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
		String fontNames[]=ge.getAvailableFontFamilyNames();//获得系统字体
		fontName=new JComboBox(fontNames);
		fontName.setSize(new Dimension(0, 15));
		toolBar.add(fontName);
		
		Integer sizes[]={20,30,40,50,60,70};//字号数组
		fontSize=new JComboBox(sizes);//字号
		fontSize.addActionListener(fontset);
		toolBar.add(fontSize);//工具栏添加  字号组合框
		
		String stylestr[]={"粗体","斜体"};
		fontStyle=new JCheckBox[stylestr.length];    //new 数组（组件）空间
		zxCheckboxMenuItem=new JCheckBoxMenuItem[stylestr.length];
		
		for(int i=0;i<stylestr.length;++i){
			fontStyle[i]=new JCheckBox(stylestr[i]);
			zxCheckboxMenuItem[i]=new JCheckBoxMenuItem(stylestr[i]);			
		    toolBar.add(fontStyle[i]);
		    fontStyle[i].addActionListener(fontset);
		    
		    ziXing.add(zxCheckboxMenuItem[i]);//字形 二级菜单  添加“复选框菜单项”
		    zxCheckboxMenuItem[i].addActionListener(fontset);
		}
		
		
		
		mainMenuBar.add(myFile);  //“文件”菜单
		myFile.add(newFile);
		myFile.add(open);
		myFile.add(save);
		myFile.add(printFile);
		myFile.addSeparator();//添加 分割线
		myFile.add(quitItem);
		
		mainMenuBar.add(Edition);//“编辑”菜单
		Edition.add(cuts);
		Edition.add(copy);
		Edition.add(paste);
		Edition.addSeparator();//添加 分割线
		Edition.add(undoEdit);
		Edition.add(delete);
		Edition.add(findReplace);
		
		mainMenuBar.add(style);//“格式”菜单
		style.add(font);
		style.add(ziXing);
		style.add(color);
		
		mainMenuBar.add(myWindow);//“窗口”菜单
				
		mainMenuBar.add(help);//”帮助“菜单
		help.add(helps);
		help.add(about);
		
		
		contentArea.setComponentPopupMenu(popMenu);//将 添加“快捷菜单”的组件new在前
		popMenu.add(cut2);
		popMenu.add(copy2);
		popMenu.add(paste2);
  //设置 快捷键............................................................
		quitItem.setAccelerator(KeyStroke.getKeyStroke("control  Q"));
		newFile.setAccelerator(KeyStroke.getKeyStroke("control  N"));
		open.setAccelerator(KeyStroke.getKeyStroke("control  O"));
		save.setAccelerator(KeyStroke.getKeyStroke("control  S"));
		
		cuts.setAccelerator(KeyStroke.getKeyStroke("control  X"));
		copy.setAccelerator(KeyStroke.getKeyStroke("control  C"));
		paste.setAccelerator(KeyStroke.getKeyStroke("control  V"));
		undoEdit.setAccelerator(KeyStroke.getKeyStroke("control  Z"));
		findReplace.setAccelerator(KeyStroke.getKeyStroke("control  F"));
		
		//.响应功能............................................................	
		findReplace.addActionListener(handle);
		undoEdit.addActionListener(handle);
		
		about.addActionListener(new ActionListener() {   //实现“粘贴”功能
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(about, "My TextEditor\n@Author:ljheee\n2000-00-00");				
			}
		});
		
		
		color.addActionListener(new ActionListener() {  //选择原色Dialog
			public void actionPerformed(ActionEvent e) {
				Color c=JColorChooser.showDialog(contentArea, "选择颜色", Color.blue);
				contentArea.setCaretColor(c);//控制光标 颜色
				contentArea.setForeground(c);//文本 字体颜色
				contentArea.repaint();
			}
		});
		
		quitItem.addActionListener(new ActionListener() {//实现“退出”功能
			@Override
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showConfirmDialog(quitItem, "终止当前程序？","确认",JOptionPane.YES_NO_CANCEL_OPTION)==0)
				    System.exit(0);
			}
		});
		
	//....“文件---新建     打开     保存”....................................................................	
		newFile.addActionListener(handle);
		open.addActionListener(handle);
		
		save.addActionListener(new ActionListener() {
			BufferedWriter bw = null;
			@Override
			public void actionPerformed(ActionEvent e) {
				int select = fileChooser.showSaveDialog(EditorJFrame.this);
//				fileChooser.setSelectedFile(new File("新建.txt")); 
				File file = null;
				
				String fileName = null;
				if(select==JFileChooser.APPROVE_OPTION){
					file =fileChooser.getSelectedFile();//如果这里并没有选取中任何的文件，下面的fileChooser.getName(file)将会返回手输入的文件名 
				}
				fileName = fileChooser.getName(file);
				if(fileName==null|| fileName.trim().length()==0){
					JOptionPane.showMessageDialog(EditorJFrame.this, "文件名为空！");
					return;
				}
				
				if(file!=null&&file.isFile()){
					fileName = file.getName();
				}
				//否则是个文件夹
				file = fileChooser.getCurrentDirectory();//获得当前目录
				
				String path = file.getPath()+java.io.File.separator+fileName;
				file =new File(path);
			
				 if(file.exists()) {  //若选择已有文件----询问是否要覆盖   
					 int i = JOptionPane.showConfirmDialog(EditorJFrame.this, "该文件已经存在，确定要覆盖吗？");     
					 if(i == JOptionPane.YES_OPTION)   ;     
					 else   return ;    
					 } 
				
				
				try {
					bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
					bw.write(contentArea.getText());
					bw.flush();
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(EditorJFrame.this, "文件保存出错"+e1.getMessage());
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
			this.file = new File("新建文件");
		this.setTitle("MDI TextEditor"+"    "+this.file.getName());
	
		
		desktop  =new JDesktopPane();
		this.getContentPane().add(desktop);
		buttonGroup = new ButtonGroup();
		new TextFrame(file);
		
		
		//状态栏---使用JToolBar，设为不可拖动
				JToolBar  bottomToolBar = new JToolBar();
				bottomToolBar.setFloatable(false);//设置JToolBar不可拖动
				
				bottomToolBar.setPreferredSize(new Dimension(this.getWidth(), 18));
				bottomToolBar.add(fileInfo);
				
//				bottomToolBar.addSeparator(); //此方法添加分隔符  无效
				JSeparator  jsSeparator = new JSeparator(SwingConstants.VERTICAL);
				bottomToolBar.add(jsSeparator);//添加分隔符
				
				fileInfo.setPreferredSize(new Dimension(150, 18));
				fileInfo.setHorizontalTextPosition(SwingConstants.LEFT);
				
				bottomToolBar.add(pathInfo);
				pathInfo.setHorizontalTextPosition(SwingConstants.LEFT);
				bottomToolBar.add(new JSeparator(SwingConstants.VERTICAL));//添加分隔符
				
				bottomToolBar.add(timeInfo);
				timeInfo.setPreferredSize(new Dimension(70, 18));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				timeInfo.setText(sdf.format(new Date()));
				
				
				this.getContentPane().add(bottomToolBar, BorderLayout.SOUTH);
		
		this.setVisible(true);
		
		
		
		
	}
	
	private class CopyAction extends AbstractAction{
		public CopyAction(){
			super("复制");
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			contentArea.copy();
		}
	}
	
	/**
	 * 私有内部Action类，为菜单项和 快捷菜单统一实现功能
	 * @author ljheee
	 *
	 */
	private class PasteAction extends AbstractAction{
		public PasteAction(){
			super("粘贴");
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			contentArea.paste();
		}
	}
	private class CutAction extends AbstractAction{
		public CutAction(){
			super("剪切");
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			contentArea.cut();
		}
	}
	
	//字体、字号、字形  设置
	class FontClass implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			int size=0;
			String fontname0=(String)fontName.getSelectedItem();  //获得字体名
			Object obj= fontSize.getSelectedItem();             //获得 字号
			size=((Integer)obj).intValue();
			
			Font curFont=contentArea.getFont(); //获取当前字体
			int curStyle=curFont.getStyle();   //获取当前字体风格
			if(e.getActionCommand().equals("粗体"))      curStyle=curStyle^1;
			if(e.getActionCommand().equals("斜体"))      curStyle=curStyle^2;
			contentArea.setFont(new Font(fontname0,curStyle,size));
		}
		
	}
	
	/**
	 * EditorJFrame的内部类,EditorJFrame可容纳一组JInternalFrame内部框架
	 * @author ljheee
	 *也就是一个个文本区
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
			this.text.setFont(new Font("宋体",1,30));
			this.text.getDocument().addUndoableEditListener(undoManager);
			this.text.setComponentPopupMenu(EditorJFrame.this.popMenu);
			this.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getButton()==3) {  //鼠标右击，显示快捷菜单
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
			rbMenuItem.setText(this.file.getName());//窗口菜单--添加标题
			this.setTitle(this.file.getName());//小窗口--文本区添加标题
			ReadWriteFile.readFrom(file, text);
			
			this.setVisible(true);
			textFrame = this;
		}
		
		/**
		 * TextFrame的内部类---内部窗口监听
		 * @author ljheee
		 *
		 */
		class MyInternalFrameListener implements InternalFrameListener{
			
			@Override
			public void internalFrameActivated(InternalFrameEvent e) { //激活时
				EditorJFrame.this.contentArea = TextFrame.this.text;//改变外部类textarea，使工具栏作用于当前text[内部文本]
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
			public void internalFrameDeiconified(InternalFrameEvent e) {}//恢复
			@Override
			public void internalFrameIconified(InternalFrameEvent e) {} //最小化
			@Override
			public void internalFrameOpened(InternalFrameEvent e) {}
		}
		
		public boolean setSelected(File file)  {//查找file是否已打开
			
			JInternalFrame[] inners = desktop.getAllFrames();
			int i = 0;
			for (i = 0; i < inners.length; i++) {
				File f = ((TextFrame)inners[i]).file;
				if(file.getName().equals(f.getName())){
					try {
						inners[i].setSelected(true);//选中内部文本窗口，不可重复打开
						return true;  //已被打开，返回true
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
	 * EditorJFrame的内部类---响应action事件
	 * @author ljheee
	 */
	class HamdleAction implements ActionListener{

		private int i = 1;
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==newFile){   //处理---新建文件
				new TextFrame(null);
				textFrame.rbMenuItem.setText("新建文本"+ i);//窗口菜单--添加标题
				textFrame.setTitle("新建文本"+ i);
				fileInfo.setText("新建文本");
				pathInfo.setText("");
				i++;
				return;
			}
			//处理---打开文件
			if(e.getSource()==open&&fileChooser.showOpenDialog(EditorJFrame.this)==0){//显示打开文件对话框，且点击“打开”   
				File file = fileChooser.getSelectedFile();
	//			System.out.println(file.getName());
				if(!textFrame.setSelected(file)){ //判断是否已打开
					if(!contentArea.getText().equals("")){//没有空文本窗口时，才new新的
						new TextFrame(file);
					}
					pathInfo.setText(file.getPath());//状态栏--显示文件路径
					
					ReadWriteFile.readFrom(file, contentArea);
					textFrame.rbMenuItem.setText(fileChooser.getSelectedFile().getName());//窗口菜单--添加标题
					textFrame.setTitle(fileChooser.getSelectedFile().getName());//小窗口--添加文本标题
				}
				return;
			}
			if(e.getSource() instanceof JRadioButtonMenuItem){
				textFrame.setSelected(new File(e.getActionCommand()));//e.getActionCommand()就是在窗口菜单，点击的 菜单项[文件名]
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
			if(e.getSource()==findReplace){//查找替换
				new FindReplaceFrame(contentArea);
			}
			if(e.getSource()==undoEdit){//撤销编辑
				if(undoManager.canUndo()) undoManager.undo();
			}
			
		}
		
		
	}
	
	
	public static void main(String[] args) {
		new EditorJFrame(new File(""));
	}
	
}
