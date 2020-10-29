package com.m2r.ktask.web.rest

import com.m2r.ktask.dto.TaskDTO
import com.m2r.ktask.mapper.TaskMapper
import com.m2r.ktask.repository.TaskRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.stream.Collectors

@RestController
@RequestMapping("/api")
class TaskResource(
        private val taskRepository: TaskRepository,
        private val taskMapper: TaskMapper
) {

    private val log = LoggerFactory.getLogger(javaClass)


    @PostMapping("/tasks")
    fun createTask(@RequestBody taskDTO: TaskDTO): ResponseEntity<TaskDTO> {
        log.debug("REST request to save Task : $taskDTO")
        if (taskDTO.id != null) {
            throw RuntimeException("A new Task cannot already have an ID")
        }
        val result = taskRepository.save(taskMapper.toEntity(taskDTO))
        return ResponseEntity.created(URI("/api/tasks/${result.id}"))
            .body(taskMapper.toDto(result))
    }

    @PutMapping("/tasks")
    fun updateTask(@RequestBody taskDTO: TaskDTO): ResponseEntity<TaskDTO> {
        log.debug("REST request to update Task : $taskDTO")
        if (taskDTO.id == null) {
            throw RuntimeException("Invalid id")
        }
        return taskRepository.findById(taskDTO.id!!)
                .map {
                    val result = taskRepository.save(taskMapper.toEntity(taskDTO))
                    ResponseEntity.ok().body(taskMapper.toDto(result))
                }
                .orElse(ResponseEntity.notFound().build())
    }

    @GetMapping("/tasks")
    fun getAllTasks(): MutableList<TaskDTO> {
        log.debug("REST request to get all Tasks")
        return taskRepository.findAll()
                .stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList())
    }

    @GetMapping("/tasks/{id}")
    fun getTask(@PathVariable id: Long): ResponseEntity<TaskDTO> {
        log.debug("REST request to get Task : $id")
        return taskRepository.findById(id)
                .map {
                    ResponseEntity.ok().body(taskMapper.toDto(it))
                }
                .orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/tasks/{id}")
    fun deleteTask(@PathVariable id: Long): ResponseEntity<Void> {
        log.debug("REST request to delete Task : $id")
        taskRepository.deleteById(id)
        return ResponseEntity.noContent().build()
    }

}
