package metaphorce.modelo;

import java.util.Objects;

public class DetalleVenta {
    private int id_detalle_venta;
    private int id_venta;
    private int id_funcion;
    private String asiento;

    @Override
    public String toString() {
        return "DetalleVenta{" +
                "id_detalle_venta=" + id_detalle_venta +
                ", id_venta=" + id_venta +
                ", id_funcion=" + id_funcion +
                ", asiento='" + asiento + '\'' +
                '}';
    }

    public int getId_detalle_venta() {
        return id_detalle_venta;
    }

    public void setId_detalle_venta(int id_detalle_venta) {
        this.id_detalle_venta = id_detalle_venta;
    }

    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

    public int getId_funcion() {
        return id_funcion;
    }

    public void setId_funcion(int id_funcion) {
        this.id_funcion = id_funcion;
    }

    public String getAsiento() {
        return asiento;
    }

    public void setAsiento(String asiento) {
        this.asiento = asiento;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DetalleVenta other = (DetalleVenta) o;
        return getId_venta() == other.getId_venta() && getId_funcion() == other.getId_funcion() && Objects.equals(getAsiento(), other.getAsiento());
    }
}
