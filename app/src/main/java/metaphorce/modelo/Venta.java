package metaphorce.modelo;

public class Venta {
    private int id_venta;
    private String comprador;
    private int no_asientos_reservados;
    private double importe;

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

    public int getNo_asientos_reservados() {
        return no_asientos_reservados;
    }

    public void setNo_asientos_reservados(int no_asientos_reservados) {
        this.no_asientos_reservados = no_asientos_reservados;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    @Override
    public boolean equals(Object obj){
        //Check if they belong to the same class
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Venta other=(Venta) obj;
        return this.id_venta == other.id_venta &&
                this.no_asientos_reservados == other.no_asientos_reservados &&
                Double.compare(this.importe, other.importe) == 0 &&
                java.util.Objects.equals(this.comprador, other.comprador);

    }

    @Override
    public String toString() {
        return "Venta{" +
                "id_venta=" + id_venta +
                ", comprador='" + comprador + '\'' +
                ", no_asientos_reservados=" + no_asientos_reservados +
                ", importe=" + importe +
                '}';
    }
}
