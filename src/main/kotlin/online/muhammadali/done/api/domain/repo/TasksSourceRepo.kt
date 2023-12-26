package online.muhammadali.done.api.domain.repo

import kotlinx.coroutines.flow.Flow
import online.muhammadali.done.api.domain.entities.Task
import online.muhammadali.done.api.errors.Result

interface TasksSourceRepo {

    fun addNewTask(task: Task): Result<Unit>
    fun updateTask(task: Task): Result<Unit>
    fun deleteTask(task: Task): Result<Unit>
    fun getTask(id: Int): Result<Flow<Task>>
    fun getAllTasks(): Result<List<Flow<Task>>>
    fun deleteAllTasks(): Result<Unit>
}