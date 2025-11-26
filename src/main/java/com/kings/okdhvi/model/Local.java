package com.kings.okdhvi.model;

public enum Local {
    NENHUM(0, "Nenhum"),
    APIUNA(1, "Apiúna"),
    ASCURRA(2, "Ascurra"),
    BENEDITO_NOVO(3, "Benedito Novo"),
    BLUMENAU(4, "Blumenau"),
    BOTUVERA(5, "Botuverá"),
    BRASIL(6, "Brasil"),
    BRUSQUE(7, "Brusque"),
    DOUTOR_PEDRINHO(8, "Doutor Pedrinho"),
    GASPAR(9, "Gaspar"),
    ILHOTA(10, "Ilhota"),
    INDAIAL(11, "Indaial"),
    LUIZ_ALVES(12, "Luiz Alves"),
    POMERODE(13, "Pomerode"),
    RODEIO(14, "Rodeio"),
    RIO_DOS_CEDROS(15, "Rio dos Cedros"),
    SANTA_CATARINA(16, "Santa Catarina"),
    TIMBO(17, "Timbó"),
    GERAL(18,"Geral");

    private final int numero;
    private final String nomeReal;

    Local(int numero, String nomeReal) {
        this.numero = numero;
        this.nomeReal = nomeReal;
    }

    public int getNumero() {
        return numero;
    }

    public String getNomeReal() {
        return nomeReal;
    }
}