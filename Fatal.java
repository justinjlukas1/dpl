public class Fatal {
    public Fatal() {
    }

    public static void FATAL(String var0, Integer var1) {
        System.out.println("Fatal error on line " + var1 + ":");
        System.out.println("\t" + var0);
        System.exit(1);
    }
}
