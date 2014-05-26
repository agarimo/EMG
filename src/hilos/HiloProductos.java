package hilos;

import main.entidades.PrestaShop;

/**
 *
 * @author Agárimo
 */
public class HiloProductos implements Runnable {

    int tipo;
    PrestaShop ps;

    public HiloProductos(int tipo) {
        this.tipo = tipo;
    }

    public HiloProductos(PrestaShop ps) {
        this.tipo = 2;
        this.ps = ps;
    }

    @Override
    public void run() {

        switch (tipo) {
            case 1:
                carga();
                break;

            case 2:
                guarda();
                break;
        }
    }

    private void carga() {
//        GuiControl.cargarOfertas();
    }

    private void guarda() {
//        try {
//            GuiControl.guardarProducto(null);
//        } catch (SQLException ex) {
//            Logger.getLogger(HiloProductos.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
}
