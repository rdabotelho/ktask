package com.m2r.ktask.mapper

import com.m2r.ktask.domain.Task
import com.m2r.ktask.dto.TaskDTO
import org.mapstruct.Mapper
import org.springframework.stereotype.Component

@Component
@Mapper(componentModel = "spring", uses = [])
interface TaskMapper :
    EntityMapper<TaskDTO, Task> {
}
