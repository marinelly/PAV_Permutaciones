
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 * Clase que pinta un grafo y calcula el camino más corto para recorrerlo.
 * @author Administrador
 */
public class Lienzo extends Canvas {

    //Matriz de costos o distancias
    double[][] distancias;
    //Lista que almacena todos los nodos que conforman el grafo
    ArrayList<Nodo> nodos;
    //Cadena que contiene el camino menor
    String caminoMenor;
    //Valor del menor camino.
    double valorMenor;

    public Lienzo() {
        distancias = new double[100][100];
        nodos = new ArrayList<Nodo>();
    }

    @Override
    public void paint(Graphics g) {
        int n = nodos.size(); 
        
        //Pintar lineas entre todos los nodos
        for(int i = 0; i < n; i++){
            Nodo nodo1 = nodos.get(i);
           for(int j = i + 1; j < n; j++){
               Nodo nodo2 = nodos.get(j);
               g.drawLine(nodo1.x + 10, nodo1.y + 10, nodo2.x + 10, nodo2.y + 10);
               
               //Pintar el valor de la distacia
               g.drawString(distancias[i][j]+"", (nodo1.x + nodo2.x)/2, (nodo1.y + nodo2.y)/2);
           }
        }
        
        //Si existe un camino lo pinta
        if(caminoMenor != null){
            String[] nodosEnCamino = caminoMenor.split(",");
            g.setColor(Color.red);
            for(int i = 0; i < n - 1; i++){
                Nodo nodo1 = nodos.get(Integer.parseInt(nodosEnCamino[i]));
                Nodo nodo2 = nodos.get(Integer.parseInt(nodosEnCamino[i+1]));
                g.drawLine(nodo1.x + 10, nodo1.y + 10, nodo2.x + 10, nodo2.y + 10);
            }
            
            Nodo nodo1 = nodos.get(Integer.parseInt(nodosEnCamino[n-1]));
            Nodo nodo2 = nodos.get(Integer.parseInt(nodosEnCamino[0]));
            g.drawLine(nodo1.x + 10, nodo1.y + 10, nodo2.x + 10, nodo2.y + 10);
            
        }
        
        //Pintar nodos
        for(int i = 0; i < n; i++){
            Nodo nodo1 = nodos.get(i);
            //Pintar circulo
            g.setColor(Color.black);
            g.fillOval(nodo1.x, nodo1.y, 20, 20);
            
            //Pintar nombre
            g.setColor(Color.white);
            g.drawString(nodo1.nombre, nodo1.x + 5, nodo1.y + 15);
            
        }

    }

    /**
     * Genera permutaciones y si comienzan por el inicio indicado
     * entonces evalua el valor de ese camino.
     * @param n Número de elementos a permutar
     * @param ini Inicio del camino
     */
    public void GenerarPermutaciones(int n, int ini) {
        int tmp = 0;
        int[] indexes = new int[n];

        //Vector que se crea con los números que se van a permutar
        int[] valores = new int[n];

        for (int i = 0; i < n;) {
            valores[i] = i;
            indexes[i] = ++i;
        }

        //Evaluar permutación almacenada en Valores
        if (valores[0] == ini) {
            EvaluarCamino(valores);
        }

        for (int i = n - 2; i >= 0;) {
            tmp = valores[indexes[i]];
            valores[indexes[i]] = valores[i];
            valores[i] = tmp;
            indexes[i]++;

            //Evaluar permutación almacenada en Valores
            if (valores[0] == ini) {
                EvaluarCamino(valores);
            }

            i = n - 1;
            while (i >= 0 && indexes[i] >= n) {
                tmp = valores[i];
                for (int k = i; k < n - 1;) {
                    valores[k] = valores[++k];
                }
                valores[n - 1] = tmp;
                indexes[i] = i + 1;
                i--;
            }
        }
    }

    /**
     * Calcula el valor del camino y si es menor al que hasta ahora se considera menor, entonces se cambia.
     * @param valores
     */
    public void EvaluarCamino(int[] valores){
        double total = 0;
        for(int i = 0; i< nodos.size() - 1; i++){
            total = total + distancias[valores[i]][valores[i+1]];
        }
        total = total + distancias[valores[0]][valores[nodos.size()-1]];
        
        if(total < valorMenor){
            valorMenor = total;
            caminoMenor = GetCamino(valores);
        }
    }

    /**
     * Recibe un vector con enteros y lo devuelve en formato string
     * @param valores
     * @return
     */
    public String GetCamino(int[] valores){
        String camino = "";
        for(int i: valores){
            camino = camino  + i+ ",";
        }
        return camino;
    }
}
