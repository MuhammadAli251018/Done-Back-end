package online.muhammadali.done.api.domain.entities

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import online.muhammadali.done.api.errors.Result

abstract class DBOperator <Source> {

    protected abstract val source: Result<Source>

    protected inline fun <T> runDBOperation(
        crossinline operation: suspend (Source) -> Result<T>
    ): Flow<Result<T>> {
        val result = source.getOrNull()
        return if (result != null) {
            flow { emit(operation(result)) }
        }
        else {
            flow { emit(Result.failure(Exception()))}
        }
    }
}