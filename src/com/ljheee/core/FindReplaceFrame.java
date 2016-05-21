package com.ljheee.core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.text.JTextComponent;

public class FindReplaceFrame extends JFrame {
	private JTextField input_keyword;
	private JLabel  jlebal_Msg;
	
	private int curPos = 0; //当前位置
	private String keyWord = "";
	
	public FindReplaceFrame(final JTextComponent text){
		this.setTitle("Find/Replace");
		this.setSize(290, 250);
		this.setLocationRelativeTo(text);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		JLabel lblFind = new JLabel("Find : ");
		input_keyword = new JTextField(12);
		
		JButton btnFindnext = new JButton("Find/next");
		
		jlebal_Msg = new JLabel("");
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblFind)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(input_keyword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
							.addComponent(btnFindnext))
						.addComponent(jlebal_Msg))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(23)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblFind)
						.addComponent(input_keyword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnFindnext))
					.addGap(42)
					.addComponent(jlebal_Msg)
					.addContainerGap(119, Short.MAX_VALUE))
		);
		getContentPane().setLayout(groupLayout);
		
		btnFindnext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(input_keyword.getText().length()<1){
					JOptionPane.showMessageDialog(FindReplaceFrame.this , "输入为空，请重新输入关键字...");
					return;
				}
				String content = text.getText();
				
				if(!keyWord.equals(input_keyword.getText())){//说明--是一次新 查询
					curPos = 0;
					keyWord = input_keyword.getText();
				}else{
					curPos += keyWord.length();
				}
				
				String nextStr = "";
				int beginIndex = curPos;
				int endIndex = content.length();
				if( beginIndex < endIndex ){  //判断是否最后一个
				
				nextStr = content.substring(beginIndex, endIndex);//截取剩下的字符串
				jlebal_Msg.setText("共查到： " + countMatch(keyWord, content));
				int offset = nextStr.indexOf(keyWord);
					if(offset >= 0){
						curPos += offset;     //当前位置加上偏移位置
						text.select(curPos, curPos+keyWord.length());//选中查找到的文本---高粱亮显示
					}else{
						JOptionPane.showMessageDialog(FindReplaceFrame.this, "已查到  最后一个...");
					}
				}else{
					if(countMatch(keyWord, content)==0){
						jlebal_Msg.setText("String not found!");
					}
					
				}
			}
		});
		
		
		this.setVisible(true);
	}
	/**
	 * 从content文本区    查找keyWord，返回查找到的个数
	 * @param keyword
	 * @param content
	 * @return  返回查找到keyWord的个数
	 */
	protected int countMatch(String keyword, String content) {
		int count = 0;
		int curIndex = 0;
		String nextStr = content;
		int offset = nextStr.indexOf(keyword); //下次查找的起始位置相对于当前位置的偏移
		
		while( offset >= 0 ){
//			count++;
			offset = nextStr.indexOf(keyword);    //计算相对于当前位置的偏移
			if(offset!=-1){
				count++;
			}else{
				break;
			}
			curIndex += offset + keyword.length();//更新当前起始位置
			if(curIndex >= content.length()) { //已经查找到最后一个
				count--;//TUDO：已经加1要减一，保持准确性
				break;
			}
			nextStr = content.substring(curIndex);//从上一个找到的字符串后截取剩下的字符串
		}
		
//		while(curIndex <= content.length()){
//			offset = content.indexOf(keyword);
//			
//			if(offset!=-1){
//				count++;
//			}else{
//				break;
//			}
//			curIndex += offset;
//		}
		return count;
	}
}
