package com.kings.okdhvi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.SQLInsert;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="Usuario")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idUsuario")
public class Usuario implements Serializable, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 100)
    private String senha;

    @Column(length = 14)
    private String telefone;

    @Column(nullable = false, length = 11, unique = true)
    private String cpf;

    @Column
    private Boolean notificacoesPorEmail = false;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name="id_foto_perfil")
    private Imagem fotoDePerfil;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(nullable = false)
    private Date dataDeNascimento;

    @OneToOne
    private PedidoExclusaoConta pedidoExclusao;

    @Column
    @Enumerated(EnumType.STRING)
    public EstadoDaConta getEstadoDaConta() {
        return estadoDaConta;
    }

    public void setEstadoDaConta(EstadoDaConta estadoDaConta) {
        this.estadoDaConta = estadoDaConta;
    }

    private EstadoDaConta estadoDaConta;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<SimpleGrantedAuthority> roles = new ArrayList<>();
        if(estadoDaConta == EstadoDaConta.ADMINISTRADOR) {
            roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            roles.add(new SimpleGrantedAuthority("ROLE_MODER"));
            roles.add(new SimpleGrantedAuthority("ROLE_PADRAO"));
            roles.add(new SimpleGrantedAuthority("ROLE_ESPEC"));
        }
        if(estadoDaConta == EstadoDaConta.MODERADOR) {
            roles.add(new SimpleGrantedAuthority("ROLE_MODER"));
            roles.add(new SimpleGrantedAuthority("ROLE_PADRAO"));
        }
        if(estadoDaConta == EstadoDaConta.ESPECIALISTA) {
            roles.add(new SimpleGrantedAuthority("ROLE_ESPEC"));
            roles.add(new SimpleGrantedAuthority("ROLE_PADRAO"));
        }
        if(estadoDaConta == EstadoDaConta.PADRAO) {
            roles.add(new SimpleGrantedAuthority("ROLE_PADRAO"));
        }
        if(estadoDaConta == EstadoDaConta.SUSPENSO) {
            roles.add(new SimpleGrantedAuthority("ROLE_SUSPEN"));
        }
        return roles;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    public Usuario() {
    }

    public Usuario(String nome, String senha, String telefone, String cpf, String eMail, Date dataDeNascimento) {
        this.nome = nome;
        this.senha = senha;
        this.telefone = telefone;
        this.cpf = cpf;
        this.email = eMail;
        this.dataDeNascimento = dataDeNascimento;
    }

    public Usuario(String nome, String senha, String telefone, String cpf, String eMail, Date dataDeNascimento, EstadoDaConta edc) {
        this.nome = nome;
        this.senha = senha;
        this.telefone = telefone;
        this.cpf = cpf;
        this.email = eMail;
        this.dataDeNascimento = dataDeNascimento;
        this.estadoDaConta = edc;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(Date dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    private static final long serialVersionId = 1L;

    private boolean oculto;

    public boolean isOculto() {
        return oculto;
    }

    public void setOculto(boolean oculto) {
        this.oculto = oculto;
    }

    public Imagem getFotoDePerfil() {
        return fotoDePerfil;
    }

    public void setFotoDePerfil(Imagem fotoDePerfil) {
        this.fotoDePerfil = fotoDePerfil;
    }

    public PedidoExclusaoConta getPedidoExclusao() {
        return pedidoExclusao;
    }

    public void setPedidoExclusao(PedidoExclusaoConta pedidoExclusao) {
        this.pedidoExclusao = pedidoExclusao;
    }

    public Boolean getNotificacoesPorEmail() {
        return notificacoesPorEmail;
    }

    public void setNotificacoesPorEmail(Boolean notificacoesPorEmail) {
        this.notificacoesPorEmail = notificacoesPorEmail;
    }

    public String gerarNome() {
        return "[" + getIdUsuario() + "] " + getNome();
    }
}
