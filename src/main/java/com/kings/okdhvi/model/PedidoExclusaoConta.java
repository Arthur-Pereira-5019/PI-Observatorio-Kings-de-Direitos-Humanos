package com.kings.okdhvi.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Date;

@Entity
@Table
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class PedidoExclusaoConta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @JsonIdentityReference(alwaysAsId = true)
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

    public PedidoExclusaoConta() {
    }
}
