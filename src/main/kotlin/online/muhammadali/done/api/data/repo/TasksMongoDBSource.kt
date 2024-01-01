package online.muhammadali.done.api.data.repo

import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoCollection
import kotlinx.coroutines.flow.*
import online.muhammadali.done.api.data.source.MongoTaskEntity
import online.muhammadali.done.api.domain.entities.DBOperator
import online.muhammadali.done.api.domain.entities.TaskEntity
import online.muhammadali.done.api.domain.repo.TasksSourceRepo
import online.muhammadali.done.api.errors.Result

class TasksMongoDBSource(
    override val source: Result<MongoCollection<MongoTaskEntity>>
) : DBOperator<MongoCollection<MongoTaskEntity>>(), TasksSourceRepo {

    override fun addNewTask(task: TaskEntity): Flow<Result<Int>> = runDBOperation {
        val result = it.insertOne(task.toMongoTask())
        return@runDBOperation if (result.wasAcknowledged())
            Result.success(task.id)
        else
            Result.failure(Exception("Todo: DB_Error: Can't add Task"))
    }

    override fun updateTask(task: TaskEntity): Flow<Result<Unit>> = runDBOperation{
        val filter = Filters.eq("innerID", task.id)
        val result = it.replaceOne(filter, task.toMongoTask()).wasAcknowledged()

        return@runDBOperation if (result)
            Result.success(Unit)
        else
            Result.failure(Exception())
    }

    override fun deleteTask(task: TaskEntity): Flow<Result<Unit>> = runDBOperation {
        val filter = Filters.eq("innerID", task.id)
        val result = it.deleteOne(filter).wasAcknowledged()

        return@runDBOperation if (result)
            Result.success(Unit)
        else
            Result.failure(Exception())
    }

    override fun getTask(id: Int): Flow<Result<TaskEntity>> = runDBOperation {
        val filter = Filters.eq("innerID", id)
        val task = it.find(filter).firstOrNull()

        return@runDBOperation if (task != null)
            Result.success(task.toTask())
        else
            Result.failure(Exception("Todo"))
    }

    override fun getAllTasks(): Flow<Result<List<TaskEntity>>> = runDBOperation {
        try {
            val tasks = it.find().toList()
            return@runDBOperation Result.success(tasks.map { it.toTask() })
        }
        catch (e: NoSuchElementException) {
            return@runDBOperation Result.failure(e)
        }
    }

    override fun deleteAllTasks(): Flow<Result<Unit>> = runDBOperation{
        val result = it.deleteMany(Filters.empty()).wasAcknowledged()

        return@runDBOperation if (result)
            Result.success(Unit)
        else
            Result.failure(Exception())
    }
}