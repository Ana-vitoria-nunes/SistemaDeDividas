package org.example.model;

import java.math.BigDecimal;

public class PagamentoModel {
    private Long idPagamento;
    private Long idCartao;
    private int parcelas;
    private BigDecimal valorTotalParcela;
    private BigDecimal valorTotalEmprestimo;
    private String status;

    public Long getIdPagamento() {
        return idPagamento;
    }

    public void setIdPagamento(Long idPagamento) {
        this.idPagamento = idPagamento;
    }

    public Long getIdCartao() {
        return idCartao;
    }

    public void setIdCartao(Long idCartao) {
        this.idCartao = idCartao;
    }

    public int getParcelas() {
        return parcelas;
    }

    public void setParcelas(int parcelas) {
        this.parcelas = parcelas;
    }

    public BigDecimal getValorTotalParcela() {
        return valorTotalParcela;
    }

    public void setValorTotalParcela(BigDecimal valorTotalParcela) {
        this.valorTotalParcela = valorTotalParcela;
    }

    public BigDecimal getValorTotalEmprestimo() {
        return valorTotalEmprestimo;
    }

    public void setValorTotalEmprestimo(BigDecimal valorTotalEmprestimo) {
        this.valorTotalEmprestimo = valorTotalEmprestimo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
