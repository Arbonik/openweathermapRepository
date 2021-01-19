package com.castprogramms.openweathermap.database.data.map

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
class Trek() {
    @PrimaryKey(autoGenerate = true)

    var trekId : Long = 0

    var name = ""
    var description = ""
    @Relation(
        parentColumn = "trekId",
        entityColumn = "id"
    )
    var locations = mutableListOf<MyLocation>()

    constructor(
        name: String,
        description: String,
        locations: MutableList<MyLocation>
    ):this(){
        this.name = name
        this.description = description
        this.locations = locations
    }
}