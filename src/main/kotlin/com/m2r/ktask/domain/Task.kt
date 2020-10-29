package com.m2r.ktask.domain

import javax.persistence.*

@Entity
@Table(name = "TASK")
data class Task(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        @Column(name = "NAME", nullable = false, length = 60)
        var name: String? = null,

        @Column(name = "STATE", nullable = false, length = 1)
        var state: String? = null

) : BaseModel() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Task) return false

        return id != null && other.id != null && id == other.id
    }

    override fun hashCode() = 31

    override fun toString() = "Task{" +
            "id=$id" +
            ", name='$name'" +
            ", state='$state'" +
            "}"

    companion object {
        private const val serialVersionUID = 1L
    }

}