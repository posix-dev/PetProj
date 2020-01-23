package me.uptop.network.converter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.util.Date

class UtcTimeDateConverter : TypeAdapter<Date?>() {

    override fun write(out: JsonWriter?, value: Date?) {
        convertToLong(value)?.let { out?.value(it) }
    }

    override fun read(input: JsonReader?): Date? {
        return input?.nextLong()?.let { Date(it) }
    }

    private fun convertToLong(date: Date?): Long? = date?.time
}