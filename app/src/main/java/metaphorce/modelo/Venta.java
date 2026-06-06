package metaphorce.modelo;

public class Venta {
    private int id_venta;
    private String comprador;
    private int asientos_reservados;
    private double importe;
    private int id_funcion;

    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

    public String getComprador() {
        return comprador;
    }

    public void setComprador(String comprador) {
        this.comprador = comprador;
    }

    public int getAsientos_reservados() {
        return asientos_reservados;
    }

    public void setAsientos_reservados(int asientos_reservados) {
        this.asientos_reservados = asientos_reservados;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public int getId_funcion() {
        return id_funcion;
    }

    public void setId_funcion(int id_funcion) {
        this.id_funcion = id_funcion;
    }

    
}
