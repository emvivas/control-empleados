/**
 * @program "Control Empleados"
 * @version 1.0
 * @author vivasrguez
 * @contact a01424732@tec.mx
 * @since 2020/03/19
 */

package controlempleados;

import javax.swing.JOptionPane;

public class TestControlEmpleados {

    private static Empleados control;

    public static void main(String[] args) {
        seleccionarNumeroTrabajadores();
        do {
            switch (JOptionPane.showOptionDialog(null, "Control de datos de los trabajadores.\n\n" + control.getTiempo(-1) + "\n" + (control.getPosicionRegistro(0, null, 0, control.getCantidadEmpleados(true) - 1, false) != -1 ? control.getPosicionRegistro(0, null, 0, control.getCantidadEmpleados(true) - 1, false) : control.getCantidadEmpleados(true)) + " de " + control.getCantidadEmpleados(true) + " empleados registrados.\n\nSeleccione una de las opciones para gestionar la información de los empleados.", "Sistema de Control de Trabajadores", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[]{"Registro", "Consulta", "Modificación", "Eliminación", "Cerrar sistema"}, null)) {
                case 0:
                    registrarTrabajador();
                    break;
                case 1:
                    consultarTrabajadores();
                    break;
                case 2:
                    modificarTrabajador();
                    break;
                case 3:
                    suprimirTrabajador();
                    break;
                default:
                    if (JOptionPane.showConfirmDialog(null, "¿Desea salir del sistema ahora?", "Cerrar sistema", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
            }
        } while (true);
    }

    private static int validarClaveEmpleado(String[] motivo) {
        int posicion;
        String claveEmpleado;
        do {
            claveEmpleado = JOptionPane.showInputDialog(null, "Introduzca la clave del empleado que desea " + motivo[0] + ":", motivo[1] + " del empleado", JOptionPane.QUESTION_MESSAGE);
            if (claveEmpleado != null) {
                claveEmpleado = claveEmpleado.trim().toUpperCase();
                if (!claveEmpleado.equals("")) {
                    posicion = control.getPosicionRegistro(0, claveEmpleado, 0, control.getPosicionRegistro(0, null, 0, control.getCantidadEmpleados(true) - 1, false) != -1 ? control.getPosicionRegistro(0, null, 0, control.getCantidadEmpleados(true) - 1, false) : control.getCantidadEmpleados(true) - 1, true);
                    if (posicion != -1) {
                        return posicion;
                    } else {
                        JOptionPane.showMessageDialog(null, "La clave introducida no pertenece a ningún usuario.\nAsegúrese de ingresar la clave auténtica del empleado por " + motivo[0] + " del sistema.", "No se ha encontrado al empleado con la clave ingresada", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El dato introducido es inválido para buscar la clave del empleado.\nIntroduzca un valor válido para proseguir con la " + motivo[1].toLowerCase() + " de un empleado.", "La clave del empleado no es válida", JOptionPane.ERROR_MESSAGE);
                }
            }
        } while (claveEmpleado != null);
        return -1;
    }

    private static int validarDato(String atributo, String datoEmpleado, String motivo[], boolean modalidad) {
        if (datoEmpleado == null) {
            if (JOptionPane.showConfirmDialog(null, "Aún no se ha " + motivo[1] + ' ' + atributo + " del empleado, es necesario almacenar este dato.\n¿Desea continuar con " + motivo[0].toLowerCase() + " desde los datos pendientes por especificar?", motivo[0] + " del empleado aún no ha finalizado", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(null, motivo[0] + " del empleado no se completó exitosamente.", motivo[0] + " del empleado no ha sido realizad" + motivo[2], JOptionPane.INFORMATION_MESSAGE);
                return -1;
            } else {
                return 1;
            }
        } else if (modalidad && datoEmpleado.trim().equals("")) {
            JOptionPane.showMessageDialog(null, "El dato introducido es inválido para especificar " + atributo + " del empleado.\nIntroduzca un valor correcto para concluir con " + motivo[0].toLowerCase() + '.', "El dato especificado no es válido", JOptionPane.ERROR_MESSAGE);
            return 1;
        }
        return 0;
    }

    private static void seleccionarNumeroTrabajadores() {
        int seleccion;
        do {
            try {
                seleccion = Integer.parseInt(JOptionPane.showInputDialog(null, "Bienvenida al Sistema de Control de Trabajadores.\nCopyright © 2020 vivasrguez. Todos los derechos reservados.\n\n¿Cuántos empleados laboran en su compañía?", "Sistema de Control de Trabajadores 1.0", JOptionPane.PLAIN_MESSAGE));
                if (seleccion > 1) {
                    control = new Empleados(seleccion);
                } else {
                    JOptionPane.showMessageDialog(null, "La cantidad introducida es inválida para iniciar el sistema.\nIntroduzca una cantidad aceptable para iniciar la herramienta.", "La cantidad de empleados no es válida", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException error) {
                if (error.toString().equals("java.lang.NumberFormatException: null")) {
                    System.exit(0);
                } else {
                    JOptionPane.showMessageDialog(null, "El dato introducido es inválido para especificar la cantidad de empleados.\nIntroduzca un valor numérico para proseguir con la gestión de los trabajadores.", "La cantidad de empleados no es válida", JOptionPane.ERROR_MESSAGE);
                }
            }
        } while (control == null);
    }

    private static void registrarTrabajador() {
        int seguimiento = 1, posicion = control.getPosicionRegistro(0, null, 0, control.getCantidadEmpleados(true) - 1, false);
        final String[] INFORMACION = new String[7], MOTIVO = {"El registro", "registrado", "o"};
        if (posicion >= 0) {
            do {
                INFORMACION[0] = JOptionPane.showInputDialog("Introduzca una clave alfanumérica para identificar al " + (posicion + 1) + "° trabajador:", control.getClaveEmpleadoEstandar(posicion));
                if (INFORMACION[0] != null) {
                    INFORMACION[0] = INFORMACION[0].trim().toUpperCase();
                    seguimiento = validarDato("la clave", INFORMACION[0], MOTIVO, true);
                    if (seguimiento == 0 && control.getPosicionRegistro(0, INFORMACION[0], 0, posicion, true) == -1) {
                        do {
                            INFORMACION[1] = JOptionPane.showInputDialog(null, "¿Cuál es el nombre del empleado?", "Registro del empleado: " + INFORMACION[0], JOptionPane.QUESTION_MESSAGE);
                            seguimiento = validarDato("el nombre", INFORMACION[1], MOTIVO, true);
                        } while (seguimiento == 1);
                        if (seguimiento != -1) {
                            do {
                                INFORMACION[2] = JOptionPane.showInputDialog(null, "¿Cuál es el apellido paterno del empleado?", "Registro del empleado: " + INFORMACION[0], JOptionPane.QUESTION_MESSAGE);
                                seguimiento = validarDato("el apellido paterno", INFORMACION[2], MOTIVO, true);
                            } while (seguimiento == 1);
                            if (seguimiento != -1) {
                                do {
                                    INFORMACION[3] = JOptionPane.showInputDialog(null, "¿Cuál es el apellido materno del empleado?", "Registro del empleado " + INFORMACION[0], JOptionPane.QUESTION_MESSAGE);
                                    seguimiento = validarDato("el apellido materno", INFORMACION[3], MOTIVO, false);
                                } while (seguimiento == 1);
                                if (seguimiento != -1) {
                                    do {
                                        try {
                                            INFORMACION[4] = Character.toString(JOptionPane.showInputDialog(null, "Introduzca el sexo del empleado:", "Registro del empleado " + INFORMACION[0], JOptionPane.QUESTION_MESSAGE, null, new String[]{"Masculino", "Femenino"}, null).toString().charAt(0));
                                        } catch (NullPointerException error) {
                                            INFORMACION[4] = null;
                                        }
                                        seguimiento = validarDato("el sexo", INFORMACION[4], MOTIVO, false);
                                    } while (seguimiento == 1);
                                    if (seguimiento != -1) {
                                        do {
                                            INFORMACION[5] = JOptionPane.showInputDialog(null, "Ingrese el año de nacimiento del empleado:", "Registro del empleado " + INFORMACION[0], JOptionPane.QUESTION_MESSAGE);
                                            try {
                                                INFORMACION[5] = Integer.toString(Integer.parseInt(INFORMACION[5]));
                                                if (Integer.parseInt(INFORMACION[5]) < Integer.parseInt(control.getTiempo(0)) - 150 || Integer.parseInt(INFORMACION[5]) > Integer.parseInt(control.getTiempo(0)) - 18) {
                                                    INFORMACION[5] = "";
                                                }
                                            } catch (NullPointerException | NumberFormatException error) {
                                                if (INFORMACION[5] != null) {
                                                    INFORMACION[5] = "";
                                                }
                                            }
                                            seguimiento = validarDato("el año de nacimiento", INFORMACION[5], MOTIVO, true);
                                        } while (seguimiento == 1);
                                        if (seguimiento != -1) {
                                            do {
                                                INFORMACION[6] = JOptionPane.showInputDialog(null, "Ingrese la altura en metros del empleado:", "Registro del empleado " + INFORMACION[0], JOptionPane.QUESTION_MESSAGE);
                                                try {
                                                    INFORMACION[6] = Float.toString(Float.parseFloat(INFORMACION[6]));
                                                    if (Float.parseFloat(INFORMACION[6]) <= 0) {
                                                        INFORMACION[6] = "";
                                                    }
                                                } catch (NullPointerException | NumberFormatException error) {
                                                    if (INFORMACION[6] != null) {
                                                        if (!INFORMACION[6].equals("")) {
                                                            INFORMACION[6] = "";
                                                        } else {
                                                            INFORMACION[6] = Float.toString((float) 0.0);
                                                        }
                                                    }
                                                }
                                                seguimiento = validarDato("la estatura", INFORMACION[6], MOTIVO, true);
                                            } while (seguimiento == 1);
                                            if (seguimiento != -1) {
                                                control.setInformacion(posicion, INFORMACION, true);
                                                JOptionPane.showMessageDialog(null, "El empleado con clave " + INFORMACION[0] + " ha sido registrado.", "Registro del empleado realizado", JOptionPane.INFORMATION_MESSAGE);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else if (seguimiento != 1) {
                        JOptionPane.showMessageDialog(null, "Se ha producido un error al procesar la clave.\nLa clave no es válida, el dato ya pertenece a un empleado registrado.", "Imposibilidad para registrar al " + (posicion + 1) + "° empleado", JOptionPane.ERROR_MESSAGE);
                        INFORMACION[0] = "";
                        seguimiento = 1;
                    }
                }
            } while (INFORMACION[0] != null && seguimiento == 1);
        } else {
            JOptionPane.showMessageDialog(null, "Es imposible registrar otro empleado en el sistema.\nNo hay almacenamiento disponible para crear un nuevo registro.", "No hay espacio de almacenamiento disponible", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void consultarTrabajadores() {
        int seleccion, posicion;
        String[] informacion;
        do {
            seleccion = JOptionPane.showOptionDialog(null, "Consulta de los registros de empleados.\n\nSeleccione una opción para comenzar la consulta. ¿Qué es lo que quiere visualizar en este momento?", "Consulta de los empleados", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[]{"Un empleado en particular", "Los registros cronológicamente", "Regresar"}, null);
            switch (seleccion) {
                case 0:
                    posicion = validarClaveEmpleado(new String[]{"consultar", "Visualización"});
                    if (posicion >= 0) {
                        informacion = control.getInformacion(posicion);
                        JOptionPane.showMessageDialog(null, "Información del empleado " + informacion[0] + ".\n\nNombre completo: " + informacion[1] + ' ' + informacion[2] + ' ' + informacion[3] + '.' + "\nSexo: " + (informacion[4].equals("M") ? "Masculino." : "Femenino.") + "\nEdad: " + (Integer.parseInt(control.getTiempo(0)) - Integer.parseInt(informacion[5])) + " años." + (informacion[6].equals("0.0") ? "" : "\nEstatura: " + informacion[6] + " metros."), "Información del empleado", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
                case 1:
                    for (posicion = 0; posicion < (control.getPosicionRegistro(0, null, 0, control.getCantidadEmpleados(true) - 1, false) != -1 ? control.getPosicionRegistro(0, null, 0, control.getCantidadEmpleados(true) - 1, false) : control.getCantidadEmpleados(true));) {
                        informacion = control.getInformacion(posicion);
                        posicion++;
                        if (JOptionPane.showConfirmDialog(null, "Información del empleado " + informacion[0] + ".\n\nNombre completo: " + informacion[1] + ' ' + informacion[2] + ' ' + informacion[3] + '.' + "\nSexo: " + (informacion[4].equals("M") ? "Masculino." : "Femenino.") + "\nEdad: " + (Integer.parseInt(control.getTiempo(0)) - Integer.parseInt(informacion[5])) + " años." + (informacion[6].equals("0.0") ? "" : "\nEstatura: " + informacion[6] + " metros.") + "\n\n¿Desea visualizar otro registro de usuario?", "Información del empleado", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE) == JOptionPane.NO_OPTION) {
                            break;
                        }
                    }
                    if (posicion == 0) {
                        JOptionPane.showMessageDialog(null, "Aún no existe ningún registro de empleado en el sistema.", "Visualización de la información de usuarios finalizada", JOptionPane.INFORMATION_MESSAGE);
                    } else if (posicion == (control.getPosicionRegistro(0, null, 0, control.getCantidadEmpleados(true) - 1, false) != -1 ? control.getPosicionRegistro(0, null, 0, control.getCantidadEmpleados(true) - 1, false) : control.getCantidadEmpleados(true))) {
                        JOptionPane.showMessageDialog(null, "Esos han sido todos los registros de empleados en el sistema.", "Visualización de la información de usuarios finalizada", JOptionPane.INFORMATION_MESSAGE);
                    }
            }
        } while (seleccion != -1 && seleccion != 2);
    }

    private static void modificarTrabajador() {
        int seguimiento, seleccion;
        String datoEmpleado;
        String[] informacion;
        final int POSICION = validarClaveEmpleado(new String[]{"modificar", "Modificación"});
        final String[] MOTIVO = {"La actualización", "actualizado", "a"};
        if (POSICION >= 0) {
            do {
                informacion = control.getInformacion(POSICION);
                seleccion = JOptionPane.showOptionDialog(null, "Información del empleado " + informacion[0] + ".\n\nNombre completo: " + informacion[1] + ' ' + informacion[2] + ' ' + informacion[3] + '.' + "\nSexo: " + (informacion[4].equals("M") ? "Masculino." : "Femenino.") + "\nEdad: " + (Integer.parseInt(control.getTiempo(0)) - Integer.parseInt(informacion[5])) + " años." + (informacion[6].equals("0.0") ? "" : "\nEstatura: " + informacion[6] + " metros.") + "\n\nSeleccione una de las opciones de modificación. ¿Qué es lo que desea actualizar del registro?", "Modificación de la información del empleado", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Clave", "Nombre", "Sexo", "Año de nacimiento", "Estatura", "Regresar"}, null);
                switch (seleccion) {
                    case 0:
                        datoEmpleado = JOptionPane.showInputDialog("Introduzca una clave alfanumérica para identificar al " + (POSICION + 1) + "° trabajador:", informacion[0]);
                        if (datoEmpleado != null) {
                            datoEmpleado = datoEmpleado.trim().toUpperCase();
                            seguimiento = validarDato("la clave", datoEmpleado, MOTIVO, true);
                            if (seguimiento == 0 && control.getPosicionRegistro(0, datoEmpleado, 0, POSICION - 1, true) == -1 && control.getPosicionRegistro(0, datoEmpleado, POSICION + 1, control.getPosicionRegistro(0, null, 0, control.getCantidadEmpleados(true) - 1, false) != -1 ? control.getPosicionRegistro(0, null, 0, control.getCantidadEmpleados(true) - 1, false) : control.getCantidadEmpleados(true) - 1, true) == -1) {
                                if (JOptionPane.showConfirmDialog(null, "¿Está seguro de continuar con esta acción?", "Actualización de la clave del usuario", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
                                    control.setDato(POSICION, 0, datoEmpleado);
                                    JOptionPane.showMessageDialog(null, "La clave del empleado ha sido actualizada exitosamente.\nA partir de ahora, utilice \"" + datoEmpleado + "\" para identificarlo -la.", "Actualización de la clave del empleado realizada", JOptionPane.INFORMATION_MESSAGE);
                                }
                            } else if (seguimiento != 1) {
                                JOptionPane.showMessageDialog(null, "Se ha producido un error al actualizar la clave.\nLa clave no es válida, el dato ya pertenece a un empleado registrado.", "Imposibilidad para actualizar la clave del empleado", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        break;
                    case 1:
                        do {
                            datoEmpleado = JOptionPane.showInputDialog("¿Cuál es el nombre del empleado?", informacion[1]);
                            seguimiento = validarDato("el nombre", datoEmpleado, MOTIVO, true);
                        } while (seguimiento == 1);
                        if (seguimiento != -1) {
                            control.setDato(POSICION, 1, datoEmpleado);
                            do {
                                datoEmpleado = JOptionPane.showInputDialog("¿Cuál es el apellido paterno del empleado?", informacion[2]);
                                seguimiento = validarDato("el apellido paterno", datoEmpleado, MOTIVO, true);
                            } while (seguimiento == 1);
                            if (seguimiento != -1) {
                                control.setDato(POSICION, 2, datoEmpleado);
                                do {
                                    datoEmpleado = JOptionPane.showInputDialog("¿Cuál es el apellido materno del empleado?", informacion[3]);
                                    seguimiento = validarDato("el apellido materno", datoEmpleado, MOTIVO, false);
                                } while (seguimiento == 1);
                                if (seguimiento != -1) {
                                    control.setDato(POSICION, 3, datoEmpleado);
                                }
                            }
                        }
                        break;
                    case 2:
                        do {
                            try {
                                datoEmpleado = Character.toString(JOptionPane.showInputDialog(null, "Introduzca el sexo del empleado:", "Actualización del sexo del empleado " + informacion[0], JOptionPane.QUESTION_MESSAGE, null, new String[]{"Masculino", "Femenino"}, informacion[4].equals("M") ? "Masculino" : "Femenino").toString().charAt(0));
                            } catch (NullPointerException error) {
                                datoEmpleado = null;
                            }
                            seguimiento = validarDato("el sexo", datoEmpleado, MOTIVO, false);
                        } while (seguimiento == 1);
                        if (seguimiento != -1) {
                            control.setDato(POSICION, 4, datoEmpleado);
                        }
                        break;
                    case 3:
                        do {
                            datoEmpleado = JOptionPane.showInputDialog("Ingrese el año de nacimiento del empleado:", informacion[5]);
                            try {
                                datoEmpleado = Integer.toString(Integer.parseInt(datoEmpleado));
                                if (Integer.parseInt(datoEmpleado) < Integer.parseInt(control.getTiempo(0)) - 150 || Integer.parseInt(datoEmpleado) > Integer.parseInt(control.getTiempo(0)) - 18) {
                                    datoEmpleado = "";
                                }
                            } catch (NullPointerException | NumberFormatException error) {
                                if (datoEmpleado != null) {
                                    datoEmpleado = "";
                                }
                            }
                            seguimiento = validarDato("el año de nacimiento", datoEmpleado, MOTIVO, true);
                        } while (seguimiento == 1);
                        if (seguimiento != -1) {
                            control.setDato(POSICION, 5, datoEmpleado);
                        }
                        break;
                    case 4:
                        do {
                            datoEmpleado = JOptionPane.showInputDialog("Ingrese la altura en metros del empleado:", informacion[6]);
                            try {
                                datoEmpleado = Float.toString(Float.parseFloat(datoEmpleado));
                                if (Float.parseFloat(datoEmpleado) <= 0) {
                                    datoEmpleado = "";
                                }
                            } catch (NullPointerException | NumberFormatException error) {
                                if (datoEmpleado != null) {
                                    if (!datoEmpleado.equals("")) {
                                        datoEmpleado = "";
                                    } else {
                                        datoEmpleado = Float.toString((float) 0.0);
                                    }
                                }
                            }
                            seguimiento = validarDato("la estatura", datoEmpleado, MOTIVO, true);
                        } while (seguimiento == 1);
                        if (seguimiento != -1) {
                            control.setDato(POSICION, 6, datoEmpleado);
                        }
                }
            } while (seleccion != -1 && seleccion != 5);
        }
    }

    private static void suprimirTrabajador() {
        final int POSICION = validarClaveEmpleado(new String[]{"eliminar", "Eliminación"});
        final String[] INFORMACION;
        if (POSICION >= 0) {
            INFORMACION = control.getInformacion(POSICION);
            if (JOptionPane.showConfirmDialog(null, "Información del empleado " + INFORMACION[0] + ".\n\nNombre completo: " + INFORMACION[1] + ' ' + INFORMACION[2] + ' ' + INFORMACION[3] + '.' + "\nSexo: " + (INFORMACION[4].equals("M") ? "Masculino." : "Femenino.") + "\nEdad: " + (Integer.parseInt(control.getTiempo(0)) - Integer.parseInt(INFORMACION[5])) + " años." + (INFORMACION[6].equals("0.0") ? "" : "\nEstatura: " + INFORMACION[6] + " metros.") + "\n\n¿Está seguro de eliminar este registro del sistema?", "Eliminación del empleado", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
                control.recorrerPosicionRegistros(POSICION);
                JOptionPane.showMessageDialog(null, "El empleado con clave " + INFORMACION[0] + " ha sido eliminado.", "Eliminación del empleado realizada", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
