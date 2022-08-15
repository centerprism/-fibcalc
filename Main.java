package fibbonaci;

/**
 * @author Luis Cunha
 * @date Junho de 2008
 */
public class Main {

    static String name_of_aplication = "fibcalc";
    static String syntax_help = "Usage: " + name_of_aplication
            + " [-f first_seed second_seed] [-d] number_of_calculations";
    static String OOBounds = "Maximum double precision value reached";
    static String calcnbr = "Number with order ";
    static String seedstxt = " using seeds ";
    static String nbrlist = "\nNumbers list:"; // cabeçalho da lista
    /*
     * int a é o primeiro valor a somar
     * int b é o segundo
     * caso sejam zero serao 0 e 1
     * fibnbr é o numero de calculos que desejamos
     * o array output contém os números de fibonacci
     */

    public static boolean fibonacci(double a, double b, double fibnbr, double[] output) {
        double result = 0, bigger, smaller;
        boolean inrange = true;
        if (a < b) {
            bigger = b;
            smaller = a;
        } else {
            bigger = a;
            smaller = b;
        }
        for (int i = 1; i < fibnbr - 1; i++) {
            result = smaller + bigger;
            output[i + 1] = result;  // escrita no array
            if (result > 1.3069892237633987E308) { // teste ao maximo double
                inrange = false;
                break;
            }
            smaller = bigger;
            bigger = result;
        }
        return inrange;
    }

    public static class arguments {

        boolean[] valid = new boolean[5]; // Array de validade dos argumentos
        boolean fswitch = false; // Teste a argumento duplicado '-f'
        boolean dswitch = false; // Teste a argumento duplicad '-d'
        boolean teststrf = false; // Teste ao caracteres do agumento -f
        boolean teststrd = false; // Teste ao caracteres do agumento -d
        boolean testdigit = false; // Teste a valor numéric do argumento
        int numitems = -1; // Número de argumentos introduzidos pelo utilizador
        String[] argtxt = {"", "", "", "", ""}; // Array dos argumentos
        int[] type = {-1, -1, -1, -1, -1};  /* Tipo de argumento:
        0-valor numérico
        1-Switch '-f'
        2-Primeiro valor do switch '-f'
        3-Segundo valor do switch '-f'
        4-Switch '-d'  */

        int fvalue; // posição do switch '-f' no array de argumentos
        int nvalue; // posição do número de cálculos no array de argumentos

        public boolean parseargs(String[] argarray) {
            // função de tratamento dos argumentos introduzidos pelo utilizador
            boolean arguments_valid = false; // Inicializar o return a false
            numitems = argarray.length; // Actualizar o número de argumentos
            if (numitems > 5) { // Teste ao máximo número de argumentos
                return false;
            } else { // Tem 5 ou menos argumentos
                for (int i = 0; i < numitems; i++) {
                    teststrf = ((argarray[i].length() == 2) // teste a argumento '-f'
                            && (argarray[i].charAt(0) == '-')
                            && (argarray[i].charAt(1) == 'f')) ? true : false;
                    teststrd = ((argarray[i].length() == 2) // teste a argumento '-d'
                            && (argarray[i].charAt(0) == '-')
                            && (argarray[i].charAt(1) == 'd')) ? true : false;
                    for (char digito : argarray[i].toCharArray()) { // teste a argumento numérico
                        testdigit = (Character.isDigit(digito)) ? true : false;
                        if (!testdigit) {
                            break;
                        }
                    }
                    if (testdigit) {
                        type[i] = 0;
                        valid[i] = true;
                    } else if (teststrd && !dswitch) {
                        type[i] = 4;                // tipo de argumento
                        valid[i] = true;            // válido
                        dswitch = true;             // teste a switch '-d' duplicado
                    } else if (teststrf && !fswitch) {
                        type[i] = 1;                // tipo de argumento
                        valid[i] = true;            // válido
                        fswitch = true;             // teste a switch '-f' duplicado
                        fvalue = i;                 // posição no switch '-f' no array de argumentos
                    }
                }
                if (type[0] == 0 && numitems == 1) { // Cálculos sem sementes
                    arguments_valid = true;
                } else if ((numitems == 2 && (type[0] == 0 && type[1] == 4))
                        || (numitems == 2 && (type[0] == 4 && type[1] == 0))) { // dswitch - apenas dois argumentos
                    nvalue = type[0] == 0 ? 0 : 1; // posição no número de cálculos
                    arguments_valid = true;
                } else if (numitems == 4 && fvalue == 0 && type[1] == 0 && type[2] == 0 && type[3] == 0) { // fswitch antes do número de cálculos
                    type[1] = 2;    // primeira semente
                    type[2] = 3;    // segunda semente
                    nvalue = 3;     // posição no número de cálculos
                    arguments_valid = true;
                } else if (numitems == 4 && fvalue == 1 && type[2] == 0 && type[3] == 0 && type[0] == 0) { // fswitch depois do número de cálculos
                    type[2] = 2;    // primeira semente
                    type[3] = 3;    // segunda semente
                    nvalue = 0;     // posição no número de cálculos
                    arguments_valid = true;
                } else if ((numitems == 5 && fvalue == 0 && type[1] == 0 && type[2] == 0) // fswitch dswitch
                        && ((type[3] == 0 && type[4] == 4) || (type[3] == 4 && type[4] == 0))) {
                    type[1] = 2;    // primeira semente
                    type[2] = 3;    // segunda semente
                    nvalue = type[3] == 0 ? 3 : 4;     // posição no número de cálculos
                    arguments_valid = true;
                } else if ((numitems == 5 && fvalue == 1 && type[2] == 0 && type[3] == 0)
                        && ((type[0] == 0 && type[4] == 4) || (type[0] == 4 && type[4] == 0))) {
                    type[2] = 2;    // primeira semente
                    type[3] = 3;    // segunda semente
                    nvalue = type[0] == 0 ? 0 : 4;     // posição no número de cálculos
                    arguments_valid = true;
                } else if ((numitems == 5 && fvalue == 2 && type[3] == 0 && type[4] == 0)
                        && ((type[0] == 0 && type[1] == 4) || (type[0] == 4 && type[1] == 0))) {
                    type[2] = 2;    // primeira semente
                    type[3] = 3;    // segunda semente
                    nvalue = type[0] == 0 ? 0 : 1;     // posição no número de cálculos
                    arguments_valid = true;
                } else {
                    arguments_valid = false;
                }
                return arguments_valid;  // a função só retorna true em qq um destes casos anteriores
            }
        }
    }

    public static void main(String[] args) {

        arguments myarguments = new arguments();
        // @param args the command line arguments
        if (myarguments.parseargs(args)) {  // chamada da função de tratamento dos argumentos
            boolean fswitch = myarguments.fswitch;  // existência de switch '-f'
            boolean dswitch = myarguments.dswitch; // existência de switch '-d'
            int nvalue = Integer.parseInt(args[myarguments.nvalue]);  // número de cálculos
            if (fswitch && dswitch) { // fswitch e dswitch
                int fvalue = myarguments.fvalue; // posição do switch 'f'
                double fseed = Double.parseDouble(args[fvalue + 1]); // primeira semente
                double sseed = Double.parseDouble(args[fvalue + 2]); // segunda semente
                if (nvalue == 1) { // o utilizador pediu apenas o primeiro valor da lista
                    System.out.println(calcnbr + "1" + ": " + seedstxt + fseed + ", " + sseed + ": " + fseed); // output
                } else {
                    double[] value_list = new double[nvalue]; // lista de valores a introduzir na função fibonacci
                    value_list[0] = fseed; // primeira semente
                    value_list[1] = sseed; // segunda semente
                    if (fibonacci(fseed, sseed, nvalue, value_list)) { // chamada da função fibonacci
                        System.out.println(nbrlist); // cabeçalho da lista
                        for (int a = 0; a < nvalue; a++) { // ciclo de impressão da lista de valores
                            System.out.print(value_list[a] + " "); // output
                        }
                        System.out.println("\n");
                        System.out.println(calcnbr + nvalue + seedstxt + fseed
                                + ", " + sseed + ": " + value_list[nvalue - 1]);  // Output do ultimo valor
                    } else {
                        System.out.println(OOBounds); // a função fibonacci retornou erro de máximo valor atingido
                    }
                }
            } else if (!fswitch && dswitch) { // Apenas dswitch
                if (nvalue == 1) { // o utilizador pediu apenas o primeiro valor da lista
                    System.out.println(calcnbr + "1" + ": " + "0.0"); // output
                } else {
                    double[] value_list = new double[nvalue]; // lista de valores a introduzir na função fibonacci
                    value_list[0] = 0; // primeira semente
                    value_list[1] = 1; // segunda semente
                    if (fibonacci(0, 1, nvalue, value_list)) { // chamada da função fibonacci
                        System.out.println(nbrlist);  // cabeçalho da lista
                        for (int a = 0; a < value_list.length; a++) {  // ciclo de impressão da lista de valores
                            System.out.print(value_list[a] + " "); // output
                        }
                        System.out.println("\n");
                        System.out.print(calcnbr + (value_list.length) + ": "
                                + value_list[value_list.length - 1] + " ");  // Output do ultimo valor
                    } else {
                        System.out.println(OOBounds); // a função fibonacci retornou erro de máximo valor atingido
                    }
                }
            } else if (fswitch && !dswitch) { // Apenas fswitch
                int fvalue = myarguments.fvalue;  // posição do switch 'f'
                double fseed = Double.parseDouble(args[fvalue + 1]); // primeira semente
                double sseed = Double.parseDouble(args[fvalue + 2]); // segunda semente
                double[] value_list = new double[nvalue]; // lista de valores a introduzir na função fibonacci
                if (nvalue == 1) { // o utilizador pediu o primeiro número
                    System.out.println(calcnbr + nvalue + seedstxt + fseed + ", " + sseed + ": " + fseed); // output
                } else {
                    value_list[0] = fseed; // primeira semente
                    value_list[1] = sseed; // segunda semente
                    if (fibonacci(fseed, sseed, nvalue, value_list)) { //cálculo do ultimo valor
                        System.out.print(calcnbr + (value_list.length) + seedstxt + fseed + ", " + sseed + ": " + value_list[nvalue - 1] + " "); // output
                    } else {
                        System.out.println(OOBounds); // a função fibonacci retornou erro de máximo valor atingido
                    }
                }
            } else if (!fswitch && !dswitch) { // Sem Switchs
                if (nvalue == 1) { // o utilizador pediu o primeiro número
                    System.out.println(calcnbr + "1" + seedstxt + "0.0" + ", " + "1.0" + ": " + Double.parseDouble(args[0])); // output
                } else { 
                    double[] value_list = new double[nvalue]; // lista de valores a introduzir na função fibonacci
                    value_list[0] = 0; // primeira semente
                    value_list[1] = 1; // segunda semente
                    if (fibonacci(0, 1, nvalue, value_list)) { //cálculo do ultimo valor
                        System.out.print(calcnbr + (value_list.length) + seedstxt + "0.0" + 
                                ", " + "1.0" + ": " + value_list[value_list.length - 1] + " "); //output
                    } else {
                        System.out.println(OOBounds); // a função fibonacci retornou erro de máximo valor atingido
                    }
                }
            }
        } else {
            System.out.println(syntax_help);  // o objecto myarguments retornou falso na função parseargs
        }
    }
}
