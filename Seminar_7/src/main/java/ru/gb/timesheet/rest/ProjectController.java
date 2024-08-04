package ru.gb.timesheet.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.timesheet.model.Project;
import ru.gb.timesheet.model.Timesheet;
import ru.gb.timesheet.service.ProjectService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@Tag(name = "Контроллер проектов", description = "API для взаимодействия с сущностью проекты")
@RequestMapping("/projects")
public class ProjectController {

  private final ProjectService service;

  public ProjectController(ProjectService service) {
    this.service = service;
  }
  @Operation(description = "Позволяет получить проект по идентефикатору")
  @GetMapping("/{id}")
  public ResponseEntity<Project> get(@PathVariable Long id) {
    return service.findById(id)
      .map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.notFound().build());
  }
  @Operation(description = "Позволяет получить заметки по идентефикатору проекта")
  @GetMapping("/{id}/timesheets")
  public ResponseEntity<List<Timesheet>> getTimesheets(@PathVariable Long id) {
    try {
      return ResponseEntity.ok(service.getTimesheets(id));
    } catch (NoSuchElementException e) {
      return ResponseEntity.notFound().build();
    }
  }
  @Operation(description = "Позволяет получить все проекты")
  @GetMapping
  public ResponseEntity<List<Project>> getAll() {
   return ResponseEntity.ok(service.findAll());}

  @Operation(description = "Позволяет создать проект")
  @PostMapping
  public ResponseEntity<Project> create(@RequestBody Project project) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.create(project));
  }

  @Operation(description = "Позволяет удалить проект по идентефикатору")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
