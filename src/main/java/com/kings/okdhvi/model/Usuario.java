package com.kings.okdhvi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
public class Usuario implements Serializable, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @OneToOne()
    private Imagem imagem;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 100)
    private String senha;

    @Column(nullable = false, length = 14)
    private String telefone;

    @Column(nullable = false, length = 11, unique = true)
    private String cpf;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(nullable = false)
    private Date dataDeNascimento;

    @JsonIgnore()
    @ManyToMany(mappedBy = "revisor")
    private List<Postagem> revisoes = new ArrayList<>();

    public EstadoDaContaEnum getEstadoDaConta() {
        return estadoDaConta;
    }

    public void setEstadoDaConta(EstadoDaContaEnum estadoDaConta) {
        this.estadoDaConta = estadoDaConta;
    }

    private EstadoDaContaEnum estadoDaConta;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<SimpleGrantedAuthority> roles = new ArrayList<>();
        if(estadoDaConta == EstadoDaContaEnum.ADMNISTRADOR) {
            roles.add(new SimpleGrantedAuthority("ADMIN"));
            roles.add(new SimpleGrantedAuthority("PADRAO"));
        }
        if(estadoDaConta == EstadoDaContaEnum.MODERADOR) {
            roles.add(new SimpleGrantedAuthority("MODER"));
            roles.add(new SimpleGrantedAuthority("PADRAO"));
        }
        if(estadoDaConta == EstadoDaContaEnum.ESPECIALISTA) {
            roles.add(new SimpleGrantedAuthority("ESPEC"));
            roles.add(new SimpleGrantedAuthority("PADRAO"));
        }
        if(estadoDaConta == EstadoDaContaEnum.PADRAO) {
            roles.add(new SimpleGrantedAuthority("PADRAO"));
        }
        if(estadoDaConta == EstadoDaContaEnum.SUSPENSO) {
            roles.add(new SimpleGrantedAuthority("SUSPEN"));
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

    public Imagem getImagem() {
        return imagem;
    }

    public void setImagem(Imagem imagem) {
        this.imagem = imagem;
    }

    public List<Postagem> getRevisoes() {
        return revisoes;
    }

    public void setRevisoes(List<Postagem> revisoes) {
        this.revisoes = revisoes;
    }

    private boolean oculto;

    public boolean isOculto() {
        return oculto;
    }

    public void setOculto(boolean oculto) {
        this.oculto = oculto;
    }

}
