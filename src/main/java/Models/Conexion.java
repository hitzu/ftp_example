/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author Hitzu
 */
public class Conexion {
    private String nombre;
    private String ip;
    private String user;
    private String pass;    

    public Conexion(String nombre, String ip, String user, String pass) {
        this.nombre = nombre;
        this.ip = ip;
        this.user = user;
        this.pass = pass;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "Conexiones{" + "ip=" + ip + ", user=" + user + ", pass=" + pass + '}';
    }
    
    
}
