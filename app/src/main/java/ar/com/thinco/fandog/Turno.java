package ar.com.thinco.fandog;

/**
 * Created by Frank on 9/1/2018.
 */

public class Turno {

    private String nombre,local,turno;
    private Articulo salchichas,panes,gaseosas;
    private int iEfectivo; //Efectivo inicial
    public Turno() {
        salchichas=new Articulo();
        panes=new Articulo();
        gaseosas=new Articulo();
    }

    //Inner class.
    private class Articulo{

        private int stockInicial,compras,rotos,sinCargo,sinCargoPersonal,salidas,stockFinal;

        //Metodos autogenerados.
        public Articulo(){
            stockInicial=compras=rotos=sinCargo=sinCargoPersonal=salidas=stockFinal=0;
        }

        public int getStockInicial() {
            return stockInicial;
        }

        public void setStockInicial(int stockInicial) {
            this.stockInicial = stockInicial;
        }

        public int getCompras() {
            return compras;
        }

        public void setCompras(int compras) {
            this.compras = compras;
        }

        public int getRotos() {
            return rotos;
        }

        public void setRotos(int rotos) {
            this.rotos = rotos;
        }

        public int getSinCargo() {
            return sinCargo;
        }

        public void setSinCargo(int sinCargo) {
            this.sinCargo = sinCargo;
        }

        public int getSinCargoPersonal() {
            return sinCargoPersonal;
        }

        public void setSinCargoPersonal(int sinCargoPersonal) {
            this.sinCargoPersonal = sinCargoPersonal;
        }

        public int getSalidas() {
            return salidas;
        }

        public void setSalidas(int salidas) {
            this.salidas = salidas;
        }

        public int getStockFinal() {
            return stockFinal;
        }

        public void setStockFinal(int stockFinal) {
            this.stockFinal = stockFinal;
        }
    }
}
