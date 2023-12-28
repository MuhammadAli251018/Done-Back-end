package online.muhammadali.done.api.data.repo

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import online.muhammadali.done.api.TestHelper
import online.muhammadali.done.api.domain.entities.TaskEntity
import org.junit.Test

class TasksMongoDBSourceTestHelper : TestHelper<TasksMongoDBSource>(){

    private val testTask = TaskEntity(
        id = 0,
        title = "test task",
        description = "test task description",
        time = "task time"
    )

    @Test
    fun `success if add new task`() = runTestAsync {
        addNewTask(testTask);

        val result = getTask(testTask.id).first().getOrThrow()
        assertThat(result).isEqualTo(testTask)
    }

    @Test
    fun `success if update task`() = runTestAsync {
        addNewTask(testTask)
        val updatedTask = testTask.copy(title = "edit title")
        updateTask(updatedTask)
        val result = getTask(testTask.id).first().getOrThrow()

        assertThat(result).isEqualTo(testTask)
    }

    @Test
    fun `success if deleteTask`() = runTestAsync {
        for (i in 0 .. 4)
            addNewTask(testTask.copy(id = i));

        deleteTask(testTask)

        val result = getAllTasks().first().getOrThrow()
        assertThat(result).doesNotContain(testTask)
    }

    @Test
    fun `success if get all tasks`() = runTestAsync{
        val tasks = mutableListOf<TaskEntity>()
        for (i in 0 .. 4) {
            val task = testTask.copy(id = i)
            tasks.add(task)
            addNewTask(task)
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
            addNewTask(task)
        }

        val result = getAllTasks().first().getOrThrow()
        assertThat(result).isEmpty()
    }

    override fun beforeTest() {

    }

    override fun initialize(): TasksMongoDBSource {
        return TasksMongoDBSource()
    }

    override fun afterTest() {

    }
}