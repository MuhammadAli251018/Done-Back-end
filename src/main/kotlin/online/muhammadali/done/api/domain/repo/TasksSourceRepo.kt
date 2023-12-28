package online.muhammadali.done.api.domain.repo

import kotlinx.coroutines.flow.Flow
import online.muhammadali.done.api.domain.entities.TaskEntity
import online.muhammadali.done.api.errors.Result

interface TasksSourceRepo {

    fun addNewTask(task: TaskEntity): Flow<Result<Int>>
    fun updateTask(task: TaskEntity): Flow<Result<Unit>>
    fun deleteTask(task: TaskEntity): Flow<Result<Unit>>
    fun getTask(id: Int): Flow<Result<TaskEntity>>
    fun getAllTasks(): Flow<Result<List<TaskEntity>>>
    fun deleteAllTasks(): Flow<Result<Unit>>
}