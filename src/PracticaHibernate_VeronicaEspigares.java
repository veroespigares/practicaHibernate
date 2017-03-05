
import static java.lang.Integer.parseInt;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import programa.Consulta;
import programa.Paciente;
import programa.Pacientesintoma;
import programa.Sintoma;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Verónica
 */
public class PracticaHibernate_VeronicaEspigares {
    
    public static void main(String[] args) {
        //Preguntamos al usuario que opción quiere
        int opcion = 0;
        //Teclado
        Scanner teclado = new Scanner (System.in);
        
        //Creamos todos los datos que necesitamos
        Configuration cfg = new Configuration().configure();
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
        SessionFactory sf = cfg.buildSessionFactory(serviceRegistry);
        Session ses = sf.openSession();
        Transaction tr = ses.beginTransaction();
     
        while(opcion!=4){
            System.out.println("Elige una opción:");
            System.out.println("1. Consultas");
            System.out.println("2. Pacientes");
            System.out.println("3. Síntomas");
            System.out.println("4. Salir");
            opcion = parseInt(teclado.nextLine());

            switch(opcion){
                    case 1: 
                        menuConsulta(ses,tr);
                        break;

                    case 2:
                        menuPaciente(ses,tr);
                        break;

                    case 3:
                        menuSintoma(ses,tr);
                        break;

                    case 4:
                        System.out.println("Cerrando aplicación ...");
                        break;

                    default:
                        System.out.println("Opción no valida");
                        break;    
                }
        }
        
        //Cerramos la sesión
        ses.close();
    }
    
    //MENÚS:
    
    //Menú consulta:
    public static void menuConsulta(Session ses, Transaction tr){
        System.out.println("");
        System.out.println("Elige una opcion:");
        System.out.println(" 1. Añadir consulta");
        System.out.println(" 2. Listar consultas");
        System.out.println(" 3. Listar todos los pacientes de una consulta en concreto");
        System.out.println(" 4. Modificar consulta");
        System.out.println(" 5. Borrar consulta");
        
        Scanner tecladoConsultas = new Scanner(System.in);
            int opcionConsulta;
            opcionConsulta = tecladoConsultas.nextInt();
                
            String nombre="", horario="", nombreProfesional="", numConsulta="";
            String nom="", nomP="";
            
            switch(opcionConsulta)
            {
                case 1:
                    System.out.println("---Añadir consulta---");
                    tecladoConsultas.nextLine();
                    System.out.print("Introduce el nombre:");
                    nombre = tecladoConsultas.nextLine();
                    System.out.print("Introduce el horario:");
                    horario = tecladoConsultas.nextLine();
                    System.out.print("Introduce el nombre completo del profesional encargado de la consulta:");
                    nombreProfesional = tecladoConsultas.nextLine();
                    System.out.print("Introduce el número de consulta:");
                    numConsulta = tecladoConsultas.nextLine();
                    System.out.println("");
                    
                    nuevaConsulta(ses,tr,nombre,horario,nombreProfesional,numConsulta);
                break;
                
                case 2:
                    System.out.println("---Listar consultas---");
                    
                    listarConsulta(ses,tr);
                break;
                
                case 3:
                    System.out.println("---Listar pacientes de una consulta---");
                    tecladoConsultas.nextLine();
                    System.out.println("Introduce el nombre de la consulta que quiere saber sus pacientes:");
                    nombre = tecladoConsultas.nextLine();
                    System.out.println("Introduce el nombre del profesional encargado de la consulta que quiere saber sus pacientes:");
                    nombreProfesional = tecladoConsultas.nextLine();
                    System.out.println("");
                    if(existeConsulta(ses,tr,nombre,nombreProfesional)){
                        listarConsultaPaciente(ses,tr,nombre,nombreProfesional);
                    }else{
                        System.out.println("Error, la consulta introducida no existe");
                    }
                break;
                
                case 4:
                    System.out.println("---Modificar consulta---");
                    tecladoConsultas.nextLine();
                    System.out.print("Introduce el nombre de la consulta que quiere modificar:");
                    nom = tecladoConsultas.nextLine();
                    System.out.print("Introduce el nombre del profesional encargado de la consulta que quiere modificar:");
                    nomP = tecladoConsultas.nextLine();
                    //Comprobamos que la consulta exista, si existe, pido los datos para modificarla
                    if(existeConsulta(ses,tr,nom,nomP)){
                        System.out.println("");
                        System.out.println("---Nuevos datos consulta---");
                        System.out.print("Introduce el nombre:");
                        nombre = tecladoConsultas.nextLine();
                        System.out.print("Introduce el horario:");
                        horario = tecladoConsultas.nextLine();
                        System.out.print("Introduce el nombre completo del profesional encargado de la consulta:");
                        nombreProfesional = tecladoConsultas.nextLine();
                        System.out.print("Introduce el número de consulta:");
                        numConsulta = tecladoConsultas.nextLine();
                        System.out.println("");
                        
                        modificarConsulta(ses,tr,nom,nomP,nombre,horario,nombreProfesional,numConsulta);
                    }else{
                        System.out.println("Error, la consulta introducida no existe");
                        System.out.println("");
                    }
                break;
                
                case 5:
                    System.out.println("---Borrar consulta---");
                    tecladoConsultas.nextLine();
                    System.out.print("Introduce el nombre de la consulta que quiere eliminar:");
                    nombre = tecladoConsultas.nextLine();
                    System.out.print("Introduce el nombre del profesional encargado de la consulta que quiere eliminar:");
                    nombreProfesional = tecladoConsultas.nextLine();
                    
                    eliminarConsulta(ses,tr,nombre,nombreProfesional);
                break;
                
                default:
                    System.out.println("Opción no valida");
            } 
    }
    
    //Menú paciente:
    public static void menuPaciente(Session ses, Transaction tr){
        System.out.println("");
        System.out.println("Elige una opcion:");
        System.out.println(" 1. Añadir paciente");
        System.out.println(" 2. Añadir síntomas a un paciente en concreto");
        System.out.println(" 3. Listar pacientes");
        System.out.println(" 4. Listar todos los síntomas que tiene un paciente en concreto");
        System.out.println(" 5. Modificar paciente");
        System.out.println(" 6. Borrar paciente");
        System.out.println(" 7. Borrar algún síntoma a un paciente en concreto");
        
        Scanner tecladoPacientes = new Scanner(System.in);
            int opcionPaciente;
            opcionPaciente = tecladoPacientes.nextInt();
                
            String nombre="", apellidos="", dni="", numSegSocial="", direccion="", telefono="", sexo="", d="";
            String nombreConsulta="", nompConsulta="", breveDescripcion="", nivelGravedad="";
            int edad=0;
            
            switch(opcionPaciente)
            {
                case 1:
                    System.out.println("---Añadir paciente---");
                    tecladoPacientes.nextLine();
                    System.out.print("Introduce el nombre:");
                    nombre = tecladoPacientes.nextLine();
                    System.out.print("Introduce los apellidos:");
                    apellidos = tecladoPacientes.nextLine();
                    System.out.print("Introduce el DNI:");
                    dni = tecladoPacientes.nextLine();
                    System.out.print("Introduce la dirección:");
                    direccion = tecladoPacientes.nextLine();
                    System.out.print("Introduce el teléfono:");
                    telefono = tecladoPacientes.nextLine();
                    System.out.print("Introduce el sexo (Hombre/Mujer):");
                    sexo = tecladoPacientes.nextLine();
                    System.out.print("Introduce la edad:");
                    edad = tecladoPacientes.nextInt();
                    tecladoPacientes.nextLine();
                    System.out.print("Introduce el número de la seguridad social:");
                    numSegSocial = tecladoPacientes.nextLine();
                    System.out.print("Introduce el nombre de la consulta a la que pertenece el paciente:");
                    nombreConsulta = tecladoPacientes.nextLine();
                    System.out.print("Introduce el nombre del profesional encargado de la consulta a la que pertenece el paciente:");
                    nompConsulta = tecladoPacientes.nextLine();
                    System.out.println("");
                    
                    nuevoPaciente(ses,tr,nombre,apellidos,dni,direccion,telefono,sexo,edad,numSegSocial,nombreConsulta,nompConsulta);
                break;
                
                case 2:
                    System.out.println("---Añadir síntomas a un paciente---");
                    tecladoPacientes.nextLine();
                    System.out.println("Introduce el DNI del paciente al que vas a asociar el síntoma:");
                    dni = tecladoPacientes.nextLine();
                    System.out.print("Introduce la descripción breve del síntoma:");
                    breveDescripcion = tecladoPacientes.nextLine();
                    System.out.print("Introduce el nivel de gravedad del síntoma (Alto,Medio,Bajo):");
                    nivelGravedad = tecladoPacientes.nextLine();
                    System.out.println("");

                    nuevoPacienteSintoma(ses,tr,dni,breveDescripcion,nivelGravedad);
                break;
                
                case 3:
                    System.out.println("---Listar pacientes---");
                    
                    listarPaciente(ses,tr);
                break;
                
                case 4:
                    System.out.println("---Listar todos los síntomas que tiene un paciente---");
                    tecladoPacientes.nextLine();
                    System.out.println("Introduce el DNI del paciente del que quiere saber los síntomas:");
                    dni = tecladoPacientes.nextLine();
                    
                    listarSintomasPaciente(ses,tr,dni);
                break;
                
                case 5:
                    System.out.println("---Modificar paciente---");
                    tecladoPacientes.nextLine();
                    System.out.print("Introduce el dni del paciente que quiere modificar:");
                    dni = tecladoPacientes.nextLine();
                    //Comprobamos que el paciente exista, si existe, pido los datos para modificarlo
                    if(existePaciente(ses,tr,dni)){
                        System.out.println("");
                        System.out.println("---Nuevos datos paciente---");
                        System.out.print("Introduce el nombre:");
                        nombre = tecladoPacientes.nextLine();
                        System.out.print("Introduce los apellidos:");
                        apellidos = tecladoPacientes.nextLine();
                        System.out.print("Introduce el dni:");
                        d = tecladoPacientes.nextLine();
                        System.out.print("Introduce la dirección:");
                        direccion = tecladoPacientes.nextLine();
                        System.out.print("Introduce el teléfono:");
                        telefono = tecladoPacientes.nextLine();
                        System.out.print("Introduce la edad:");
                        edad = tecladoPacientes.nextInt();
                        tecladoPacientes.nextLine();
                        System.out.print("Introduce el sexo (Hombre/Mujer):");
                        sexo = tecladoPacientes.nextLine();
                        System.out.print("Introduce el número de la seguridad social:");
                        numSegSocial = tecladoPacientes.nextLine();
                        System.out.print("Introduce el nombre de la consulta a la que pertenece el paciente:");
                        nombreConsulta = tecladoPacientes.nextLine();
                        System.out.print("Introduce el nombre del profesional encargado de la consulta a la que pertenece el paciente:");
                        nompConsulta = tecladoPacientes.nextLine();
                        System.out.println("");
                        
                        modificarPaciente(ses,tr,dni,d,nombre,apellidos,direccion,telefono,edad,sexo,numSegSocial,nombreConsulta,nompConsulta);
                    }else{
                        System.out.println("Error, el paciente introducido no existe");
                        System.out.println("");
                    }
                break;
                
                case 6:
                    System.out.println("---Borrar paciente---");
                    tecladoPacientes.nextLine();
                    System.out.print("Introduce el dni del paciente que quiere eliminar:");
                    dni = tecladoPacientes.nextLine();
                    
                    eliminarPaciente(ses,tr,dni);
                break;
                
                case 7:
                    System.out.println("---Borrar síntomas a un paciente---");
                    tecladoPacientes.nextLine();
                    System.out.println("Introduce el DNI del paciente al que vas a eliminar un síntoma:");
                    dni = tecladoPacientes.nextLine();
                    System.out.print("Introduce la descripción breve del síntoma que le vas a eliminar:");
                    breveDescripcion = tecladoPacientes.nextLine();
                    System.out.print("Introduce el nivel de gravedad del síntoma (Alto,Medio,Bajo) que le vas a eliminar:");
                    nivelGravedad = tecladoPacientes.nextLine();
                    System.out.println("");

                    eliminarPacienteSintoma(ses,tr,dni,breveDescripcion,nivelGravedad);
                break;
                
                default:
                    System.out.println("Opción no valida");
            } 
    }
    
     //Menú síntoma:
    public static void menuSintoma(Session ses, Transaction tr){
        System.out.println("");
        System.out.println("Elige una opcion:");
        System.out.println(" 1. Añadir síntoma");
        System.out.println(" 2. Listar síntomas");
        System.out.println(" 3. Listar todos los pacientes que tienen un síntoma en concreto");
        System.out.println(" 4. Modificar síntoma");
        System.out.println(" 5. Borrar síntoma");
        
        Scanner tecladoSintomas = new Scanner(System.in);
            int opcionSintoma;
            opcionSintoma = tecladoSintomas.nextInt();
                
            String breveDescripcion="", descripcion="", nivelGravedad="", nGravedad="", bDescripcion="";
            
            switch(opcionSintoma)
            {
                case 1:
                    System.out.println("---Añadir síntoma---");
                    tecladoSintomas.nextLine();
                    System.out.print("Introduce la descripción breve:");
                    breveDescripcion = tecladoSintomas.nextLine();
                    System.out.print("Introduce la descripción:");
                    descripcion = tecladoSintomas.nextLine();
                    System.out.print("Introduce el nivel de gravedad (Alto/Medio/Bajo):");
                    nivelGravedad = tecladoSintomas.nextLine();
                    System.out.println("");
                    
                    nuevoSintoma(ses,tr,breveDescripcion,descripcion,nivelGravedad);
                break;
                
               
                case 2:
                    System.out.println("---Listar síntomas---");
                    
                    listarSintoma(ses,tr);
                break;
                
                case 3:
                    System.out.println("---Listar todos los pacientes que tienen un síntoma---");
                    tecladoSintomas.nextLine();
                    System.out.println("Introduce la descripción breve del síntoma:");
                    breveDescripcion = tecladoSintomas.nextLine();
                    System.out.println("Introduce el nivel de gravedad del síntoma:");
                    nivelGravedad = tecladoSintomas.nextLine();
                    
                    listarPacientesSintomas(ses,tr,breveDescripcion,nivelGravedad);
                break;
                
                case 4:
                    System.out.println("---Modificar síntoma---");
                    tecladoSintomas.nextLine();
                    System.out.print("Introduce la descripción breve del síntoma que quiere modificar:");
                    breveDescripcion = tecladoSintomas.nextLine();
                    System.out.print("Introduce el nivel de gravedad del síntoma que quiere modificar:");
                    nivelGravedad = tecladoSintomas.nextLine();
                    //Comprobamos que el síntoma exista, si existe, pido los datos para modificarlo
                    if(existeSintoma(ses,tr,breveDescripcion,nivelGravedad)){
                        System.out.println("");
                        System.out.println("---Nuevos datos síntoma---");
                        System.out.print("Introduce la descripción breve:");
                        bDescripcion = tecladoSintomas.nextLine();
                        System.out.print("Introduce la descripción:");
                        descripcion = tecladoSintomas.nextLine();
                        System.out.print("Introduce el nivel de gravedad:");
                        nGravedad = tecladoSintomas.nextLine();
                        System.out.println("");
                        
                        modificarSintoma(ses,tr,breveDescripcion,nivelGravedad,bDescripcion,descripcion,nGravedad);
                    }else{
                        System.out.println("Error, el síntoma introducido no existe");
                        System.out.println("");
                    }
                break;
                
                case 5:
                    System.out.println("---Borrar síntoma---");
                    tecladoSintomas.nextLine();
                    System.out.print("Introduce la descripción breve del síntoma que quiere eliminar:");
                    breveDescripcion = tecladoSintomas.nextLine();
                    System.out.print("Introduce el nivel de gravedad del síntoma que quiere eliminar:");
                    nivelGravedad = tecladoSintomas.nextLine();
                    
                    eliminarSintoma(ses,tr,breveDescripcion,nivelGravedad);
                break;

                default:
                    System.out.println("Opción no valida");
            } 
    }
    
    //Nueva consulta:
    public static void nuevaConsulta(Session ses,Transaction tr,String nombre, String horario, String nombreProfesional, String numConsulta){
        if(!existeConsulta(ses,tr,nombre,nombreProfesional)){
            Consulta c = new Consulta(nombre,horario,nombreProfesional,numConsulta,"si");
            ses.save(c);
            tr.commit();
            System.out.println("Nueva consulta añadida correctamente");
        }else{
            System.out.println("Error, la consulta que ha introducido ya existe");
        }
    }
    
    //Nuevo síntoma:
    public static void nuevoSintoma(Session ses,Transaction tr,String breveDescripcion,String descripcion,String nivelGravedad){
        if(!existeSintoma(ses,tr,breveDescripcion,nivelGravedad)){
            Sintoma s = new Sintoma(breveDescripcion,descripcion,nivelGravedad,"si");
            ses.save(s);
            tr.commit();
            System.out.println("Nuevo síntoma añadido correctamente");
        }else{
            System.out.println("Error, el síntoma que ha introducido ya existe");
        }
    }
    
    //Nuevo paciente:
    public static void nuevoPaciente(Session ses,Transaction tr,String nombre,String apellidos,String dni,String direccion,String telefono,String sexo,int edad,String numSegSocial,String nombreConsulta,String nompConsulta){
        if(!existePaciente(ses,tr,dni)){
            if(existeConsulta(ses,tr,nombreConsulta,nompConsulta)){
                //Buscamos la consulta a la que va a pertenecer el paciente
                Query query = ses.createQuery("from Consulta where nombre='"+nombreConsulta+"' and nombreProfesional='"+nompConsulta+"'");
                List<Consulta> consulta = query.list();
                Consulta c = consulta.get(0);
                Paciente p = new Paciente(c,nombre,apellidos,dni,numSegSocial,direccion,telefono,edad,sexo,"si");
                ses.save(p);
                tr.commit();
                System.out.println("Nuevo paciente añadido correctamente");
            }else{
                System.out.println("Error, la consulta que ha introducido no existe");
            }
        }else{
            System.out.println("Error, el paciente que ha introducido ya existe");
        }
    }
    
    //Nueva relación paciente-síntoma:
    public static void nuevoPacienteSintoma(Session ses,Transaction tr,String dni,String breveDescripcion,String nivelGravedad){
        if(existePaciente(ses,tr,dni)){
            if(existeSintoma(ses,tr,breveDescripcion,nivelGravedad)){
                //Añadimos la nueva asociación paciente-síntoma
                Query query = ses.createQuery("from Sintoma where breveDescripcion='"+breveDescripcion+"' and nivelGravedad='"+nivelGravedad+"'");
                List<Sintoma> sintoma = query.list();
                Sintoma s = sintoma.get(0);
                Query query2 = ses.createQuery("from Paciente where dni='"+dni+"'");
                List<Paciente> paciente = query2.list();
                Paciente p = paciente.get(0);
                Pacientesintoma ps = new Pacientesintoma(p,s,"si");
                ses.save(ps);
                tr.commit();
                System.out.println("Nuevo paciente-síntoma añadido correctamente");
            }else{
                System.out.println("Error, el síntoma que ha introducido no existe");
            }
        }else{
            System.out.println("Error, el paciente que ha introducido no existe");
        }
    }
    
    //Eliminar relación paciente-síntoma:
    public static void eliminarPacienteSintoma(Session ses,Transaction tr,String dni,String breveDescripcion,String nivelGravedad){
        if(existePaciente(ses,tr,dni)){
            if(existeSintoma(ses,tr,breveDescripcion,nivelGravedad)){
                //Eliminamos la asociación paciente-síntoma
                Query query = ses.createQuery("from Sintoma where breveDescripcion='"+breveDescripcion+"' and nivelGravedad='"+nivelGravedad+"'");
                List<Sintoma> sintoma = query.list();
                Sintoma s = sintoma.get(0);
                Query query2 = ses.createQuery("from Paciente where dni='"+dni+"'");
                List<Paciente> paciente = query2.list();
                Paciente p = paciente.get(0);
                Query query3 = ses.createQuery("from Pacientesintoma where paciente.dni='"+dni+"' and sintoma.breveDescripcion='"+breveDescripcion+"' and sintoma.nivelGravedad='"+nivelGravedad+"'");
                List<Pacientesintoma> pacientesintoma = query3.list();
                Pacientesintoma ps = pacientesintoma.get(0);
                ps.setVisible("no");
                
                //Actualizamos la relación paciente-síntoma
                ses.merge(ps);
                tr.commit();
                System.out.println("Relación paciente-síntoma eliminada correctamente");
            }else{
                System.out.println("Error, el síntoma que ha introducido no existe");
            }
        }else{
            System.out.println("Error, el paciente que ha introducido no existe");
        }
    }
    
    //Eliminar consulta:
    public static void eliminarConsulta(Session ses,Transaction tr,String nombre, String nombreProfesional){
        //Sacamos el id de la consulta que queremos eliminar
        Query query = ses.createQuery("from Consulta where nombre='"+nombre+"' and nombreProfesional='"+nombreProfesional+"'");
        List<Consulta> consulta = query.list();
        consulta.get(0).setVisible("no");
        ses.merge(consulta.get(0));
        //Actualizamos en la base de datos
        tr.commit();
        System.out.println("Consulta eliminada correctamente");
    }
    
    //Eliminar paciente:
    public static void eliminarPaciente(Session ses,Transaction tr,String dni){
        //Sacamos el id del paciente que queremos eliminar
        Query query = ses.createQuery("from Paciente where dni='"+dni+"'");
        List<Paciente> paciente = query.list();
        paciente.get(0).setVisible("no");
        ses.merge(paciente.get(0));
        //Actualizamos en la base de datos
        tr.commit();
        System.out.println("Paciente eliminado correctamente");
    }
    
    //Eliminar síntoma:
    public static void eliminarSintoma(Session ses,Transaction tr,String breveDescripcion,String nivelGravedad){
        //Sacamos el id del síntoma que queremos eliminar
        Query query = ses.createQuery("from Sintoma where breveDescripcion='"+breveDescripcion+"' and nivelGravedad='"+nivelGravedad+"'");
        List<Sintoma> sintoma = query.list();
        sintoma.get(0).setVisible("no");
        ses.merge(sintoma.get(0));
        //Actualizamos en la base de datos
        tr.commit();
        System.out.println("Síntoma eliminado correctamente");
    }
    
    //Listar consulta:
    public static void listarConsulta(Session ses,Transaction tr){
        Query query = ses.createQuery("from Consulta");
        List<Consulta> consultas = query.list();
        for (int i=0; i<consultas.size(); i++) {
            if(consultas.get(i).getVisible().equals("si")){
                System.out.println("Nombre consulta: "+consultas.get(i).getNombre());
                System.out.println("Horario consulta: "+consultas.get(i).getHorario());
                System.out.println("Nombre del profesional de la consulta: "+consultas.get(i).getNombreProfesional());
                System.out.println("Número de consulta: "+consultas.get(i).getNumConsulta());
                System.out.println("");
            }
        }
        System.out.println("");
    }
    
    //Listar paciente:
    public static void listarPaciente(Session ses,Transaction tr){
        Query query = ses.createQuery("from Paciente");
        List<Paciente> pacientes = query.list();
        for (int i=0; i<pacientes.size(); i++) {
            if(pacientes.get(i).getVisible().equals("si")){
                System.out.println("Nombre paciente: "+pacientes.get(i).getNombre());
                System.out.println("Apellidos paciente: "+pacientes.get(i).getApellidos());
                System.out.println("DNI paciente: "+pacientes.get(i).getDni());
                System.out.println("Dirección paciente: "+pacientes.get(i).getDireccion());
                System.out.println("Teléfono paciente: "+pacientes.get(i).getTelefono());
                System.out.println("Sexo paciente: "+pacientes.get(i).getSexo());
                System.out.println("Edad paciente: "+pacientes.get(i).getEdad());
                System.out.println("Número seguridad social paciente: "+pacientes.get(i).getNumSegSocial());
                System.out.println("Consulta a la que pertenece el paciente: "+pacientes.get(i).getConsulta().getNombre());
                System.out.println("Profesional de la consulta a la que pertenece el paciente: "+pacientes.get(i).getConsulta().getNombreProfesional());
                System.out.println("");
            }
        }
        System.out.println("");
    }
    
    //Listar paciente:
    public static void listarSintoma(Session ses,Transaction tr){
        Query query = ses.createQuery("from Sintoma");
        List<Sintoma> sintomas = query.list();
        for (int i=0; i<sintomas.size(); i++) {
            if(sintomas.get(i).getVisible().equals("si")){
                System.out.println("Descripción breve síntoma: "+sintomas.get(i).getBreveDescripcion());
                System.out.println("Descripción síntoma: "+sintomas.get(i).getDescripcion());
                System.out.println("Nivel de gravedad síntoma: "+sintomas.get(i).getNivelGravedad());
                System.out.println("");
            }
        }
        System.out.println("");
    }
    
    //Listar los pacientes de una consulta:
    public static void listarConsultaPaciente(Session ses,Transaction tr,String nombre,String nombreProfesional){
        //Sacamos el id de la consulta que queremos ver sus pacientes
        Query query = ses.createQuery("from Consulta where nombre='"+nombre+"' and nombreProfesional='"+nombreProfesional+"'");
        List<Consulta> consulta = query.list();
        Set pacientes = consulta.get(0).getPacientes();
        Iterator<Paciente> it;
        Paciente p;
        it = pacientes.iterator();
            while (it.hasNext() ) {
                p = it.next();
                System.out.println("Nombre del paciente: "+p.getNombre()+" "+p.getApellidos()+" con DNI "+p.getDni());
            }
    }
    
    //Listar los síntomas que tiene un paciente:
    public static void listarSintomasPaciente(Session ses,Transaction tr,String dni){
        //Sacamos el id del paciente del que queremos saber los síntomas
        Query query = ses.createQuery("from Paciente where dni='"+dni+"'");
        List<Paciente> paciente = query.list();
        Paciente p = paciente.get(0);
        //Sacamos el id de todos los síntomas que tiene el paciente
        Query query2 = ses.createQuery("from Pacientesintoma where paciente.dni='"+p.getDni()+"'");
        List<Pacientesintoma> pacientesintoma = query2.list();
        for (int i=0; i<pacientesintoma.size(); i++) {
            if(pacientesintoma.get(i).getVisible().equals("si")){
                System.out.println("");
                System.out.println("Breve descripción síntoma: "+pacientesintoma.get(i).getSintoma().getBreveDescripcion());
                System.out.println("Descripción síntoma: "+pacientesintoma.get(i).getSintoma().getDescripcion());
                System.out.println("Nivel de gravedad síntoma: "+pacientesintoma.get(i).getSintoma().getNivelGravedad());
                System.out.println("");
            }
        }
        System.out.println("");
    }
    
    //Listar los pacientes que tienen un síntoma:
    public static void listarPacientesSintomas(Session ses,Transaction tr,String breveDescripcion,String nivelGravedad){
        //Sacamos el id del síntoma del que queremos saber los pacientes
        Query query = ses.createQuery("from Sintoma where breveDescripcion='"+breveDescripcion+"' and nivelGravedad='"+nivelGravedad+"'");
        List<Sintoma> sintoma = query.list();
        Sintoma s = sintoma.get(0);
        //Sacamos el id de todos los pacientes que tienen el síntoma
        Query query2 = ses.createQuery("from Pacientesintoma where sintoma.breveDescripcion='"+s.getBreveDescripcion()+"' and sintoma.nivelGravedad='"+s.getNivelGravedad()+"'");
        List<Pacientesintoma> pacientesintoma = query2.list();
        for (int i=0; i<pacientesintoma.size(); i++) {
            if(pacientesintoma.get(i).getVisible().equals("si")){
                System.out.println("Nombre paciente: "+pacientesintoma.get(i).getPaciente().getNombre());
                System.out.println("Apellidos paciente: "+pacientesintoma.get(i).getPaciente().getApellidos());
                System.out.println("DNI paciente: "+pacientesintoma.get(i).getPaciente().getDni());
                System.out.println("Dirección paciente: "+pacientesintoma.get(i).getPaciente().getDireccion());
                System.out.println("Teléfono paciente: "+pacientesintoma.get(i).getPaciente().getTelefono());
                System.out.println("Sexo paciente: "+pacientesintoma.get(i).getPaciente().getSexo());
                System.out.println("Edad paciente: "+pacientesintoma.get(i).getPaciente().getEdad());
                System.out.println("Número seguridad social paciente: "+pacientesintoma.get(i).getPaciente().getNumSegSocial());
                System.out.println("Consulta a la que pertenece el paciente: "+pacientesintoma.get(i).getPaciente().getConsulta().getNombre());
                System.out.println("Profesional de la consulta a la que pertenece el paciente: "+pacientesintoma.get(i).getPaciente().getConsulta().getNombreProfesional());
                System.out.println("");
            }
        }
        System.out.println("");
    }
    
    //Existe consulta:
    public static boolean existeConsulta(Session ses,Transaction tr,String nombre, String nombreProfesional){
        Query query = ses.createQuery("from Consulta where nombre='"+nombre+"' and nombreProfesional='"+nombreProfesional+"'");
        List<Consulta> consulta = query.list();
        boolean existe;
        if(consulta.size()>0){
            existe = true;
        }else{
          existe = false;
        }
        return existe;
    }
    
    //Existe paciente:
    public static boolean existePaciente(Session ses,Transaction tr,String dni){
        Query query = ses.createQuery("from Paciente where dni='"+dni+"'");
        List<Paciente> paciente = query.list();
        boolean existe;
        if(paciente.size()>0){
            existe = true;
        }else{
          existe = false;
        }
        return existe;
    }
    
    //Existe síntoma:
    public static boolean existeSintoma(Session ses,Transaction tr,String breveDescripcion,String nivelGravedad){
        Query query = ses.createQuery("from Sintoma where breveDescripcion='"+breveDescripcion+"' and nivelGravedad='"+nivelGravedad+"'");
        List<Sintoma> sintomas = query.list();
        boolean existe;
        if(sintomas.size()>0){
            existe = true;
        }else{
          existe = false;
        }
        return existe;
    }
    
    //Modificar consulta:
    public static void modificarConsulta(Session ses,Transaction tr,String nom,String nomP,String nombre, String horario, String nombreProfesional, String numConsulta){
        //Sacamos el id de la consulta que queremos modificar
        Query query = ses.createQuery("from Consulta where nombre='"+nom+"' and nombreProfesional='"+nomP+"'");
        List<Consulta> consulta = query.list();
        int id = consulta.get(0).getId();
        
        //Creamos un objeto consulta con los nuevos datos 
        Consulta co = new Consulta ();
        co.setId(id);
        co.setNombre(nombre);
        co.setHorario(horario);
        co.setNombreProfesional(nombreProfesional);
        co.setNumConsulta(numConsulta);
        co.setVisible("si");
        
        //Actualizamos la consulta
        ses.merge(co);
        
        tr.commit();
        System.out.println("Consulta modificada correctamente");
    }   
    
     //Modificar paciente:
    public static void modificarPaciente(Session ses,Transaction tr,String dni,String d,String nombre,String apellidos,String direccion,String telefono,int edad,String sexo,String numSegSocial,String nombreConsulta,String nompConsulta){
        //Sacamos el id del paciente que queremos modificar
        Query query = ses.createQuery("from Paciente where dni='"+dni+"'");
        List<Paciente> paciente = query.list();
        int id = paciente.get(0).getId();
        
        //Creamos un objeto paciente con los nuevos datos 
        Paciente pa = new Paciente ();
        if(existeConsulta(ses,tr,nombreConsulta,nompConsulta)){
            pa.setId(id);
            pa.setDni(d);
            pa.setNombre(nombre);
            pa.setApellidos(apellidos);
            pa.setDireccion(direccion);
            pa.setTelefono(telefono);
            pa.setEdad(edad);
            pa.setSexo(sexo);
            pa.setNumSegSocial(numSegSocial);
            pa.setVisible("si");
            
            //Buscamos el id de la consulta a la que pertenece el paciente
            Query query2 = ses.createQuery("from Consulta where nombre='"+nombreConsulta+"' and nombreProfesional='"+nompConsulta+"'");
            List<Consulta> consulta = query2.list();
            Consulta c = consulta.get(0);
            
            pa.setConsulta(c);

            //Actualizamos la consulta
            ses.merge(pa);

            tr.commit();
            System.out.println("Paciente modificado correctamente");
        }else{
            System.out.println("Error, la consulta introducida no existe");
        }  
    }
    
    //Modificar síntoma:
    public static void modificarSintoma(Session ses,Transaction tr,String breveDescripcion,String nivelGravedad,String bDescripcion,String descripcion,String nGravedad){
        //Sacamos el id del síntoma que queremos modificar
        Query query = ses.createQuery("from Sintoma where breveDescripcion='"+breveDescripcion+"' and nivelGravedad='"+nivelGravedad+"'");
        List<Sintoma> sintoma = query.list();
        int id = sintoma.get(0).getId();
        
        //Creamos un objeto síntoma con los nuevos datos 
        Sintoma si = new Sintoma ();
        si.setId(id);
        si.setBreveDescripcion(bDescripcion);
        si.setDescripcion(descripcion);
        si.setNivelGravedad(nGravedad);
        si.setVisible("si");
        
        //Actualizamos la consulta
        ses.merge(si);
        
        tr.commit();
        System.out.println("Síntoma modificado correctamente");
    }
}
