package com.elessa.immigrant.model;

import com.elessa.immigrant.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ServiceRecord> serviceRecords = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // ===== MÉTODOS OBRIGATÓRIOS DO UserDetails =====

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Converte o papel (ADMIN, VOLUNTEER) em uma autorização do Spring Security
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        // O Spring Security usa este método para pegar o identificador do usuário
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Sua conta nunca expira
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Sua conta nunca é bloqueada
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Suas credenciais nunca expiram
    }

    @Override
    public boolean isEnabled() {
        return isActive != null && isActive; // Usuário ativo ou não
    }
}