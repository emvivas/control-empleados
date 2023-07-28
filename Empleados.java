/**
 * @program "Control Empleados"
 * @version 1.0
 * @author vivasrguez
 * @contact a01424732@tec.mx
 * @since 2020/03/19
 */

package controlempleados;

import java.util.Date;
import java.util.Random;
import java.text.SimpleDateFormat;

public class Empleados {

    private final String[][] REGISTRO;

    protected Empleados(int cantidad) {
        REGISTRO = new String[cantidad][7]; //0: clave; 1: nombre; 2: apellidoMaterno; 3: apellidoPaterno; 4: sexo; 5: annoNacimiento; 6: estatura;
    }

    private String getDato(int posicion, int atributo) {
        return (REGISTRO[posicion][atributo]);
    }

    protected void setDato(int posicion, int atributo, String datoEmpleado) {
        REGISTRO[posicion][atributo] = datoEmpleado.trim();
    }

    protected int getCantidadEmpleados(boolean modalidad) {
        if (modalidad) {
            return (REGISTRO.length);
        } else {
            return (REGISTRO[0].length);
        }
    }

    protected String getClaveEmpleadoEstandar(int posicion) {
        final Random NO_ALEATORIO = new Random();
        return (getTiempo(0) + "EMP" + getTiempo(1) + '0' + posicion + getTiempo(2) + NO_ALEATORIO.nextInt(getCantidadEmpleados(true)));
    }

    protected String[] getInformacion(int posicion) {
        return (REGISTRO[posicion]);
    }

    protected void setInformacion(int posicion, String[] informacion, boolean modalidad) {
        if (modalidad) {
            for (int atributo = 0; atributo < getCantidadEmpleados(false); atributo++) {
                setDato(posicion, atributo, informacion[atributo]);
            }
        } else {
            REGISTRO[posicion] = informacion;
        }
    }

    protected String getTiempo(int modalidad) {
        final String AUXILIAR;
        final Date FECHA = new Date();
        switch (modalidad) {
            case 0:
                return (new SimpleDateFormat("yyyy").format(FECHA));
            case 1:
                return (new SimpleDateFormat("MM").format(FECHA));
            case 2:
                return (new SimpleDateFormat("dd").format(FECHA));
            default:
                switch (new SimpleDateFormat("MM").format(FECHA)) {
                    case "01":
                        AUXILIAR = "enero";
                        break;
                    case "02":
                        AUXILIAR = "febrero";
                        break;
                    case "03":
                        AUXILIAR = "marzo";
                        break;
                    case "04":
                        AUXILIAR = "abril";
                        break;
                    case "05":
                        AUXILIAR = "mayo";
                        break;
                    case "06":
                        AUXILIAR = "junio";
                        break;
                    case "07":
                        AUXILIAR = "julio";
                        break;
                    case "08":
                        AUXILIAR = "agosto";
                        break;
                    case "09":
                        AUXILIAR = "septiembre";
                        break;
                    case "10":
                        AUXILIAR = "octubre";
                        break;
                    case "11":
                        AUXILIAR = "noviembre";
                        break;
                    default:
                        AUXILIAR = "diciembre";
                }
                return (new SimpleDateFormat("dd").format(FECHA) + " de " + AUXILIAR + " de " + new SimpleDateFormat("yyyy").format(FECHA) + '.');
        }
    }

    protected int getPosicionRegistro(int atributo, String datoEmpleado, int posicion, int posicionFinal, boolean modalidad) {
        for (int posicionInicial = posicion; posicionInicial <= posicionFinal; posicionInicial++) {
            if (modalidad && (getDato(posicionInicial, atributo) != null && getDato(posicionInicial, atributo).equals(datoEmpleado))) {
                return (posicionInicial);
            } else if (!modalidad && getDato(posicionInicial, atributo) == null) {
                return (posicionInicial);
            }
        }
        return (-1);
    }

    protected void recorrerPosicionRegistros(int posicion) {
        int posicionInicial;
        for (posicionInicial = posicion; posicionInicial < getCantidadEmpleados(true) - 1; posicionInicial++) {
            if (getDato(posicionInicial, 0) != null) {
                setInformacion(posicionInicial, getInformacion(posicionInicial + 1), false);
            } else {
                break;
            }
        }
        setInformacion(posicionInicial, new String[getCantidadEmpleados(false)], false);
    }
}
