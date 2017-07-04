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

import Constants.Constants;
import Models.Conexion;
import java.awt.EventQueue;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import javax.swing.JFrame;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JFileChooser;


public class Proyecto {

	private JFrame frmSistemaDeArchivos;
	private JTextField ruta;
	private JTextField nom_dir;
	private JTextField nom_arch;
	private JTextArea muestra;
	private String ruta_act;
	private FTPClient ftp;
	private String c_ruta;
        private List<Conexion> conexiones;
        
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
		frmSistemaDeArchivos.setResizable(false);
		frmSistemaDeArchivos.setTitle("Sistema de Archivos Distribuido");
		frmSistemaDeArchivos.setBounds(100, 50, 650, 500);
		frmSistemaDeArchivos.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSistemaDeArchivos.getContentPane().setLayout(null);
		ftp = new FTPClient();
                LLenarConexiones();
                
                Thread h = new Thread(new Runnable() {
                    @Override
                    public void run(){
                        while(true){
                           String archivos="";
                            FTPFile[] lista;
                            String ip = "localhost";
                            String user = "Sandy";
                            String pass = "sandy";
                            try{
				ftp.connect(ip);
				if(ftp.login(user, pass)){
                                    ftp.enterLocalPassiveMode();
                                    lista = ftp.listFiles();
                                    for(int i=0; i < lista.length; i++){
					archivos += lista[i]+"\n";	
                                    }
                                }
				ruta.setText("/Sandy");
                                muestra.setText(archivos);
			}
			catch(IOException e){
				System.out.print("Error de conexion: " + e.toString());
			}
                        }
                    }
                });
                h.start();
		
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
				}catch (IOException e3)
				{
					System.out.print(e3);
				}
				
			}
		});
		back.setIcon(new ImageIcon("Ico"+File.separator+"arrow-back-icon.png"));
		back.setBounds(40, 40, 24, 24);
		frmSistemaDeArchivos.getContentPane().add(back);
		
		JButton sig = new JButton("");
		sig.addActionListener(new ActionListener() {
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
				}catch (IOException e3){
					System.out.print(e3);
				}
			}
		});
		sig.setIcon(new ImageIcon("Ico"+File.separator+"arrow-next-3-icon.png"));
		sig.setBounds(68, 40, 24, 24);
		frmSistemaDeArchivos.getContentPane().add(sig);
		
		ruta = new JTextField();
		ruta.setEditable(false);
		ruta.setBounds(100, 41, 450, 25);
		frmSistemaDeArchivos.getContentPane().add(ruta);
		ruta.setColumns(10);
		
		JButton nuevaC = new JButton("NUEVA CARPETA");
		nuevaC.addActionListener(new ActionListener() {
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
				}catch(IOException e1){
					System.out.print(e1);
				}
			}
		});
		nuevaC.setBounds(40, 380, 150, 28);
		frmSistemaDeArchivos.getContentPane().add(nuevaC);
		
		JButton eliminaA = new JButton("");
		eliminaA.addActionListener(new ActionListener() {
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
			}catch(IOException e1){
				System.out.print("Error de conexion " + e1.toString());
			}	
			}
		});
		eliminaA.setIcon(new ImageIcon("Ico"+File.separator+"delete-file-icon.png"));
		eliminaA.setBounds(80, 380, 150, 24);
		frmSistemaDeArchivos.getContentPane().add(eliminaA);
		
		JButton eliminaC = new JButton("");
		eliminaC.addActionListener(new ActionListener() {
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
			}catch(IOException e1){
				System.out.print("Error de conexion: " + e1.toString());
			}
			}
		});
		eliminaC.setIcon(new ImageIcon("Ico"+File.separator+"folder-delete-icon.png"));
		eliminaC.setBounds(240, 350, 24, 24);
		frmSistemaDeArchivos.getContentPane().add(eliminaC);
		
		JLabel lblDirectorio = new JLabel("Directorio:");
		lblDirectorio.setBounds(43, 340, 61, 14);
		frmSistemaDeArchivos.getContentPane().add(lblDirectorio);
		
		nom_dir = new JTextField();
		nom_dir.setBounds(105, 340, 113, 20);
		frmSistemaDeArchivos.getContentPane().add(nom_dir);
		nom_dir.setColumns(10);
		
		nom_arch = new JTextField();
		nom_arch.setBounds(380, 340, 127, 20);
		frmSistemaDeArchivos.getContentPane().add(nom_arch);
		nom_arch.setColumns(10);
		
		JLabel lblArchivo = new JLabel("Archivo:");
		lblArchivo.setBounds(327, 340, 46, 14);
		frmSistemaDeArchivos.getContentPane().add(lblArchivo);
		
		JButton btnAbrir = new JButton("Abrir D");
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
				}catch (IOException e3){
					System.out.print(e3);
				}
			}
		});
		btnAbrir.setBounds(224, 340, 82, 23);
		frmSistemaDeArchivos.getContentPane().add(btnAbrir);
		
		muestra = new JTextArea();
		muestra.setLineWrap(true);
		muestra.setWrapStyleWord(true);
		muestra.setBounds(100, 80, 450, 136);
		
		JScrollPane scroll= new JScrollPane(muestra);
		scroll.setBounds(100, 80, 450, 233);
		frmSistemaDeArchivos.getContentPane().add(scroll);
		
		JButton subir = new JButton("Subir");
		subir.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                        try{
                            JFileChooser fc = new JFileChooser();
                            int seleccion = fc.showOpenDialog(frmSistemaDeArchivos);
                            File file = null;
                            if(seleccion == JFileChooser.APPROVE_OPTION) {
                                file = fc.getSelectedFile();
                            }
                            FileInputStream fis = new FileInputStream(file.getAbsolutePath());
                            for(Conexion conexion : conexiones){
                                    System.out.println(conexion);
                                    String ip = conexion.getIp();
                                    String user = conexion.getUser();
                                    String pass = conexion.getPass();
                                    try{
                                        ftp.connect(ip);
                                        if(ftp.login(user, pass))
                                        {
                                            ftp.enterLocalPassiveMode();
                                            ftp.changeWorkingDirectory("/"); 
                                            ftp.setFileType(FTPClient.BINARY_FILE_TYPE); //Se pone tipo binario para poder enviar archivos de cualquier tipo
                                            if(ftp.storeFile(file.getName(),fis)){
                                                System.out.println("Se envio el archivo a " + conexion.getNombre());
                                            }else{
                                                System.out.println("NO Se envio el archivo a " + conexion.getNombre());
                                            }
                                        } else{
                                            System.out.println("No se logro conectar con: " + conexion.getNombre());
                                        }
                                    }catch(IOException e2){
                                            System.out.print("Error de conexion en multiprueba: " + e2.toString());
                                    }     
                                }
                        }catch(IOException e1){
                                System.out.print(e1);
                        }
                    }
		});
		subir.setBounds(284, 380, 89, 23);
		frmSistemaDeArchivos.getContentPane().add(subir);
		
		JButton abrir = new JButton("Abrir Archivo");
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
		abrir.setBounds(515, 340, 83, 23);
		frmSistemaDeArchivos.getContentPane().add(abrir);
	}
        
        private void LLenarConexiones()
        {
            conexiones = new LinkedList<Conexion>();
            Constants c = new Constants();
            Conexion conexion1 = new Conexion(c.nombre1,c.ip1,c.usr1,c.pass1);
            conexiones.add(conexion1);
            Conexion conexion2 = new Conexion(c.nombre2,c.ip2,c.usr2,c.pass2);
            conexiones.add(conexion2);
            Conexion conexion3 = new Conexion(c.nombre3,c.ip3,c.usr3,c.pass3);
            conexiones.add(conexion3);
            //Conexion conexion4 = new Conexion(c.nombre4,c.ip4,c.usr4,c.pass4);
            //conexiones.add(conexion4);
        }
}
