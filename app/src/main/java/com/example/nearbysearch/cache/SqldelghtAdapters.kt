package com.example.nearbysearch.cache

import com.example.nearbysearch.network.model.search_result.*
import com.kpstv.bindings.AutoGenerateSQLDelightAdapters
import com.kpstv.bindings.ConverterType
import com.squareup.sqldelight.ColumnAdapter

@AutoGenerateSQLDelightAdapters(using = ConverterType.KOTLIN_SERIALIZATION)
interface SqldelghtAdapters{
    fun addressConverter() : ColumnAdapter<Address,String>
    fun entryPointsConverter() : ColumnAdapter<List<EntryPoint>,String>
    fun poiConverter(): ColumnAdapter<Poi, String>
    fun viewPortConverter() : ColumnAdapter<Viewport, String>
}