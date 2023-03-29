package Model;

import java.util.Arrays;
import java.util.Observable;

public class Restaurant extends Observable {
    public boolean client;
    public int count=0;
    public boolean confirm;
    public int maxnumClient;
    public boolean[] tables;
    public int tableAux;
    public int numClient;
    public boolean Access;
    public int peticiones;
    public int order;
    public int food;

    public Restaurant(){
        tables = new boolean[20];
        Arrays.fill(tables, false);
        order=0;
        food=0;
        confirm=false;
        maxnumClient = 0;
        peticiones=0;
        client=false;
        Access=false;
        numClient=0;
        tableAux = -1;


    }


    public synchronized int entry(String nombre){
        int numMesa = -1;
        try {
            while (maxnumClient > 20) {
                wait();
            }
            numClient++;
            maxnumClient++;
            Access = true;
            client = true;
            for (int i = 0; i < 21; i++) {
                if (!tables[i]) {
                    numMesa = i;
                    tableAux = i;
                    tables[i] = true;
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setChanged();
        notifyObservers("seat " + numMesa);
        System.out.println(numMesa);
        return numMesa;
    }

    public int ordenar(){
        synchronized (this) {
            order++;
            notifyAll();
            int rest=order-1;
            return rest  ;
        }
    }

    public void servicioOrden() {
        synchronized (this) {
            if (order <= 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                notifyAll();
                setChanged();
                notifyObservers("libreMesero " + tableAux);
            } else {
                order--;
                peticiones++;
                notifyAll();
                setChanged();
                notifyObservers("ocupadoMesero " + tableAux);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
        Thread.sleep(6000
        );
    }

    public void salir(int numMesaLibre){
        synchronized (this) {
            if(!confirm){
                confirm=true;
                System.out.println(numClient+"  se fue");
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
