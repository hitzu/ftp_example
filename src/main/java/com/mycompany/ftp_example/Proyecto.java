/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ftp_example;

/**
 *
 * @author roberto
 */
import java.awt.BorderLayout;
import java.awt.EventQueue;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import javax.swing.JFrame;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;

import javax.swing.Box;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import java.awt.Color;


public class Proyecto {

	private JFrame frmSistemaDeArchivos;
	private JTextField ruta;
	private JTextField nom_dir;
	private JTextField nom_arch;
	private JTextArea muestra;
	private String ruta_act;
	private FTPClient ftp;
	private String c_ruta;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Proyecto window = new Proyecto();
					window.frmSistemaDeArchivos.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Proyecto() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		ruta_act="";
		c_ruta="";
		frmSistemaDeArchivos = new JFrame();
		frmSistemaDeArchivos.getContentPane().setBackground(new Color(102, 205, 170));
		frmSistemaDeArchivos.setResizable(false);
		frmSistemaDeArchivos.setTitle("Sistema de Archivos Distribuido");
		frmSistemaDeArchivos.setBounds(100, 100, 619, 608);
		frmSistemaDeArchivos.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSistemaDeArchivos.getContentPane().setLayout(null);
		ftp = new FTPClient();
		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		frmSistemaDeArchivos.getContentPane().add(btnNewButton);
		
		JButton back = new JButton("");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				muestra.setText("");
				FTPFile[] lista;
				try{
				c_ruta= ftp.printWorkingDirectory();
				ftp.changeWorkingDirectory("..");
				lista = ftp.listFiles();
				for(int i=0; i < lista.length; i++){
					muestra.append(lista[i].toString() + "\n");
					
				}
				ruta.setText(ruta_act + ftp.printWorkingDirectory());
				nom_dir.setText("");
			
				}
				catch (IOException e3)
				{
					System.out.print(e3);
				}
				
			}
		});
		back.setIcon(new ImageIcon("Ico"+File.separator+"arrow-back-icon.png"));
		back.setBounds(46, 59, 24, 24);
		frmSistemaDeArchivos.getContentPane().add(back);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				muestra.setText("");
				FTPFile[] lista;
				try{
				ftp.changeWorkingDirectory(c_ruta);
				lista = ftp.listFiles();
				for(int i=0; i < lista.length; i++){
					muestra.append(lista[i].toString() + "\n");
					
				}
				ruta.setText(ruta_act + c_ruta);
				nom_dir.setText("");
				}
				catch (IOException e3)
				{
					System.out.print(e3);
				}
				
				
			}
		});
		btnNewButton_1.setIcon(new ImageIcon("Ico"+File.separator+"arrow-next-3-icon.png"));
		btnNewButton_1.setBounds(80, 59, 24, 24);
		frmSistemaDeArchivos.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("");
		btnNewButton_2.setIcon(new ImageIcon("Ico"+File.separator+"Places-folder-green-icon.png"));
		btnNewButton_2.setBounds(151, 59, 24, 24);
		frmSistemaDeArchivos.getContentPane().add(btnNewButton_2);
		
		ruta = new JTextField();
		ruta.setEditable(false);
		ruta.setBounds(185, 63, 368, 20);
		frmSistemaDeArchivos.getContentPane().add(ruta);
		ruta.setColumns(10);
		
		JButton actualiza = new JButton("");
		actualiza.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        muestra.setText("");
                        FTPFile[] lista;
			try{
                            lista = ftp.listFiles();
                            for(int i=0; i < lista.length; i++){
                                    muestra.append(lista[i].toString() + "\n");	
                            }
			}
			catch(IOException e1){
                            System.out.print("Error de conexion: " + e1.toString());
			}	
                    }
		});
		actualiza.setIcon(new ImageIcon("Ico"+File.separator+"update-icon.png"));
		actualiza.setBounds(568, 60, 24, 24);
		frmSistemaDeArchivos.getContentPane().add(actualiza);
		
		JLabel lblNewLabel = new JLabel("Herramientas");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 124, 94, 14);
		frmSistemaDeArchivos.getContentPane().add(lblNewLabel);
		
		JButton btnNewButton_4 = new JButton("");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				muestra.setText("");
				FTPFile[] lista;
				try{
					String dir= nom_dir.getText();
					ftp.makeDirectory(dir);
					nom_dir.setText("");
					lista = ftp.listFiles();
					for(int i=0; i < lista.length; i++){
						muestra.append(lista[i].toString() + "\n");	
					}
					
				}
				catch(IOException e1){
					System.out.print(e1);
				}
			}
		});
		btnNewButton_4.setIcon(new ImageIcon("Ico"+File.separator+"folder-documents-icon.png"));
		btnNewButton_4.setBounds(114, 120, 24, 24);
		frmSistemaDeArchivos.getContentPane().add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("");
		btnNewButton_5.setIcon(new ImageIcon("Ico"+File.separator+"copy-icon.png"));
		btnNewButton_5.setBounds(155, 120, 24, 24);
		frmSistemaDeArchivos.getContentPane().add(btnNewButton_5);
		
		JButton btnNewButton_6 = new JButton("");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String arch = nom_arch.getText();
				muestra.setText("");
				FTPFile[] lista;
			try{
				ftp.deleteFile(arch);
				lista = ftp.listFiles();
				for(int i=0; i < lista.length; i++){
					muestra.append(lista[i].toString() + "\n");	
				}
			}
			catch(IOException e1){
				System.out.print("Error de conexion " + e1.toString());
			}
				
			}
		});
		btnNewButton_6.setIcon(new ImageIcon("Ico"+File.separator+"delete-file-icon.png"));
		btnNewButton_6.setBounds(195, 120, 24, 24);
		frmSistemaDeArchivos.getContentPane().add(btnNewButton_6);
		
		JButton btnNewButton_7 = new JButton("");
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dir = nom_dir.getText();
				muestra.setText("");
				FTPFile[] lista;
			try{
				ftp.removeDirectory(dir);
				lista = ftp.listFiles();
				for(int i=0; i < lista.length; i++){
					muestra.append(lista[i].toString() + "\n");	
				}
			}
			catch(IOException e1){
				System.out.print("Error de conexion: " + e1.toString());
			}
			}
		});
		btnNewButton_7.setIcon(new ImageIcon("Ico"+File.separator+"folder-delete-icon.png"));
		btnNewButton_7.setBounds(240, 120, 24, 24);
		frmSistemaDeArchivos.getContentPane().add(btnNewButton_7);
		
		JButton bt_norma = new JButton("");
		bt_norma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ruta_act="/Sandy";
				muestra.setText("");
				FTPFile[] lista;
				String ip = "192.168.0.13";
				String user = "Sandy";
				String pass = "sandy";
			
			try{
				ftp.connect(ip);
				boolean login = ftp.login(user, pass);
				ftp.enterLocalPassiveMode();
				lista = ftp.listFiles();
				for(int i=0; i < lista.length; i++){
					muestra.append(lista[i].toString() + "\n");
					
				}
				ruta.setText("/Sandy");
			}
			catch(IOException e){
				System.out.print("Error de conexion: " + e.toString());
			}
				
				
			}
		});
		bt_norma.setIcon(new ImageIcon("Ico"+File.separator+"Folder-icon (3).png"));
		bt_norma.setBounds(119, 227, 64, 64);
		frmSistemaDeArchivos.getContentPane().add(bt_norma);
		
		JButton bt_cesar = new JButton("");
		bt_cesar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ruta_act="Ivan";
				muestra.setText("");
				FTPFile[] lista;
				String ip = "192.168.0.12";
				String user = "Ivan&Liz";
				String pass = "ihernandez";
			
			try{
				ftp.connect(ip);
				boolean login = ftp.login(user, pass);
				ftp.enterLocalPassiveMode();
				lista = ftp.listFiles();
				for(int i=0; i < lista.length; i++){
					muestra.append(lista[i].toString() + "\n");
					
				}
				ruta.setText("/Ivan");
			}
			catch(IOException e){
				System.out.print("Error de conexion");
			}
			
			}
		});
		bt_cesar.setIcon(new ImageIcon("Ico"+File.separator+"Folder-icon (3).png"));
		bt_cesar.setBounds(221, 227, 64, 64);
		frmSistemaDeArchivos.getContentPane().add(bt_cesar);
		
		JButton bt_Luis = new JButton("");
		bt_Luis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ruta_act="Roberto";
				muestra.setText("");
				FTPFile[] lista;
				String ip = "192.168.0.11";
				String user = "hitzu";
				String pass = "olagatito!";
			
			try{
				ftp.connect(ip);
				boolean login = ftp.login(user, pass);
				ftp.enterLocalPassiveMode();
				lista = ftp.listFiles();
				for(int i=0; i < lista.length; i++){
					muestra.append(lista[i].toString() + "\n");
				}
				ruta.setText("/Roberto");
			}
			catch(IOException e1){
				System.out.print("Error de conexion " + e1.toString());
			}			
			}
		});
		bt_Luis.setIcon(new ImageIcon("Ico"+File.separator+"Folder-icon (3).png"));
		bt_Luis.setBounds(327, 227, 64, 64);
		frmSistemaDeArchivos.getContentPane().add(bt_Luis);
		
		JButton bt_fanny = new JButton("");
		bt_fanny.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ruta_act="Fanny";
				muestra.setText("");
				FTPFile[] lista;
				String ip = "192.168.0.4";
				String user = "digo7195@hotmail.com";
				String pass = "7195fannydigo";
			
			try{
				ftp.connect(ip);
				boolean login = ftp.login(user, pass);
				ftp.enterLocalPassiveMode();
				lista = ftp.listFiles();
				for(int i=0; i < lista.length; i++){
					muestra.append(lista[i].toString() + "\n");
					
				}
				ruta.setText("/Fanny");
			}
			catch(IOException e2){
				System.out.print("Error de conexion");
			}
			}
		});
		bt_fanny.setIcon(new ImageIcon("C:\\eclipse\\SistemasOperativos\\Ico\\Folder-icon (3).png"));
		bt_fanny.setBounds(430, 227, 64, 64);
		//frmSistemaDeArchivos.getContentPane().add(bt_fanny);
		
		JLabel lblDirectorio = new JLabel("Directorio:");
		lblDirectorio.setBounds(43, 175, 61, 14);
		frmSistemaDeArchivos.getContentPane().add(lblDirectorio);
		
		nom_dir = new JTextField();
		nom_dir.setBounds(105, 172, 113, 20);
		frmSistemaDeArchivos.getContentPane().add(nom_dir);
		nom_dir.setColumns(10);
		
		nom_arch = new JTextField();
		nom_arch.setBounds(380, 172, 127, 20);
		frmSistemaDeArchivos.getContentPane().add(nom_arch);
		nom_arch.setColumns(10);
		
		JLabel lblArchivo = new JLabel("Archivo:");
		lblArchivo.setBounds(327, 175, 46, 14);
		frmSistemaDeArchivos.getContentPane().add(lblArchivo);
		
		JButton btnAbrir = new JButton("Abrir");
		btnAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				muestra.setText("");
				FTPFile[] lista;
				String nombre = nom_dir.getText();
				try{
				ftp.changeWorkingDirectory(nombre);
				lista = ftp.listFiles();
				for(int i=0; i < lista.length; i++){
					muestra.append(lista[i].toString() + "\n");
					
				}
				ruta.setText(ruta_act + ftp.printWorkingDirectory());
				nom_dir.setText("");
				}
				catch (IOException e3)
				{
					System.out.print(e3);
				}
				
				
			}
		});
		btnAbrir.setBounds(224, 170, 82, 23);
		frmSistemaDeArchivos.getContentPane().add(btnAbrir);
		
		muestra = new JTextArea();
		muestra.setLineWrap(true);
		muestra.setWrapStyleWord(true);
		muestra.setBounds(80, 309, 484, 136);
		
		JScrollPane scroll= new JScrollPane(muestra);
		scroll.setBounds(80, 309, 484, 233);
		frmSistemaDeArchivos.getContentPane().add(scroll);
		
		JButton subir = new JButton("Subir");
		subir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					 ftp.changeWorkingDirectory("/");            //nos movemos dentro del arbol de directorios        
				      FileInputStream fis = new FileInputStream("C:/Users/Cesar/Documents/Radiotelegrafo.pdf"); //Se abre un archivo de nuestra maquina local
				      ftp.setFileType(ftp.BINARY_FILE_TYPE); //Se pone tipo binario para poder enviar archivos de cualquier tipo
				      boolean res = ftp.storeFile("Hola.pdf", fis ); //arch=nombre que va a tener el archivo
				}
				catch(IOException e1){
					System.out.print(e1);
				}
			}
		});
		subir.setBounds(284, 120, 89, 23);
		frmSistemaDeArchivos.getContentPane().add(subir);
		
		JButton abrir = new JButton("Abrir");
		abrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				try{
				FileOutputStream stream = null;
				String archivo = "/" + nom_arch.getText();
				stream = new FileOutputStream("C:/Users/normita.jacqui/Desktop/Abrir" + archivo);

				ftp.retrieveFile(archivo, stream);//pone el archivo en tu stream
				stream.close();
				String f= new String("C:/Users/normita.jacqui/Desktop/Abrir" + archivo);
				Runtime.getRuntime().exec("cmd /c start " + f);
				}
				catch(IOException e1){
					System.out.print(e1);
				}
			}	
		});
		abrir.setBounds(515, 170, 83, 23);
		frmSistemaDeArchivos.getContentPane().add(abrir);
		
		JLabel lblNorma = new JLabel("Sandy");
		lblNorma.setHorizontalAlignment(SwingConstants.CENTER);
		lblNorma.setBounds(120, 205, 63, 14);
		frmSistemaDeArchivos.getContentPane().add(lblNorma);
		
		JLabel lblCesar = new JLabel("Ivan");
		lblCesar.setHorizontalAlignment(SwingConstants.CENTER);
		lblCesar.setBounds(221, 205, 64, 14);
		frmSistemaDeArchivos.getContentPane().add(lblCesar);
		
		JLabel lblOmar = new JLabel("Roberto");
		lblOmar.setHorizontalAlignment(SwingConstants.CENTER);
		lblOmar.setBounds(327, 205, 64, 14);
		frmSistemaDeArchivos.getContentPane().add(lblOmar);
		
		JLabel lblFanny = new JLabel("Fanny");
		lblFanny.setHorizontalAlignment(SwingConstants.CENTER);
		lblFanny.setBounds(430, 205, 64, 14);
		//frmSistemaDeArchivos.getContentPane().add(lblFanny);
		
	}
}
