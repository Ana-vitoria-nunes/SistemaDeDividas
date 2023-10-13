package org.example.model;

import java.math.BigDecimal;
import java.util.Date;

public class CartaoModel {
    private Long id;
    private Long idCliente;
    private String nomeRemetente;
    private String numeroCartao;
    private String cvv;
    private Date dataValidade;
    private BigDecimal limiteCartao;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getNomeRemetente() {
        return nomeRemetente;
    }

    public void setNomeRemetente(String nomeRemetente) {
        this.nomeRemetente = nomeRemetente;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public Date getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(Date dataValidade) {
        this.dataValidade = dataValidade;
    }

    public BigDecimal getLimiteCartao() {
        return limiteCartao;
    }

    public void setLimiteCartao(BigDecimal limiteCartao) {
        this.limiteCartao = limiteCartao;
    }

}
