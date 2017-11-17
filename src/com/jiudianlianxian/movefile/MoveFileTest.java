package com.jiudianlianxian.movefile;

import java.io.File;

public class MoveFileTest {
	
	public static void main(String[] args) {
		
		File f1 = new File("E:\\test\\a.txt");
		
		File f2 = new File("F:\\a.txt");
		
		f1.renameTo(f2);
		
	}
	
	
}
