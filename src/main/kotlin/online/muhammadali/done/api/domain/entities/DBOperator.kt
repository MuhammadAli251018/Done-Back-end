package online.muhammadali.done.api.domain.entities

import io.ktor.utils.io.errors.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import online.muhammadali.done.api.errors.Result
import kotlin.coroutines.coroutineContext

abstract class DBOperator <Source> {

    protected abstract val source: Result<Source>

    protected inline fun <T> runDBOperation(
        crossinline operation: suspend (Source) -> Result<T>
    ): Flow<Result<T>> {
        val result = source.getOrNull()
        return if (result != null) {
            try {
                flow {
                    emit(operation(result))
                }
            } catch (e: Exception) {
                flow { emit(Result.failure(IOException("DB_EXCEPTION: Data source is null"))) }
            }
        }
        else {
            flow { emit(Result.failure(IOException("DB_EXCEPTION: Data source is null")))}
        }
    }
}