package Model;

import java.util.Observable;

public class Restaurant extends Observable {
    public boolean VIP;
    public boolean reservation;
    public boolean client;
    public boolean Access;
    public int numClient;
    public String reserved;
    public int order;
    public int food;
    public int peticiones;
    public int count=0;
    public boolean confirm;
    public int maxnumClient;
    public boolean[] tables;
    public int tableAux;

    public Restaurant(){
        VIP=false;
        reservation = true;
        client=false;
        Access=false;
        numClient=0;
        reserved ="";
        order=0;
        food=0;
        peticiones=0;
        tableAux = -1;
        confirm=false;
        maxnumClient = 0;
        tables = new boolean[20];

        for (int i=0; i<20; i++) {
            tables[i] = false;
        }
    }

/*    public synchronized boolean reserved(String nombre){
        //Para el hilo cliente
        synchronized (this) {
            if(reservation){
                System.out.println("reserved por "+nombre);
                reservation =false;
                reserved = nombre;
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;
            }
            else {
                return false;
            }
        }
    }*/

    public int entry(String nombre){
        int numMesa = -1;
        try {
            if(reserved.equals(nombre)){
                confirm = false;
                numMesa = 20;
                tableAux = 20;
            }else{
                synchronized (this) {
                    numClient++;
                    maxnumClient++;
                    while (maxnumClient==20) {
                        wait();
                    }
                    Access=true;
                    client=true;
                    for (int i=0; i<20; i++) {
                        if(!tables[i]) {
                            numMesa = i;
                            tableAux = i;
                            tables[i] = true;
                            i = 100;
                        }
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setChanged();
        notifyObservers("seat " + numMesa);
        return numMesa;
    }
    public void ordenar(){
        synchronized (this) {
            order++;
            notifyAll();
        }
    }

    public void servicioOrden(){
        String txt;
        boolean aux = false;
        synchronized (this) {
            if (order<=0){
                txt = "libreMesero";
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                aux = true;
                txt = "ocupadoMesero";
                peticiones++;
                order--;
            }
            notifyAll();
            setChanged();
            notifyObservers(txt +" "+ tableAux);
        }
        if (aux){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void cocinar() {
        boolean notify = false;
        synchronized (this) {
            if (peticiones > 0) {
                food++;
                peticiones--;
                notify = true;
            }
        }
        if (notify) {
            synchronized (this) {
                setChanged();
                notifyObservers("ocupado");
                notifyAll();
            }
        }
    }



    public synchronized void comer() throws InterruptedException {
        while (food <= 0) {
            wait();
        }
        food--;
        Thread.sleep(1000);
    }

    public void salir(int numMesaLibre){
        synchronized (this) {
            if(!confirm){
                confirm=true;
                reservation =true;
            }else{
                numClient--;
                maxnumClient--;
                client=false;
                System.out.println(numClient+" Clientes en fila");
            }
            tables[numMesaLibre] = false;
            notifyAll();
            count++;
            setChanged();
            notifyObservers("" + count);
        }
    }

    public void recepcion(){
        synchronized (this) {
            while(numClient < 1 || client){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Access=false;
        }
        synchronized (this) {
            notifyAll();
        }
    }


}
