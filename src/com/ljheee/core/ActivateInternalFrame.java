package com.ljheee.core;

import javax.swing.DefaultDesktopManager;
import javax.swing.JInternalFrame;
/**
 * ��װ��---���ڼ���JInternalFrame
 * ԭDefaultDesktopManager���activateFrame()������static�ģ����¼�����actionPerformed()�в�����
 * @author ljheee
 *
 */
public class ActivateInternalFrame extends DefaultDesktopManager{
	public void activateInternalFrame(JInternalFrame f){
		super.activateFrame(f);//�ڴ˵��ø���ķ���
	}
}
