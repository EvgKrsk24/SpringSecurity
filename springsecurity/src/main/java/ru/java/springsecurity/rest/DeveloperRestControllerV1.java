package ru.java.springsecurity.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.java.springsecurity.model.Developer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/developers")
public class DeveloperRestControllerV1 { // вместо бд
    private List<Developer> DEVELOPERS = Stream.of(
            new Developer(1L, "Ilya", "Ivanov"),
            new Developer(2L, "Semen", "Sergeev"),
            new Developer(3L, "Kirill", "Petrov")
    ).collect(Collectors.toList());

    @GetMapping //возвращает лист с разрабами
    public List<Developer> getAll() {return DEVELOPERS;}

    @GetMapping("/{id}") // возврат по id
    @PreAuthorize("hasAnyAuthority('developer:write')") // выдаем права на авторизацию согласно Role
    public Developer getById(@PathVariable Long id) {
        return DEVELOPERS.stream().filter(developer -> developer.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('developer:write')") // выдаем права на авторизацию согласно Role
    public Developer create(@RequestBody Developer developer) {// добавление разработчика
        this.DEVELOPERS.add(developer);
        return developer;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('developer:write')") // выдаем права на авторизацию согласно Role
    public void deleteById(@PathVariable Long id) { //удаление разработчика
    this.DEVELOPERS.removeIf(developer -> developer.getId().equals("id"));
    }

}