package ng.com.techdepo.kotlincodelabs.mappers

import ng.com.techdepo.kotlincodelabs.database.MarsPropertyEntity
import ng.com.techdepo.kotlincodelabs.network.MarsProperty

class EntityMapper()

    /**
     * Map DatabaseVideos to domain entities
     */
    fun List<MarsPropertyEntity>.toDomainModel(): List<MarsProperty> {
        return map {
            MarsProperty(
                    it.id,
                    it.imgSrcUrl,
                    it.type,
                    it.price
            )
        }
    }

    fun List<MarsProperty>.toDatabaseModel(): List<MarsPropertyEntity> {
        return map {
            MarsPropertyEntity(
                    it.id,
                    it.imgSrcUrl,
                    it.type,
                    it.price
            )
        }
    }
