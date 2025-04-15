package br.com.seuapp.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "pautaUpdate")
public class PautaUpdate {

    @Id
    @Column(name = "VEP_ID")
    private Integer id;

    @Column(name = "VEP_DESCRICAO")
    private String descricao;

    @Column(name = "VEP_OBSERVACAO")
    private String observacao;

    @Column(name = "VEP_UNIDADE")
    private String unidade;

    @Column(name = "VEP_PRECO_PAUTA")
    private BigDecimal precoPauta;

    @Column(name = "VEP_INICIO_VIGENCIA")
    private LocalDate inicioVigencia;

    @Column(name = "VEP_FINAL_VIGENCIA")
    private LocalDate finalVigencia;

    // Alterando a coluna para refletir o nome correto
    @Column(name = "VEP_GTIN")
    private String gtin;  // Renomeado de 'ean' para 'gtin'

    @Column(name = "VEP_CEST")
    private String cest;

    @Column(name = "VEP_NCM")
    private String ncm;

    // Getters e setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public BigDecimal getPrecoPauta() {
        return precoPauta;
    }

    public void setPrecoPauta(BigDecimal precoPauta) {
        this.precoPauta = precoPauta;
    }

    public LocalDate getInicioVigencia() {
        return inicioVigencia;
    }

    public void setInicioVigencia(LocalDate inicioVigencia) {
        this.inicioVigencia = inicioVigencia;
    }

    public LocalDate getFinalVigencia() {
        return finalVigencia;
    }

    public void setFinalVigencia(LocalDate finalVigencia) {
        this.finalVigencia = finalVigencia;
    }

    // Alterando getter e setter para 'gtin'
    public String getGtin() {
        return gtin;
    }

    public void setGtin(String gtin) {
        this.gtin = gtin;
    }

    public String getCest() {
        return cest;
    }

    public void setCest(String cest) {
        this.cest = cest;
    }

    public String getNcm() {
        return ncm;
    }

    public void setNcm(String ncm) {
        this.ncm = ncm;
    }
}
