package com.jsoft.jminas;

import com.jsoft.jminas.gui.MainWindow;



/**
 * 
 * 
 * 
 * 
 * 
 * @author joaquin
 *
 */



public class JMInas {
	
	int i = 0;
	
	/**
	 * 
	 * @author joaquin
	 *
	 */
	public class InnerClass {
		
		public class InnerInnerClass {
			
			public void test() {
				System.out.println("El valor de i es:"+i);
			}
		}
		
		public void test() {
			new InnerInnerClass().test();
			
		}
	}
	
	public void test() {
		new InnerClass().test();
		}

	public static void main(String[] args) {
		System.out.println("Funciona");
		try {
		new MainWindow();
		} finally {
			System.out.println("hola soy yo...");
		}
//		System.exit(0);
	}
}
