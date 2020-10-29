package com.m2r.ktask.dto

import java.io.Serializable

data class TaskDTO(

    var id: Long? = null,

    var name: String? = null,

    var state: String? = null

) : Serializable {

    constructor(): this(null, null, null)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TaskDTO) return false
        return id == other.id
    }

    override fun hashCode() = 31

}
