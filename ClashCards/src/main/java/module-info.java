module pc {
    requires javafx.controls;
    exports pc;
    // 2. CORREÇÃO CRÍTICA: Permite que o TableView (javafx.base) acesse os getters
    // da classe Carta (no pacote classes). ISSO RESOLVE O IllegalAccessException.
    opens classes to javafx.base;
}
