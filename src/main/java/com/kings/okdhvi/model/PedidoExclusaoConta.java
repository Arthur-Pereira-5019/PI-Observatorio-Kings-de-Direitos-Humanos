package com.kings.okdhvi.model;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.Date;

@Entity
public class PedidoExclusaoConta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Usuario usuarioPedido;

    @Column
    private Date dataPedido;

    public PedidoExclusaoConta(Usuario usuarioPedido) {
        this.usuarioPedido = usuarioPedido;
        this.dataPedido = Date.from(Instant.now());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuarioPedido() {
        return usuarioPedido;
    }

    public void setUsuarioPedido(Usuario usuarioPedido) {
        this.usuarioPedido = usuarioPedido;
    }

    public Date getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(Date dataPedido) {
        this.dataPedido = dataPedido;
    }
}
