package com.ljheee.core;

import javax.swing.DefaultDesktopManager;
import javax.swing.JInternalFrame;
/**
 * 包装类---用于激活JInternalFrame
 * 原DefaultDesktopManager类的activateFrame()方法是static的，在事件处理actionPerformed()中不可用
 * @author ljheee
 *
 */
public class ActivateInternalFrame extends DefaultDesktopManager{
	public void activateInternalFrame(JInternalFrame f){
		super.activateFrame(f);//在此调用父类的方法
	}
}
