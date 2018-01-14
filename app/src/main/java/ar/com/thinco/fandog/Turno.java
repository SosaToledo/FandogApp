package ar.com.thinco.fandog;

/**
 * Created by Frank on 9/1/2018.
 */

public class Turno {

    private String vendedor,local,precioSal,precioGas; //Stock Inicial
    private String salIni,salCom,salRot,salSc,salScp,salSal,salFin,salVta; //Salchichas
    private String panIni,panCom,panRot,panSc,panScp,panSal,panFin,panVta; //Pan
    private String gasIni,gasCom,gasRot,gasSc,gasScp,gasSal,gasFin,gasVta; //Gaseosas
    private String efeIni,ventasTotales,dAlivios,hAlivios,tAlivios,efeFin,difCaja; //CajaFragment

    public Turno(String vendedor, String local, String precioSal, String precioGas, String salIni, String salCom, String salRot, String salSc, String salScp, String salSal, String salFin, String salVta, String panIni, String panCom, String panRot, String panSc, String panScp, String panSal, String panFin, String panVta, String gasIni, String gasCom, String gasRot, String gasSc, String gasScp, String gasSal, String gasFin, String gasVta, String efeIni, String ventasTotales, String dAlivios, String hAlivios, String tAlivios, String efeFin, String difCaja) {
        this.vendedor = vendedor;
        this.local = local;
        this.precioSal = precioSal;
        this.precioGas = precioGas;
        this.salIni = salIni;
        this.salCom = salCom;
        this.salRot = salRot;
        this.salSc = salSc;
        this.salScp = salScp;
        this.salSal = salSal;
        this.salFin = salFin;
        this.salVta = salVta;
        this.panIni = panIni;
        this.panCom = panCom;
        this.panRot = panRot;
        this.panSc = panSc;
        this.panScp = panScp;
        this.panSal = panSal;
        this.panFin = panFin;
        this.panVta = panVta;
        this.gasIni = gasIni;
        this.gasCom = gasCom;
        this.gasRot = gasRot;
        this.gasSc = gasSc;
        this.gasScp = gasScp;
        this.gasSal = gasSal;
        this.gasFin = gasFin;
        this.gasVta = gasVta;
        this.efeIni = efeIni;
        this.ventasTotales = ventasTotales;
        this.dAlivios = dAlivios;
        this.hAlivios = hAlivios;
        this.tAlivios = tAlivios;
        this.efeFin = efeFin;
        this.difCaja = difCaja;
    }

    public Turno() {
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getPrecioSal() {
        return precioSal;
    }

    public void setPrecioSal(String precioSal) {
        this.precioSal = precioSal;
    }

    public String getPrecioGas() {
        return precioGas;
    }

    public void setPrecioGas(String precioGas) {
        this.precioGas = precioGas;
    }

    public String getSalIni() {
        return salIni;
    }

    public void setSalIni(String salIni) {
        this.salIni = salIni;
    }

    public String getSalCom() {
        return salCom;
    }

    public void setSalCom(String salCom) {
        this.salCom = salCom;
    }

    public String getSalRot() {
        return salRot;
    }

    public void setSalRot(String salRot) {
        this.salRot = salRot;
    }

    public String getSalSc() {
        return salSc;
    }

    public void setSalSc(String salSc) {
        this.salSc = salSc;
    }

    public String getSalScp() {
        return salScp;
    }

    public void setSalScp(String salScp) {
        this.salScp = salScp;
    }

    public String getSalSal() {
        return salSal;
    }

    public void setSalSal(String salSal) {
        this.salSal = salSal;
    }

    public String getSalFin() {
        return salFin;
    }

    public void setSalFin(String salFin) {
        this.salFin = salFin;
    }

    public String getSalVta() {
        return salVta;
    }

    public void setSalVta(String salVta) {
        this.salVta = salVta;
    }

    public String getPanIni() {
        return panIni;
    }

    public void setPanIni(String panIni) {
        this.panIni = panIni;
    }

    public String getPanCom() {
        return panCom;
    }

    public void setPanCom(String panCom) {
        this.panCom = panCom;
    }

    public String getPanRot() {
        return panRot;
    }

    public void setPanRot(String panRot) {
        this.panRot = panRot;
    }

    public String getPanSc() {
        return panSc;
    }

    public void setPanSc(String panSc) {
        this.panSc = panSc;
    }

    public String getPanScp() {
        return panScp;
    }

    public void setPanScp(String panScp) {
        this.panScp = panScp;
    }

    public String getPanSal() {
        return panSal;
    }

    public void setPanSal(String panSal) {
        this.panSal = panSal;
    }

    public String getPanFin() {
        return panFin;
    }

    public void setPanFin(String panFin) {
        this.panFin = panFin;
    }

    public String getPanVta() {
        return panVta;
    }

    public void setPanVta(String panVta) {
        this.panVta = panVta;
    }

    public String getGasIni() {
        return gasIni;
    }

    public void setGasIni(String gasIni) {
        this.gasIni = gasIni;
    }

    public String getGasCom() {
        return gasCom;
    }

    public void setGasCom(String gasCom) {
        this.gasCom = gasCom;
    }

    public String getGasRot() {
        return gasRot;
    }

    public void setGasRot(String gasRot) {
        this.gasRot = gasRot;
    }

    public String getGasSc() {
        return gasSc;
    }

    public void setGasSc(String gasSc) {
        this.gasSc = gasSc;
    }

    public String getGasScp() {
        return gasScp;
    }

    public void setGasScp(String gasScp) {
        this.gasScp = gasScp;
    }

    public String getGasSal() {
        return gasSal;
    }

    public void setGasSal(String gasSal) {
        this.gasSal = gasSal;
    }

    public String getGasFin() {
        return gasFin;
    }

    public void setGasFin(String gasFin) {
        this.gasFin = gasFin;
    }

    public String getGasVta() {
        return gasVta;
    }

    public void setGasVta(String gasVta) {
        this.gasVta = gasVta;
    }

    public String getEfeIni() {
        return efeIni;
    }

    public void setEfeIni(String efeIni) {
        this.efeIni = efeIni;
    }

    public String getVentasTotales() {
        return ventasTotales;
    }

    public void setVentasTotales(String ventasTotales) {
        this.ventasTotales = ventasTotales;
    }

    public String getdAlivios() {
        return dAlivios;
    }

    public void setdAlivios(String dAlivios) {
        this.dAlivios = dAlivios;
    }

    public String gethAlivios() {
        return hAlivios;
    }

    public void sethAlivios(String hAlivios) {
        this.hAlivios = hAlivios;
    }

    public String gettAlivios() {
        return tAlivios;
    }

    public void settAlivios(String tAlivios) {
        this.tAlivios = tAlivios;
    }

    public String getEfeFin() {
        return efeFin;
    }

    public void setEfeFin(String efeFin) {
        this.efeFin = efeFin;
    }

    public String getDifCaja() {
        return difCaja;
    }

    public void setDifCaja(String difCaja) {
        this.difCaja = difCaja;
    }

    @Override
    public String toString() {
        return "Turno{" +
                "vendedor='" + vendedor + '\'' +
                ", local='" + local + '\'' +
                ", precioSal='" + precioSal + '\'' +
                ", precioGas='" + precioGas + '\'' +
                ", salIni='" + salIni + '\'' +
                ", salCom='" + salCom + '\'' +
                ", salRot='" + salRot + '\'' +
                ", salSc='" + salSc + '\'' +
                ", salScp='" + salScp + '\'' +
                ", salSal='" + salSal + '\'' +
                ", salFin='" + salFin + '\'' +
                ", salVta='" + salVta + '\'' +
                ", panIni='" + panIni + '\'' +
                ", panCom='" + panCom + '\'' +
                ", panRot='" + panRot + '\'' +
                ", panSc='" + panSc + '\'' +
                ", panScp='" + panScp + '\'' +
                ", panSal='" + panSal + '\'' +
                ", panFin='" + panFin + '\'' +
                ", panVta='" + panVta + '\'' +
                ", gasIni='" + gasIni + '\'' +
                ", gasCom='" + gasCom + '\'' +
                ", gasRot='" + gasRot + '\'' +
                ", gasSc='" + gasSc + '\'' +
                ", gasScp='" + gasScp + '\'' +
                ", gasSal='" + gasSal + '\'' +
                ", gasFin='" + gasFin + '\'' +
                ", gasVta='" + gasVta + '\'' +
                ", efeIni='" + efeIni + '\'' +
                ", ventasTotales='" + ventasTotales + '\'' +
                ", dAlivios='" + dAlivios + '\'' +
                ", hAlivios='" + hAlivios + '\'' +
                ", tAlivios='" + tAlivios + '\'' +
                ", efeFin='" + efeFin + '\'' +
                ", difCaja='" + difCaja + '\'' +
                '}';
    }
}
