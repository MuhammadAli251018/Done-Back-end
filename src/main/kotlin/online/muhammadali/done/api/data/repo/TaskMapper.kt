package online.muhammadali.done.api.data.repo

import online.muhammadali.done.api.data.source.MongoTaskEntity
import online.muhammadali.done.api.domain.entities.TaskEntity


fun MongoTaskEntity.toTask(): TaskEntity {
    return TaskEntity(
        id = innerID,
        title = title,
        description = description,
        time = time
    )
}

fun TaskEntity.toMongoTask(): MongoTaskEntity {
    return MongoTaskEntity(
        innerID = id,
        title = title,
        description = description,
        time = time
    )
}
