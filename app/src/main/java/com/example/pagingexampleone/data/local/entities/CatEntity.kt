package com.example.pagingexampleone.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pagingexampleone.core.mappers.EntityMapper
import com.example.pagingexampleone.core.models.Cat
import com.example.pagingexampleone.core.models.DataModel

@Entity(tableName = "cats")
data class CatEntity(
    @PrimaryKey
    @ColumnInfo("cat_id")
    override val id: String,
    @ColumnInfo("cat_url")
    val url: String
) : DataModel(), EntityMapper<CatEntity, Cat> {
    override fun mapFromEntity(entity: CatEntity) =
        Cat(
            id = id,
            url = url
        )

    override fun mapToEntity(domain: Cat) =
        CatEntity(
            id = id,
            url = url
        )
}
