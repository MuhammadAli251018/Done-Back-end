package online.muhammadali.done.api.data.repo

import com.google.common.truth.Truth.assertThat
import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import online.muhammadali.done.api.TestStructure
import online.muhammadali.done.api.data.source.MongoTaskEntity
import online.muhammadali.done.api.domain.entities.TaskEntity
import online.muhammadali.done.api.errors.Result
import org.junit.Test

class TasksMongoDBSourceTestStructure : TestStructure<TasksMongoDBSource>(){

    private val testTask = TaskEntity(
        id = 0,
        title = "test task",
        description = "test task description",
        time = "task time"
    )

    private lateinit var client: MongoClient
    private lateinit var db: MongoDatabase

    @Test
    fun `success if add new task`() = runTestAsync {
       try{
           val id = addNewTask(testTask).first().getOrNull()
           println("id: ${id}")
           val result = getTask(testTask.id).first().getOrThrow()
           assertThat(result).isEqualTo(testTask)
        } catch (e: Exception) {
            println("DB_Error: " + e.message)
        }

    }

    @Test
    fun `success if update task`() = runTestAsync {
        val id = addNewTask(testTask).first().getOrNull()
        println("id: ${id}")
        val updatedTask = testTask.copy(title = "edit title")
        updateTask(updatedTask)
        val result = getTask(testTask.id).first().getOrThrow()

        assertThat(result).isEqualTo(testTask)
    }

    @Test
    fun `success if deleteTask`() = runTestAsync {
        for (i in 0 .. 4)
            addNewTask(testTask.copy(id = i)).first()

        deleteTask(testTask).first()

        val result = getAllTasks().first().getOrThrow()

        assertThat(result).doesNotContain(testTask)
    }

    @Test
    fun `success if get all tasks`() = runTestAsync{
        val tasks = mutableListOf<TaskEntity>()
        for (i in 0 .. 4) {
            val task = testTask.copy(id = i)
            tasks.add(task)
            addNewTask(task).first()
        }

        val result = getAllTasks().first().getOrThrow()
        assertThat(result).isEqualTo(tasks)
    }

    @Test
    fun `success if deletes tasks`() = runTestAsync {
        val tasks = mutableListOf<TaskEntity>()
        for (i in 0 .. 4) {
            val task = testTask.copy(id = i)
            tasks.add(task)
            addNewTask(task).first()
        }

        deleteAllTasks().first()

        val result = getAllTasks().first().getOrThrow()
        assertThat(result).isEmpty()
    }

    override fun beforeAll() {
        client = MongoClient.create("mongodb://localhost:27017")
        db = client.getDatabase("test")
        println("DB_LOG: Connected to DB")
    }

    override fun beforeEach() {

    }

    override fun initialize(): TasksMongoDBSource {
        return TasksMongoDBSource(
            Result.success(db.getCollection<MongoTaskEntity>("tasks$id"))
        ).also { id ++ }
    }

    override fun afterTest() {

    }
}
var id = 0
