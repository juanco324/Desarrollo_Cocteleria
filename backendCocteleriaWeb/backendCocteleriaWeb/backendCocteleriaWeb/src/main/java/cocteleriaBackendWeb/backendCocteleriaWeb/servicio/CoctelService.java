package cocteleriaBackendWeb.backendCocteleriaWeb.servicio;

import cocteleriaBackendWeb.backendCocteleriaWeb.entidades.Cocteles;
import cocteleriaBackendWeb.backendCocteleriaWeb.reppositorio.CoctelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoctelService {

    @Autowired
    private final CoctelRepository repo;

    public CoctelService(CoctelRepository repo) {
        this.repo = repo;
    }

    public List<Cocteles> listar() {
        return repo.findAll();
    }

    public Cocteles guardar(Cocteles c) {
        return repo.save(c);
    }
}
