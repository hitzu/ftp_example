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
import java.util.logging.Level;
import java.util.logging.Logger;
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
        private Constants c;
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
		frmSistemaDeArchivos.setTitle("Sistema de Archivos FTP Distribuido");
		frmSistemaDeArchivos.setBounds(200, 50, 640, 550);
		frmSistemaDeArchivos.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSistemaDeArchivos.getContentPane().setLayout(null);
		ftp = new FTPClient();
                c = new Constants();
                LLenarConexiones();
                
                Thread h = new Thread(new Runnable() {
                    @Override
                    public void run(){
                        while(true){
                            String archivos="";
                            FTPFile[] lista;
                            String ip = c.localhost;
                            String user = c.usrLocal;
                            String pass = c.passLocal;
                            try{
				ftp.connect(ip);
				if(ftp.login(user, pass)){
                                    ftp.enterLocalPassiveMode();
                                    lista = ftp.listFiles();
                                    for(int i=0; i < lista.length; i++){
					archivos += lista[i]+"\n";	
                                    }
                                }
				ruta.setText(c.nombreLocal);
                                muestra.setText(archivos);
                                try {
                                Thread.sleep(10000);
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(Proyecto.class.getName()).log(Level.SEVERE, null, ex);
                                }
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
                            try{
                        for(Conexion conexion : conexiones){
                                System.out.println(conexion);
                                String ip = conexion.getIp();
                                String user = conexion.getUser();
                                String pass = conexion.getPass();
                                ftp.connect(ip);
                                if(ftp.login(user, pass))
                                {
                                    ftp.enterLocalPassiveMode();
                                    String dir= nom_dir.getText();
                                    if(ftp.makeDirectory(dir))
                                    {
                                        System.out.println("Se creo el directorio");
                                    }
                                    else
                                    {
                                        System.out.println("No Se creo el directorio");
                                    }
                                }    
                                else
                                {
                                    System.out.println("Error en la conexion");
                                }
                            }
                        }catch(IOException e1){
                                System.out.print(e1);
                        }
                            
                            
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
		nuevaC.setBounds(110, 410, 150, 28);
		frmSistemaDeArchivos.getContentPane().add(nuevaC);
		
		JButton eliminaA = new JButton("ELIMINAR ARCHIVO");
		eliminaA.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {        
                        try{
                        for(Conexion conexion : conexiones){
                                System.out.println(conexion);
                                String ip = conexion.getIp();
                                String user = conexion.getUser();
                                String pass = conexion.getPass();
                                ftp.connect(ip);
                                if(ftp.login(user, pass))
                                {
                                    ftp.enterLocalPassiveMode();
                                    String arch = nom_arch.getText();
                                    if(ftp.deleteFile(arch))
                                    {
                                        System.out.println("Se elimino el archivo");
                                    }
                                    else
                                    {
                                        System.out.println("No Se elimino el archivo");
                                    }
                                }    
                                else
                                {
                                    System.out.println("Error en la conexion");
                                }
                            }
                        }catch(IOException e1){
                                System.out.print(e1);
                        }	
                    }
		});
		eliminaA.setBounds(420, 448, 150, 25);
		frmSistemaDeArchivos.getContentPane().add(eliminaA);
		
		JButton eliminaC = new JButton("ELIMINAR CARPETA");
		eliminaC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                            try{
                        for(Conexion conexion : conexiones){
                                System.out.println(conexion);
                                String ip = conexion.getIp();
                                String user = conexion.getUser();
                                String pass = conexion.getPass();
                                ftp.connect(ip);
                                if(ftp.login(user, pass))
                                {
                                    ftp.enterLocalPassiveMode();
                                    String dir = nom_dir.getText();
                                    if(ftp.removeDirectory(dir))
                                    {
                                        System.out.println("Se elimino el directorio");
                                    }
                                    else
                                    {
                                        System.out.println("No Se elimino el directorio");
                                    }
                                }    
                                else
                                {
                                    System.out.println("Error en la conexion");
                                }
                            }
                        }catch(IOException e1){
                                System.out.print(e1);
                        }
                    }
		});
		eliminaC.setBounds(110, 450, 150, 24);
		frmSistemaDeArchivos.getContentPane().add(eliminaC);
		
		JLabel lblDirectorio = new JLabel("Directorio:");
		lblDirectorio.setBounds(43, 340, 61, 14);
		frmSistemaDeArchivos.getContentPane().add(lblDirectorio);
		
		nom_dir = new JTextField();
		nom_dir.setBounds(110, 340, 150, 20);
		frmSistemaDeArchivos.getContentPane().add(nom_dir);
		nom_dir.setColumns(10);
		
		nom_arch = new JTextField();
		nom_arch.setBounds(420, 340, 150, 20);
		frmSistemaDeArchivos.getContentPane().add(nom_arch);
		nom_arch.setColumns(10);
		
		JLabel lblArchivo = new JLabel("Archivo:");
		lblArchivo.setBounds(360, 340, 46, 14);
		frmSistemaDeArchivos.getContentPane().add(lblArchivo);
		
		JButton btnAbrir = new JButton("ABRIR DIRECTORIO");
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
		btnAbrir.setBounds(110, 370, 150, 25);
		frmSistemaDeArchivos.getContentPane().add(btnAbrir);
		
		muestra = new JTextArea();
		muestra.setLineWrap(true);
		muestra.setWrapStyleWord(true);
		muestra.setBounds(100, 80, 450, 136);
		
		JScrollPane scroll= new JScrollPane(muestra);
		scroll.setBounds(100, 80, 450, 233);
		frmSistemaDeArchivos.getContentPane().add(scroll);
		
		JButton subir = new JButton("SUBIR ARCHIVO");
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
		subir.setBounds(420, 410, 150, 25);
		frmSistemaDeArchivos.getContentPane().add(subir);
		
		JButton abrir = new JButton("ABRIR ARCHIVO");
		abrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
                                {          
                                    FTPFile[] lista;
                                    String ip = c.localhost;
                                    String user = c.usrLocal;
                                    String pass = c.passLocal;
                                
                                    ftp.connect(ip);
                                    if(ftp.login(user, pass))
                                    {
                                        ftp.enterLocalPassiveMode();
                                        lista = ftp.listFiles();
                                        for (FTPFile archivo : lista) 
                                        {
                                            if(archivo.getName().equals(nom_arch.getText()))
                                            {
                                                Runtime.getRuntime().exec("cmd /c start " + c.rutaLocal+archivo.getName());
                                            }
                                        }
                                    }
				
				}catch(IOException e1){
					System.out.print(e1);
				}
			}	
		});
		abrir.setBounds(420, 375, 150, 25);
		frmSistemaDeArchivos.getContentPane().add(abrir);
	}
        
        private void LLenarConexiones()
        {
            conexiones = new LinkedList<Conexion>();
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
