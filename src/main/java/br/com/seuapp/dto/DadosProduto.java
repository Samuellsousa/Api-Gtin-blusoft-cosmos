package br.com.seuapp.dto;

public class DadosProduto {
    private String gtin;  // Alterando de ean para gtin
    private String cest;
    private String ncm;

    // Construtor padrão para deserialização
    public DadosProduto() {
    }

    public DadosProduto(String gtin, String cest, String ncm) {
        this.gtin = gtin;  // Alterado para gtin
        this.cest = cest;
        this.ncm = ncm;
    }

    public String getGtin() { return gtin; }  // Alterado para gtin
    public String getCest() { return cest; }
    public String getNcm() { return ncm; }

    public void setGtin(String gtin) { this.gtin = gtin; }  // Alterado para gtin
    public void setCest(String cest) { this.cest = cest; }
    public void setNcm(String ncm) { this.ncm = ncm; }
}
