package online.muhammadali.done.api.data.source

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class MongoTaskEntity(
    @BsonId
    val id: ObjectId? = null,
    val innerID: Int,
    val title: String,
    val description: String,
    val time: String
)
