public class practicaMatrices {

    private static final char lineaVertical = '║';
    private static final char lineaHorizontal = '═';
    private static final char esquinaSupIzq = '╔';
    private static final char esquinaInfIzq = '╚';
    private static final char esquinaSupDer = '╗';
    private static final char esquinaInfDer = '╝';
    private static final char separadorSup = '╦';
    private static final char separadorInf = '╩';
    private static final char separadorIzq = '╠';
    private static final char separadorDer = '╣';
    private static final char separadorCruz = '╬';
    private static final int bordeSup = 0;
    private static final int bordeInf = 1;

    public static void main(String[] args) {
        String texto = "Proyecto creado por AeroAlvaro";
        int[] nums = {1, 24, 2124, -124, 6223, 6};
        int[][] matriz = {{125, 1254, 124, 125, -1, -12512}, {1, -124124, 12464237, 123}, {12, 1251115, 136, 13, 1}};

        mostrarTextoConBordes(texto);
        mostrarArrayConBordes(nums);
        mostrarMatrizConBordes(matriz);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    // MÉTODOS PÚBLICOS
    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Imprime por pantalla un texto con bordes
     *
     * @param texto - Texto a imprimir
     */
    public static void mostrarTextoConBordes(String texto) {

        // obtenemos un array de Strings
        String[] lineas = texto.split("\n");

        // obtenemos la longitud de la línea más larga
        int ancho = obtenerAnchoMaximo(lineas);

        // imprimimos el texto con los bordes
        imprimirLetrasBordes(lineas, ancho);
    }

    /**
     * Imprime por pantalla un Array de enteros con bordes
     *
     * @param array - Array a imprimir
     */
    public static void mostrarArrayConBordes(int[] array) {
        System.out.println(crearStringArrayConBordes(array));
    }

    /**
     * Imprime por pantalla una matriz de enteros con bordes
     *
     * @param matriz - Matriz a imprimir
     */
    public static void mostrarMatrizConBordes(int[][] matriz) {
        System.out.println(crearStringMatrizConBordes(matriz));
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static String crearStringArrayConBordes(int[] array) {

        String arrayConBordes = "";

        if (array.length > 0) {
            arrayConBordes += obtenerBordeSuperiorArray(array) + "\n";
            arrayConBordes += obtenerStringEnteros(array) + "\n";
            arrayConBordes += obtenerBordeInferiorArray(array) + "\n";
        }

        return arrayConBordes;
    }

    private static String crearStringMatrizConBordes(int[][] matriz) {

        String matrizConBordes = "";

        if (matriz.length > 0) {
            matrizConBordes += obtenerBordeSuperiorArray(matriz[0]) + "\n";
            matrizConBordes += obtenerParteCentralMatriz(matriz);
            matrizConBordes += obtenerBordeInferiorArray(matriz[matriz.length - 1]) + "\n";
        }

        return matrizConBordes;
    }

    private static String obtenerParteCentralMatriz(int[][] matriz) {

        String parteCentralMatriz = "";

        // recorremos cada fila de la matriz
        for (int i = 0; i < matriz.length; i++) {

            // añadir una String con los enteros de la fila actual
            parteCentralMatriz += obtenerStringEnteros(matriz[i]) + "\n";

            // si no es la última fila, obtenemos el borde intermedio
            if (i < matriz.length - 1) {

                // añadir la String con el borde intermedio
                parteCentralMatriz += obtenerBordeIntermedio(matriz[i], matriz[i + 1]) + "\n";
            }
        }
        return parteCentralMatriz;
    }

    private static String obtenerBordeIntermedio(int[] filaActual, int[] filaInferior) {

        // array con los separadores de la fila actual
        int[] posicionesSeparadoresInferiores = posicionarSeparadores(filaActual);
        int anchoFilaActual = obtenerAnchoFila(posicionesSeparadoresInferiores);

        // array con los separadores de la fila inferior
        int[] posicionesSeparadoresSuperiores = posicionarSeparadores(filaInferior);
        int anchoFilaInferior = obtenerAnchoFila(posicionesSeparadoresSuperiores);

        // creamos un array de char para guardar cada uno de los caracteres
        int anchoBorde = anchoFilaActual > anchoFilaInferior ? anchoFilaActual : anchoFilaInferior;
        char[] bordeIntermedio = new char[anchoBorde];

        // establecemos las líneas horizontales y los separadores
        bordeIntermedio = establecerHorizontalesConSeparadores(bordeIntermedio, posicionesSeparadoresInferiores,
                posicionesSeparadoresSuperiores);

        // establecemos las esquinas
        bordeIntermedio = establecerEsquinas(bordeIntermedio, anchoFilaActual, anchoFilaInferior);

        // generar String a partir del array
        String bordeIntermedioFinal = "";
        for (char c : bordeIntermedio) {
            bordeIntermedioFinal += c;
        }
        return bordeIntermedioFinal;
    }

    private static char[] establecerEsquinas(char[] bordeIntermedio, int anchoFilaActual, int anchoFilaInferior) {

        // casos con arrays sin elementos
        if (anchoFilaActual == 1 || anchoFilaInferior == 1) {

            // si ambos tienen un array sin elementos
            if (anchoFilaActual == 1 && anchoFilaInferior == 1) {
                bordeIntermedio = new char[2];
                bordeIntermedio[0] = separadorIzq;
                bordeIntermedio[1] = separadorDer;
            }
            // si la fila de arriba tiene elementos y la de bajo no
            else if (anchoFilaActual > 1 && anchoFilaInferior == 1) {
                bordeIntermedio[1] = separadorSup;
                bordeIntermedio[bordeIntermedio.length - 1] = esquinaInfDer;
            }
            // si la fila de bajo tiene elementos y la de arriba no
            else if (anchoFilaActual == 1 && anchoFilaInferior > 1) {
                bordeIntermedio[1] = separadorInf;
                bordeIntermedio[bordeIntermedio.length - 1] = esquinaSupDer;
            }
        }
        // casos con arrays con elementos
        else if (anchoFilaActual > anchoFilaInferior)
            bordeIntermedio[bordeIntermedio.length - 1] = esquinaInfDer;
        else if (anchoFilaActual < anchoFilaInferior)
            bordeIntermedio[bordeIntermedio.length - 1] = esquinaSupDer;
        else
            bordeIntermedio[bordeIntermedio.length - 1] = separadorDer;

        return bordeIntermedio;
    }

    private static char[] establecerHorizontalesConSeparadores(char[] bordeIntermedio,
                                                               int[] posicionesSeparadoresInferiores, int[] posicionesSeparadoresSuperiores) {

        // ponemos el separador izquierdo
        bordeIntermedio[0] = separadorIzq;

        // separadores inferiores
        bordeIntermedio = establecerSeparadoresInferiores(bordeIntermedio, posicionesSeparadoresInferiores);

        // separadores superiores
        bordeIntermedio = establecerSeparadoresSuperiores(bordeIntermedio, posicionesSeparadoresSuperiores);

        return bordeIntermedio;
    }

    private static char[] establecerSeparadoresInferiores(char[] bordeIntermedio,
                                                          int[] posicionesSeparadoresInferiores) {

        int contadorPosicionBorde = 1; // empezamos en 1, la pos 0 es del separadorIzq
        for (int i = 0; i < posicionesSeparadoresInferiores.length; i++) {

            // ponemos líneas horizontales
            for (int j = 0; j < posicionesSeparadoresInferiores[i]; j++) {
                bordeIntermedio[contadorPosicionBorde] = lineaHorizontal;
                contadorPosicionBorde++;
            }
            // ponemos el separador inferior
            bordeIntermedio[contadorPosicionBorde] = separadorInf;
            contadorPosicionBorde++;
        }
        return bordeIntermedio;
    }

    private static char[] establecerSeparadoresSuperiores(char[] bordeIntermedio,
                                                          int[] posicionesSeparadoresSuperiores) {

        int contadorPosicionBorde = 1; // empezamos en 1, la pos 0 es del separadorIzq
        for (int i = 0; i < posicionesSeparadoresSuperiores.length; i++) {

            // ya están puestas las líneas horizontales, pero pueden faltar
            for (int j = 0; j < posicionesSeparadoresSuperiores[i]; j++) {

                if (bordeIntermedio[contadorPosicionBorde] == '\u0000') // si no hay nada, añadimos horizontal
                    bordeIntermedio[contadorPosicionBorde] = lineaHorizontal;
                contadorPosicionBorde++;
            }
            // si tenemo separador en la misma posición, ponemos la cruz
            if (bordeIntermedio[contadorPosicionBorde] == separadorInf)
                bordeIntermedio[contadorPosicionBorde] = separadorCruz;
                // de lo contrario, ponemos separador sup únicamente
            else
                bordeIntermedio[contadorPosicionBorde] = separadorSup;
            contadorPosicionBorde++;
        }
        return bordeIntermedio;
    }

    private static int[] posicionarSeparadores(int[] fila) {

        // colocamos un índice en la posición de cada separador
        int[] posicionesSeparadores = new int[fila.length];
        for (int i = 0; i < posicionesSeparadores.length; i++) {
            posicionesSeparadores[i] = digitos(Math.abs(fila[i])) + 2; // +2 por los espacios en blanco
            if (fila[i] < 0) // si es negativo, añadimos un espacio
                posicionesSeparadores[i]++;
        }
        return posicionesSeparadores;
    }

    private static int obtenerAnchoFila(int[] posicionesSeparadores) {

        int anchoFila = 1; // carácter inicial, separador vertical
        for (int i = 0; i < posicionesSeparadores.length; i++) {
            anchoFila += posicionesSeparadores[i] + 1; // +1 por añadir el separador
        }
        return anchoFila;
    }

    private static String obtenerStringEnteros(int[] array) {

        String enterosFila = "";

        // imprimimos los enteros
        enterosFila += lineaVertical;
        for (int i = 0; i < array.length; i++) {
            enterosFila += " " + array[i] + " " + lineaVertical;
        }

        // si tenemos un array sin elementos
        if (array.length == 0)
            enterosFila += lineaVertical;

        return enterosFila;
    }

    private static String obtenerBordeSuperiorArray(int[] array) {
        return obtenerBordeArray(array, bordeSup);
    }

    private static String obtenerBordeInferiorArray(int[] array) {
        return obtenerBordeArray(array, bordeInf);
    }

    private static String obtenerBordeArray(int[] array, int tipoBorde) {

        String borde = "";

        // imprimimos la esquina suprior izquierda
        borde += tipoBorde == bordeSup ? esquinaSupIzq : esquinaInfIzq;

        // por cada entero del Array
        for (int i = 0; i < array.length; i++) {

            // imprimimos las líneas horizontales
            int lineas = digitos(Math.abs(array[i])) + 2;
            if (array[i] < 0)
                lineas++;
            for (int j = 0; j < lineas; j++)
                borde += lineaHorizontal;

            // imprimimos el separador
            if (i < array.length - 1)
                borde += tipoBorde == bordeSup ? separadorSup : separadorInf;
        }

        // imprimimos la esquina suprior derecha
        borde += tipoBorde == bordeSup ? esquinaSupDer : esquinaInfDer;

        return borde;
    }

    private static void imprimirLetrasBordes(String[] lineas, int ancho) {

        // borde superior
        imprimirBordeSuperior(ancho);

        // líneas centrales
        imprimirLineas(lineas, ancho);

        // borde inferior
        imprimirBordeInferior(ancho);

    }

    private static void imprimirBordeSuperior(int ancho) {
        imprimirBorde(bordeSup, ancho);
    }

    private static void imprimirBordeInferior(int ancho) {
        imprimirBorde(bordeInf, ancho);
    }

    private static int obtenerAnchoMaximo(String[] lineas) {
        int anchoMax = 0;
        for (String lineaActual : lineas) {
            if (lineaActual.length() > anchoMax)
                anchoMax = lineaActual.length();
        }
        return anchoMax;
    }

    private static void imprimirLineas(String[] lineas, int lineaMax) {
        for (int i = 0; i < lineas.length; i++) {
            System.out.print(lineaVertical + "  ");
            for (int j = 0; j < lineaMax; j++) {
                if (j < lineas[i].length())
                    System.out.print(lineas[i].charAt(j) + "");
                else
                    System.out.print(" ");
            }
            System.out.println(" " + lineaVertical);
        }
    }

    private static void imprimirBorde(int tipoBorde, int ancho) {

        System.out.print(tipoBorde == bordeSup ? esquinaSupIzq : esquinaInfIzq);
        for (int i = 0; i < ancho + 3; i++)
            System.out.print(lineaHorizontal);
        System.out.println(tipoBorde == bordeSup ? esquinaSupDer : esquinaInfDer);

    }

    private static int digitos(int n) {

        if (n < 10)
            return 1;
        return 1 + digitos(n / 10);
    }

}