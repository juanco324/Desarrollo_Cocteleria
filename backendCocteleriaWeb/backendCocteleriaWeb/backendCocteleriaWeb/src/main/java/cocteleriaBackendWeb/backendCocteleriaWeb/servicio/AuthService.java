package cocteleriaBackendWeb.backendCocteleriaWeb.servicio;

import cocteleriaBackendWeb.backendCocteleriaWeb.Dto.AuthRequest;
import cocteleriaBackendWeb.backendCocteleriaWeb.Dto.AuthResponse;
import cocteleriaBackendWeb.backendCocteleriaWeb.entidades.Usuario;
import cocteleriaBackendWeb.backendCocteleriaWeb.reppositorio.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final PasswordEncoder encoder;
    private final UsuarioRepository usuarioRepository; // ✅ Agregar esto

    public AuthService(PasswordEncoder encoder, UsuarioRepository usuarioRepository) {
        this.encoder = encoder;
        this.usuarioRepository = usuarioRepository; // ✅ Agregar esto
    }

    // 🔹 REGISTER
    public AuthResponse register(Usuario usuario) {
        usuario.setContraseña(encoder.encode(usuario.getContraseña()));

        if (usuario.getRol() == null) {
            usuario.setRol("EMPLEADO");
        }

        usuarioRepository.save(usuario); // ✅ Guardar en MySQL

        return new AuthResponse("TOKEN_FAKE", usuario.getRol());
    }

    // 🔹 LOGIN
    public AuthResponse login(AuthRequest request) {
        Usuario user = usuarioRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new RuntimeException("Usuario no existe"));

        if (!encoder.matches(request.getContraseña(), user.getContraseña())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return new AuthResponse("TOKEN_FAKE", user.getRol());
    }
}